package com.management.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.management.BaseClasses.DataBaseClasses.Task;

import java.util.ArrayList;

/**
 * Created by Andre on 2/11/2017.
 */

public class taskListAdapter extends RecyclerView.Adapter<taskListAdapter.viewHolderTask> {
    LayoutInflater inflater;
    Context context;
    ArrayList<Task> data;
    @Override
    public taskListAdapter.viewHolderTask onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return inflater.inflate(R)
    }

    @Override
    public void onBindViewHolder(taskListAdapter.viewHolderTask holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class  viewHolderTask extends RecyclerView.ViewHolder{


        public viewHolderTask(View itemView) {
            super(itemView);
        }
    }
}
