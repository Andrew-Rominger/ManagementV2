package com.management.Adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.model.Headers;
import com.management.BaseClasses.recviewHeader;
import com.management.R;
import com.management.Utilities;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wesleybanghart on 2/11/17.
 */

public class sortAdapter extends RecyclerView.Adapter<sortAdapter.viewHolderHeader> {

    ArrayList<recviewHeader> data;
    LayoutInflater inflater;
    Context c;
    public sortAdapter(ArrayList<recviewHeader> data,Context c)
    {
        this.data = data;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public sortAdapter.viewHolderHeader onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolderHeader(inflater.inflate(R.layout.sort_item_layout, parent, false),c);
    }

    @Override
    public void onBindViewHolder(sortAdapter.viewHolderHeader holder, int position) {
        recviewHeader header = data.get(position);
        holder.setData(header, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void updatedList()
    {
        notifyDataSetChanged();
    }

    void setData(ArrayList<recviewHeader> data)
    {
        this.data = data;
    }

    public class viewHolderHeader extends RecyclerView.ViewHolder
    {
        Context context;
        RecyclerView taskListRecView;
        TextView label;
        public viewHolderHeader(View itemView, Context c)
        {
            super(itemView);
            this.context = c;
            this.taskListRecView = (RecyclerView) itemView.findViewById(R.id.itemRecyclerView);
            this.label = (TextView) itemView.findViewById(R.id.sortItemHeader);
        }

        public void setData(recviewHeader header, int position)
        {
            label.setText(header.getTitle());
            taskListAdapter adapter = new taskListAdapter();
            taskListRecView.setAdapter(adapter);
            LinearLayoutManager linLayoutVertical = new LinearLayoutManager(context)
            {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linLayoutVertical.setOrientation(LinearLayoutManager.VERTICAL);
            taskListRecView.setLayoutManager(linLayoutVertical);
            taskListRecView.setItemAnimator(new DefaultItemAnimator());
        }
    }

}

