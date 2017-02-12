package edu.rosehulman.boylecj.wmhdonlineradio;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luke on 1/22/17.
 */

public class UpdateTimer extends TimerTask implements GetStreamData.DataDisplayer {

    private MainActivity mActivity;

    public UpdateTimer(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void run() {
        GetStreamData task = new GetStreamData(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.LIVE_INFO_URL);
        else
            task.execute(Constants.LIVE_INFO_URL);
        Log.d("WMHD", "Timer!");
    }

    @Override
    public void onDataLoaded(LiveInfoData data) {
        if (data != null && data.getCurrent().getEnds() != null) {
            SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS Z");
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

            Timer timer = new Timer();
            UpdateTimer task = new UpdateTimer(mActivity);
            Log.d(Constants.TAG, "Testing " + data.getSchedulerTime());
            Date timeEnd = sdfEnd.parse(data.getCurrent().getEnds() + " -0000", new ParsePosition(0));
            Date timeNow = sdfNow.parse(data.getSchedulerTime() + " -0500", new ParsePosition(0));
            if (timeEnd == null) {
                Log.e(Constants.TAG, "Could not fetch or parse end time");
                mActivity.onDataLoaded(data);
                return;
            } else if (timeNow == null) {
                Log.e(Constants.TAG, "Could not fetch or parse current time");
                mActivity.onDataLoaded(data);
                return;
            }
            Log.d("WMHD", timeEnd.getTime() + " " + timeNow.getTime());
            timer.schedule(task, timeEnd.getTime() - timeNow.getTime() + 10000);
            mActivity.onDataLoaded(data);
        } else {
            Log.d(Constants.TAG, "Data is null");
        }
    }
}
