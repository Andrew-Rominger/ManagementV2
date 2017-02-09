package com.management;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andrew on 10/18/2016.
 */

public class Utilities
{
    public static SimpleDateFormat MonthDayYearsdf = new SimpleDateFormat("MMM d, yyyy");
    public static SimpleDateFormat fullDateWithTime = new SimpleDateFormat("MMM d, yyyy @ h:mm aa");
    public static SimpleDateFormat justTime = new SimpleDateFormat("h:mm aa");
    public static ArrayList<Calendar> getDays(Context context, Calendar calendar)
    {
        ArrayList<Calendar> arr = new ArrayList<>();
        Calendar c = (Calendar) calendar.clone();
        c.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        //Log.i("Day retured", String.valueOf(dayOfWeek));
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        //Log.i("First Day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        for(int i = 0;i<42;i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(c.getTimeInMillis());
            arr.add(cal);
            c.add(Calendar.DAY_OF_MONTH, 1);

        }
        return arr;

    }
    public static void hideSoftKeyboard(Activity activity)
    {
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static Calendar makeCal(int minute, int hour, int day, int month, int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MONTH, month);
        return c;

    }

    public static Calendar makeCal(Long ms)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        return c;
    }


    public static ArrayList<Calendar> getNext6Days(Context context, Calendar from)
    {
        ArrayList<Calendar> cal = new ArrayList<>();
        Log.i("getNext6Days", "PASSED: " +Utilities.MonthDayYearsdf.format(from.getTime()));
        for(int i = 0;i<6;i++)
        {
            Calendar temp = Calendar.getInstance();
            temp.setTimeInMillis(from.getTimeInMillis());
            temp.add(Calendar.DAY_OF_MONTH, i);
            cal.add(temp);
            Log.i("getNext6Days", "ADDING: " + Utilities.MonthDayYearsdf.format(temp.getTime()));
        }
        Log.i("getNext6Days", String.valueOf(cal.size()));
        return cal;

    }


}

