package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.manage.drone.R;
import com.tony.drawing.Applications;
import com.tony.drawing.Drawer;
import com.tony.drawing.FlyCtrl;
import com.tony.drawing.Rudder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ControlActivity extends BaseActivity implements Rudder.OnRudderListener{
    @BindView(R.id.frame_back)
    FrameLayout frameBack;
    private Drawer mDrawer;
    @BindView(R.id.rudder)
    Rudder rudder;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_control;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        rudder.setOnRudderListener(this);
        this.mDrawer = new Drawer(this, this.rudder);
        mDrawer.stopDrawing();
    }

    @OnClick(R.id.frame_back)
    public void onBack(){
        onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public static void startControl(Context context){
        Intent intent=new Intent(context,ControlActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void OnLeftRudder(int x, int y) {
        if (!Applications.isRightHandMode) {
            FlyCtrl.rudderdata[3] = y;
            FlyCtrl.rudderdata[4] = x;
        } else if (Applications.isSensorOn) {
            FlyCtrl.rudderdata[4] = x;
        } else {
            FlyCtrl.rudderdata[2] = y;
            FlyCtrl.rudderdata[4] = x;
        }

    }

    @Override
    public void OnRightRudder(int x, int y, boolean highSpeed) {
        if (!Applications.isRightHandMode) {
            FlyCtrl.rudderdata[1] = x;
            FlyCtrl.rudderdata[2] = y;
        } else if (Applications.isSensorOn) {
            FlyCtrl.rudderdata[3] = y;
        } else {
            FlyCtrl.rudderdata[1] = x;
            FlyCtrl.rudderdata[3] = y;
        }

    }

    @Override
    public void OnRightRudderUp() {

    }
}
