package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.manage.drone.R;
import com.manage.drone.adapter.AdapterGuide;
import com.manage.drone.fragment.BaseFragment;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class GuideActivity extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    public static GuideActivity newInstance() {

        Bundle args = new Bundle();

        GuideActivity fragment = new GuideActivity();
        fragment.setArguments(args);
        return fragment;
    }
    private AdapterGuide adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }



    @Override
    protected void initData() {
       adapter=new AdapterGuide(getActivity().getSupportFragmentManager(),getActivity());
       viewPager.setAdapter(adapter);
    }

    public static void startGuide(Context context){
        Intent intent=new Intent(context,GuideActivity.class);
        context.startActivity(intent);
    }
}
