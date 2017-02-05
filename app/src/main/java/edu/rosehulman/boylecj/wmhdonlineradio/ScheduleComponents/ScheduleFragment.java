package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.boylecj.wmhdonlineradio.R;


public class ScheduleFragment extends Fragment {

    private ScheduleAdapter mAdapter;
    private ViewPager mPager;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager) view.findViewById(R.id.schedule_pager);
        mPager.setAdapter(new ScheduleAdapter(getChildFragmentManager()));
        PagerTabStrip strip = (PagerTabStrip) view.findViewById(R.id.title_strip);
        strip.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextLight));
        strip.setTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorBackgroundDark));
    }
}
