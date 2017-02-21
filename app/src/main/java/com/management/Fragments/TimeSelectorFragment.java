package com.management.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.management.Listners.timeSelectorListner;
import com.management.R;
import com.management.Utilities;
import com.management.Views.TimeSelecterView;
import com.management.interfaces.IcalendarViewScroll;
import com.management.interfaces.ItimeSelector;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Andre on 2/10/2017.
 */

public class TimeSelectorFragment extends Fragment
{
    private static final String TAG = TimeSelectorFragment.class.getSimpleName();
    TimeSelecterView timeSelecterView;
    Calendar selectedTime;
    Calendar endTime;
    IcalendarViewScroll scroll;

    public TimeSelecterView getTimeSelecterView() {
        return timeSelecterView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    ItimeSelector timeSelector;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.time_selectoer_fragment,container, false);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        timeSelector = (ItimeSelector) context;
        scroll = (IcalendarViewScroll) context;
        if(selectedTime != null)
        {
            timeSelector.passTime(selectedTime);
        }
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        timeSelecterView = (TimeSelecterView) view.findViewById(R.id.timeSelectorFragmentViews);

        timeSelecterView.setOnOkListner(new timeSelectorListner()
        {
            @Override
            public void timePass(Calendar calendar)
            {
                selectedTime = calendar;
                Log.d(TAG, "Fragment recived time: " + Utilities.justTime.format(calendar.getTime()));
                timeSelector.passTime(calendar);
            }
        });
    }

}
