package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.manage.drone.R;
import com.manage.drone.control.GyroscopeObserver;
import com.manage.drone.control.PanoramaImageView;
import com.manage.drone.customs.FlyImageView;
import com.manage.drone.fragment.ControlFragment;
import com.manage.drone.fragment.ObserveFragment;


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
    @BindView(R.id.ivVideo)
    RelativeLayout viewVideo;
    @BindView(R.id.layout_record)
    LinearLayout viewRecord;
    @BindView(R.id.imgVideo)
    ImageView imgVideo;
    @BindView(R.id.main)
    RelativeLayout layoutMain;
    @BindView(R.id.frameView)
    FrameLayout frameLayout;
    @BindView(R.id.viewSpeed)
    PointerSpeedometer speedView;
    @BindView(R.id.tvHeight)
    TextView tvHeight;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSpeed)
    TextView tvSpeed;
    @BindView(R.id.layout_info)
    LinearLayout layout_info;
    @BindView(R.id.imgUpSpeed)
    ImageView imgSpeedUp;
    @BindView(R.id.imgDownSpeed)
    ImageView imgSpeedDown;
    @BindView(R.id.tvTime)
    TextView tvTime;
    private Thread thread;
    private int time = 0;
    private float unitHeight = 0.5f;
    private float height=0.0f;
    private float unitSpeed = 0.5f;
    private boolean isRecord = false;
    private GyroscopeObserver gyroscopeObserver;
    @BindView(R.id.imgBackGround)
    PanoramaImageView imgBackGround;


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
        getSupportFragmentManager().beginTransaction().replace(R.id.frameView, ControlFragment.newInstance()).commit();

        speedView.setSpeedAt(50);
        speedView.setMaxSpeed(100);
        speedView.setWithPointer(false);
        speedView.setUnit("Km/h");
        gyroscopeObserver = new GyroscopeObserver();

        imgBackGround.setGyroscopeObserver(gyroscopeObserver);
        hideView();

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
        capture(layoutMain);
    }

    private void capture(View view) {
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.RESTART);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }

    private void hideView() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout_info.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        thread.start();
    }

    @OnClick(R.id.ivFlyUp)
    public void onFlyUp() {
        if (layout_info.getVisibility() != View.VISIBLE) {
            layout_info.setVisibility(View.VISIBLE);
            hideView();
        }
        height=height+0.1f;
        gyroscopeObserver.updateHeight(height);
        float height = getHeight(tvHeight.getText().toString().replace("m", "")) + unitHeight;
        tvHeight.setText(height + "m");
    }

    @OnClick(R.id.ivFlyDown)
    public void onFlyDown() {
        if (layout_info.getVisibility() != View.VISIBLE) {
            layout_info.setVisibility(View.VISIBLE);
            hideView();
        }
        height=height-0.1f;
        gyroscopeObserver.updateHeight(height);
        float height = getHeight(tvHeight.getText().toString().replace("m", "")) - unitHeight;
        tvHeight.setText(height + "m");

    }

    private float getHeight(String text) {
        try {
            Float f = Float.parseFloat(text);
            return f;
        } catch (Exception e) {
            return 25.0f;
        }
    }

    @OnClick(R.id.imgDownSpeed)
    public void onSpeedDown() {
        capture(imgSpeedDown);
        if (layout_info.getVisibility() != View.VISIBLE) {
            layout_info.setVisibility(View.VISIBLE);
            hideView();
        }
        float speed = speedView.getSpeed() - unitSpeed;
        speedView.setSpeedAt(speed);
        tvSpeed.setText(speed + "km/h");
    }

    @OnClick(R.id.imgUpSpeed)
    public void onSpeedUp() {

        capture(imgSpeedUp);
        if (layout_info.getVisibility() != View.VISIBLE) {
            layout_info.setVisibility(View.VISIBLE);
            hideView();
        }
        float speed = speedView.getSpeed() + unitSpeed;
        speedView.setSpeedAt(speed);
        tvSpeed.setText(speed + "km/h");

    }

    @OnClick(R.id.ivVideo)
    public void onVideo() {
        if (isRecord) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(1, 0);
            time = 0;
            viewRecord.setVisibility(View.GONE);
            imgVideo.setImageResource(R.drawable.videodis);
            Toast.makeText(this, "Video của bạn đã được lưu", Toast.LENGTH_SHORT).show();
        } else {
            viewRecord.setVisibility(View.VISIBLE);
            setTime();
            imgVideo.setImageResource(R.drawable.stop);
        }
        isRecord = !isRecord;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setTime();
                    break;
                case 1:
                    time = 0;
                    break;
            }


        }
    };

    private String convertTime(int seconds) {
        int minute = seconds / 60;
        int second = seconds - minute * 60;
        return (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
    }

    private void setTime() {
        tvTime.setText(convertTime(time));
        time = time + 1;
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @OnClick(R.id.frameView)
    public void onZoom() {

    }
}
