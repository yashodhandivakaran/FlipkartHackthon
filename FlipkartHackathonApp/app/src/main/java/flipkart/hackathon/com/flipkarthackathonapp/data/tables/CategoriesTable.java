package flipkart.hackathon.com.flipkarthackathonapp.data.tables;

import android.content.ContentValues;

import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;

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
                    + NAME + " TEXT ," +
                    COUNT+" INT, " +
                    CITY+" TEXT ," +
                    "UNIQUE ( "+ NAME+", "+CITY+"));";


    public static ContentValues getContentValueObject(Categories action) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, action.getName());
        cv.put(COUNT,action.getCount());
        cv.put(CITY,action.getCity());
        return cv;
    }

    public static final String TABLE_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}

