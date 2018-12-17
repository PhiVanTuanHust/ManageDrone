package com.manage.drone.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.manage.drone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public abstract class BaseListFragment extends BaseFragment {
    @BindView(R.id.rcView)
    protected RecyclerView rcView;
    @Override
    protected int getLayoutRes() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        rcView.setLayoutManager(getLayoutManger());
    }

    @Override
    protected void initData() {

    }
    protected abstract RecyclerView.LayoutManager getLayoutManger();
}
