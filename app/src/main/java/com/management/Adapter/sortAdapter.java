package com.management.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.management.Utilities;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wesleybanghart on 2/11/17.
 */

public class sortAdapter extends RecyclerView.Adapter<sortAdapter.viewHolderHeader> {

    ArrayList data;
    int sortBy;
    ArrayList<Calendar> calendars;
    public sortAdapter(ArrayList data, int sortBy,Context c) {
        this.data = data;
        this.sortBy = sortBy;
        this.calendars = Utilities.getDaysWithTasks(c, sortBy);
    }


    @Override
    public sortAdapter.viewHolderHeader onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(sortAdapter.viewHolderHeader holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    private class viewHolderHeader()
    {

    }
}

