package com.management;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andre on 2/11/2017.
 */

public class oldUtils {
    private static final String TAG = oldUtils.class.getSimpleName();

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
