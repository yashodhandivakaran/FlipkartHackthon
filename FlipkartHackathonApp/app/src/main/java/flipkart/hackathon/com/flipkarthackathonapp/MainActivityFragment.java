package flipkart.hackathon.com.flipkarthackathonapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.FrameLayout;

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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

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
public class MainActivityFragment extends Fragment  implements InsertValuesInDBTask.InsertionDone,GetCitiesFromDB.RetrivalDoneCities
        ,OnChartValueSelectedListener,
         View.OnClickListener   {
    View mainView;
    PieChart pieChart;
    int materialColors[];
    PieDataSet dataSet;
    FrameLayout listButton;
    int totalTweetCount;

    List<Cities> mCities;
    TextView city1;
    TextView city2;
    TextView city3;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_main, container, false);
        pieChart = (PieChart)mainView.findViewById(R.id.pieChart);
        listButton = (FrameLayout)mainView.findViewById(R.id.listButton);

        city1 = (TextView)mainView.findViewById(R.id.city1);
        city2 = (TextView)mainView.findViewById(R.id.city2);
        city3 = (TextView)mainView.findViewById(R.id.city3);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CityCategoriesActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //animateChart();
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
        task.execute();
    }

    @Override
    public void onClick(View v) {
        if(mCities == null){
            Toast.makeText(getActivity(),"Loading data ...",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(getActivity(),CityListActivity.class);
        /*i.putExtra("cities",new ArrayList<Cities>(mCities));*/
        startActivity(i);
    }

    private void makeNetworkCall() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://172.20.186.118:5003/get";
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
                    totalTweetCount= totalTweets;

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

    public void redrawColor(int size){
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


        int colors[] = new int[size];
        Random rand = new Random();
        int baseColor = rand.nextInt(size);
        for(int i =0;i<size;i++){
            colors[i] = materialColors[(baseColor+i)%size];
        }
        dataSet.setColors(colors);
        pieChart.invalidate();
    }

    @Override
    public void updatedCities(List<Cities> citieses) {
        mCities = citieses;
        List<Entry> vals = new ArrayList<Entry>();

        List<String> labels = new ArrayList<>();
        int count = 0;
        for(Cities city: citieses){
            vals.add(new Entry(city.getCount(),count++));
            labels.add(city.getName().toUpperCase());
        }

        dataSet = new PieDataSet(vals,"");
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Medium.ttf");
        dataSet.setValueTypeface(tf);
        dataSet.setValueTextSize(9f);

        PieData data = new PieData(labels,dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawSliceText(false);
        pieChart.setDescription("");


        redrawColor(citieses.size());
        pieChart.setTouchEnabled(true);

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);

        pieChart.invalidate();
        pieChart.notifyDataSetChanged();
        animateChart();
        pieChart.setOnChartValueSelectedListener(this);

        city1.setText(citieses.get(0).getName()+" : "+citieses.get(0).getCount());
        city2.setText(citieses.get(1).getName()+" : "+citieses.get(1).getCount());
        city3.setText(citieses.get(2).getName()+" : "+citieses.get(2).getCount());

        listButton.setOnClickListener(this);

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Intent i = new Intent(getActivity(),CityCategoriesActivity.class);
        i.putExtra("city_name",mCities.get(dataSetIndex).getName());
        startActivity(i);

    }

    @Override
    public void onNothingSelected() {

    }
}
