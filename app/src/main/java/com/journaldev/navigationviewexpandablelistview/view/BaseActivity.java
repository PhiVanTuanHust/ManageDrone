package com.journaldev.navigationviewexpandablelistview.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.fragment.StartFragment;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected abstract int getLayoutRes();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        initView();

        initData();

    }
}
