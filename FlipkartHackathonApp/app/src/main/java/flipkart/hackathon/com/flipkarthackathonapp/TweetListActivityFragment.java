package flipkart.hackathon.com.flipkarthackathonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import flipkart.hackathon.com.flipkarthackathonapp.asynctask.GetTweetsFromDB;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;


/**
 * A placeholder fragment containing a simple view.
 */
public class TweetListActivityFragment extends Fragment implements GetTweetsFromDB.RetrivalDoneTweets {
    View mainView;
    String mCityName;
    String mCategoryName;
    RecyclerView tweetsList;
    public List<Tweets> mTweetses;
    TweeetsAdapter adapter;

    public TweetListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.tweet_list_fragment_main, container, false);
        mCityName = getActivity().getIntent().getStringExtra("city_name");
        mCategoryName = getActivity().getIntent().getStringExtra("category_name");
        tweetsList = (RecyclerView)mainView.findViewById(R.id.tweets_list);

        tweetsList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        tweetsList.setLayoutManager(mLayoutManager);

        GetTweetsFromDB task = new GetTweetsFromDB(getActivity(),new Cities(mCityName),new Categories(mCategoryName),this);
        task.execute();
        mTweetses = new ArrayList<>();
        adapter = new TweeetsAdapter(mTweetses,getActivity());
        tweetsList.setAdapter(adapter);

        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void updatedTweets(List<Tweets> tweetses) {
        mTweetses.clear();
        mTweetses.addAll(tweetses);
        adapter.updateTweets(tweetses);

    }
}

class TweeetsAdapter  extends RecyclerView.Adapter<TweeetsAdapter.ViewHolder>{

    TweeetsAdapter(List<Tweets> tweetses, Context mContext) {
        this.tweetses = tweetses;
        this.mContext = mContext;
    }

    private List<Tweets> tweetses;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout continer;
        public TextView text;

        public ViewHolder(final LinearLayout itemView) {
            super(itemView);
            continer = itemView;
            continer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    //TODO: open tweet;
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweetlistrow, parent, false);

        TweeetsAdapter.ViewHolder vh = new ViewHolder((LinearLayout)v);
        vh.text = (TextView)v.findViewById(R.id.text);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(tweetses.get(position).getTweetText());
    }

    @Override
    public int getItemCount() {
        if(tweetses == null){
            return  0;
        }
        return tweetses.size();
    }

    public void updateTweets(List<Tweets> tweetses){
        this.tweetses.clear();
        this.tweetses.addAll(tweetses);
        this.notifyDataSetChanged();
    }
}
