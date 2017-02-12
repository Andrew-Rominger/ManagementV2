package com.management.BaseClasses;

import com.management.BaseClasses.DataBaseClasses.Task;

import java.util.ArrayList;

/**
 * Created by Andre on 2/11/2017.
 */

public class recviewHeader
{
    String title;
    ArrayList<Task> displayedTasks;

    public ArrayList<Task> getDisplayedTasks() {
        return displayedTasks;
    }

    public void setDisplayedTasks(ArrayList<Task> displayedTasks) {
        this.displayedTasks = displayedTasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
