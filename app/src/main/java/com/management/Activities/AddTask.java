package com.management.Activities;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.Fragments.TimeSelectorFragment;
import com.management.Fragments.calendarFragment;
import com.management.R;
import com.management.TaskCreatedBroadcaster;
import com.management.Utilities;
import com.management.Views.CalenderView;
import com.management.interfaces.CalendarFragmentDataPasser;
import com.management.interfaces.CreateTaskListner;
import com.management.interfaces.IcalendarViewScroll;
import com.management.interfaces.ItimeSelector;
import com.thebluealliance.spectrum.SpectrumDialog;
import static com.management.sqldatabase.SqlContract.FeedTasks.*;

import java.util.Calendar;
import java.util.Random;


public class AddTask extends AppCompatActivity implements CalendarFragmentDataPasser, ItimeSelector, AdapterView.OnItemSelectedListener, IcalendarViewScroll {
    private static final String TAG = AddTask.class.getSimpleName();

    int urgency = URGANCY_LOW;
    @ColorRes int color;
    int index;
    Random rand = new Random();

    LinearLayout startDateTouchTarget;
    LinearLayout startTimeTouchTarget;
    LinearLayout colorChangeTouchTarget;
    LinearLayout urgencyFieldBackground;

    FrameLayout startTimeFragmentHolder;
    FrameLayout startDateFragmentHolder;

    TextView title;
    TextView description;
    TextView startDateDisplay;
    TextView startTimeDisplay;

    EditText taskLengthDisplay;

    View startDateUnderbar;
    View startTimeUnderbar;

    Spinner urgencySpinner;

    Toolbar toolbar;

    calendarFragment startDateFragment;
    TimeSelectorFragment startTimeFragment;

    TaskLayoutManager manager;

    Calendar selection;

    @Override
    public void passDate(Calendar calendar)
    {
        selection.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        selection.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        selection.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        startDateDisplay.setText(Utilities.MonthDayYearsdf.format(selection.getTime()));
        manager.close();
    }
    @Override
    public void passTime(Calendar calendar)
    {
        selection.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        selection.set(Calendar.MINUTE, calendar.get(Calendar.HOUR_OF_DAY));
        startTimeDisplay.setText(Utilities.justTime.format(selection.getTime()));
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
        Utilities.makeTask(
                title.getText().toString(),
                description.getText().toString(),
                color,
                selection.get(Calendar.DAY_OF_MONTH),
                selection.get(Calendar.MONTH),
                selection.get(Calendar.YEAR),
                Utilities.parseLength(taskLengthDisplay.getText().toString()),
                this,
                urgency,
                Utilities.parseLength(taskLengthDisplay.getText().toString())
        );
        TaskCreatedBroadcaster.broadcast();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_task_save, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        toolbar = (Toolbar) findViewById(R.id.addTaskToolbar);

        selection = Calendar.getInstance();
        selection.set(Calendar.SECOND, 0);
        selection.set(Calendar.MILLISECOND, 0);

        startDateTouchTarget = (LinearLayout) findViewById(R.id.StartDateTouchTarget);
        startTimeTouchTarget = (LinearLayout) findViewById(R.id.StartTimeTouchTarget);
        colorChangeTouchTarget = (LinearLayout) findViewById(R.id.colorChangeTouchTarget);
        urgencyFieldBackground = (LinearLayout) findViewById(R.id.LL_urgencyFieldBackground);


        startTimeFragmentHolder = (FrameLayout) findViewById(R.id.startTimeFragmentHolder);
        startDateFragmentHolder = (FrameLayout) findViewById(R.id.startDateFragmentHolder);

        title = (EditText) findViewById(R.id.taskAddTitle);
        description = (EditText) findViewById(R.id.taskAddDescription);
        taskLengthDisplay = (EditText) findViewById(R.id.taskLengthInput);

        startDateDisplay = (TextView) findViewById(R.id.startDateDisplay);
        startTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);

        urgencySpinner = (Spinner) findViewById(R.id.addTaskUrgencySpinner);

        startDateUnderbar = findViewById(R.id.startDateDivider);
        startTimeUnderbar= findViewById(R.id.startTimeDivider);


        index = rand.nextInt(20);
        Log.d(TAG, "Color resource: " + Utilities.colorArray[index] + ", Array index: " + index);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,Utilities.colorArray[index]));
        color = Utilities.colorArray[index];
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        manager = new TaskLayoutManager(this);
        ArrayAdapter <CharSequence> SpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.urgencyOptions, R.layout.simplespinnerlayout);
        SpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        urgencySpinner.setAdapter(SpinnerAdapter);
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
        urgencySpinner.setOnItemSelectedListener(this);
        startDateTouchTarget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(startDateFragment == null)
                {
                    startDateFragment = new calendarFragment();
                }
                Utilities.hideSoftKeyboard(AddTask.this);
                manager.updateDisplay(startDateFragment,0);
            }
        });
        startTimeTouchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch((String) urgencySpinner.getItemAtPosition(position))
        {
            case "Low":
                urgency = URGANCY_LOW;
                urgencyFieldBackground.setBackgroundColor(ContextCompat.getColor(AddTask.this, URGANCY_LOW_COLOR));
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(AddTask.this, URGANCY_LOW_COLOR));
                break;
            case "Medium":
                urgency = URGANCY_MED;
                urgencyFieldBackground.setBackgroundColor(ContextCompat.getColor(AddTask.this, URGANCY_MED_COLOR));
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(AddTask.this, URGANCY_MED_COLOR));
                break;
            case "High":
                urgency = URGANCY_HIGH;
                urgencyFieldBackground.setBackgroundColor(ContextCompat.getColor(AddTask.this, URGANCY_HIGH_COLOR));
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(AddTask.this, URGANCY_HIGH_COLOR));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    @Override
    public void done()
    {

    }

    public class TaskLayoutManager
    {
        private Fragment shownFragment;
        int toScroll;
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
        void updateDisplay(Fragment fragment, int index2)
        {
            if(fragment.equals(shownFragment))
            {
                close();
                return;
            }
            this.index = index2;
            if(fragment instanceof calendarFragment)
            {

                openStartDateFragment((calendarFragment) fragment);


            }else if(fragment instanceof TimeSelectorFragment)
            {


                openStartTimeFragment((TimeSelectorFragment) fragment);


            }
        }

        private void openStartTimeFragment(TimeSelectorFragment fragment)
        {
            openFragment(fragment, startTimeFragmentHolder);
            toScroll = 1;
        }


        private void openStartDateFragment(calendarFragment fragment)
        {
            openFragment(fragment, startDateFragmentHolder);
            toScroll = 0;
        }

        public void close()
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
