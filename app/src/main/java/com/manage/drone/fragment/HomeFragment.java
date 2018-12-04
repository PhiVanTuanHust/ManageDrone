package com.manage.drone.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.manage.drone.AppAction;
import com.manage.drone.AppConstant;
import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.adapter.DashboardAdapter;
import com.manage.drone.customs.SpacesItemDecoration;
import com.manage.drone.customs.VerticalSpacesItemDecoration;
import com.manage.drone.utils.SharePref;
import com.manage.drone.view.ControlActivity;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class HomeFragment extends BaseFragment implements AppConstant {
    @BindView(R.id.rcvList)
    RecyclerView rcvList;
    DashboardAdapter dashboardAdapter;
    private SharePref prefs;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dardboard_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    protected void initData() {
        if (getActivity() != null)
            prefs = new SharePref(getActivity());
        dashboardAdapter = new DashboardAdapter();
        rcvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvList.addItemDecoration(new SpacesItemDecoration(10, true));
        rcvList.setAdapter(dashboardAdapter);
    }

    @OnClick(R.id.startBtn)
    public void onStartClick() {
        if (getActivity() != null) {
            if (prefs.getBooleanValue(SharePref.NAME_FIRST_OPEN)) {
                showDialog();
            } else {
                ControlActivity.startControl(getActivity());
            }
        }
    }

    public void showDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Thông báo")
                .setMessage("Đây là lần đầu sử dụng, hãy làm theo các bước để có thể bắt đầu điều khiển drone!")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((MainActivity) getActivity()).switchFragment(StepFragment.newInstance());
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
