package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.boylecj.wmhdonlineradio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekScheduleFragment extends Fragment {


    public WeekScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_schedule_component, container, false);
    }

}
