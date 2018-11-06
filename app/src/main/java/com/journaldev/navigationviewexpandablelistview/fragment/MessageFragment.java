package com.journaldev.navigationviewexpandablelistview.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.journaldev.navigationviewexpandablelistview.MainActivity;
import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.adapter.AdapterMessage;
import com.journaldev.navigationviewexpandablelistview.adapter.BaseRecycleViewAdapter;
import com.journaldev.navigationviewexpandablelistview.models.MessageModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 1/11/2018.
 */

public class MessageFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener {
    @BindView(R.id.rcView)
    RecyclerView rcView;
    private AdapterMessage adapter;

    public static MessageFragment newInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        adapter = new AdapterMessage(getActivity(), this);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.addItemDecoration(new DividerItemDecoration(rcView.getContext(), DividerItemDecoration.VERTICAL));
        adapter.loadData(new MessageModel().getMessage());
    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity)getActivity()).switchFragment(ObserveFragment.newInstance());
    }
}
