package com.journaldev.navigationviewexpandablelistview;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.journaldev.navigationviewexpandablelistview.adapter.ExpandableListAdapter;
import com.journaldev.navigationviewexpandablelistview.fragment.BaseFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.ConnectFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.MessageFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.ObserveFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.OperationFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.QuestionFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.StartFragment;
import com.journaldev.navigationviewexpandablelistview.fragment.ZoningFragment;
import com.journaldev.navigationviewexpandablelistview.utils.SharePref;
import com.journaldev.navigationviewexpandablelistview.view.ControlActivity;
import com.journaldev.navigationviewexpandablelistview.view.GuideActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    private SharePref prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);
//        prepareMenuData();
        populateExpandableList();
        prefs=new SharePref(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (prefs.getBooleanValue(SharePref.NAME_FIRST_OPEN)){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, StartFragment.newInstance(StartFragment.FIRST), StartFragment.class.getSimpleName());
            transaction.commit();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, StartFragment.newInstance(StartFragment.END), StartFragment.class.getSimpleName());
            transaction.commit();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (groupPosition) {
                    case 0:
                        ControlActivity.startControl(MainActivity.this);
                        break;
                    case 2:
                        switchFragment(ZoningFragment.newInstance());
                        onBackPressed();
                        break;
                    case 3:
                        switchFragment(ObserveFragment.newInstance());
                        onBackPressed();
                        break;
                    case 4:
                        switchFragment(MessageFragment.newInstance());
                        onBackPressed();
                        break;
                    case 5:
                        onBackPressed();
                        break;
                    default:

                        break;
                }


                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 1) {
                    if (childPosition == 0) {
                        switchFragment(ConnectFragment.newInstance());
                    } else {
                        switchFragment(OperationFragment.newInstance());
                    }
                } else if (groupPosition == 6) {
                    if (childPosition == 0) {
                        GuideActivity.startGuide(MainActivity.this);
                    } else {
                        switchFragment(QuestionFragment.newInstance());
                    }
                }
                onBackPressed();
                return false;
            }
        });
    }

    public void switchFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getTag());
        transaction.commit();

    }
}
