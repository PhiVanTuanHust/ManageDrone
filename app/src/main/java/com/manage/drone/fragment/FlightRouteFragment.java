package com.manage.drone.fragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Phí Văn Tuấn on 14/11/2018.
 */

public class FlightRouteFragment extends BaseFragment {
    public static FlightRouteFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FlightRouteFragment fragment = new FlightRouteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
