package flipkart.hackathon.com.flipkarthackathonapp.data.tables;

import android.content.ContentValues;

import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;

/**
 * Created by webyog on 06/06/15.
 */
public class TweetsTable {
    public static final String TABLE_NAME = "tweets_table";

    //columns
    public static final String CITY = "city";
    public static final String CATEGORY = "category";
    public static final String TWEET_ID = "tweet_id";
    public static final String TWEET_TEXT = "tweet_text";

    //Create Table Query
    public static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + TWEET_ID + " TEXT PRIMARY KEY,"
                    + CITY + " TEXT," +
                    CATEGORY + " TEXT," +
                    TWEET_TEXT + " TEXT);";

    public static ContentValues getContentValueObject(Tweets action) {
        ContentValues cv = new ContentValues();
        cv.put(CITY, action.getCity().getName());
        cv.put(CATEGORY, action.getCategory().getName());
        cv.put(TWEET_ID, action.getTweetId());
        cv.put(TWEET_TEXT, action.getTweetText());
        return cv;
    }
}
