package com.management.BaseClasses;

import android.widget.ArrayAdapter;

/**
 * Created by Andrew on 2/9/2017.
 */

public class TaskHour
{
    private ArrayAdapter<Task> tasksInHour;

    public ArrayAdapter<Task> getTasksInHour() {
        return tasksInHour;
    }

    public void setTasksInHour(ArrayAdapter<Task> tasksInHour) {
        this.tasksInHour = tasksInHour;
    }
}
