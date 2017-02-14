package com.management.interfaces;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Andrew on 2/13/2017.
 */

public class CreateTaskListner
{
    static ArrayList<Activity>  subscribers = new ArrayList<>();

    public static void Subscribe(Activity a)
    {
        subscribers.add(a);
    }


}
