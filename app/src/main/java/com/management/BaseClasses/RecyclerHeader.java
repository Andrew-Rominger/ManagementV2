package com.management.BaseClasses;

import com.management.BaseClasses.DataBaseClasses.Task;

import java.util.ArrayList;

/**
 * Created by Andre on 2/11/2017.
 */

public class RecyclerHeader
{
    String title;
    ArrayList<Task> displayedTasks;

    public RecyclerHeader(ArrayList<Task> displayedTasks) {
        this.displayedTasks = displayedTasks;
    }

    public RecyclerHeader(String title, ArrayList<Task> displayedTasks) {
        this.title = title;
        this.displayedTasks = displayedTasks;
    }

    public ArrayList<Task> getDisplayedTasks() {
        return displayedTasks;
    }

    public void setDisplayedTasks(ArrayList<Task> displayedTasks)
    {
        this.displayedTasks = displayedTasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
