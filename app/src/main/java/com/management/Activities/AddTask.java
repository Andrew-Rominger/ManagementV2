package com.management.Activities;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.management.R;
import com.management.Views.CalenderView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTask extends AppCompatActivity
{
    private static final String TAG = AddTask.class.getSimpleName();
    public static SimpleDateFormat justTime = new SimpleDateFormat("h:mm aa");

    private LinearLayout startTimeSelector;
    private LinearLayout endTimeSelector;

    private FrameLayout startSelectorFragment;

    boolean isStartOpen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addTaskToolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

        startTimeSelector = (LinearLayout) findViewById(R.id.taskAddStartDateFrame);
        endTimeSelector = (LinearLayout) findViewById(R.id.taskAddEndDateFrame);
        startSelectorFragment = (FrameLayout) findViewById(R.id.startSelectorHolder);
        isStartOpen = false;

        startTimeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startAnimateOpen(new com.management.Fragments.calendarFragment());
                isStartOpen = true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAnimateOpen(Fragment toInflate)
    {

    }

    private void startAnimateClose()
    {

    }
}
