package com.management.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.management.Adapter.ScrollerHourAdapter;
import com.management.Adapter.ScrollerMinuteAdapter;
import com.management.R;

/**
 * Created by Andrew on 2/14/2017.
 */

public class TimeScrollerPicker extends Fragment
{
    RecyclerView hourScroll;
    RecyclerView minureScroll;
    TextView display;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_scroller_layout, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hourScroll = (RecyclerView) view.findViewById(R.id.hourTimeScroller);
        minureScroll = (RecyclerView) view.findViewById(R.id.minuteTimeScroller);
        display = (TextView) view.findViewById(R.id.scrollerTimeDisplay);
        setupRecViews();
    }

    private void setupRecViews()
    {
        hourScroll.setAdapter(new ScrollerHourAdapter(getActivity()));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        hourScroll.setLayoutManager(manager);
        hourScroll.setItemAnimator(new DefaultItemAnimator());

        minureScroll.setAdapter(new ScrollerMinuteAdapter(getActivity()));
    }
}
