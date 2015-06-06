package flipkart.hackathon.com.flipkarthackathonapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.asynctask.GetCategoriesFromDB;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;


/**
 * A placeholder fragment containing a simple view.
 */
public class CityCategoriesActivityFragment extends Fragment implements GetCategoriesFromDB.RetrivalDoneCategories{
    View mainView;
    HorizontalBarChart barChart;

    public CityCategoriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.city_categories_fragment_main, container, false);
        barChart = (HorizontalBarChart)mainView.findViewById(R.id.barChart);

        barChart.setDescription("");
        GetCategoriesFromDB task = new GetCategoriesFromDB(getActivity(),this,
                new Cities(getActivity().getIntent().getStringExtra("city_name")));
        task.execute();
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void updatedCategories(List<Categories> categories) {

        List<BarEntry> barEntries = new ArrayList<>();
        List<String> xVals = new ArrayList<>();
        int count =0;
        for(Categories category: categories){
            barEntries.add(new BarEntry(category.getCount(),count++));
            xVals.add(category.getName());
        }/*
        BarEntry entry1 = new BarEntry(40.0f,0);
        BarEntry entry2 = new BarEntry(10.0f,1);
        BarEntry entry3 = new BarEntry(30.0f,2);
        BarEntry entry4 = new BarEntry(20.0f,3);
        BarEntry entry5 = new BarEntry(15.0f,4);

        barEntries.add(entry1);
        barEntries.add(entry2);
        barEntries.add(entry3);
        barEntries.add(entry4);
        barEntries.add(entry5);*/

        BarDataSet dataSet = new BarDataSet(barEntries,"No of Tweets");

       /* String [] = new String[5];
        xVals[0] = "Cat 1";
        xVals[1] = "Cat 2";
        xVals[2] = "Cat 3";
        xVals[3] = "Cat 4";
        xVals[4] = "Cat 5";*/
        BarData barData = new BarData(xVals,dataSet);

        barChart.setData(barData);
        barChart.invalidate();

    }
}
