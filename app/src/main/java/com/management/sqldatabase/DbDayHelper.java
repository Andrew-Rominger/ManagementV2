package com.management.sqldatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 2/13/2017.
 */

public class DbDayHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "days.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INT_TYPE = " INTEGER";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SqlContract.FeedDays.TABLE_NAME +
                    " (" +
                    SqlContract.FeedDays._ID + " INTEGER PRIMARY KEY," +
                    SqlContract.FeedDays.COLUMN_DAY + INT_TYPE  + COMMA_SEP +
                    SqlContract.FeedDays.COLUMN_MONTH + INT_TYPE  + COMMA_SEP +
                    SqlContract.FeedDays.COLUMN_YEAR + INT_TYPE
                    + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +SqlContract.FeedDays.TABLE_NAME;

    public DbDayHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
