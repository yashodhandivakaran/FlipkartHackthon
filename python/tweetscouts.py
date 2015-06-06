__author__ = 'anurag'


from twitter import Twitter, OAuth, TwitterHTTPError
import sqlite3
import time
import unirest
import json


OAUTH_TOKEN = "30196809-L9KYgrXbcJFRZt86kL81dqbChNSJ89ICpZDdBs3V8"
OAUTH_SECRET = "KudKz1c5XMAZTHfFZ6TqyEVl4UiFruZayJ0BQuQKc6B3y"
CONSUMER_KEY = "c2ogYBGP1bN5LeeaZr3ig"
CONSUMER_SECRET = "7sTNH7a5i4gejudPI0S9BfiDcPgBS4Oc3uDiUqh3fQ"
TWITTER_HANDLE = "borax12"


ALREADY_FOLLOWED_FILE = "already-followed.csv"

t = Twitter(auth=OAuth(OAUTH_TOKEN, OAUTH_SECRET,
            CONSUMER_KEY, CONSUMER_SECRET))


top_cities = [["mumbai", "mum", "maharashtra"], ["bangalore", "bangaluru", "benga"], ["delhi", "north"],
              ["pune", "aurangabad"], ["chennai", "nadu"], ["kolkata", "bengal"], ["hyderabad", "andhra"], ["ahmedabad", "gujrat", "gujarat"],
              ["jaipur", "ajmar"], ["surat"], ["lucknow", "noida", "uttar"], ['visakhapatnam', "kerala"]]

counter = {}
last_ts = -1

def search_tweets(count=100, result_type="recent", max_id=-1):
    if max_id == -1:
        return t.search.tweets(q='@flipkart', result_type=result_type, count=count)
    else:
        return t.search.tweets(q='@flipkart', result_type=result_type, count=count, max_id=max_id)


def get_max_id(next_result):
    posts = next_result.split('&')
    for post in posts:
        if 'max_id' in post:
            return post.split('=')[1]

    return -1


def save_to_db(info):
    conn = sqlite3.connect('tweetscouts.db')
    conn.execute("INSERT OR IGNORE INTO tweets (ts, id, location, user_id, tweet, happiness_percentage) VALUES (?, ?, ?, ?, ?, ?)", (info['ts'],
                 info['id'], info['location'], info['user_id'], info['tweet'], info['happiness_percentage']))
    conn.commit()
    conn.close()


def get_happiness(text):
    response = unirest.post("https://community-sentiment.p.mashape.com/text/",
                          headers={
                            "X-Mashape-Key": "kwiYlYjbWpmshdmkl7buIiIVuxeOp1dGwV0jsneR2jDJlTptJG",
                            "Content-Type": "application/x-www-form-urlencoded",
                            "Accept": "application/json"
                          },
                          params={
                            "txt": text
                          }
                        )

    if response.body['result']['sentiment'] == 'Negative' and int(response.body['result']['confidence'][:2]) > 66:
        return 0

    return 1

def process():
    max_last_ts = -1
    try:
        max_id = -1
        count = 0
        while True:
            result = search_tweets(max_id=max_id)
            count += 1
            for tweet in result["statuses"]:
                location = tweet["user"]["location"].lower()

                if tweet['in_reply_to_user_id'] is not None or 'RT ' in tweet['text']:
                    continue
                print location
                for region in top_cities:
                    city = region[0]
                    for loc in region:
                        if loc in location:
                            print "Found", location
                            counter[city] = counter.get(city, 0) + 1
                            info = {'ts': int(time.mktime(time.strptime(tweet['created_at'],"%a %b %d %H:%M:%S +0000 %Y"))),
                                    'id': tweet["id"],
                                    'location': city,
                                    'user_id': tweet["user"]["id"],
                                    'tweet': tweet['text'],
                                    'happiness_percentage': get_happiness(tweet['text'])}

                            if info["ts"] > max_last_ts:
                                max_last_ts = info["ts"]

                            if info["ts"] < last_ts:
                                if last_ts < max_last_ts:
                                    last_ts = max_last_ts
                                return

                            save_to_db(info=info)
                            continue


            if 'next_results' not in result['search_metadata'].keys() or count > 200:
                global last_ts
                if last_ts < max_last_ts:
                    last_ts = max_last_ts
                return


            max_id = get_max_id(result['search_metadata']['next_results'])

            if max_id == -1:
                global last_ts
                if last_ts < max_last_ts:
                    last_ts = max_last_ts
                return



            print "Done", count
    except Exception as e:
        print e


while True:
    process()
    print counter
    time.sleep(600)


