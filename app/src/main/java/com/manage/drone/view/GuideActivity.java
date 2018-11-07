package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.manage.drone.R;
import com.manage.drone.adapter.AdapterGuide;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


    private AdapterGuide adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
       adapter=new AdapterGuide(getSupportFragmentManager(),this);
       viewPager.setAdapter(adapter);
    }

    public static void startGuide(Context context){
        Intent intent=new Intent(context,GuideActivity.class);
        context.startActivity(intent);
    }
}
