package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.boylecj.wmhdonlineradio.Constants;
import edu.rosehulman.boylecj.wmhdonlineradio.GetWeekInfoTask;
import edu.rosehulman.boylecj.wmhdonlineradio.R;
import edu.rosehulman.boylecj.wmhdonlineradio.WeekInfoData;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekScheduleFragment extends Fragment implements GetWeekInfoTask.WeekInfoDisplayer {


    public WeekScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get current week information
        (new GetWeekInfoTask(this)).execute(Constants.WEEK_INFO_URL);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week_schedule_component, container, false);

        return view;

    }

    @Override
    public void onWeekInfoLoaded(WeekInfoData wid) {
        Log.d(Constants.TAG, "WeekInfoData fetched");
        Log.d(Constants.TAG, "Tuesday shows: "+wid.getTuesday()[0].getName());
    }
}
