package com.manage.drone.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
