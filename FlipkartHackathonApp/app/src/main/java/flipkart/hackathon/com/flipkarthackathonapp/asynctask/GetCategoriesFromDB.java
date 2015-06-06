package flipkart.hackathon.com.flipkarthackathonapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import flipkart.hackathon.com.flipkarthackathonapp.data.FKDBWrapper;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Categories;
import flipkart.hackathon.com.flipkarthackathonapp.data.entities.Cities;

/**
 * Created by webyog on 06/06/15.
 */
public class GetCategoriesFromDB extends AsyncTask<Void,Void,List<Categories>>{
    private Cities city;


    public GetCategoriesFromDB(Context mContext, RetrivalDoneCategories mListener,Cities city) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.city = city;
    }

    private Context mContext;
    private RetrivalDoneCategories mListener;


    public interface RetrivalDoneCategories {
        void updatedCategories(List<Categories> categories);
    }

    @Override
    protected List<Categories> doInBackground(Void... params) {
        FKDBWrapper dbWrapper = new FKDBWrapper(mContext);
        return dbWrapper.getCategories(city);
    }

    @Override
    protected void onPostExecute(List<Categories> categorieses) {
        mListener.updatedCategories(categorieses);
    }
}
