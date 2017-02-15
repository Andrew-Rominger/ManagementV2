package com.management.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.BaseClasses.RecyclerHeader;
import com.management.R;
import com.management.Utilities;

import java.util.ArrayList;


/**
 * Created by Andre on 2/11/2017.
 */

public class taskListAdapter extends RecyclerView.Adapter<taskListAdapter.taskViewHolder> {
    private static final String TAG = taskListAdapter.class.getSimpleName();
    LayoutInflater inflater;
    Context context;
    ArrayList<Task> taskList;
    RecyclerHeader header;
    public taskListAdapter(Context context, RecyclerHeader header)
    {
        this.context = context;
        this.header = header;
        this.taskList = header.getDisplayedTasks();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public taskListAdapter.taskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new taskListAdapter.taskViewHolder(inflater.inflate(R.layout.taskinlist, parent, false));
    }

    @Override
    public void onBindViewHolder(taskListAdapter.taskViewHolder holder, int position)
    {
        Log.i(TAG, "Binding data for Task " + position);
        holder.setData(taskList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return taskList.size();
    }


    public class taskViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        TextView startTime;
        TextView endTime;
        ImageView colorCircle;

        public taskViewHolder(View inflate)
        {
            super(inflate);
            title = (TextView) inflate.findViewById(R.id.TV_taskInListTitle);
            description = (TextView) inflate.findViewById(R.id.TV_taskInListDes);
            startTime = (TextView) inflate.findViewById(R.id.TV_taskInListStartTime);
            endTime = (TextView) inflate.findViewById(R.id.TV_taskInListEndTime);
            colorCircle = (ImageView) inflate.findViewById(R.id.IV_taskInListColor);

        }

        public void setData(Task task)
        {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            startTime.setText(Utilities.justTime.format(task.getDate().getTime()));
            endTime.setText(Utilities.justTime.format(task.getEndDate().getTime()));
            Drawable d = ContextCompat.getDrawable(context, R.drawable.circle);
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context,task.getColor()), PorterDuff.Mode.SRC_IN));
            colorCircle.setBackground(d);
        }
    }
}
