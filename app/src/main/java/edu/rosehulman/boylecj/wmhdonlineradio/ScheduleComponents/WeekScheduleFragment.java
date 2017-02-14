package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;


import android.os.AsyncTask;
import android.os.Build;
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            (new GetWeekInfoTask(this)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.WEEK_INFO_URL);
        } else {
            (new GetWeekInfoTask(this)).execute(Constants.WEEK_INFO_URL);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week_schedule_component, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.week_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }



    @Override
    public void onWeekInfoLoaded(WeekInfoData wid) {
        if (wid != null) {
            Log.d(Constants.TAG, "WeekInfoData fetched");
            mAdapter = new SectionedRecyclerViewAdapter();

            mAdapter.addSection(new DaySection(getString(R.string.monday), wid.getMonday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.tuesday), wid.getTuesday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.wednesday), wid.getWednesday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.thursday), wid.getThursday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.friday), wid.getFriday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.saturday), wid.getSaturday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.sunday), wid.getSunday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_monday), wid.getNextmonday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_tuesday), wid.getNexttuesday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_wednesday), wid.getNextwednesday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_thursday), wid.getNextthursday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_friday), wid.getNextfriday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_saturday), wid.getNextsaturday(), this));
            mAdapter.addSection(new DaySection(getString(R.string.next_sunday), wid.getNextsunday(), this));
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
