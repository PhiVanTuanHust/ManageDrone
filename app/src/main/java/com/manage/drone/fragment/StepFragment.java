package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.manage.drone.AppAction;
import com.manage.drone.AppConstant;
import com.manage.drone.R;
import com.manage.drone.adapter.AdapterStep;
import com.manage.drone.customs.Step;
import com.manage.drone.customs.StepView;
import com.manage.drone.customs.ViewPagerDisable;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class StepFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPagerDisable viewPager;
    @BindView(R.id.stepView)
    StepView stepView;
    List<Step> steps = new ArrayList<>();


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
        steps.add(new Step(getString(R.string.step_1), false));
        steps.add(new Step(getString(R.string.step_2), false));
        steps.add(new Step(getString(R.string.step_3), false));
        steps.add(new Step(getString(R.string.step_4), false));
        stepView.setSteps(steps);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                viewPager.setCurrentItem(step);
            }
        });
        adapter = new AdapterStep(getActivity().getSupportFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stepView.go(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Subscribe
    public void onAppAction(AppAction appAction) {
        if (appAction == AppAction.DONE_STEP_1) {
            stepView.setDone(0);
            if (stepView.getSteps().get(1).isDone() && stepView.getSteps().get(2).isDone())
                stepView.setDone(3);
        } else if (appAction == AppAction.DONE_STEP_2) {
            stepView.setDone(1);
            if (stepView.getSteps().get(0).isDone() && stepView.getSteps().get(2).isDone())
                stepView.setDone(3);
        } else if (appAction == AppAction.DONE_STEP_3) {
            stepView.setDone(2);
            if (stepView.getSteps().get(0).isDone() && stepView.getSteps().get(1).isDone())
                stepView.setDone(3);
        } else if (appAction == AppAction.REVERSE_STEP_2) {
            stepView.reverse(1);
            stepView.reverse(2);
        } else if (appAction == AppAction.CHECK_STEP) {
            List<Step> steps = stepView.getSteps();
            if (steps != null) {
                if (steps.get(0).isDone() && steps.get(1).isDone() && steps.get(2).isDone()) {
                    bus.post(AppAction.DO_START);
                } else {
                    Toast.makeText(getContext(), "Chưa hoàn thành tất cả các bước", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
