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
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SqlContract.FeedTasks.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SqlContract.FeedTasks.TABLE_NAME + " (" +
                    SqlContract.FeedTasks._ID + " INTEGER PRIMARY KEY," +
                    SqlContract.FeedTasks.COLUMN_TASK_NAME + TEXT_TYPE  + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_URGANCY + INT_TYPE  + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_COLOR + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_IS_COMPLETE + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_DAY + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_MONTH + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_YEAR + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_DAYID + INT_TYPE + COMMA_SEP +
                    SqlContract.FeedTasks.COLUMN_LENGTH + INT_TYPE +
                    " )";

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
