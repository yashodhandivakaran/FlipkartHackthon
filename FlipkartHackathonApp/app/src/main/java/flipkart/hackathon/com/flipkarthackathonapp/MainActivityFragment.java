package flipkart.hackathon.com.flipkarthackathonapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    View mainView;
    PieChart pieChart;
    int materialColors[];
    PieDataSet dataSet;
    FrameLayout listButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_main, container, false);
        pieChart = (PieChart)mainView.findViewById(R.id.pieChart);
        listButton = (FrameLayout)mainView.findViewById(R.id.listButton);

        List<Entry> vals = new ArrayList<>();

        Entry val1= new Entry(20,0);
        Entry val2= new Entry(30,0);
        Entry val3= new Entry(10,0);
        Entry val4= new Entry(50,0);
        Entry val5= new Entry(10,0);
        Entry val6= new Entry(20,0);
        Entry val7= new Entry(5,0);
        Entry val8= new Entry(15,0);
        Entry val9= new Entry(25,0);

        vals.add(val1);
        vals.add(val2);
        vals.add(val3);
        vals.add(val4);
        vals.add(val5);
        vals.add(val6);
        vals.add(val7);
        vals.add(val8);
        vals.add(val9);

        dataSet = new PieDataSet(vals,"Customer Satisfaction");
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
        dataSet.setValueTypeface(tf);
        dataSet.setValueTextSize(9f);

        List<String> labels = new ArrayList<>();
        labels.add("Bangalore");
        labels.add("B");
        labels.add("C");
        labels.add("D");
        labels.add("E");
        labels.add("F");
        labels.add("G");
        labels.add("H");
        labels.add("I");

        PieData data = new PieData(labels,dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawSliceText(false);

        redrawColor();
        //pieChart.setTouchEnabled(true);

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CityListActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void animateChart() {
        pieChart.animateXY(1000, 1000, Easing.EasingOption.Linear,Easing.EasingOption.Linear);
    }

    public void redrawColor(){
        pieChart.invalidate();
        materialColors = new int[]{
                getActivity().getResources().getColor(R.color.red_A100),
                getActivity().getResources().getColor(R.color.pink_A100),
                getActivity().getResources().getColor(R.color.purple_A100),
                getActivity().getResources().getColor(R.color.dark_purple_A100),
                getActivity().getResources().getColor(R.color.indigo_A100),
                getActivity().getResources().getColor(R.color.blue_A100),
                getActivity().getResources().getColor(R.color.light_blue_A100),
                getActivity().getResources().getColor(R.color.cyan_A100),
                getActivity().getResources().getColor(R.color.teal_A100),
                getActivity().getResources().getColor(R.color.yellow_A100),
                getActivity().getResources().getColor(R.color.orange_A100),
                getActivity().getResources().getColor(R.color.amber_A100)};


        int colors[] = new int[9];
        Random rand = new Random();
        int baseColor = rand.nextInt(9);
        for(int i =0;i<9;i++){
            colors[i] = materialColors[(baseColor+i)%12];
        }
        dataSet.setColors(colors);

    }
}
