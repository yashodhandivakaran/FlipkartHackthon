package flipkart.hackathon.com.flipkarthackathonapp.data.tables;

import android.content.ContentValues;

import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;

/**
 * Created by webyog on 06/06/15.
 */
public class CategoriesTable {
    public static final String TABLE_NAME = "categories_table";

    //columns
    public static final String NAME = "name";
    public static final String CITY = "city";
    public static final String COUNT = "count";

    //Create Table Query
    public static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + NAME + " TEXT PRIMARY KEY," +
                    COUNT+" INT, " +
                    CITY+" TEXT );";

    public static ContentValues getContentValueObject(Categories action) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, action.getName());
        cv.put(COUNT,action.getCount());
        cv.put(CITY,action.getCity());
        return cv;
    }
}

