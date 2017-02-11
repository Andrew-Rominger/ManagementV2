package com.management;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.sqldatabase.DbTaskHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.management.sqldatabase.SqlTaskContract.FeedTasks.*;


/**
 * Created by Andrew on 10/18/2016.
 */

public class Utilities
{
    private static final String TAG = Utilities.class.getSimpleName();
    public static SimpleDateFormat MonthDayYearsdf = new SimpleDateFormat("MMM d, yyyy");
    public static SimpleDateFormat monthAndYear = new SimpleDateFormat("MMM, yyyy");
    public static SimpleDateFormat fullDateWithTime = new SimpleDateFormat("MMM d, yyyy @ h:mm aa");
    public static SimpleDateFormat justTime = new SimpleDateFormat("h:mm aa");
    public static int[] colorArray = {
            R.color.md_red_500,
            R.color.md_pink_500,
            R.color.md_purple_500,
            R.color.md_deep_purple_500,
            R.color.md_indigo_500,
            R.color.md_blue_500,
            R.color.md_light_blue_500,
            R.color.md_cyan_500,
            R.color.md_teal_500,
            R.color.md_green_500,
            R.color.md_light_green_500,
            R.color.md_lime_500,
            R.color.md_yellow_500,
            R.color.md_amber_500,
            R.color.md_orange_500,
            R.color.md_deep_orange_500,
            R.color.md_brown_500,
            R.color.md_grey_500,
            R.color.md_blue_grey_500,
            R.color.md_white,
    };
    public static void hideSoftKeyboard(Activity activity)
    {
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
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
    public static void saveTask(Context c, Task task)
    {
        SQLiteException exception = null;

        DbTaskHelper th = new DbTaskHelper(c);
        SQLiteDatabase db = th.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_NAME, task.getTitle());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_END_TIME_H, task.getEndTimeH());
        cv.put(COLUMN_END_TIME_M, task.getEndTimeM());
        cv.put(COLUMN_START_TIME_H, task.getStartTimeH());
        cv.put(COLUMN_START_TIME_M, task.getStartTimeM());
        cv.put(COLUMN_COLOR, task.getColor());
        cv.put(COLUMN_IS_COMPLETE, task.getIsComplete());
        cv.put(COLUMN_URGANCY, task.getUrgency());
        cv.put(COLUMN_START_DATE_DAY, task.getStartDateDay());
        cv.put(COLUMN_START_DATE_MONTH, task.getStartDateMonth());
        cv.put(COLUMN_START_DATE_YEAR, task.getStartDateYear());
        cv.put(COLUMN_END_DATE_DAY, task.getEndDateDay());
        cv.put(COLUMN_END_DATE_MONTH, task.getEndDateMonth());
        cv.put(COLUMN_END_DATE_YEAR, task.getEndDateYear());
        cv.put(COLUMN_START_DATE_MS, task.getStartDateMS());
        cv.put(COLUMN_END_DATE_MS, task.getEndDateMS());

        try{
            db.insert(TABLE_NAME, null, cv);
        }
        catch (SQLiteException e)
        {
            exception = e;
            Log.e(TAG, "Failed to insert into database: "  + e.getMessage());
        }
        db.close();
        if(exception != null)
        {
            Toast.makeText(c, "Task failed to be created", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(c, "Task created", Toast.LENGTH_LONG).show();

        }
    }
}

