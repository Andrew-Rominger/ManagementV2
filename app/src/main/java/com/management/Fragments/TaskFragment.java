package com.management.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.management.Activities.AddTask;
import com.management.R;

/**
 * Created by Andrew on 2/9/2017.
 */

public class TaskFragment extends Fragment
{
    Spinner sortSpinner;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.taskfragmentlayout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sortSpinner = (Spinner) view.findViewById(R.id.taskListSortOrder);
        fab = (FloatingActionButton) view.findViewById(R.id.addTaskButton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortOptions, R.layout.simplespinnerlayout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getActivity(), AddTask.class),0);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
