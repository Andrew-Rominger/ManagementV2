package com.management;

import android.app.Activity;
import android.app.Fragment;

import com.management.interfaces.CreateTaskListner;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Andrew on 2/13/2017.
 */

public class TaskCreatedBroadcaster
{
    static ArrayList<Object> subscribers = new ArrayList<>();

    public static void Subscribe(Object o)
    {
        subscribers.add(o);
    }
    public static void broadcast()
    {
        for (Object o : subscribers)
        {
            ((CreateTaskListner) o).recieveBroadcast();
        }
    }

}
