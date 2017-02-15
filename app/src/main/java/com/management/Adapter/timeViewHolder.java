package com.management.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.management.R;

/**
 * Created by Andrew on 2/14/2017.
 */

public class timeViewHolder extends RecyclerView.ViewHolder {

    TextView data;

    public timeViewHolder(View itemView)
    {
        super(itemView);
        data = (TextView) itemView.findViewById(R.id.infoInScroller);
    }


}
