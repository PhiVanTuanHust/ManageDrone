package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.manage.drone.R;
import com.manage.drone.adapter.ViewPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView() {
        setTitle("File của bạn");
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public static void startGalleryActivity(Context context){
        Intent intent=new Intent(context,GalleryActivity.class);
        context.startActivity(intent);
    }


}
