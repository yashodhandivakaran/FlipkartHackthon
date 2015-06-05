package flipkart.hackathon.com.flipkarthackathonapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CategoriesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CitiesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.TweetsTable;

/**
 * Created by webyog on 06/06/15.
 */
public class FKDBWrapper {

    private FKOpenHelper dbHelper;
    private Context mContext;

    public FKDBWrapper(Context context) {
        dbHelper = new FKOpenHelper(context);
        this.mContext = context;
    }

    public void insertCities(List<Cities> cities) {
        for (Cities city : cities) {
            insetCity(city);
        }

    }

    private void insetCity(Cities city) {
        FKOpenHelper dbHelper = new FKOpenHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        db.insertWithOnConflict(CitiesTable.TABLE_NAME, null,
                CitiesTable.getContentValueObject(city),
                SQLiteDatabase.CONFLICT_IGNORE);
        db.endTransaction();

    }

    public void insertCategories(List<Categories> categories) {
        for (Categories category : categories) {
            insertCategory(category);
        }

    }

    private void insertCategory(Categories category) {
        FKOpenHelper dbHelper = new FKOpenHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        db.insertWithOnConflict(CategoriesTable.TABLE_NAME, null,
                CategoriesTable.getContentValueObject(category),
                SQLiteDatabase.CONFLICT_IGNORE);
        db.endTransaction();

    }

    public void insertTweets(List<Tweets> tweets) {
        for (Tweets tweet : tweets) {
            insertTweet(tweet);
        }
    }

    private void insertTweet(Tweets tweet) {
        FKOpenHelper dbHelper = new FKOpenHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        db.insertWithOnConflict(TweetsTable.TABLE_NAME, null,
                TweetsTable.getContentValueObject(tweet),
                SQLiteDatabase.CONFLICT_IGNORE);
        db.endTransaction();
    }

    public List<Cities> getCities() {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.query(CitiesTable.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Cities> citiesList = new ArrayList<Cities>();
        while (cursor != null) {
            citiesList.add(new Cities(cursor));
            cursor.moveToNext();
        }
        return citiesList;
    }

    public List<Categories> getCategories() {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.query(CategoriesTable.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Categories> categoriesesList = new ArrayList<Categories>();
        while (cursor != null) {
            categoriesesList.add(new Categories(cursor));
            cursor.moveToNext();
        }
        return categoriesesList;
    }

    public List<Tweets> getTweets(Cities city, Categories category) {

        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.query(CategoriesTable.TABLE_NAME, new String[]{TweetsTable.CITY,
                        TweetsTable.CATEGORY, TweetsTable.TWEET_ID, TweetsTable.TWEET_TEXT},
                TweetsTable.CITY + "=? AND " + TweetsTable.CATEGORY + "=? "
                , new String[]{city.getName(), category.getName()}, null, null, null);
        cursor.moveToFirst();
        ArrayList<Tweets> tweetsList = new ArrayList<Tweets>();
        while (cursor != null) {
            tweetsList.add(new Tweets(cursor));
            cursor.moveToNext();
        }
        return tweetsList;

    }
}

