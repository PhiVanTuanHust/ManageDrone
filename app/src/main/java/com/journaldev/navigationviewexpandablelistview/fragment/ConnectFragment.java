package com.journaldev.navigationviewexpandablelistview.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.adapter.BaseRecycleViewAdapter;
import com.journaldev.navigationviewexpandablelistview.adapter.ConnectAdapter;
import com.journaldev.navigationviewexpandablelistview.customs.VerticalSpacesItemDecoration;
import com.journaldev.navigationviewexpandablelistview.models.BaseItemModel;
import com.journaldev.navigationviewexpandablelistview.models.HeaderConnect;
import com.journaldev.navigationviewexpandablelistview.models.ItemConnect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ConnectFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener {
    public static ConnectFragment newInstance() {
        Bundle args = new Bundle();
        ConnectFragment fragment = new ConnectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rcvList)
    RecyclerView rcvList;
    ConnectAdapter connectAdapter;
    List<BaseItemModel> items = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.connect_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void initData() {
        rcvList.setLayoutManager(new LinearLayoutManager(getContext()));
        connectAdapter = new ConnectAdapter(getActivity(), this);
        rcvList.addItemDecoration(new VerticalSpacesItemDecoration(10, connectAdapter));
        rcvList.setAdapter(connectAdapter);
        dummy();
        connectAdapter.replace(items);

    }

    @Override
    public void onItemClick(int position) {
        BaseItemModel item = connectAdapter.getItem(position);
        if (item != null) {
            showDialog(item.getTitle());
        }


    }

    private void showDialog(String title) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_edt, null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        dialog.setContentView(view);
        dialog.show();
    }

    private void dummy() {
        items.clear();
        items.add(new HeaderConnect("Đã kết nối"));
        items.add(new ItemConnect("DR11899922"));
        items.add(new ItemConnect("DR11890022"));
        items.add(new ItemConnect("DR12899922"));
        items.add(new ItemConnect("DR33899922"));
        items.add(new HeaderConnect("Drone có sẵn"));
        items.add(new ItemConnect("DR118992922"));
        items.add(new ItemConnect("DR118903022"));
        items.add(new ItemConnect("DR128994922"));
        items.add(new ItemConnect("DR3389249922"));
        items.add(new ItemConnect("DR1189494922"));
        items.add(new ItemConnect("DR1189044222"));
        items.add(new ItemConnect("DR1289992222"));
        items.add(new ItemConnect("DR3389944922"));
    }
}
