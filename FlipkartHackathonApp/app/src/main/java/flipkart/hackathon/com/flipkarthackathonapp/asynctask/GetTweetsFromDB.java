package flipkart.hackathon.com.flipkarthackathonapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.data.FKDBWrapper;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;

/**
 * Created by webyog on 06/06/15.
 */
public class GetTweetsFromDB extends AsyncTask<Void,Void,List<Tweets>> {
    private Context mContext;
    private Cities city;
    private Categories categories;

    public GetTweetsFromDB(Context mContext, Cities city, Categories categories,RetrivalDoneTweets listener) {
        this.mContext = mContext;
        this.city = city;
        this.categories = categories;
        this.mListener = listener;
    }

    private RetrivalDoneTweets mListener;


    public interface RetrivalDoneTweets {
        void updatedTweets(List<Tweets> tweetses);
    }

    @Override
    protected List<Tweets> doInBackground(Void... params) {

        FKDBWrapper dbWrapper = new FKDBWrapper(mContext);
        return dbWrapper.getTweets(city,categories);
    }

    @Override
    protected void onPostExecute(List<Tweets> tweetses) {
        super.onPostExecute(tweetses);
        if(tweetses != null && mListener != null){
        mListener.updatedTweets(tweetses);}
    }
}
