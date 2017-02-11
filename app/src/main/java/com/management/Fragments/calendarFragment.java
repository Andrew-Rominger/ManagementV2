package com.management.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.management.R;
import com.management.Views.CalenderView;

public class calendarFragment extends Fragment
{
    CalenderView calenderView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.calendarfragmentlayout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        calenderView = (CalenderView) view.findViewById(R.id.fragmentCV);

        super.onViewCreated(view, savedInstanceState);
    }
}
