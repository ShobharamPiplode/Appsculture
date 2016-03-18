
package com.appsculture.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

/**
 * AsyncTaskLoader class to load data from Server,
 * Parse Downloaded data and Store it to Sqlite Database.
 *
 * Created by shobharam.piplode on 3/16/2016.
 */
public class VocabularyDataLoader extends AsyncTaskLoader<Boolean>
{
    private final String mUrl;
    private Context mContext;
    private boolean mRunDownload;

    public VocabularyDataLoader(Context context, String url,boolean runDownload)
    {
        super(context);
        mContext = context;
        this.mUrl = url;
        mRunDownload = runDownload;
    }

    @Override
    public Boolean loadInBackground()
    {
        // Flat to run download or Not
        if(mRunDownload)
        {
            //Get ConnectivityManager manager and check if network is connected or Not.
            ConnectivityManager connMgr = (ConnectivityManager)
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

            if (activeInfo != null && activeInfo.isConnected())
            {
                try {
                    return  WordDataProvider.buildVocabulary(mContext,mUrl);
                } catch (JSONException e) {
                    return false;
                }
            }
        }else
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }


}
