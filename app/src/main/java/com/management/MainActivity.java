package com.management;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.management.Activities.AddTask;
import com.management.Adapter.MenuAdapter;
import com.management.BaseClasses.MenuItem;
import com.management.Fragments.HomeFragment;
import com.management.Fragments.TaskFragment;
import com.management.Fragments.calendarFragment;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ListView navList;
    MenuAdapter menuAdapter;
    String TAG = MainActivity.class.getSimpleName();
    FrameLayout contentHolder;


    FragmentManager fm;
    FragmentTransaction transaction;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addTask:
                startActivityForResult(new Intent(this, AddTask.class),0);
                break;
            case R.id.settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_mainDrawer);
        navList = (ListView) findViewById(R.id.navMenuList);

        menuAdapter = new MenuAdapter(this, MenuItem.getData());

        navList.setAdapter(menuAdapter);
        navList.setOnItemClickListener(new DrawerItemClickListener());
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentHolder, new HomeFragment(), "homeFragment");
        transaction.commit();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG,"Item " + position + " clicked");
            drawerLayout.closeDrawers();
            switch (position)
            {
                case 0:
                    moveToHome();
                    break;
                case 1:
                    moveToCalendar();
                    break;
                case 2:
                    moveToSchedule();
                    break;
                case 3:
                    moveToTasks();
                    break;
                case 4:
                    moveToSettings();
                    break;
            }

        }
    }

    private void moveToSettings()
    {

    }

    private void moveToTasks()
    {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentHolder, new TaskFragment());
        transaction.commit();

    }
    //method that changes the current fragment to the schedule fragment
    //called at case 2
    private void moveToSchedule()
    {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentHolder, new scheduleFragment());
        transaction.commit();
    }

    private void moveToCalendar()
    {
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentHolder, new calendarFragment());
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void moveToHome()
    {
        transaction = fm.beginTransaction();
        HomeFragment homeFragment =  new HomeFragment();
        transaction.replace(R.id.contentHolder,homeFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
