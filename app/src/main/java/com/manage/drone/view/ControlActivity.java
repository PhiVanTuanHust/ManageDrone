package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.manage.drone.R;
import com.manage.drone.customs.FlyImageView;
import com.manage.drone.fragment.ObserveFragment;
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
public class ControlActivity extends BaseActivity {
    @BindView(R.id.frame_back)
    FrameLayout frameBack;
    @BindView(R.id.left_rudder)
    FlyImageView imgControl;
    @BindView(R.id.ivCamera)
    RelativeLayout imgCamera;
    @BindView(R.id.main)
    RelativeLayout layoutMain;
    @BindView(R.id.frameView)
    FrameLayout frameLayout;


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
          getSupportFragmentManager().beginTransaction().replace(R.id.frameView, ObserveFragment.newInstance()).commit();

    }

    @OnClick(R.id.frame_back)
    public void onBack() {
        onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public static void startControl(Context context) {
        Intent intent = new Intent(context, ControlActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.ivCamera)
    public void onCamera() {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.camera);
        mediaPlayer.start();
        capture();
    }

    private void capture() {
        Animation animation = new AlphaAnimation(1, 0); // Change alpha
        animation.setDuration(200); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter
        animation.setRepeatCount(Animation.RESTART);
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at
        layoutMain.startAnimation(animation);
    }
}
