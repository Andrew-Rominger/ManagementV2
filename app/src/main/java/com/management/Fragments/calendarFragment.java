package com.management.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.management.Listners.dayClickListner;
import com.management.R;
import com.management.Views.CalenderView;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.management.interfaces.IcalendarViewScroll;

import java.util.Calendar;


public class calendarFragment extends Fragment
{
    private static final String TAG = calendarFragment.class.getSimpleName();
    CalenderView calenderView;
    IcalendarViewScroll viewScroll;

    CalendarFragmentDataPasser dataPasser;

    public CalenderView getCalenderView() {
        return calenderView;
    }

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
        calenderView.setOnDayClickListner(new dayClickListner()
        {
            @Override
            public void onDayClicked(Calendar c)
            {
                dataPasser.passDate(c);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        dataPasser = (CalendarFragmentDataPasser) context;
        viewScroll = (IcalendarViewScroll) context;
    }

    @Override
    public void onStart()
    {
        viewScroll.done();
        super.onStart();
    }

}
