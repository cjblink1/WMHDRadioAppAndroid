package edu.rosehulman.boylecj.wmhdonlineradio;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Created by luke on 1/19/17.
 */

public class GetStreamData extends AsyncTask<String, Void, Data> {

    private DataDisplayer mDataDisplayer;

    public GetStreamData(DataDisplayer activity) {
        mDataDisplayer = activity;
    }

    @Override
    protected Data doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        Data data = null;
        try {
            data = new ObjectMapper().readValue(new URL(urlString), Data.class);
            Log.d("WMHD", "REading");
        } catch (IOException e) {
            Log.d("WMHD", "Error: " + e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(Data data) {
        super.onPostExecute(data);
        mDataDisplayer.onDataLoaded(data);
    }

    public interface DataDisplayer {
        public void onDataLoaded(Data data);
    }

}
