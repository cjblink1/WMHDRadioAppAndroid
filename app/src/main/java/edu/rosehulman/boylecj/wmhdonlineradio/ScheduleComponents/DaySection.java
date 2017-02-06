package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.rosehulman.boylecj.wmhdonlineradio.R;
import edu.rosehulman.boylecj.wmhdonlineradio.Show;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by Connor on 2/5/2017.
 */

public class DaySection extends StatelessSection {

    private Show[] shows;
    private String sectionName;


    public DaySection(String sectionName, Show[] shows) {
        super(R.layout.day_section_header, R.layout.day_section_item);
        this.shows = shows;
        this.sectionName = sectionName;
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder)holder;

        itemHolder.mName.setText(shows[position].getName());
        itemHolder.mStart.setText(shows[position].getStart_timestamp());
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
