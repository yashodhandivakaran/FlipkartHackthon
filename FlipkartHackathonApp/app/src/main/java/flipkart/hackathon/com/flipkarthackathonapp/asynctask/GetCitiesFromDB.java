package flipkart.hackathon.com.flipkarthackathonapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.data.FKDBWrapper;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;

/**
 * Created by webyog on 06/06/15.
 */
public class GetCitiesFromDB extends AsyncTask<Void, Void, List<Cities>> {

    public GetCitiesFromDB(Context mContext, RetrivalDoneCities mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }

    private Context mContext;
    private RetrivalDoneCities mListener;


    public interface RetrivalDoneCities {
        void updatedCities(List<Cities> citieses);
    }

    @Override
    protected List<Cities> doInBackground(Void... params) {
        FKDBWrapper dbWrapper = new FKDBWrapper(mContext);
        return dbWrapper.getCities();
    }

    @Override
    protected void onPostExecute(List<Cities> citieses) {
        mListener.updatedCities(citieses);

    }
}
