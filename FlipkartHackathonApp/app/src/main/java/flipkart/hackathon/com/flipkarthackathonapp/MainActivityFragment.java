package flipkart.hackathon.com.flipkarthackathonapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import flipkart.hackathon.com.flipkarthackathonapp.asynctask.GetCitiesFromDB;
import flipkart.hackathon.com.flipkarthackathonapp.asynctask.InsertValuesInDBTask;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  implements InsertValuesInDBTask.InsertionDone,GetCitiesFromDB.RetrivalDoneCities {
    View mainView;
    PieChart pieChart;
    int materialColors[];
    PieDataSet dataSet;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_main, container, false);
        pieChart = (PieChart)mainView.findViewById(R.id.pieChart);

        /*List<Entry> vals = new ArrayList<>();

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
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);*/

        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeNetworkCall();

    }

    @Override
    public void updateUI() {
        GetCitiesFromDB task = new GetCitiesFromDB(getActivity(), this);
        //task.execute();
    }

    private void makeNetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://172.20.186.118:5002/get";
        //String url = "http://ip.jsontest.com/";
        JSONObject empty = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    ArrayList<Cities> citieses = new ArrayList<Cities>();
                    ArrayList<Categories> categorieses = new ArrayList<Categories>();
                    ArrayList<Tweets> tweets = new ArrayList<Tweets>();

                    int totalTweets = jsonObject.getInt("total_count");
                    JSONArray list = jsonObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject obj = list.getJSONObject(i);
                        Cities city = new Cities();
                        city.setCount(obj.getInt("count"));
                        city.setName(obj.getString("city"));
                        citieses.add(city);

                        JSONObject tweetsObj = obj.getJSONObject("tweet");
                        Iterator keys = tweetsObj.keys();
                        while (keys.hasNext()) {
                            String categoryKey = (String) keys.next();
                            JSONArray categoryArray = tweetsObj.getJSONArray(categoryKey);

                            Categories category = new Categories(categoryKey, categoryArray.length(),city.getName());
                            categorieses.add(category);


                            for (int j = 0; j < categoryArray.length(); j++) {
                                JSONObject object = categoryArray.getJSONObject(j);
                                Tweets tweet = new Tweets(city, category, object.getString("id"), object.getString("tweet"));
                                tweets.add(tweet);
                            }
                        }
                    }

                    InsertValuesInDBTask task = new InsertValuesInDBTask(getActivity(), citieses, categorieses, tweets,MainActivityFragment.this);
                    task.execute();

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

                    List<String> labels = new ArrayList<>();
                    for(Cities city: citieses){
                        vals.add(new Entry(city.getCount(),0));
                        labels.add(city.getName());
                    }

                    dataSet = new PieDataSet(vals,"Customer Satisfaction");
                    Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
                    dataSet.setValueTypeface(tf);
                    dataSet.setValueTextSize(9f);

                    PieData data = new PieData(labels,dataSet);
                    pieChart.setData(data);
                    pieChart.setUsePercentValues(true);
                    pieChart.setDrawSliceText(false);

                    redrawColor();
                    //pieChart.setTouchEnabled(true);

                    Legend legend = pieChart.getLegend();
                    legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                    pieChart.invalidate();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
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

    @Override
    public void updatedCities(List<Cities> citieses) {

        List<Entry> vals = new ArrayList<Entry>();

        List<String> labels = new ArrayList<>();
        for(Cities city: citieses){
            vals.add(new Entry(city.getCount(),0));
            labels.add(city.getName());
        }

        dataSet = new PieDataSet(vals,"Customer Satisfaction");
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
        dataSet.setValueTypeface(tf);
        dataSet.setValueTextSize(9f);

        PieData data = new PieData(labels,dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawSliceText(false);

        redrawColor();
        //pieChart.setTouchEnabled(true);

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        pieChart.invalidate();


    }
}
