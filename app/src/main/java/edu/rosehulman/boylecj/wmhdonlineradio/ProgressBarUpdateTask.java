package edu.rosehulman.boylecj.wmhdonlineradio;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.Date;

/**
 * Created by Connor on 1/23/2017.
 */

public class ProgressBarUpdateTask extends AsyncTask<Params, Void, Void> {

    long startTime;
    long endTime;
    boolean updating = true;

    @Override
    protected Void doInBackground(Params... paramses) {
        startTime = paramses[0].startTime;
        endTime = paramses[0].endTime;
        MainActivity mainActivity = paramses[0].mainActivity;

        while(updating) {
            // calculate percentage

            //(currentTime - startTime) / (endTime - startTime)
            long currentTime = (new Date()).getTime();
            int progress = (int) ((currentTime - startTime)*100 / (endTime - startTime));

            // update progress bar
            mainActivity.updateProgressBar(progress);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public void updateInfo(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

class Params {
    long startTime;
    long endTime;
    MainActivity mainActivity;

    public Params(long startTime, long endTime, MainActivity mainActivity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.mainActivity = mainActivity;
    }
}
