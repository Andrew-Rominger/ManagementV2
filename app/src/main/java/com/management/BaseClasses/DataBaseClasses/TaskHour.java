package com.management.BaseClasses.DataBaseClasses;

import java.util.ArrayList;

/**
 * Created by Andrew on 2/9/2017.
 */

public class TaskHour
{
    private ArrayList<Task> tasksInHour;

    public ArrayList<Task> getTasksInHour() {
        return tasksInHour;
    }

    public void setTasksInHour(ArrayList<Task> tasksInHour) {
        this.tasksInHour = tasksInHour;
    }
}
