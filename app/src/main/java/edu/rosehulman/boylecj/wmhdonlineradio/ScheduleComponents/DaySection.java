package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParsePosition;
import java.util.Date;

import edu.rosehulman.boylecj.wmhdonlineradio.Constants;
import edu.rosehulman.boylecj.wmhdonlineradio.R;
import edu.rosehulman.boylecj.wmhdonlineradio.Show;
import edu.rosehulman.boylecj.wmhdonlineradio.UpdateTimer;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by Connor on 2/5/2017.
 */

public class DaySection extends StatelessSection {

    private Show[] shows;
    private String sectionName;
    private Fragment mFragment;


    public DaySection(String sectionName, Show[] shows, Fragment frag) {
        super(R.layout.day_section_header, R.layout.day_section_item);
        this.shows = shows;
        this.sectionName = sectionName;
        this.mFragment = frag;
    }

    @Override
    public int getContentItemsTotal() {
        return this.shows.length;
    }

    @Override
    public ItemViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder itemHolder = (ItemViewHolder)holder;

        itemHolder.mName.setText(shows[position].getName());
        itemHolder.mStart.setText(ScheduleUtils.readableTime(shows[position].getStart_timestamp()));
        itemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(Constants.TAG, "long press");
                Date startTime = UpdateTimer.sdfNow.parse(shows[position].getStart_timestamp() + Constants.STATION_TIME_OFFSET, new ParsePosition(0));
                Date nowTime = new Date();
                final Context context = mFragment.getActivity();

                if (startTime.before(nowTime)) {
                    pastEventDialog(context);
                    return true;
                }

                confirmEventDialog(position, context);


                return true;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new DaySection.HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder)holder;

        headerHolder.mSectionName.setText(this.sectionName);
    }

    public void pastEventDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNeutralButton(android.R.string.ok, null);

        builder.setTitle(context.getString(R.string.past_event_dialog_title));

        builder.create().show();
    }

    public void confirmEventDialog(final int position, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Date startTime = UpdateTimer.sdfNow.parse(shows[position].getStart_timestamp() + Constants.STATION_TIME_OFFSET, new ParsePosition(0));
                Date endTime = UpdateTimer.sdfNow.parse(shows[position].getEnd_timestamp() + Constants.STATION_TIME_OFFSET, new ParsePosition(0));
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, shows[position].getName());
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startTime.getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        endTime.getTime());
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton(android.R.string.no, null);

        builder.setTitle(context.getString(R.string.event_dialog_title));

        builder.create().show();

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mStart;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.section_item_name);
            mStart = (TextView)itemView.findViewById(R.id.section_item_start);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView mSectionName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mSectionName = (TextView)itemView.findViewById(R.id.section_header_name);
        }

    }
}
