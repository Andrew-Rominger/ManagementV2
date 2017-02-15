package com.management.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.management.R;

/**
 * Created by Andrew on 2/14/2017.
 */
public class ScrollerMinuteAdapter extends RecyclerView.Adapter<timeViewHolder>
{
    LayoutInflater inflater;

    public ScrollerMinuteAdapter(Activity activity)
    {
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public timeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new timeViewHolder(inflater.inflate(R.layout.scroller_item_layout,parent));
    }

    @Override
    public void onBindViewHolder(timeViewHolder holder, int position)
    {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
