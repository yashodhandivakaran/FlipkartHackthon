package flipkart.hackathon.com.flipkarthackathonapp.data.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.ResultSet;
import java.sql.SQLException;

import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CategoriesTable;
import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CitiesTable;

/**
 * Created by webyog on 06/06/15.
 */
public class Categories implements Parcelable {

    private String name;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndexOrThrow(CategoriesTable.NAME));
        this.count = cursor.getInt(cursor.getColumnIndexOrThrow(CategoriesTable.COUNT));
    }


    public Categories(ResultSet result) {
        try {
            this.name = result.getString(CategoriesTable.NAME);
            this.count = result.getInt(CategoriesTable.COUNT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Categories() {
    }


    public Categories(String name) {
        this.name = name;
    }

    public Categories(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);

    }
}
