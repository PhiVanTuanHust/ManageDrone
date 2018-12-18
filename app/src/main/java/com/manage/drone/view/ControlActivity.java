package com.manage.drone.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.manage.drone.utils.ViewUtil;


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
    @BindView(R.id.control_view)
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
    @BindView(R.id.imgAI)
    ImageView imgAI;
    private boolean isAI = false;
    private Thread thread;
    private int time = 0;
    private float unitHeight = 0.5f;
    private float height = 0.0f;
    private float unitSpeed = 0.5f;
    private boolean isRecord = false;
    private GyroscopeObserver gyroscopeObserver;
    private boolean isHide = false;
    private static final int PERMISSION_STORAGE=1;
    @BindView(R.id.imgBackGround)
    PanoramaImageView imgBackGround;
    @BindView(R.id.layout_right)
    LinearLayout layoutRight;

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
        imgControl.setObserver(gyroscopeObserver);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_STORAGE){
            if (ActivityCompat.checkSelfPermission(ControlActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ControlActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_STORAGE);
            }
        }else {

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        if (thread != null) {
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
                        isHide = true;
                        createAnimation();
                    }
                });
            }
        };
        thread.start();
    }

    @OnClick(R.id.ivFlyUp)
    public void onFlyUp() {

        animation();
        height = height + 0.1f;
        gyroscopeObserver.updateHeight(height);
        float height = getHeight(tvHeight.getText().toString().replace("m", "")) + unitHeight;
        tvHeight.setText(height + "m");
    }

    @OnClick(R.id.ivFlyDown)
    public void onFlyDown() {

        animation();
        height = height - 0.1f;
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
        animation();
        capture(imgSpeedDown);
        if (layout_info.getVisibility() != View.VISIBLE) {
            createAnimation();
            hideView();
        }
        float speed = speedView.getSpeed() - unitSpeed;
        speedView.setSpeedAt(speed);
        tvSpeed.setText(speed + "km/h");
    }

    @OnClick(R.id.imgUpSpeed)
    public void onSpeedUp() {
        animation();
        capture(imgSpeedUp);
        float speed = speedView.getSpeed() + unitSpeed;
        speedView.setSpeedAt(speed);
        tvSpeed.setText(speed + "km/h");

    }
    @OnClick(R.id.imgGallery)
    public void onGallery(){
        GalleryActivity.startGalleryActivity(ControlActivity.this);
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

    private void createAnimation() {
        Animation animation = isHide ? new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, (float) (-ViewUtil.dip2px(this, 120.0f))) : new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, (float) (-ViewUtil.dip2px(this, 120.0f)), 0, 0.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        layout_info.startAnimation(animation);

    }

    @OnClick(R.id.imgAI)
    public void onAI() {
        isAI = !isAI;
        if (isAI) {
            AIControl();
        } else {
            HandControl();
        }

    }

    private void animation() {
        if (isHide) {
            isHide = false;
            createAnimation();
            hideView();
        }
    }

    private void AIControl() {
        imgControl.setVisibility(View.GONE);
        imgAI.setImageResource(R.drawable.robot);
        Animation animation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, (float) (-ViewUtil.dip2px(this, 120.0f)), 0, 0.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        speedView.setSpeedAt(50);
        layout_info.startAnimation(animation);
        imgSpeedUp.setVisibility(View.GONE);
        imgSpeedDown.setVisibility(View.GONE);layoutRight.setVisibility(View.GONE);

        Toast.makeText(this,"Bạn đang ở chế độ tự động điều khiển drone",Toast.LENGTH_SHORT).show();
    }

    private void HandControl() {
        imgControl.setVisibility(View.VISIBLE);
        imgAI.setImageResource(R.drawable.track);
        imgSpeedUp.setVisibility(View.VISIBLE);
        imgSpeedDown.setVisibility(View.VISIBLE);
        layoutRight.setVisibility(View.VISIBLE);
        Toast.makeText(this,"Bạn đang ở chế độ điều khiển drone bằng tay",Toast.LENGTH_SHORT).show();
    }


}
