package flipkart.hackathon.com.flipkarthackathonapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.asynctask.GetCitiesFromDB;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;


/**
 * A placeholder fragment containing a simple view.
 */
public class CityListActivityFragment extends Fragment implements GetCitiesFromDB.RetrivalDoneCities{
    View mainView;
    List<Cities> mCitites;
    RecyclerView cityRecyclerView;
    CityRecyclerViewAdapter mCityRecyclerViewAdapter;

    public CityListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.city_list_fragment_main, container, false);

        //Bundle bundle = getActivity().getIntent().getExtras();
        //mCitites = bundle.getParcelableArrayList("cities");
        cityRecyclerView = (RecyclerView)mainView.findViewById(R.id.city_recycler_view);
        cityRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cityRecyclerView.setLayoutManager(mLayoutManager);
        mCitites = new ArrayList<Cities>();

        mCityRecyclerViewAdapter = new CityRecyclerViewAdapter(mCitites,getActivity());
        cityRecyclerView.setAdapter(mCityRecyclerViewAdapter);

        GetCitiesFromDB task = new GetCitiesFromDB(getActivity(),this);
        task.execute();

        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void updatedCities(List<Cities> citieses) {
        mCitites.clear();
        mCitites.addAll(citieses);
        mCityRecyclerViewAdapter.notifyDataSetChanged();
    }
}

class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder>{

    private List<Cities> mCities;
    private Context mContext;

    public CityRecyclerViewAdapter(List<Cities> mCities, Context mContext) {
        this.mCities = mCities;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout continer;
        public TextView cityCount;
        public TextView cityName;

        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            continer = itemView;
            continer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    //TODO: start new activity with city name;
                    Intent i = new Intent(mContext,CityCategoriesActivity.class);
                    i.putExtra("city_name",mCities.get(position).getName());
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citylistrow, parent, false);

        CityRecyclerViewAdapter.ViewHolder vh = new ViewHolder((LinearLayout)v);
        vh.cityCount = (TextView)v.findViewById(R.id.cityCount);
        vh.cityName = (TextView)v.findViewById(R.id.cityName);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mCities == null || mCities.isEmpty()){
            return;
        }

        holder.cityCount.setText(String.valueOf(mCities.get(position).getCount()));
        holder.cityName.setText(mCities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
