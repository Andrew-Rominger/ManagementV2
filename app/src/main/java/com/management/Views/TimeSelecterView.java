package com.management.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.Listners.timeSelectorListner;

import com.management.R;
import com.management.Utilities;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Andre on 2/10/2017.
 */

public class TimeSelecterView extends LinearLayout
{
    private static final String TAG = TimeSelecterView.class.getSimpleName();
    ImageView minusTime;
    ImageView plusTime;

    SeekBar timeBar;

    private TextView shownTime;

    Button okButton;

    Calendar selectedTime;
    int hour;
    int minute;

    timeSelectorListner listner;

    public TimeSelecterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        listner = new timeSelectorListner();
        setupView();
        updateCalendars();
    }

    void setupView()
    {
        selectedTime = Calendar.getInstance();
        hour = selectedTime.get(Calendar.HOUR);
        minute = selectedTime.get(Calendar.MINUTE);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timeselecterlayout, this);

        timeBar = (SeekBar) findViewById(R.id.timeSeek);

        shownTime = (TextView) findViewById(R.id.shownTime);

        plusTime = (ImageView) findViewById(R.id.plusTime);
        minusTime = (ImageView) findViewById(R.id.minusTime);

        okButton = (Button) findViewById(R.id.timeFragmentButton);


        timeBar.setMax(11);

        timeBar.setProgress(selectedTime.get(Calendar.MINUTE) / 5);

        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                selectedTime.set(Calendar.MINUTE, (progress * 5));
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
        plusTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(hour == 23)
                {
                    hour = 0;
                }
                else
                {
                    hour++;
                }
                selectedTime.set(Calendar.HOUR_OF_DAY, hour);
                updateCalendars();
            }
        });
        minusTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(hour == 0)
                {
                    hour = 23;
                }
                else
                {
                    hour--;
                }
                selectedTime.set(Calendar.HOUR_OF_DAY, hour);
                updateCalendars();
            }
        });

        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listner.timePass(selectedTime);
            }
        });

    }

    public void setOnOkListner(timeSelectorListner listner)
    {
        this.listner = listner;
    }

    void updateCalendars()
    {
        shownTime.setText(Utilities.justTime.format(selectedTime.getTime()));
    }
}
