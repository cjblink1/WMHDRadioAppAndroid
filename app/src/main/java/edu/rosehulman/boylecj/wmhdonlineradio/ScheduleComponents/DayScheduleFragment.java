package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import edu.rosehulman.boylecj.wmhdonlineradio.Constants;
import edu.rosehulman.boylecj.wmhdonlineradio.GetWeekInfoTask;
import edu.rosehulman.boylecj.wmhdonlineradio.R;
import edu.rosehulman.boylecj.wmhdonlineradio.Show;
import edu.rosehulman.boylecj.wmhdonlineradio.WeekInfoData;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayScheduleFragment extends Fragment implements GetWeekInfoTask.WeekInfoDisplayer{

    private RecyclerView mRecyclerView;
    private SectionedRecyclerViewAdapter mAdapter;

    public DayScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get current week information
        (new GetWeekInfoTask(this)).execute(Constants.WEEK_INFO_URL);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_schedule, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.day_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onWeekInfoLoaded(WeekInfoData wid) {
        mAdapter = new SectionedRecyclerViewAdapter();
        mAdapter.addSection(createTodaySecation(wid));
        mRecyclerView.setAdapter(mAdapter);
    }

    private DaySection createTodaySecation(WeekInfoData wid){

        Calendar cal = Calendar.getInstance();

        Show[] showsForToday = new Show[0];
        String day = "Today";

        switch(cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                showsForToday = wid.getMonday();
                break;
            case Calendar.TUESDAY:
                showsForToday = wid.getTuesday();
                break;
            case Calendar.WEDNESDAY:
                showsForToday = wid.getWednesday();
                break;
            case Calendar.THURSDAY:
                showsForToday = wid.getThursday();
                break;
            case Calendar.FRIDAY:
                showsForToday = wid.getFriday();
                break;
            case Calendar.SATURDAY:
                showsForToday = wid.getSaturday();
                break;
            case Calendar.SUNDAY:
                showsForToday = wid.getSunday();
                break;
        }


        return new DaySection(day, showsForToday);
    }
}
