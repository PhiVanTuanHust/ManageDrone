package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutRes(), container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        ButterKnife.bind(this,view);
        initData();
        super.onViewCreated(view, savedInstanceState);
    }
}
