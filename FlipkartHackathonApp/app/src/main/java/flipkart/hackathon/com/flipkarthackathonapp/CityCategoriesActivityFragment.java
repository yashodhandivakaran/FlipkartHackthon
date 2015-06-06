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


/**
 * A placeholder fragment containing a simple view.
 */
public class CityCategoriesActivityFragment extends Fragment {
    View mainView;
    HorizontalBarChart barChart;

    public CityCategoriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.city_categories_fragment_main, container, false);
        barChart = (HorizontalBarChart)mainView.findViewById(R.id.barChart);

        List<BarEntry> barEntries = new ArrayList<>();
        BarEntry entry1 = new BarEntry(40.0f,0);
        BarEntry entry2 = new BarEntry(10.0f,1);
        BarEntry entry3 = new BarEntry(30.0f,2);
        BarEntry entry4 = new BarEntry(20.0f,3);
        BarEntry entry5 = new BarEntry(15.0f,4);

        barEntries.add(entry1);
        barEntries.add(entry2);
        barEntries.add(entry3);
        barEntries.add(entry4);
        barEntries.add(entry5);

        BarDataSet dataSet = new BarDataSet(barEntries,"No of Tweets");

        String xVals[] = new String[5];
        xVals[0] = "Cat 1";
        xVals[1] = "Cat 2";
        xVals[2] = "Cat 3";
        xVals[3] = "Cat 4";
        xVals[4] = "Cat 5";
        BarData barData = new BarData(xVals,dataSet);

        barChart.setData(barData);
        barChart.setDescription("");
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
