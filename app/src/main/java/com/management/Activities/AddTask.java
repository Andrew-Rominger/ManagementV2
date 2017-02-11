package com.management.Activities;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
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

import com.management.Fragments.TimeSelectorFragment;
import com.management.Fragments.calendarFragment;
import com.management.R;
import com.management.Utilities;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.management.interfaces.ItimeSelector;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;

public class AddTask extends AppCompatActivity implements CalendarFragmentDataPasser, ItimeSelector
{
    private static final String TAG = AddTask.class.getSimpleName();

    Calendar startTimeC = Calendar.getInstance();
    Calendar endTimeC = Calendar.getInstance();
    Calendar startDateC = Calendar.getInstance();
    Calendar endDateC = Calendar.getInstance();

    @ColorInt int color;
    @ColorRes int colorResource;
    int index;
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

    Fragment currentShownFragment;

    boolean isStartTimeOpen = false;
    boolean isEndTimeOpen = false;
    boolean isStartDateOpen = false;
    boolean isEndDateOpen = false;

    @Override
    public void passData(Calendar calendar)
    {
        Log.d(TAG, "Activity got calendar");
        if(isStartDateOpen) {
            startDateDisplay.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
            startDateC = calendar;
        }
        else
        {
            endDateDisplay.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
            endDateC = calendar;
        }
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

        index = rand.nextInt(20);
        Log.d(TAG, "Color resource: " + Utilities.colorArray[index] + ", Array index: " + index);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,Utilities.colorArray[index]));
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        startDateTouchTarget = (LinearLayout) findViewById(R.id.StartDateTouchTarget);
        startTimeTouchTarget = (LinearLayout) findViewById(R.id.StartTimeTouchTarget);
        endDateTouchTarget = (LinearLayout) findViewById(R.id.EndDateTouchTarget);
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
                closeFragment();
                startDateUnderbar.setVisibility(View.VISIBLE);
                startTimeUnderbar.setVisibility(View.VISIBLE);
                endTimeUnderbar.setVisibility(View.VISIBLE);
                endDateUnderbar.setVisibility(View.VISIBLE);
                SpectrumDialog dialog = new SpectrumDialog.Builder(v.getContext())
                        .setColors(R.array.task_colors)
                        .setSelectedColorRes(Utilities.colorArray[index])
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
                                    for(int i = 0;i<20;i++)
                                    {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if(selectedColor == getResources().getColor(Utilities.colorArray[i], null))
                                            {
                                                index = i;
                                            }
                                        }
                                        else
                                        {
                                            if(selectedColor == ContextCompat.getColor( getApplicationContext(),Utilities.colorArray[i]))
                                            {
                                                index = i;
                                            }
                                        }
                                    }

                                }
                            }
                        }).build();

                dialog.show(getSupportFragmentManager(), "Pick Color");
            }
        });


        startDateTouchTarget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "start date tapped");
                if(currentShownFragment instanceof calendarFragment && isStartDateOpen)
                {
                    Log.d(TAG, "tapped again, closing");
                    isStartDateOpen = false;
                    closeFragment();
                    startDateUnderbar.setVisibility(View.VISIBLE);
                    startTimeUnderbar.setVisibility(View.VISIBLE);
                    endTimeUnderbar.setVisibility(View.VISIBLE);
                    endDateUnderbar.setVisibility(View.VISIBLE);

                    return;
                }
                isStartDateOpen = true;
                isEndDateOpen = false;
                openStartFragment(new calendarFragment());
                startDateUnderbar.setVisibility(View.INVISIBLE);
                startTimeUnderbar.setVisibility(View.VISIBLE);
                endTimeUnderbar.setVisibility(View.VISIBLE);
                endDateUnderbar.setVisibility(View.VISIBLE);

            }
        });
        endDateTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "end date tapped");
                if(currentShownFragment instanceof  calendarFragment && isEndDateOpen)
                {
                    Log.d(TAG, "tapped again, closing");
                    isEndDateOpen = false;
                    closeFragment();
                    endDateUnderbar.setVisibility(View.VISIBLE);
                    endTimeUnderbar.setVisibility(View.VISIBLE);
                    startDateUnderbar.setVisibility(View.VISIBLE);
                    startTimeUnderbar.setVisibility(View.VISIBLE);
                    return;
                }
                isEndDateOpen = true;
                isStartDateOpen = false;
                openEndFragment(new calendarFragment());
                endDateUnderbar.setVisibility(View.INVISIBLE);
                endTimeUnderbar.setVisibility(View.VISIBLE);
                startDateUnderbar.setVisibility(View.VISIBLE);
                startTimeUnderbar.setVisibility(View.VISIBLE);
            }
        });
        endTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "end time tapped");
                if(currentShownFragment instanceof TimeSelectorFragment && isEndTimeOpen)
                {
                    Log.d(TAG, "tapped again, closing");
                    isEndTimeOpen = false;
                    closeFragment();
                    endTimeUnderbar.setVisibility(View.VISIBLE);
                    endDateUnderbar.setVisibility(View.VISIBLE);
                    startTimeUnderbar.setVisibility(View.VISIBLE);
                    startDateUnderbar.setVisibility(View.VISIBLE);
                    return;
                }
                isEndTimeOpen = true;
                isStartTimeOpen = false;
                openEndFragment(new TimeSelectorFragment());
                endTimeUnderbar.setVisibility(View.INVISIBLE);
                endDateUnderbar.setVisibility(View.VISIBLE);
                startTimeUnderbar.setVisibility(View.VISIBLE);
                startDateUnderbar.setVisibility(View.VISIBLE);

            }
        });
        startTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "start time tapped");
                if(currentShownFragment instanceof TimeSelectorFragment && isStartTimeOpen)
                {
                    Log.d(TAG, "tapped again, closing");
                    isStartTimeOpen = false;
                    closeFragment();
                    startTimeUnderbar.setVisibility(View.VISIBLE);
                    startDateUnderbar.setVisibility(View.VISIBLE);
                    endDateUnderbar.setVisibility(View.VISIBLE);
                    endTimeUnderbar.setVisibility(View.VISIBLE);
                    return;
                }
                isStartTimeOpen = true;
                isEndTimeOpen = false;
                openStartFragment(new TimeSelectorFragment());
                startTimeUnderbar.setVisibility(View.INVISIBLE);
                startDateUnderbar.setVisibility(View.VISIBLE);
                endDateUnderbar.setVisibility(View.VISIBLE);
                endTimeUnderbar.setVisibility(View.VISIBLE);
            }
        });
    }

    void openStartFragment(Fragment fragment)
    {
        if(currentShownFragment != null)
        {
            closeFragment();
        }
        currentShownFragment = fragment;
        openFragment(currentShownFragment, R.id.StartFragmentHolder);
    }
    void openEndFragment(Fragment fragment)
    {
        if(currentShownFragment != null)
        {
            closeFragment();
        }
        if((currentShownFragment instanceof calendarFragment && fragment instanceof calendarFragment) || (currentShownFragment instanceof TimeSelectorFragment && fragment instanceof TimeSelectorFragment))
        {
            Log.d(TAG, "Tapped end start again, closing");
            closeFragment();
            return;
        }
        currentShownFragment = fragment;
        openFragment(currentShownFragment, R.id.EndFragmentHolder);
    }
    void closeFragment()
    {
        if(currentShownFragment == null)
        {
            return;
        }
        FragmentTransaction transaction = fragmentManger.beginTransaction();
        transaction.remove(currentShownFragment);
        transaction.commit();
        currentShownFragment = null;

    }
    void openFragment(Fragment fragment, int view)
    {
        FragmentTransaction transaction = fragmentManger.beginTransaction();
        transaction.replace(view, fragment);
        transaction.commit();
    }

    @Override
    public void passTime(Calendar c)
    {
        Log.d(TAG, "isStartTimeOpen: " + isStartTimeOpen + ", isEndTimeOpen: " + isEndTimeOpen);
        if(isStartTimeOpen)
        {
            startTimeC = c;
            startTimeDisplay.setText(Utilities.justTime.format(c.getTime()));
        }
        else if(isEndTimeOpen)
        {
            endTimeC = c;
            endTimeDisplay.setText(Utilities.justTime.format(c.getTime()));

        }
    }
}
