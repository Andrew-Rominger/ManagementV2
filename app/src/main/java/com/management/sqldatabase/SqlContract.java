package com.management.sqldatabase;

import android.provider.BaseColumns;

import com.management.R;

import java.util.ArrayList;

/**
 * Created by wesleybanghart on 2/10/17.
 */

public class SqlContract
{
    public ArrayList<String> list = new ArrayList<>();

    private SqlContract()
    {

    }

    public class FeedTasks implements BaseColumns
    {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_COLOR = "color";
        public static final String COLUMN_IS_COMPLETE = "isComplete";
        public static final String COLUMN_DESCRIPTION = "description";
        public  static final String COLUMN_URGANCY = "urgancy";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_LENGTH = "length";


        //Urgency Levels
        public static final int URGANCY_LOW = 0;
        public static final int URGANCY_MED = 1;
        public static final int URGANCY_HIGH = 2;

        //Urgency Colors
        public static final int URGANCY_LOW_COLOR = R.color.md_light_blue_100;
        public static final int URGANCY_MED_COLOR = R.color.md_orange_100;
        public static final int URGANCY_HIGH_COLOR = R.color.md_red_a200;

    }

    public class FeedDays implements BaseColumns
    {
        static final String TABLE_NAME = "daysWithTasks";
        static final String COLUMN_DAY = "day";
        static final String COLUMN_MONTH = "month";
        static final String COLUMN_YEAR = "year";

    }
    public static String[] getColumnsTask()
    {
        String[] list = {
                FeedTasks._ID,
                FeedTasks.COLUMN_TASK_NAME,
                FeedTasks.COLUMN_COLOR,
                FeedTasks.COLUMN_IS_COMPLETE,
                FeedTasks.COLUMN_DESCRIPTION,
                FeedTasks.COLUMN_URGANCY,
                FeedTasks.COLUMN_DAY,
                FeedTasks.COLUMN_MONTH,
                FeedTasks.COLUMN_YEAR,
                FeedTasks.COLUMN_LENGTH
        };
        return list;
    }

    public static String[] getColumnsDay()
    {
        String[] list = {
                FeedDays._ID,
                FeedDays.COLUMN_DAY,
                FeedDays.COLUMN_MONTH,
                FeedDays.COLUMN_YEAR
        };
        return list;
    }

}
