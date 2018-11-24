package com.manage.drone.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.manage.drone.AppAction;
import com.manage.drone.AppConstant;
import com.manage.drone.R;
import com.manage.drone.StringUtil;
import com.manage.drone.utils.SharePref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class OperationFragment extends BaseFragment implements AppConstant {
    private SharePref pref;
    @BindView(R.id.edtHeight)
    EditText edtHeight;
    @BindView(R.id.edtSpeed)
    EditText edtSpeed;
    @BindView(R.id.switch_send_data)
    Switch switch_fly;
    @BindView(R.id.txtSendData)
    TextView tvFly;
    @BindView(R.id.radio_auto_fly)
    RadioButton radioAuto;
    @BindView(R.id.radio_manual_fly)
    RadioButton radioManual;

    public static OperationFragment newInstance() {

        Bundle args = new Bundle();
        OperationFragment fragment = new OperationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.operation_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        pref = new SharePref(getActivity());
    }

    @Override
    protected void initData() {
        switch_fly.setChecked(pref.getBooleanValue(SharePref.NAME_SWITCH));
        edtHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString()) || StringUtil.isEmpty(edtSpeed.getText().toString())) {
                    bus.post(AppAction.REVERSE_STEP_2);
                } else {
                    bus.post(AppAction.DONE_STEP_2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(s.toString()) || StringUtil.isEmpty(edtSpeed.getText().toString())) {
                    bus.post(AppAction.REVERSE_STEP_2);
                } else {
                    bus.post(AppAction.DONE_STEP_2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setTextFly();

    }

    @OnCheckedChanged(R.id.switch_send_data)
    public void onSwitchChange() {

        pref.putBoolean(SharePref.NAME_SWITCH, !pref.getBooleanValue(SharePref.NAME_SWITCH));
        switch_fly.setChecked(pref.getBooleanValue(SharePref.NAME_SWITCH));
        setTextFly();
    }

    private void setTextFly() {
        tvFly.setText(pref.getBooleanValue(SharePref.NAME_SWITCH) ? getResources().getString(R.string.auto_sendata) : getResources().getString(R.string.not_send_data));
    }

}
