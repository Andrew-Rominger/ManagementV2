package com.management.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.management.R;
import com.management.Utilities;

import java.util.ArrayList;

/**
 * Created by Andrew on 2/14/2017.
 */

public class ScrollerHourAdapter extends RecyclerView.Adapter<timeViewHolder>
{
    LayoutInflater inflater;
    ArrayList<String> data = Utilities.getHours();
    public ScrollerHourAdapter(Context c)
    {
        inflater = LayoutInflater.from(c);
    }
    @Override
    public timeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new timeViewHolder(inflater.inflate(R.layout.scroller_item_layout,parent));
    }

    @Override
    public void onBindViewHolder(timeViewHolder holder, int position)
    {
        holder.data.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
