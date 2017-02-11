package com.management.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.management.R;
import com.management.Views.CalenderView;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.management.interfaces.CalendarViewDataPasser;

import java.util.Calendar;

public class calendarFragment extends Fragment implements CalendarViewDataPasser
{
    CalenderView calenderView;
    CalendarFragmentDataPasser dataPasser;
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

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        dataPasser = (CalendarFragmentDataPasser) context;
    }

    void passData(Calendar startDate)
    {
        dataPasser.passData(startDate);
    }

    @Override
    public void dayClicked(Calendar calendar)
    {
        passData(calendar);
    }
}
