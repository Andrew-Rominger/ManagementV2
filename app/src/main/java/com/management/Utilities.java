package com.management;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.management.BaseClasses.DataBaseClasses.Day;
import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.BaseClasses.RecyclerHeader;
import com.management.sqldatabase.DbDayHelper;
import com.management.sqldatabase.DbTaskHelper;
import com.management.sqldatabase.SqlContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import static com.management.sqldatabase.SqlContract.FeedTasks.*;


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
    public static SimpleDateFormat justHour = new SimpleDateFormat("h aa");
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
        //Either this line or the one above crashes app :'(
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
    private static Calendar makeCalendar(int day, int month, int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c;
    }
    public static String getFormatedDescription(String description)
    {
        if(description.contains("\n"))
        {
            String finals = "";
            char[] arr = description.toCharArray();
            for(int i =0;i<arr.length;i++)
            {
                if(arr[i] == '\n')
                {
                    finals = description.substring(0, i) + "...";
                    break;
                }
            }
            return finals;
        }
        else if(description.length() > 30)
        {
            return  description.substring(0,30) + "...";
        }
        else
        {
            return description;
        }
    }
    public static void deleteAllTasks(Activity a)
    {
        Toast.makeText(a, "Deleting all tasks", Toast.LENGTH_LONG).show();
        DbTaskHelper helper = new DbTaskHelper(a);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
        DbDayHelper helper1 = new DbDayHelper(a);
        SQLiteDatabase db2 = helper1.getWritableDatabase();
        db2.delete(SqlContract.FeedDays.TABLE_NAME, null,null);
        db2.close();
    }

    public static ArrayList<Task> getAllTasks(Context context) throws utilityDatabaseError {
        ArrayList<Task> List = new ArrayList<>();
        DbTaskHelper helper = new DbTaskHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(
                SqlContract.FeedTasks.TABLE_NAME,
                SqlContract.getColumnsTask(),
                null,
                null,
                null,
                null,
                SqlContract.FeedTasks.sortOrder
        );
        if(cursor.getCount() < 1)
        {
            throw new utilityDatabaseError("No Tasks found");
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            List.add(new Task(cursor));
            cursor.moveToNext();
        }
        return List;
    }

    public static ArrayList<Day> getAllDays(Context context) throws utilityDatabaseError {
    ArrayList<Day> Days = new ArrayList<>();
    DbDayHelper helper = new DbDayHelper(context);
    SQLiteDatabase database = helper.getReadableDatabase();
    Cursor cursor = database.query(
            SqlContract.FeedDays.TABLE_NAME,
            SqlContract.getColumnsDay(),
            null,
            null,
            null,
            null,
            SqlContract.FeedDays.sortOrder
    );
    if(cursor.getCount() < 1)
    {
        throw new utilityDatabaseError("No Days found");
    }
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
        Days.add(new Day(cursor));
        cursor.moveToNext();
    }
     return Days;
    }

    public static ArrayList<Day> getDaysWithTasks(Context context)
    {
        Log.d(context.getClass().getSimpleName(), "Getting days with tasks");
        ArrayList<RecyclerHeader> headerList = new ArrayList<>();

        ArrayList<Day> dayList = new ArrayList<>();

        DbDayHelper dayHelper = new DbDayHelper(context);

        SQLiteDatabase dayDatabase = dayHelper.getReadableDatabase();

        String sortOrder = SqlContract.FeedDays.COLUMN_YEAR + " ASC," + SqlContract.FeedDays.COLUMN_MONTH + " ASC," + SqlContract.FeedDays.COLUMN_DAY + " ASC";

        Cursor c = dayDatabase.query(
                SqlContract.FeedDays.TABLE_NAME,
                SqlContract.getColumnsDay(),
                null,
                null,
                null,
                null,
                sortOrder
        );

        Log.d(context.getClass().getSimpleName(), "Days found:" + c.getCount());
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Log.e(TAG, "Cursor at: " + c.getPosition());
            Log.e(TAG, "ID: " + c.getInt(c.getColumnIndexOrThrow(SqlContract.FeedDays._ID)));
            dayList.add(new Day(
                    c.getInt(c.getColumnIndexOrThrow(SqlContract.FeedDays.COLUMN_DAY)),
                    c.getInt(c.getColumnIndex(SqlContract.FeedDays.COLUMN_MONTH)),
                    c.getInt(c.getColumnIndexOrThrow(SqlContract.FeedDays.COLUMN_YEAR)),
                    context,
                    c.getInt(c.getColumnIndexOrThrow(SqlContract.FeedDays._ID))
            ));
            c.moveToNext();
        }
        dayDatabase.close();
        c.close();
        return dayList;
    }

    public static ArrayList<RecyclerHeader> getHeaders(Context context)
    {
        ArrayList<RecyclerHeader> headerList = new ArrayList<>();
        ArrayList<Day> dayList = getDaysWithTasks(context);
        for(Day dayInList : dayList)
        {
            headerList.add(new RecyclerHeader(Utilities.MonthDayYearsdf.format(dayInList.getDate().getTime()),dayInList.getTasks(context)));
        }
        return headerList;
    }

    public static void makeTask(String title, String description, int color, int day, int month, int year, int length, Context context, int urgency, int isComplete)
    {
        int dayID = getOrMakeDay(day, month, year, context);
        Log.d(TAG, "Using " + dayID  + " for dayId");
        DbTaskHelper helper = new DbTaskHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues newTask = new ContentValues();
        newTask.put(SqlContract.FeedTasks.COLUMN_TASK_NAME, title);
        newTask.put(SqlContract.FeedTasks.COLUMN_DESCRIPTION, description);
        newTask.put(SqlContract.FeedTasks.COLUMN_COLOR, color);
        newTask.put(SqlContract.FeedTasks.COLUMN_DAY, day);
        newTask.put(SqlContract.FeedTasks.COLUMN_MONTH, month);
        newTask.put(SqlContract.FeedTasks.COLUMN_YEAR, year);
        newTask.put(SqlContract.FeedTasks.COLUMN_URGANCY, urgency);
        newTask.put(SqlContract.FeedTasks.COLUMN_IS_COMPLETE, isComplete);
        newTask.put(SqlContract.FeedTasks.COLUMN_DAYID, dayID);
        newTask.put(SqlContract.FeedTasks.COLUMN_LENGTH, length);
        try {
            db.insertOrThrow(SqlContract.FeedTasks.TABLE_NAME, null, newTask);
        }catch (SQLException e)
        {
            Toast.makeText(context, "Failed to make task. Reason: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to make task. Reason: " + e.getMessage());
        }
        db.close();
    }

    private static int getOrMakeDay(int day, int month, int year, Context context)
    {
        Log.d(context.getClass().getSimpleName(), "Getting or making day");
        DbDayHelper helper = new DbDayHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String daySelection = SqlContract.FeedDays.COLUMN_DAY + " = ? AND " + SqlContract.FeedDays.COLUMN_MONTH + " = ? AND " + SqlContract.FeedDays.COLUMN_YEAR + " = ?";
        String[] selectionArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};

        Cursor cursor = db.query(
                SqlContract.FeedDays.TABLE_NAME,
                SqlContract.getColumnsDay(),
                daySelection,
                selectionArgs,
                null,
                null,
                SqlContract.FeedDays.sortOrder
        );
        cursor.moveToFirst();
        Log.d(context.getClass().getSimpleName(), "Found day: " + cursor.getCount());
        if(cursor.getCount() < 1)
        {
            Log.d(TAG, "No day found, creating new one");
            return makeDay(day, month, year, context);
        }
        int ID = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedDays._ID));
        db.close();
        Log.d(TAG, "Day found, returning " + ID);
        return ID;
    }

    private static int makeDay(int day, int month, int year, Context context)
    {
        DbDayHelper helper = new DbDayHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues newDay = new ContentValues();
        newDay.put(SqlContract.FeedDays.COLUMN_DAY, day);
        newDay.put(SqlContract.FeedDays.COLUMN_MONTH, month);
        newDay.put(SqlContract.FeedDays.COLUMN_YEAR, year);
        long ID = db.insertOrThrow(SqlContract.FeedDays.TABLE_NAME, null, newDay);
        Log.d(TAG, "Made day with id " + ID);
        Log.e(TAG, "Specs: Day: " +day + " Month: " + month + " Year: " + year);
        db.close();
        return (int) ID;
    }

    public static int parseLength(String s)
    {
        char[] input = s.toCharArray();

        int i = 0;
        while(input[i] != ':' && i < s.length())
        {
            i++;
        }

        if(i == s.length())
        {
            if(s.charAt(0) == ':')
            {
                return Integer.valueOf(s.substring(1,s.length()));
            }
            else
            {
                return Integer.valueOf(s);
            }

        }
        int hours = Integer.valueOf(s.substring(0,i));
        int minutes = Integer.valueOf(s.substring(i+1,s.length()));
        return minutes + (hours * 60);

    }

    public static void printAllTasks(Context c) throws utilityDatabaseError {
        ArrayList<Task> tasks = getAllTasks(c);
        for(Task task : tasks)
        {
            task.printInfo();
        }
    }

    public static void printAllDays(Context c) throws utilityDatabaseError {
        ArrayList<Day> Days = getAllDays(c);
        for(Day d : Days)
        {
            d.printInfo();
        }
    }

    public static Task getNextTask(Activity activity)
    {
        DbTaskHelper helper = new DbTaskHelper(activity);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(
                SqlContract.FeedTasks.TABLE_NAME,
                SqlContract.getColumnsTask(),
                null,
                null,
                null,
                null,
                SqlContract.FeedTasks.COLUMN_YEAR + " ASC," + SqlContract.FeedTasks.COLUMN_MONTH + " ASC," + SqlContract.FeedTasks.COLUMN_DAY + " ASC",
                "1"

        );
        c.moveToFirst();
        if(c.getCount() == 0)
        {
            return null;
        }
        return new Task(c);


    }

    public static ArrayList<String> getHours()
    {
        ArrayList<String> h = new ArrayList<>();
        for(int i = 0; i < 24; i++)
        {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR,i);
            h.add(justHour.format(c.getTime()));
        }
        return h;
    }


    public static class utilityDatabaseError extends Throwable {
        public utilityDatabaseError(String message) {
            super(message);
        }
    }
}

