package com.journaldev.navigationviewexpandablelistview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.journaldev.navigationviewexpandablelistview.MainActivity;
import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.utils.SharePref;
import com.journaldev.navigationviewexpandablelistview.view.ControlActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class StartFragment extends BaseFragment {
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
            if (getArguments().getInt("type")==FIRST){
                tvTitle.setText(getActivity().getResources().getString(R.string.first));
                ((MainActivity) getActivity()).switchFragment(StepFragment.newInstance());
            }else {
                pref.putBoolean(SharePref.NAME_FIRST_OPEN, false);
                ControlActivity.startControl(getActivity());
            }

        }
    }

    @Override
    protected void initData() {
        pref = new SharePref(getActivity());
        if (getArguments().getInt("type")==FIRST){
            tvTitle.setText(getActivity().getResources().getString(R.string.first));
        }else {
            tvTitle.setText(getActivity().getResources().getString(R.string.end));
        }
    }
}
