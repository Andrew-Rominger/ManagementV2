package com.management.Activities;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.management.Fragments.calendarFragment;
import com.management.R;
import com.management.Utilities;
import com.management.interfaces.CalendarFragmentDataPasser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTask extends AppCompatActivity implements CalendarFragmentDataPasser
{
    private static final String TAG = AddTask.class.getSimpleName();
    public static SimpleDateFormat justTime = new SimpleDateFormat("h:mm aa");

    FragmentManager fragmentManger;

    private LinearLayout startDateSelector;
    private LinearLayout endTimeSelector;
    private LinearLayout endDateSelector;

    private FrameLayout startSelectorFragment;

    Fragment currentShownStart;

    TextView title;
    TextView description;
    TextView startDate;
    TextView endDate;
    TextView startTime;
    TextView endTime;
    int color;

    Calendar startDateC;
    Calendar startTimeC;
    Calendar endDateC;
    Calendar endTimeC;

    View startDateLine;
    View startTimeLine;
    View endDateLine;
    View endTimeLine;

    Toolbar toolbar;

    boolean isStartDateOpen = false;
    boolean isStartTimeOpen = false;

    boolean isEndDateOpen = false;
    boolean isEndTimeOpen = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        toolbar = (Toolbar) findViewById(R.id.addTaskToolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        startDateSelector = (LinearLayout) findViewById(R.id.taskAddStartDateFrame);
        endTimeSelector = (LinearLayout) findViewById(R.id.taskAddEndDateFrame);
        endDateSelector = (LinearLayout) findViewById(R.id.endDateSelector);
        startSelectorFragment = (FrameLayout) findViewById(R.id.startSelectorHolder);
        title = (EditText) findViewById(R.id.taskAddTitle);
        description = (EditText) findViewById(R.id.taskAddDescription);
        startDate = (TextView) findViewById(R.id.startDateTask);
        endDate = (TextView) findViewById(R.id.endDateTask);
        startTime = (TextView) findViewById(R.id.startTimeTask);
        endTime = (TextView) findViewById(R.id.endTimeTask);
        fragmentManger = getSupportFragmentManager();
        startDateLine = findViewById(R.id.startDateLine);
        endDateLine = findViewById(R.id.endDateLine);

        startDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(isStartDateOpen) {
                    startAnimateClose();
                    startDateLine.setVisibility(View.VISIBLE);
                    isStartDateOpen = false;
                }else
                {
                    currentShownStart = new calendarFragment();
                    startAnimateOpen(currentShownStart, startDateSelector);
                    startDateLine.setVisibility(View.INVISIBLE);
                    isStartDateOpen = true;
                }
            }
        });
        endDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEndDateOpen)
                {
                    startAnimateClose();
                    endDateLine.setVisibility(View.VISIBLE);
                    isEndDateOpen = false;
                }
                else
                {
                    currentShownStart = new calendarFragment();
                    startAnimateOpen(currentShownStart, endDateSelector);
                    endDateLine.setVisibility(View.INVISIBLE);
                    isEndDateOpen = true;
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_save:
                createTask();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void createTask()
    {

    }


    private void startAnimateOpen(Fragment toInflate, LinearLayout l)
    {
        if(l == startDateSelector) {
            FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
            fragmentTransaction.replace(R.id.startSelectorHolder, toInflate, "startDateFragment");
            fragmentTransaction.addToBackStack("addStartFragment");
            fragmentTransaction.commit();
        }
        else
        {
            FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
            fragmentTransaction.replace(R.id.endSelectorHolder, toInflate);
            fragmentTransaction.addToBackStack("addStartFragment");
            fragmentTransaction.commit();
        }
    }

    private void startAnimateClose()
    {

            FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
            fragmentTransaction.remove(currentShownStart);
            fragmentTransaction.commit();

    }
    @Override
    public void passData(Calendar calendar)
    {
        Log.d(TAG, "Activity got calendar");
        startDate.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
    }
}
