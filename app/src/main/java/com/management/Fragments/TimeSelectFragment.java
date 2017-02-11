package com.management.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.management.R;

import java.util.Calendar;


/**
 * Created by wesleybanghart on 2/10/17.
 */

public class TimeSelectFragment extends Fragment {

    LinearLayout minus;
    LinearLayout plus;
    TextView minusTextView;
    TextView plusTextView;
    int startTime;
    int endTime;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_select_fragment, container ,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        minus = (LinearLayout) view.findViewById(R.id.minusLL);
        plus = (LinearLayout) view.findViewById(R.id.plusLL);
        minusTextView = (TextView) view.findViewById(R.id.minusTime);
        plusTextView = (TextView) view.findViewById(R.id.plusTime);
        Calendar c = Calendar.getInstance();
        startTime = c.get(Calendar.HOUR);
        endTime = c.get(Calendar.HOUR) + 1;
        minusTextView.setText(String.valueOf(startTime));
        plusTextView.setText(String.valueOf(endTime));

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime--;
                minusTextView.setText(String.valueOf(startTime));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime++;
                plusTextView.setText(String.valueOf(endTime));
            }
        });
    }

}
