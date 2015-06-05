package flipkart.hackathon.com.flipkarthackathonapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;


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
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
