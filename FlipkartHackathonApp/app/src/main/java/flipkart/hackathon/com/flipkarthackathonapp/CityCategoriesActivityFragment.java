package flipkart.hackathon.com.flipkarthackathonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.util.ArrayList;
import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.asynctask.GetCategoriesFromDB;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;


/**
 * A placeholder fragment containing a simple view.
 */
public class CityCategoriesActivityFragment extends Fragment implements GetCategoriesFromDB.RetrivalDoneCategories,OnChartValueSelectedListener {
    View mainView;
    HorizontalBarChart barChart;
    List<Categories> mCategories;
    String mCityName;

    public CityCategoriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.city_categories_fragment_main, container, false);
        barChart = (HorizontalBarChart)mainView.findViewById(R.id.barChart);

        barChart.setDescription("");
        mCityName = getActivity().getIntent().getStringExtra("city_name");
        GetCategoriesFromDB task = new GetCategoriesFromDB(getActivity(),this,
                new Cities(mCityName));
        task.execute();
        barChart.setOnChartValueSelectedListener(this);
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void updatedCategories(List<Categories> categories) {
        mCategories = categories;

        List<BarEntry> barEntries = new ArrayList<>();
        List<String> xVals = new ArrayList<>();
        int count =0;
        for(Categories category: categories){
            barEntries.add(new BarEntry(category.getCount(),count++));
            xVals.add(category.getName());
        }

        BarDataSet dataSet = new BarDataSet(barEntries,"No of Tweets");

        BarData barData = new BarData(xVals,dataSet);

        barChart.setData(barData);
        barChart.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Intent i = new Intent(getActivity(),TweetsListActivity.class);
        i.putExtra("city_name",mCityName);
        i.putExtra("category_name",mCategories.get(dataSetIndex).getName());
        startActivity(i);
    }

    @Override
    public void onNothingSelected() {

    }
}
