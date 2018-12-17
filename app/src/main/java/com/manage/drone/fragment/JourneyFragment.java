package com.manage.drone.fragment;

import android.os.Bundle;
import android.view.View;

import com.manage.drone.R;

import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public class JourneyFragment extends BaseFragment {
    public static JourneyFragment newInstance() {

        Bundle args = new Bundle();

        JourneyFragment fragment = new JourneyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.journey_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initData() {

    }
}
