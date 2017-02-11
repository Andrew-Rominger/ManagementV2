package com.management.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.management.R;
import com.management.Utilities;

import java.util.Calendar;

/**
 * Created by Andre on 2/10/2017.
 */

public class TimeSelecterView extends LinearLayout
{
    ImageView minusEndTime;
    ImageView minusStartTime;
    ImageView plusEndTime;
    ImageView plusStartTime;
    SeekBar startTimeBar;
    SeekBar endTimeBar;
    private TextView endTime;
    private TextView startTime;

    Calendar startTimeC;
    Calendar endTimeC;


    public TimeSelecterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupView();
    }

    void setupView()
    {
        startTimeC = Calendar.getInstance();
        endTimeC = Calendar.getInstance();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timeselecterlayout, this);

        startTimeBar = (SeekBar) findViewById(R.id.startSeek);
        endTimeBar = (SeekBar) findViewById(R.id.endSeek);

        endTime = (TextView) findViewById(R.id.endClock);
        startTime = (TextView) findViewById(R.id.startClock);

        plusEndTime = (ImageView) findViewById(R.id.plusEndTime);
        minusEndTime = (ImageView) findViewById(R.id.minusEndTime);


        minusStartTime = (ImageView) findViewById(R.id.minusStartTime);
        plusStartTime = (ImageView) findViewById(R.id.plusStartTime);


        startTimeBar.setMax(11);
        endTimeBar.setMax(11);

        startTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                startTimeC.set(Calendar.MINUTE, (progress * 5));
                updateCalendars();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        endTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endTimeC.set(Calendar.MINUTE, (progress * 5));
                updateCalendars();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        plusEndTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                endTimeC.add(Calendar.HOUR, 1);
                updateCalendars();
            }
        });
        plusStartTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeC.add(Calendar.HOUR, 1);
                updateCalendars();
            }
        });
        minusEndTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeC.add(Calendar.HOUR, -1);
                updateCalendars();
            }
        });
        minusStartTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeC.add(Calendar.HOUR, -1);
                updateCalendars();
            }
        });

    }

    void updateCalendars()
    {
        endTime.setText(Utilities.justTime.format(endTimeC.getTime()));
        startTime.setText(Utilities.justTime.format(startTimeC.getTime()));
    }
}
