package com.manage.drone.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.manage.drone.AppAction;
import com.manage.drone.AppConstant;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.utils.SharePref;
import com.manage.drone.view.ControlActivity;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class StartFragment extends BaseFragment implements AppConstant {
    public static int FIRST = 1;
    public static int END = 2;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private SharePref pref;

    public static StartFragment newInstance(int type) {
        Bundle args = new Bundle();
        StartFragment fragment = new StartFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.start_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }


    @OnClick(R.id.btnStart)
    public void startClick() {
        if (getActivity() != null) {
            if (getArguments().getInt("type") == FIRST) {
                tvTitle.setText(getActivity().getResources().getString(R.string.first));
                ((MainActivity) getActivity()).switchFragment(StepFragment.newInstance());
            } else {
                bus.post(AppAction.CHECK_STEP);
            }

        }
    }

    @Subscribe
    public void onAppAction(AppAction appAction) {
        if (appAction == AppAction.DO_START) {
            pref.putBoolean(SharePref.NAME_FIRST_OPEN, false);
            ControlActivity.startControl(getActivity());
        }
    }

    @Override
    protected void initData() {
        pref = new SharePref(getActivity());
        if (getArguments().getInt("type") == FIRST) {
            tvTitle.setText(getActivity().getResources().getString(R.string.first));
        } else {
            tvTitle.setText(getActivity().getResources().getString(R.string.end));
        }
    }
}
