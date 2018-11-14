package com.manage.drone;

import android.content.Intent;
import android.os.Bundle;

import android.os.Environment;
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

import com.manage.drone.adapter.ExpandableListAdapter;
import com.manage.drone.fragment.BaseFragment;
import com.manage.drone.fragment.ConnectFragment;
import com.manage.drone.fragment.MessageFragment;
import com.manage.drone.fragment.ObserveFragment;
import com.manage.drone.fragment.OperationFragment;
import com.manage.drone.fragment.QuestionFragment;
import com.manage.drone.fragment.StartFragment;
import com.manage.drone.fragment.ZoningFragment;
import com.manage.drone.utils.SharePref;
import com.manage.drone.view.ControlActivity;
import com.manage.drone.view.GuideActivity;
import com.manage.drone.view.ProfileActivity;

import java.io.File;

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
        prefs = new SharePref(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (prefs.getBooleanValue(SharePref.NAME_FIRST_OPEN)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, StartFragment.newInstance(StartFragment.FIRST), StartFragment.class.getSimpleName());
            transaction.commit();
        } else {
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
        switch (item.getItemId()){
            case R.id.action_profile:
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
