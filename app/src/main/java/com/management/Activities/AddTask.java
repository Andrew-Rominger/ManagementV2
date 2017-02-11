package com.management.Activities;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.Fragments.TimeSelectorFragment;
import com.management.Fragments.calendarFragment;
import com.management.R;
import com.management.Utilities;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.management.interfaces.ItimeSelector;
import com.management.sqldatabase.DbTaskHelper;
import com.management.sqldatabase.SqlTaskContract;
import com.thebluealliance.spectrum.SpectrumDialog;
import static com.management.sqldatabase.SqlTaskContract.FeedTasks.*;

import java.util.Calendar;
import java.util.Random;


public class AddTask extends AppCompatActivity implements CalendarFragmentDataPasser, ItimeSelector
{
    private static final String TAG = AddTask.class.getSimpleName();

    Calendar startTimeC = Calendar.getInstance();
    Calendar endTimeC = Calendar.getInstance();
    Calendar startDateC = Calendar.getInstance();
    Calendar endDateC = Calendar.getInstance();

    @ColorRes int color;
    int index;
    Random rand = new Random();



    LinearLayout startDateTouchTarget;
    LinearLayout startTimeTouchTarget;
    LinearLayout endDateTouchTarget;
    LinearLayout endTimeTouchTarget;
    LinearLayout colorChangeTouchTarget;

    FrameLayout startTimeFragmentHolder;
    FrameLayout startDateFragmentHolder;
    FrameLayout endTimeFragmentHolder;
    FrameLayout endDateFragmentHolder;

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

    calendarFragment startDateFragment;
    calendarFragment endDateFragment;
    TimeSelectorFragment startTimeFragment;
    TimeSelectorFragment endTimeFragment;

    TaskLayoutManager manager;


    boolean isStartTimeOpen = false;
    boolean isEndTimeOpen = false;
    boolean isStartDateOpen = false;
    boolean isEndDateOpen = false;

    @Override
    public void passData(Calendar calendar)
    {
        Log.d(TAG, "Activity got calendar");
        if(manager.getIndex() == 0) {
            startDateDisplay.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
            startDateC = calendar;
        }
        else if(manager.getIndex() == 2)
        {
            endDateDisplay.setText(Utilities.MonthDayYearsdf.format(calendar.getTime()));
            endDateC = calendar;
        }
        manager.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_save:
                makeTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeTask()
    {
        startTimeC.set(Calendar.SECOND, 0);
        endTimeC.set(Calendar.SECOND, 0);
        Task task = new Task();
        task.setColor(color);
        task.setDescription(description.getText().toString());
        task.setTitle(title.getText().toString());
        task.setStartTimeM(startTimeC.get(Calendar.MINUTE));
        task.setStartTimeH(startTimeC.get(Calendar.HOUR));
        task.setEndTimeM(endTimeC.get(Calendar.MINUTE));
        task.setEndTimeH(endTimeC.get(Calendar.HOUR));
        task.setIsComplete(0);
        task.setStartDateMS((int) startTimeC.getTimeInMillis());
        task.setEndDateMS((int) endTimeC.getTimeInMillis());
        Utilities.saveTask(this, task);
        this.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_task_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        toolbar = (Toolbar) findViewById(R.id.addTaskToolbar);

        startDateTouchTarget = (LinearLayout) findViewById(R.id.StartDateTouchTarget);
        startTimeTouchTarget = (LinearLayout) findViewById(R.id.StartTimeTouchTarget);
        endDateTouchTarget = (LinearLayout) findViewById(R.id.EndDateTouchTarget);
        endTimeTouchTarget = (LinearLayout) findViewById(R.id.EndTimeTouchTarget);
        colorChangeTouchTarget = (LinearLayout) findViewById(R.id.colorChangeTouchTarget);


        startTimeFragmentHolder = (FrameLayout) findViewById(R.id.startTimeFragmentHolder);
        startDateFragmentHolder = (FrameLayout) findViewById(R.id.startDateFragmentHolder);
        endTimeFragmentHolder = (FrameLayout) findViewById(R.id.endTimeFragmentHolder);
        endDateFragmentHolder = (FrameLayout) findViewById(R.id.endDateFragmentHolder);


        title = (EditText) findViewById(R.id.taskAddTitle);
        description = (EditText) findViewById(R.id.taskAddDescription);

        startDateDisplay = (TextView) findViewById(R.id.startDateDisplay);
        endDateDisplay = (TextView) findViewById(R.id.endDateDisplay);
        startTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);
        endTimeDisplay = (TextView) findViewById(R.id.endTimeDisplay);



        startDateUnderbar = findViewById(R.id.startDateDivider);
        endDateUnderbar= findViewById(R.id.endDateDivider);
        startTimeUnderbar= findViewById(R.id.startTimeDivider);
        endTimeUnderbar = findViewById(R.id.endTimeDivider);


        index = rand.nextInt(20);
        Log.d(TAG, "Color resource: " + Utilities.colorArray[index] + ", Array index: " + index);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,Utilities.colorArray[index]));
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        manager = new TaskLayoutManager(this);

        colorChangeTouchTarget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.close();
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
                                    color = Utilities.colorArray[index];
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
                if(startDateFragment == null)
                {
                    startDateFragment = new calendarFragment();
                }
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.updateDisplay(startDateFragment,0);
            }
        });
        endDateTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "end date tapped");
                if(endDateFragment == null)
                {
                    endDateFragment = new calendarFragment();
                }
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.updateDisplay(endDateFragment,2);
            }
        });
        endTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "end time tapped");
                if(endTimeFragment == null)
                {
                    endTimeFragment = new TimeSelectorFragment();
                }
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.updateDisplay(endTimeFragment, 3);

            }
        });
        startTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "start time tapped");
                if(startTimeFragment == null)
                {
                    startTimeFragment = new TimeSelectorFragment();
                }
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.updateDisplay(startTimeFragment, 1);

            }
        });
    }

    @Override
    public void passTime(Calendar c)
    {
        Log.d(TAG, "isStartTimeOpen: " + isStartTimeOpen + ", isEndTimeOpen: " + isEndTimeOpen);
        if(manager.getIndex() == 1)
        {
            startTimeC = c;
            startTimeDisplay.setText(Utilities.justTime.format(c.getTime()));
        }
        else if(manager.getIndex() == 3)
        {
            endTimeC = c;
            endTimeDisplay.setText(Utilities.justTime.format(c.getTime()));

        }
        manager.close();
    }
    public class TaskLayoutManager
    {
        private Fragment shownFragment;
        Context context;
        FragmentManager fragmentManger;
        private int index;

        int getIndex() {
            return index;
        }

        TaskLayoutManager(Context context)
        {
            this.context = context;
            fragmentManger = getFragmentManager();
            shownFragment = null;
        }
        void updateDisplay(Fragment fragment, int index)
        {
            if(fragment.equals(shownFragment))
            {
                close();
                return;
            }
            this.index = index;
            if(fragment instanceof calendarFragment)
            {
                switch (index)
                {
                    case 0:
                        openStartDateFragment(fragment);
                        break;
                    case 2:
                        openEndDateFragment(fragment);
                }
            }else if(fragment instanceof TimeSelectorFragment)
            {
                switch (index)
                {
                    case 1:
                        openStartTimeFragment(fragment);
                        break;
                    case 3:
                        openEndTimeFragment(fragment);
                }
            }
        }

        private void openEndTimeFragment(Fragment fragment)
        {
            openFragment(fragment, endTimeFragmentHolder);
        }

        private void openStartTimeFragment(Fragment fragment)
        {
            openFragment(fragment, startTimeFragmentHolder);
        }

        private void openEndDateFragment(Fragment fragment)
        {
            openFragment(fragment, endDateFragmentHolder);
        }

        private void openStartDateFragment(Fragment fragment)
        {
            openFragment(fragment, startDateFragmentHolder);
        }

        private void close()
        {
            if(shownFragment == null)
            {
                return;
            }
            FragmentTransaction transaction = fragmentManger.beginTransaction();
            transaction.remove(shownFragment);
            transaction.commit();
            shownFragment = null;
        }

        private void openFragment(Fragment fragment, View view)
        {
            close();
            FragmentTransaction transaction = fragmentManger.beginTransaction();
            shownFragment = fragment;
            transaction.replace(view.getId(), fragment);
            transaction.commit();
        }
    }
}
