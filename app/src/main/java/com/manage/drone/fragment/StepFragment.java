package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.manage.drone.R;
import com.manage.drone.adapter.AdapterStep;
import com.manage.drone.utils.Const;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class StepFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


    private AdapterStep adapter;

    public static StepFragment newInstance() {
        Bundle args = new Bundle();
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.step_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void initData() {
        adapter = new AdapterStep(getActivity().getSupportFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        int position = viewPager.getCurrentItem();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, position);
                View view = fragment.getView();
                view.setBackgroundColor(getResources().getColor(Const.getGuide(getActivity()).get(position).getResColor()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
