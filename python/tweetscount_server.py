__author__ = 'anurag'
import sqlite3
import json
import utils
from flask import Flask
app = Flask(__name__)


classifier = utils.load_classifier('classifier.pickle')

HTML = '''<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

        var data = google.visualization.arrayToDataTable(%s);

        var options = {
          title: 'My Daily Activities'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
  </body>
</html>'''


@app.route("/get")
def hello():
    try:
        sum = 0
        conn = sqlite3.connect('tweetscouts.db')
        res = conn.execute("select group_concat(id), count(*) as cnt, location, group_concat(tweet, '####') from tweets where happiness_percentage=0 group by location order by cnt desc;")
        result = {"list":[]}
        for row in res:
            info = {}
            info["city"] = row[2]
            info["count"] = row[1]
            sum += row[1]
            info["tweet"] = {}

            ids = row[0].split(',')
            tweets = row[3].split('####')

            for i in range(0, len(tweets)):
                print tweets[i], ids[i], classifier.classify(tweets[i])
                category = classifier.classify(tweets[i])
                if category not in info["tweet"].keys():
                    info["tweet"][category] = []

                info["tweet"][category].append({"id": ids[i], "tweet": tweets[i]})

            result["list"].append(info)
        result["total_count"] = sum
        conn.close()
        return json.dumps(result)
    except Exception as e:
        print e


'''@app.route("/gethtml")
def hello():
    try:
        conn = sqlite3.connect('tweetscouts.db')
        res = conn.execute("select group_concat(id), count(*) as cnt, location from tweets where happiness_percentage=0 group by location order by cnt desc;")
        cities = [['Cities', 'Count']]
        for row in res:
            cities.append([row[2], row[1]])
        conn.close()
        html = HTML % (json.dumps(cities))
        return html
    except Exception as e:
        print e'''

if __name__ == "__main__":
    app.run(host= '0.0.0.0')