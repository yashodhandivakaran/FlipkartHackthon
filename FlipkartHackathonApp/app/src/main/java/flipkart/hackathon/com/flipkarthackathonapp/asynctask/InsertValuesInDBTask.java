package flipkart.hackathon.com.flipkarthackathonapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import flipkart.hackathon.com.flipkarthackathonapp.data.FKDBWrapper;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Tweets;

/**
 * Created by webyog on 06/06/15.
 */
public class InsertValuesInDBTask extends AsyncTask<Void, Void, Void> {

    public interface InsertionDone{
        public void updateUI();
    }

    private InsertionDone mListener;

    public InsertValuesInDBTask(Context mContext, ArrayList<Cities> citieses, ArrayList<Categories> categorieses, ArrayList<Tweets> tweetses,InsertionDone listener) {
        this.mContext = mContext;
        this.citieses = citieses;
        this.categorieses = categorieses;
        this.tweetses = tweetses;
        this.mListener = listener;
    }

    private Context mContext;
    private ArrayList<Cities> citieses;
    private ArrayList<Categories> categorieses;
    private ArrayList<Tweets> tweetses;


    @Override
    protected Void doInBackground(Void... params) {
        FKDBWrapper dbWrapper = new FKDBWrapper(mContext);
        dbWrapper.insertCities(citieses);
        dbWrapper.insertCategories(categorieses);
        dbWrapper.insertTweets(tweetses);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.updateUI();
    }
}
