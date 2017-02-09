package com.management.BaseClasses;

import android.graphics.Color;

/**
 * Created by Andrew on 2/8/2017.
 */

public class Task
{
    private String title;
    private int endTimeH;
    private int endTimeM;
    private int startTimeH;
    private int startTimeM;
    private Color color;
    private boolean isComplete;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEndTimeH() {
        return endTimeH;
    }

    public void setEndTimeH(int endTimeH) {
        this.endTimeH = endTimeH;
    }

    public int getEndTimeM() {
        return endTimeM;
    }

    public void setEndTimeM(int endTimeM) {
        this.endTimeM = endTimeM;
    }

    public int getStartTimeH() {
        return startTimeH;
    }

    public void setStartTimeH(int startTimeH) {
        this.startTimeH = startTimeH;
    }

    public int getStartTimeM() {
        return startTimeM;
    }

    public void setStartTimeM(int startTimeM) {
        this.startTimeM = startTimeM;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
