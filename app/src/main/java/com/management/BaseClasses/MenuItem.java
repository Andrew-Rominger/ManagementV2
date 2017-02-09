package com.management.BaseClasses;

import com.management.R;

import java.util.ArrayList;

/**
 * Created by wesleybanghart on 2/8/17.
 */

public class MenuItem {
    private String name;
    private int filePath;
    MenuItem(String name, int filePath)
    {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilePath() {
        return filePath;
    }

    public void setFilePath(int filePath) {
        this.filePath = filePath;
    }

   public static ArrayList<MenuItem> getData()
    {
        ArrayList<MenuItem> data = new ArrayList<>();
        data.add(new MenuItem("Home", R.drawable.home));
        data.add(new MenuItem("Calender", R.drawable.calendar));
        data.add(new MenuItem("Schedule", R.drawable.pencil));
        data.add(new MenuItem("Tasks", R.drawable.check));
        data.add(new MenuItem("Settings", R.drawable.settings));
        return data;
    }

}
