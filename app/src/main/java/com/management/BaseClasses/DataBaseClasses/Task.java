package com.management.BaseClasses.DataBaseClasses;

import android.database.Cursor;
import android.support.annotation.ColorRes;
import android.util.Log;

import com.management.Utilities;
import com.management.sqldatabase.SqlContract;

import java.util.Calendar;

/**
 * Created by Andrew on 2/8/2017.
 */

public class Task
{
    private static final String TAG = Task.class.getSimpleName();
    private String title;
    @ColorRes private int color;
    private int isComplete;

    private String description;

    private int urgency;

    private Calendar date;
    private Calendar endDate;

    private int day;
    private int month;
    private int year;

    private int length;

    private int dayKey;

    public Task(Cursor cursor)
    {
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_TASK_NAME));
        this.description = cursor.getString(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_DESCRIPTION));
        this.color = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_COLOR));
        this.isComplete =cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_IS_COMPLETE));
        this.urgency = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_URGANCY));
        this.day = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_DAY));
        this.month = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_MONTH));
        this.year = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_YEAR));
        this.length = cursor.getInt(cursor.getColumnIndexOrThrow(SqlContract.FeedTasks.COLUMN_LENGTH));
        this.date = Calendar.getInstance();
        this.date.set(Calendar.YEAR, year);
        this.date.set(Calendar.MONTH, month);
        this.date.set(Calendar.DAY_OF_MONTH, day);
        this.endDate = (Calendar) date.clone();
        endDate.add(Calendar.MINUTE, length);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        updateEndDate();
    }

    public Task(String title, int color, int isComplete, String description, int urgency, int day, int month, int year, int length)
    {
        this.title = title;
        this.color = color;
        this.isComplete = isComplete;
        this.description = description;
        this.urgency = urgency;
        this.day = day;
        this.month = month;
        this.year = year;
        this.length = length;
        this.date = Calendar.getInstance();
        this.date.set(Calendar.YEAR, year);
        this.date.set(Calendar.MONTH, month);
        this.date.set(Calendar.DAY_OF_MONTH, day);
        this.endDate = (Calendar) date.clone();
        endDate.add(Calendar.MINUTE, length);
    }

    public Task(){};
    public void printInfo()
    {
        Log.w(TAG, "-----------------------------------------------------------------------------");
        Log.w(TAG, "1.Title: " + title);
        Log.w(TAG, "2.color: " + color);
        Log.w(TAG, "3.isComplete: " + isComplete);
        Log.w(TAG, "4.description: " + description);
        Log.w(TAG, "7.urgency: " + urgency);
        Log.w(TAG, "8.Day: " + day);
        Log.w(TAG, "9.Month: " + month);
        Log.w(TAG, "10.Year: " + year);
        Log.w(TAG, "11.Day Key: " + dayKey);
        Log.w(TAG, "12.Length: " + length);
        Log.w(TAG, "13.StartDateCalendar: " + Utilities.fullDateWithTime.format(date.getTime()));
        Log.w(TAG, "14.End Date Calendar: " + Utilities.fullDateWithTime.format(endDate.getTime()));
        Log.w(TAG, "-----------------------------------------------------------------------------");
    }

    //Getter and Setter methods
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        this.date.set(Calendar.DAY_OF_MONTH, day);
        updateEndDate();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        this.date.set(Calendar.MONTH, month);
        updateEndDate();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        this.date.set(Calendar.YEAR, year);
        updateEndDate();
    }

    private void updateEndDate()
    {
        this.endDate = (Calendar) date.clone();
        endDate.add(Calendar.MINUTE, length);
    }

    public Calendar getEndDate() {
        return endDate;
    }


    public Calendar getDate() {
        return date;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDayKey() {
        return dayKey;
    }

    public void setDayKey(int dayKey) {
        this.dayKey = dayKey;
    }
}
