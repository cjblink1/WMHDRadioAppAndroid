package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import edu.rosehulman.boylecj.wmhdonlineradio.HomeFragment;
import edu.rosehulman.boylecj.wmhdonlineradio.R;

/**
 * Created by luke on 1/29/17.
 */

public class ScheduleAdapter extends FragmentPagerAdapter {

    public ScheduleAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new DayScheduleFragment();
            case 1:
                return new WeekScheduleFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0 ) {
            return "Day";
        } else {
            return "Week";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
