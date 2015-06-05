package flipkart.hackathon.com.flipkarthackathonapp.data.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.ResultSet;
import java.sql.SQLException;

import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CategoriesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.TweetsTable;

/**
 * Created by webyog on 06/06/15.
 */
public class Tweets implements Parcelable {

    private Cities city;
    private Categories category;

    private String tweetId;
    private String tweetText;

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }


    public Tweets(Cities city, Categories category, String tweetId, String tweetText) {
        this.city = city;
        this.category = category;
        this.tweetId = tweetId;
        this.tweetText = tweetText;
    }

    public Tweets(Cursor cursor) {
        this.city = new Cities(cursor.getString(cursor.getColumnIndexOrThrow(TweetsTable.CITY)));
        this.category = new Categories(cursor.getString(cursor.getColumnIndexOrThrow(TweetsTable.CATEGORY)));
        this.tweetId = cursor.getString(cursor.getColumnIndexOrThrow(TweetsTable.TWEET_ID));
        this.tweetText = cursor.getString(cursor.getColumnIndexOrThrow(TweetsTable.TWEET_TEXT));

    }


    public Tweets(ResultSet result) {
        try {
            this.city = new Cities(result.getString(TweetsTable.CITY));
            this.category = new Categories(result.getString(TweetsTable.CATEGORY));
            this.tweetId = result.getString(TweetsTable.TWEET_ID);
            this.tweetText = result.getString(TweetsTable.TWEET_TEXT);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(city, 0);
        parcel.writeParcelable(category, 0);
        parcel.writeString(tweetId);
        parcel.writeString(tweetText);

    }
}
