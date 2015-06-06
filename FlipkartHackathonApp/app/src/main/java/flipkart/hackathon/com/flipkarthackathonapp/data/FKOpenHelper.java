package flipkart.hackathon.com.flipkarthackathonapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CategoriesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CitiesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.TweetsTable;

/**
 * Created by webyog on 06/06/15.
 */
public class FKOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fk_tweets.db";
    public static final int DATABASE_VERSION = 1;

    public FKOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CitiesTable.TABLE_CREATE);
        db.execSQL(CategoriesTable.TABLE_CREATE);
        db.execSQL(TweetsTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
