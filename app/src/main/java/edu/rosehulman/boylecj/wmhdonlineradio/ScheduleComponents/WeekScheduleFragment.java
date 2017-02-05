package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.boylecj.wmhdonlineradio.Constants;
import edu.rosehulman.boylecj.wmhdonlineradio.GetWeekInfoTask;
import edu.rosehulman.boylecj.wmhdonlineradio.R;
import edu.rosehulman.boylecj.wmhdonlineradio.WeekInfoData;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekScheduleFragment extends Fragment implements GetWeekInfoTask.WeekInfoDisplayer {


    private RecyclerView mRecyclerView;
    private SectionedRecyclerViewAdapter mAdapter;

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
        mRecyclerView = (RecyclerView)view.findViewById(R.id.week_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }

    @Override
    public void onWeekInfoLoaded(WeekInfoData wid) {
        Log.d(Constants.TAG, "WeekInfoData fetched");
        Log.d(Constants.TAG, "Tuesday shows: "+wid.getTuesday()[0].getName());
        mAdapter = new SectionedRecyclerViewAdapter();

        mAdapter.addSection(new DaySection("Monday", wid.getMonday()));
        mAdapter.addSection(new DaySection("Tuesday", wid.getTuesday()));
        mAdapter.addSection(new DaySection("Wednesday", wid.getWednesday()));
        mAdapter.addSection(new DaySection("Thursday", wid.getThursday()));
        mAdapter.addSection(new DaySection("Friday", wid.getFriday()));
        mAdapter.addSection(new DaySection("Saturday", wid.getSaturday()));
        mAdapter.addSection(new DaySection("Sunday", wid.getSunday()));
        mAdapter.addSection(new DaySection("Next Monday", wid.getNextmonday()));
        mAdapter.addSection(new DaySection("Next Tuesday", wid.getNexttuesday()));
        mAdapter.addSection(new DaySection("Next Wednesday", wid.getNextwednesday()));
        mAdapter.addSection(new DaySection("Next Thursday", wid.getNextthursday()));
        mAdapter.addSection(new DaySection("Next Friday", wid.getNextfriday()));
        mAdapter.addSection(new DaySection("Next Saturday", wid.getNextsaturday()));
        mAdapter.addSection(new DaySection("Next Sunday", wid.getNextsunday()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
