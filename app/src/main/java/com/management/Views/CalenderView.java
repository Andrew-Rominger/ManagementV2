package com.management.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.management.Fragments.calendarFragment;
import com.management.R;
import com.management.Utilities;
import com.management.interfaces.CalendarViewDataPasser;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andrew on 10/20/2016.
 */

public class CalenderView extends LinearLayout
{
    private RelativeLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    LayoutInflater inflator;
    Calendar curCal;
    int highlightColor;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
    ArrayList<Calendar> days;
    calendarAdapter adapter;
    private int prevSelectedDay;
    int prevSelectedPosition;
    private View prevSelectedView;
    Calendar selectedDay = Calendar.getInstance();
    int selectedPosition;
    boolean changeColor = false;
    boolean isSwitched = false;

    CalendarViewDataPasser dataPasser;

    final String TAG = CalenderView.class.getSimpleName();
    public CalenderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        prevSelectedDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        dataPasser = (CalendarViewDataPasser) getContext();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CalenderView, 0,0);
        try {
            highlightColor = a.getColor(R.styleable.CalenderView_highliteColor, Color.YELLOW);
        }
        finally {
            a.recycle();
        }
        initControl();

    }
    private void initControl()
    {
        inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.calendarlayout, this);
        header = (RelativeLayout) findViewById(R.id.header);
        btnNext = (ImageView) findViewById(R.id.calenderMoveFoward);
        btnPrev = (ImageView) findViewById(R.id.calenderMoveBack);
        txtDate = (TextView) findViewById(R.id.calendarMonthName);
        grid = (GridView) findViewById(R.id.calendarGrid);
        curCal = Calendar.getInstance();
        initCalendar();
    }
    public void initCalendar()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        days = Utilities.getDays(getContext(), Calendar.getInstance());
        adapter = new calendarAdapter(getContext(), 0, 0, days);
        txtDate.setText(sdf.format(Calendar.getInstance().getTime()));
        grid.setAdapter(adapter);
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             moveFowardMonth();
            }
        });
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
             moveBackMonth();
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Calendar c2 = Calendar.getInstance();
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()) + " at position " + i);
                dataPasser.dayClicked(c);
                highlightSelection(view);

                prevSelectedDay = c.get(Calendar.DAY_OF_MONTH);
                prevSelectedPosition = i;
                selectedDay = c;
            }
        });

    }

    private void highlightSelection(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle_primaryfill, null));
        }
        if(prevSelectedView == null)
        {
            prevSelectedView = view;
            return;
        }

        if(prevSelectedDay <= 13 && prevSelectedPosition >= 28)
        {
            //Log.d(TAG, "Prev day: " + prevSelectedDay + ", Position: " + prevSelectedPosition + ", highlighting grey");
            prevSelectedView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle, null));
        }
        else if(prevSelectedDay >= 26 && prevSelectedPosition <= 5)
        {
            //Log.d(TAG, "Prev day: " + prevSelectedDay + ", Position: " + prevSelectedPosition + ", highlighting grey");
            prevSelectedView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle, null));
        }
        else
        {
            //Log.d(TAG, "Prev day: " + prevSelectedDay + ", Position: " + prevSelectedPosition + ", highlighting white");
            prevSelectedView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle, null));
        }
        prevSelectedView = view;
    }

    public void moveFowardMonth()
    {
        curCal.add(Calendar.MONTH, 1);
        adapter.adaptMove(Utilities.getDays(getContext(), curCal));
        txtDate.setText(sdf.format(curCal.getTime()));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()) + " at position " + i);
                dataPasser.dayClicked(c);
                highlightSelection(view);
                prevSelectedDay = c.get(Calendar.DAY_OF_MONTH);
                prevSelectedPosition = i;
                selectedDay = c;
            }
        });
        Log.d(TAG, "Selected day is in " + Utilities.monthAndYear.format(selectedDay.getTime()));
        boolean isSelShown = (selectedDay.get(Calendar.MONTH) == curCal.get(Calendar.MONTH)) && (selectedDay.get(Calendar.YEAR) == curCal.get(Calendar.YEAR));
        Log.d(TAG, "Is selected day shown: " + isSelShown);
        if(isSelShown)
        {
            prevSelectedView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle_primaryfill, null));
        }
        animateoutLeft();
    }
    public void moveBackMonth()
    {
        curCal.add(Calendar.MONTH, -1);
        adapter.adaptMove(Utilities.getDays(getContext(), curCal));
        txtDate.setText(sdf.format(curCal.getTime()));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Calendar c2 = Calendar.getInstance();
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()) + " at position " + i);
                highlightSelection(view);
                dataPasser.dayClicked(c);
                prevSelectedDay = c.get(Calendar.DAY_OF_MONTH);
                prevSelectedPosition = i;
                selectedDay = c;
            }
        });
        Log.d(TAG, "Selected day is in " + Utilities.monthAndYear.format(selectedDay.getTime()));
        boolean isSelShown = (selectedDay.get(Calendar.MONTH) == curCal.get(Calendar.MONTH)) && (selectedDay.get(Calendar.YEAR) == curCal.get(Calendar.YEAR));
        Log.d(TAG, "Is selected day shown: " + isSelShown);
        if(isSelShown)
        {
            prevSelectedView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle_primaryfill, null));
        }
        animateOutRight();
    }
    public void animateInRight()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        anim.setDuration(250);
        grid.startAnimation(anim);

    }
    public void animateInLeft()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        anim.setDuration(250);
        grid.startAnimation(anim);

    }
    public void animateoutLeft()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
        anim.setDuration(250);
        grid.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {

            public void run()
            {
                adapter.notifyDataSetChanged();
                animateInRight();
            }

        }, anim.getDuration());

    }
    public void animateOutRight()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        anim.setDuration(250);
        grid.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {

            public void run()
            {
                adapter.notifyDataSetChanged();
                animateInLeft();
            }

        }, anim.getDuration());
    }

    public class calendarAdapter extends ArrayAdapter<Calendar>
    {
        ArrayList<Calendar> days;
        ImageView lowCircle;
        ImageView mediumCircle;
        ImageView highCircle;
        ImageView critCircle;

        public calendarAdapter(Context context, int resource, int textViewResourceId, List<Calendar> objects)
        {
            super(context, resource, textViewResourceId, objects);
            days = (ArrayList<Calendar>) objects;
        }
        public void adaptMove(ArrayList<Calendar> newDays)
        {
            Log.d(TAG, "adaptMove: addapting");
            this.days = newDays;
        }
        @Nullable
        @Override
        public Calendar getItem(int position)
        {
            return days.get(position);
        }

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
                //Log.i(TAG, "getView: " + position);
                Calendar c = days.get(position);
                Calendar now = Calendar.getInstance();

                if(convertView == null)
                {
                    convertView = inflator.inflate(R.layout.daylayout, parent, false);
                }
                TextView dateView = (TextView) convertView.findViewById(R.id.dayOfMonthNum);
                LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.dayLayoutLin);
                dateView.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                boolean nowb = (c.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) && (c.get(Calendar.MONTH) == now.get(Calendar.MONTH)) && (c.get(Calendar.YEAR) == now.get(Calendar.YEAR));
                lin.setBackgroundColor(Color.WHITE);
                dateView.setTextColor(Color.BLACK);
                if(position <= 5 && c.get(Calendar.DAY_OF_MONTH) >= 23)
                {
                    dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
                }
                if(position >= 29 && c.get(Calendar.DAY_OF_MONTH) <= 14)
                {
                    dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
                }
                if(nowb)
                {
                    dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                }
                if((selectedDay.get(Calendar.MONTH) == c.get(Calendar.MONTH)) && (selectedDay.get(Calendar.YEAR) == c.get(Calendar.YEAR)) && (selectedDay.get(Calendar.DAY_OF_MONTH)==c.get(Calendar.DAY_OF_MONTH)))
                {
                    if(changeColor) {
                        lin.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.circle_primaryfill, null));
                    }
                    else {changeColor = true;}
                }
                return  convertView;


        }
    }


}
