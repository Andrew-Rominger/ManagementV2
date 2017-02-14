package com.management.Fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.management.BaseClasses.DataBaseClasses.Task;
import com.management.R;
import com.management.Utilities;

/**
 * Created by Andrew on 2/8/2017.
 */

public class HomeFragment extends Fragment
{
    TextView title;
    TextView description;
    TextView startTime;
    TextView endTime;
    ImageView circle;
    View bar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        title = (TextView) view.findViewById(R.id.homeScreenTitle);
        description = (TextView) view.findViewById(R.id.homeScreenDescription);
        startTime = (TextView) view.findViewById(R.id.homeScreenStartTime);
        endTime = (TextView) view.findViewById(R.id.homeScreenEndTime);
        circle = (ImageView) view.findViewById(R.id.homeScreenCircle);
        bar = view.findViewById(R.id.homeBar);
        setupViews();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupViews()
    {
        Task t = Utilities.getNextTask(getActivity());
        if(t!=null) {
            title.setText(t.getTitle());
            description.setText(t.getDescription());
            startTime.setText(Utilities.justTime.format(t.getDate().getTime()));
            endTime.setText(Utilities.justTime.format(t.getEndDate().getTime()));
            Drawable d = ContextCompat.getDrawable(getActivity(), R.drawable.circle);
            d.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getActivity(), t.getColor()), PorterDuff.Mode.SRC_IN));
            circle.setBackground(d);
        }
        else
        {
            title.setText("No Tasks");
            description.setText("");
            endTime.setText("");
            startTime.setText("");
            bar.setVisibility(View.INVISIBLE);
        }
    }
}
