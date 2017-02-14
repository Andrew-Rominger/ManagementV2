package com.management.BaseClasses.DataBaseClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.management.Utilities;
import com.management.sqldatabase.DbDayHelper;
import com.management.sqldatabase.DbTaskHelper;
import com.management.sqldatabase.SqlContract;

import java.util.ArrayList;
import java.util.Calendar;

import com.management.sqldatabase.SqlContract.FeedTasks.*;
/**
 * Created by Andrew on 2/13/2017.
 */

public class Day
{
    private static final String TAG = Day.class.getSimpleName();
    Calendar date;
    ArrayList<Task> tasksOnDay;
    int day;
    int month;
    int year;
    Context context;

    int ID;

    public Day(Cursor cursor)
    {
        this.day = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedDays.COLUMN_DAY));
        this.month = cursor.getInt(cursor.getColumnIndex(SqlContract.FeedDays.COLUMN_MONTH));
        this.year = cursor.getInt(cursor.getColumnIndex(SqlContract.FeedDays.COLUMN_YEAR));
        Log.e(TAG, "Making Day from cursor: Day: " + day + " Month: " + month + " year: " + year );
        finishDay(day,month,year);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Day(int day, int month, int year, Context context, int ID)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.context = context;
        this.ID = ID;
        Log.e(TAG, "Making Day from info: Day: " + day + " Month: " + month + " year: " + year );
        finishDay(day, month, year);
    }

    public void printInfo()
    {
        Log.w(TAG, "-----------------------------------------------------------------------------");
        Log.w(TAG, "1.Day: " + day);
        Log.w(TAG, "2.Month: " + month);
        Log.w(TAG, "3.Year: " + year);
        Log.w(TAG, "4.ID: " + ID);
        Log.w(TAG, "-----------------------------------------------------------------------------");
    }

    public Day(Calendar date, Context context, int ID)
    {
        this.context = context;
        this.ID = ID;
        Log.e(TAG, "Making Day from calendar: " + Utilities.MonthDayYearsdf.format(date.getTime()));
        finishDay(date);

    }

    public int getNumTasks() {
        return tasksOnDay.size();
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Task> getTasksOnDay() {
        return tasksOnDay;
    }

    public void setTasksOnDay(ArrayList<Task> tasksOnDay) {
        this.tasksOnDay = tasksOnDay;
    }

    private void finishDay(int day, int month, int year)
    {
        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        Log.e(TAG, "Making day with "  + month + "/" + day + "/" + year);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }
    private void updateDay() throws databaseDateError {
        getTasks(context);
    }
    private void finishDay(Calendar date)
    {
        finishDay(date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
        Log.e(TAG, "Making day with "  + Utilities.MonthDayYearsdf.format(date.getTime()));
    }

    public ArrayList<Task> getTasks(Context context)
    {
        Log.e(TAG, "Getting tasks for " + Utilities.MonthDayYearsdf.format(date.getTime()));
        Log.e(TAG, "Getting tasks for " + month + "/" + day + "/" + year);
        if(tasksOnDay != null)
        {
            return tasksOnDay;
        }
        ArrayList <Task> list = new ArrayList<>();
        DbTaskHelper taskHelper = new DbTaskHelper(context);
        DbDayHelper dayHelper = new DbDayHelper(context);

        SQLiteDatabase taskDatabase = taskHelper.getReadableDatabase();
        SQLiteDatabase dayDatabase = dayHelper.getReadableDatabase();

        String daySelection = SqlContract.FeedDays.COLUMN_DAY + " = ? AND " + SqlContract.FeedDays.COLUMN_MONTH + " = ? AND " + SqlContract.FeedDays.COLUMN_YEAR + " = ?";

        String[] daySelectionArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};

        Cursor cursor = dayDatabase.query(
                SqlContract.FeedDays.TABLE_NAME,
                SqlContract.getColumnsDay(),
                daySelection,
                daySelectionArgs,
                null,
                null,
                SqlContract.FeedDays.COLUMN_YEAR + " ASC"
        );
        cursor.moveToFirst();
        Log.d(TAG, "Cursor Size: " + cursor.getCount());
        int dayID = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedDays._ID));
        cursor.close();

        String taskSelection = SqlContract.FeedTasks.COLUMN_DAYID + " = ?";
        String[] taskSelectionArgs = {String.valueOf(dayID)};
        String sortOrder = SqlContract.FeedTasks.COLUMN_YEAR + " ASC," + SqlContract.FeedTasks.COLUMN_MONTH + " ASC," + SqlContract.FeedTasks.COLUMN_DAY + " ASC";

        Cursor cursor1 = taskDatabase.query(
                SqlContract.FeedTasks.TABLE_NAME,
                SqlContract.getColumnsTask(),
                taskSelection,
                taskSelectionArgs,
                null,
                null,
                sortOrder

        );
        cursor1.moveToFirst();

        while (!cursor1.isAfterLast())
        {
            String title = cursor1.getString(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_TASK_NAME));
            String description = cursor1.getString(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_DESCRIPTION));
            int color = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_COLOR));
            int isComplete =cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_IS_COMPLETE));
            int urgency = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_URGANCY));
            int day = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_DAY));
            int month = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_MONTH));
            int year = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_YEAR));
            int length = cursor1.getInt(cursor1.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_LENGTH));
            list.add(new Task(title, color, isComplete, description, urgency, day, month, year, length));
            cursor1.moveToNext();
        }
        return list;
    }

    public class databaseDateError extends Throwable
    {
        public databaseDateError(String message) {
            super(message);
        }
    }
}
