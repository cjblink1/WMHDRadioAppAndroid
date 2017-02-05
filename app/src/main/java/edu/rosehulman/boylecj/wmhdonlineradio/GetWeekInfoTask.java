package edu.rosehulman.boylecj.wmhdonlineradio;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Connor on 2/5/2017.
 */

public class GetWeekInfoTask extends AsyncTask<String, Void, WeekInfoData> {

    private WeekInfoDisplayer mWIDisplayer;

    public GetWeekInfoTask(WeekInfoDisplayer mWIDipslayer){
        this.mWIDisplayer = mWIDipslayer;
    }

    @Override
    protected WeekInfoData doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        WeekInfoData data = null;
        try {
            data = new ObjectMapper().readValue(new URL(urlString), WeekInfoData.class);
        } catch (IOException e) {
            Log.d("WMHD", "Error: " + e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(WeekInfoData weekInfoData) {
        super.onPostExecute(weekInfoData);
        mWIDisplayer.onWeekInfoLoaded(weekInfoData);

    }

    public interface WeekInfoDisplayer {
        public void onWeekInfoLoaded(WeekInfoData wid);
    }
}
