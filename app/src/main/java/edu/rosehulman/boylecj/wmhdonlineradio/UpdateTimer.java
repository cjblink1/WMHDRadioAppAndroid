package edu.rosehulman.boylecj.wmhdonlineradio;

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
        new GetStreamData(this).execute("http://dj.wmhdradio.org/api/live-info");
        Log.d("WMHD", "Timer!");
    }

    @Override
    public void onDataLoaded(Data data) {
        if (data.getCurrent().getEnds() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS Z");
            Timer timer = new Timer();
            UpdateTimer task = new UpdateTimer(mActivity);
            Date time = sdf.parse(data.getCurrent().getEnds() + " -0000", new ParsePosition(0));
            Log.d("WMHD", time.getTime() + " " + (new Date()).getTime());
            timer.schedule(task, time.getTime() - (new Date()).getTime() + 1000);
            mActivity.onDataLoaded(data);
        }
    }
}
