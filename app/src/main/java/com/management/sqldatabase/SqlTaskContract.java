package com.management.sqldatabase;

import android.provider.BaseColumns;

/**
 * Created by wesleybanghart on 2/10/17.
 */

public class SqlTaskContract {

    private SqlTaskContract()
    {

    }

    public static class FeedTasks implements BaseColumns

    {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_END_TIME_H = "startTimeH";
        public static final String COLUMN_END_TIME_M = "endTimeM";
        public static final String COLUMN_START_TIME_H = "startTimeH";
        public static final String COLUMN_START_TIME_M = "startTimeM";
        public static final String COLUMN_COLOR = "color";
        public static final String COLUMN_IS_COMPLETE = "isComplete";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URGANCY = "urgancy";

        //Urgency Levels
        public static final int URGANCY_LOW = 0;
        public static final int URGANCY_MED = 1;
        public static final int URGANCY_HIGH = 2;
    }
}
