package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.manage.drone.R;
import com.manage.drone.customs.FlyImageView;
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
    @BindView(R.id.left_rudder)
    FlyImageView imgControl;


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
//        rudder.setOnRudderListener(this);
//        rudder.setSpeedLevel(1);
//        this.mDrawer = new Drawer(this, this.rudder);
//        mDrawer.stopDrawing();
//
        float centreX=imgControl.getX() + imgControl.getWidth()  / 2;
        float centreY=imgControl.getY() + imgControl.getHeight() / 2;
        Rect rectf=new Rect();
        imgControl.getLocalVisibleRect(rectf);

        Log.d("WIDTH        :",centreX+"   "+centreY);
        Log.d("HEIGHT       :",String.valueOf(imgControl.getMeasuredHeight()));
        Log.d("left         :",String.valueOf(rectf.left));
        Log.d("right        :",String.valueOf(rectf.right));
        Log.d("top          :",String.valueOf(rectf.top));
        Log.d("bottom       :",String.valueOf(rectf.bottom));

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
