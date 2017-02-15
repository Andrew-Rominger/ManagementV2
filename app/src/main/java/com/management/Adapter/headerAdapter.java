package com.management.Adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.management.BaseClasses.RecyclerHeader;
import com.management.R;

import java.util.ArrayList;

/**
 * Created by wesleybanghart on 2/11/17.
 */

public class headerAdapter extends RecyclerView.Adapter<headerAdapter.headerViewHolder>
{
    private static final String TAG = headerAdapter.class.getSimpleName();
    LayoutInflater inflater;
    Context c;
    ArrayList<RecyclerHeader> headers = new ArrayList<>();
    public headerAdapter(ArrayList<RecyclerHeader> data, Context c)
    {
        this.c = c;
        this.headers = data;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public headerAdapter.headerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new headerViewHolder(inflater.inflate(R.layout.sort_item_layout, parent, false),c);
    }

    @Override
    public void onBindViewHolder(headerViewHolder holder, int position)
    {
        Log.i(TAG, "Binding data for header " + position);
        holder.setData(headers.get(position));
    }
    @Override
    public int getItemCount() {
        return headers.size();
    }
    public void updateList()
    {
        notifyDataSetChanged();
    }

    void setData(ArrayList<RecyclerHeader> data)
    {
        this.headers = data;
    }

    public class headerViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView taskList;
        Context context;
        TextView header;

        public headerViewHolder(View inflate, Context c)
        {
            super(inflate);
            context = c;
            taskList = (RecyclerView) inflate.findViewById(R.id.itemRecyclerView);
            header = (TextView) inflate.findViewById(R.id.sortItemHeader);
        }

        public void setData(RecyclerHeader recyclerHeader)
        {
            this.taskList.setAdapter(new taskListAdapter(c, recyclerHeader));
            header.setText(recyclerHeader.getTitle());
            LinearLayoutManager manager = new LinearLayoutManager(context){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            taskList.setLayoutManager(manager);
            taskList.setItemAnimator(new DefaultItemAnimator());
        }
    }

}

