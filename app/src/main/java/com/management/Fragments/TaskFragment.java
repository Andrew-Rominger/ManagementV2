package com.management.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.management.Adapter.headerAdapter;
import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.BaseClasses.RecyclerHeader;
import com.management.R;
import com.management.TaskCreatedBroadcaster;
import com.management.Utilities;
import com.management.interfaces.CreateTaskListner;

import java.util.ArrayList;

/**
 * Created by Andrew on 2/9/2017.
 */

public class TaskFragment extends Fragment implements CreateTaskListner
{
    private static final String TAG = TaskFragment.class.getSimpleName();

    RecyclerView mainList;
    headerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        TaskCreatedBroadcaster.Subscribe(this);
        return inflater.inflate(R.layout.taskfragmentlayout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        mainList = (RecyclerView) view.findViewById(R.id.taskListRecycler);
        setupRec(view);
        super.onViewCreated(view, savedInstanceState);
    }
    private void setupRec(View view)
    {
        ArrayList<RecyclerHeader> list;
        list = Utilities.getHeaders(view.getContext());
        adapter = new headerAdapter(list, view.getContext());
        Log.e(TAG, "Found " + list.size() + " headers");
        for(RecyclerHeader h : list)
        {
            ArrayList<Task> tList = h.getDisplayedTasks();
            for (Task t : tList)
            {
                t.printInfo();
            }
        }
        mainList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mainList.setLayoutManager(manager);
        mainList.setNestedScrollingEnabled(false);
        mainList.setItemAnimator(new DefaultItemAnimator());
    }
    private void changeSort()
    {

    }

    @Override
    public void recieveBroadcast()
    {
        Log.d(TAG, "TaskFragment Recieved broadcast");
        mainList.getAdapter().notifyDataSetChanged();
    }
}
