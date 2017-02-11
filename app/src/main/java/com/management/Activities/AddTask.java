package com.management.Activities;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

import com.management.Fragments.TimeSelectorFragment;
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
    private LinearLayout startTimeSelector;
    private LinearLayout endDateSelector;
    private LinearLayout endTimeSelector;

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
    public void passData(Calendar calendar)
    {
        Log.d(TAG, "Activity got calendar");
        startDate.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_save:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

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
        startTimeSelector = (LinearLayout) findViewById(R.id.taskAddEndDateFrame);
        endDateSelector = (LinearLayout) findViewById(R.id.endDateSelector);
        startSelectorFragment = (FrameLayout) findViewById(R.id.startSelectorHolder);
        title = (EditText) findViewById(R.id.taskAddTitle);
        description = (EditText) findViewById(R.id.taskAddDescription);
        startDate = (TextView) findViewById(R.id.startDateTask);
        endDate = (TextView) findViewById(R.id.endDateTask);
        endTimeSelector = (LinearLayout) findViewById(R.id.endTimeSelector);
        startTime = (TextView) findViewById(R.id.startTimeTask);
        endTime = (TextView) findViewById(R.id.endTimeTask);
        fragmentManger = getFragmentManager();
        startDateLine = findViewById(R.id.startDateLine);
        endDateLine = findViewById(R.id.endDateLine);
        startTimeLine = findViewById(R.id.startTimeView);
        endTimeLine = findViewById(R.id.endTimeLine);


        startDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        endDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        endTimeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        startTimeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

}
