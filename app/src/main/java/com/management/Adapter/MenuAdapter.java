package com.management.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.management.BaseClasses.MenuItem;
import com.management.R;

import java.util.ArrayList;

/**
 * Created by wesleybanghart on 2/8/17.
 */

public class MenuAdapter extends ArrayAdapter<MenuItem> {

    public MenuAdapter(Context context, ArrayList<MenuItem> object) {
        super(context, 0 ,object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem current = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nav_menu_item, parent, false);

        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.navMenuIcon);
        TextView textView = (TextView) convertView.findViewById(R.id.navMenuTitle);

        textView.setText(current.getName());
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), current.getFilePath(), null));

        return convertView;
    }
}
