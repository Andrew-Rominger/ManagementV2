package com.management.sqldatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wesleybanghart on 2/10/17.
 */

public class DbTaskHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = " ,";
    private static final String INT_TYPE = " INTEGER";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SqlTaskContract.FeedTasks.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SqlTaskContract.FeedTasks.TABLE_NAME + " (" +
                    SqlTaskContract.FeedTasks._ID + " INTEGER PRIMARY KEY," +
                    SqlTaskContract.FeedTasks.COLUMN_TASK_NAME + TEXT_TYPE  + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_TIME_H + INT_TYPE  + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_URGANCY + INT_TYPE  + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_TIME_M + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_TIME_H + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_TIME_M + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_COLOR + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_IS_COMPLETE + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_DATE_DAY + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_DATE_MONTH + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_DATE_YEAR + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_DATE_DAY + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_DATE_MONTH + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_DATE_YEAR + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_START_DATE_MS + INT_TYPE + COMMA_SEP +
                    SqlTaskContract.FeedTasks.COLUMN_END_DATE_MS + INT_TYPE  +" )";

    public DbTaskHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onUpgrade(db, oldVersion, newVersion);
    }
}
