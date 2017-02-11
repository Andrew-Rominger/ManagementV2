package com.management.Activities;



import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.management.R;
import com.management.Utilities;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;

public class AddTask extends AppCompatActivity implements CalendarFragmentDataPasser
{
    private static final String TAG = AddTask.class.getSimpleName();

    @ColorInt int color;
    @ColorRes int colorResource;
    Random rand = new Random();

    FragmentManager fragmentManger;

    LinearLayout startDateTouchTarget;
    LinearLayout startTimeTouchTarget;
    LinearLayout endDateTouchTarget;
    LinearLayout endTimeTouchTarget;
    LinearLayout colorChangeTouchTarget;
    FrameLayout startFragmentHolder;

    TextView title;
    TextView description;
    TextView startDateDisplay;
    TextView endDateDisplay;
    TextView startTimeDisplay;
    TextView endTimeDisplay;

    View startDateUnderbar;
    View startTimeUnderbar;
    View endDateUnderbar;
    View endTimeUnderbar;

    Toolbar toolbar;

    @Override
    public void passData(Calendar calendar)
    {
        Log.d(TAG, "Activity got calendar");
        startDateDisplay.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_save:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        toolbar = (Toolbar) findViewById(R.id.addTaskToolbar);

        int index = rand.nextInt(20);
        Log.d(TAG, "Color resource: " + Utilities.colorArray[index] + ", Array index: " + index);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,Utilities.colorArray[index]));
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        startDateTouchTarget = (LinearLayout) findViewById(R.id.StartDateTouchTarget);
        startTimeTouchTarget = (LinearLayout) findViewById(R.id.EndDateTouchTarget);
        endDateTouchTarget = (LinearLayout) findViewById(R.id.StartTimeTouchTarget);
        startFragmentHolder = (FrameLayout) findViewById(R.id.StartFragmentHolder);
        title = (EditText) findViewById(R.id.taskAddTitle);
        description = (EditText) findViewById(R.id.taskAddDescription);
        startDateDisplay = (TextView) findViewById(R.id.startDateDisplay);
        endDateDisplay = (TextView) findViewById(R.id.endDateDisplay);
        endTimeTouchTarget = (LinearLayout) findViewById(R.id.EndTimeTouchTarget);
        startTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);
        endTimeDisplay = (TextView) findViewById(R.id.endTimeDisplay);
        fragmentManger = getFragmentManager();
        startDateUnderbar = findViewById(R.id.StartDateUnderbar);
        endDateUnderbar= findViewById(R.id.EndDateUnderbar);
        startTimeUnderbar= findViewById(R.id.StartTimeUnderbar);
        endTimeUnderbar = findViewById(R.id.EndTimeUnderbar);
        colorChangeTouchTarget = (LinearLayout) findViewById(R.id.colorChangeTouchTarget);

        colorChangeTouchTarget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SpectrumDialog dialog = new SpectrumDialog.Builder(v.getContext())
                        .setColors(R.array.task_colors)
                        .setSelectedColorRes()
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener()
                        {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int selectedColor)
                            {
                                if (positiveResult)
                                {
                                    toolbar.setBackgroundColor(selectedColor);
                                    setSupportActionBar(toolbar);

                                }
                            }
                        }).build();

                dialog.show(getSupportFragmentManager(), "Pick Color");
            }
        });


        startDateTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        endDateTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        endTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        startTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

}
