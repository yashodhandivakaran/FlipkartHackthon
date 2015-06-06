package flipkart.hackathon.com.flipkarthackathonapp.data.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.ResultSet;
import java.sql.SQLException;

import flipkart.hackathon.com.flipkarthackathonapp.data.tables.CitiesTable;

/**
 * Created by webyog on 06/06/15.
 */
public class Cities implements Parcelable {

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

    public Cities(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndexOrThrow(CitiesTable.NAME));
        this.count = cursor.getInt(cursor.getColumnIndexOrThrow(CitiesTable.COUNT));
    }


    public Cities(ResultSet result) {
        try {
            this.name = result.getString(CitiesTable.NAME);
            this.count = result.getInt(CitiesTable.COUNT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Cities(){}

    public Cities(Parcel parcel){
        this.name = parcel.readString();
        this.count = parcel.readInt();
    }


    public Cities(String name) {
        this.name = name;
    }

    public Cities(String name,int count) {
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
        parcel.writeInt(count);

    }

    public static Creator<Cities> CREATOR = new Creator<Cities>() {

        @Override
        public Cities createFromParcel(Parcel source) {
            return new Cities(source);
        }

        @Override
        public Cities[] newArray(int size) {
            return new Cities[size];
        }
    };
}
