package com.tony.drawing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.manage.drone.R;


public class Drawer {
    public static final int UPDATE_DRAW_PATH = 1000;
    public static final int UPDATE_DRAW_START = 1001;
    public static final int UPDATE_DRAW_STOP = 1002;
    public static int drawingColor = -256;
    public static boolean isSetData = false;
    private Handler handler = new Handler(new Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Drawer.UPDATE_DRAW_START /*1001*/:
                    Drawer.this.iv_Main_FlightBall.setVisibility(View.INVISIBLE);
                    Drawer.this.index = 0;
                    Drawer.this.drawFlightPath();
                    break;
                case Drawer.UPDATE_DRAW_STOP /*1002*/:
                    Drawer.this.index = SupportMenu.USER_MASK;
                    Drawer.this.iv_Main_FlightBall.clearAnimation();
                    Drawer.this.iv_Main_FlightBall.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    });
    int index;
    private ImageView iv_Main_FlightBall;
    private Activity mActivity;
    private Drawing mDrawing;
    private DrawingFactory mDrawingFactory;
    private Rudder mRudder;
    private ScreenInfo mScreenInfo;
    int mX;
    int mY;
    private Animation transAnimation;

    public Drawer(Activity activity, Rudder rudder) {
        this.mActivity = activity;
        this.mRudder = rudder;
        this.mRudder.setHandler(this.handler);
        this.mScreenInfo = new ScreenInfo(this.mActivity);
        this.mDrawingFactory = new DrawingFactory();
       this.iv_Main_FlightBall = (ImageView) this.mActivity.findViewById(R.id.iv_lw93main_flightball);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        this.iv_Main_FlightBall.setLayoutParams(layoutParams);
        setDefaultDrawing(0, drawingColor);
    }

    public void startDrawing() {
        this.mRudder.startDrawing();
    }

    public void stopDrawing() {
        this.mRudder.stopDrawing();
    }

    private int updateFlightPath(int mX, int mY, int lastX, int lastY) {
        int IntegralCount;
        int offsetX = mX - lastX;
        int offsetY = mY - lastY;
        int absX = Math.abs(offsetX);
        int absY = Math.abs(offsetY);
        if (Rudder.mFlightPath.size() - this.index > 20) {
            IntegralCount = 20;
        } else {
            IntegralCount = (Rudder.mFlightPath.size() - this.index) - 1;
        }
        for (int i = 0; i < IntegralCount - 1; i++) {
            offsetX += mX - lastX;
            offsetY += mY - lastY;
        }
        if (offsetX >= 60) {
            FlyCtrl.rudderdata[1] = MotionEventCompat.ACTION_MASK;
        } else if (offsetX < -60) {
            FlyCtrl.rudderdata[1] = 1;
        } else {
            FlyCtrl.rudderdata[1] = 128;
        }
        if (offsetY >= 60) {
            FlyCtrl.rudderdata[2] = 1;
        } else if (offsetY < -60) {
            FlyCtrl.rudderdata[2] = MotionEventCompat.ACTION_MASK;
        } else {
            FlyCtrl.rudderdata[2] = 128;
        }
        return (((int) Math.sqrt((double) ((absX * absX) + (absY * absY)))) * 5) / 2;
    }

    @SuppressLint("ResourceType")
    private void drawFlightPath() {
        if (this.index >= Rudder.mFlightPath.size() - 1) {
            FlyCtrl.rudderdata[1] = 128;
            FlyCtrl.rudderdata[2] = 128;
            this.mRudder.clearCanvas();
            isSetData = false;
            return;
        }
        LayoutParams rlps = new LayoutParams(-2, -2);
        this.mX = ((int) ((Coordinate) Rudder.mFlightPath.get(this.index)).mX) - (this.iv_Main_FlightBall.getWidth() / 2);
        this.mY = ((int) ((Coordinate) Rudder.mFlightPath.get(this.index)).mY) - (this.iv_Main_FlightBall.getHeight() / 2);
        rlps.leftMargin = 0;
        rlps.topMargin = 0;
        this.iv_Main_FlightBall.setLayoutParams(rlps);
        this.index++;
        int toX = ((int) ((Coordinate) Rudder.mFlightPath.get(this.index)).mX) - (this.iv_Main_FlightBall.getWidth() / 2);
        int toY = ((int) ((Coordinate) Rudder.mFlightPath.get(this.index)).mY) - (this.iv_Main_FlightBall.getHeight() / 2);
        int durationTime = updateFlightPath(toX, toY, this.mX, this.mY);
        this.transAnimation = new TranslateAnimation((float) this.mX, (float) toX, (float) this.mY, (float) toY);
        this.transAnimation.setDuration((long) durationTime);
        this.transAnimation.setInterpolator(this.mActivity, 17432587);
        this.transAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (Applications.isStartDrawing) {
                    Drawer.this.drawFlightPath();
                    return;
                }
                Drawer.this.iv_Main_FlightBall.clearAnimation();
                Drawer.this.iv_Main_FlightBall.setVisibility(View.GONE);
                Drawer.this.mRudder.clearCanvas();
                Rudder.mFlightPath.clear();
            }
        });
        this.iv_Main_FlightBall.startAnimation(this.transAnimation);
        this.mX = toX;
        this.mY = toY;
    }

    private void setDefaultDrawing(int id, int color) {
        this.mDrawing = this.mDrawingFactory.createDrawing(id);
        this.mRudder.setDrawing(this.mDrawing);
        DrawingPaint.getMyPaint().reset(this.mScreenInfo.getWidthPixels() / 250);
        DrawingPaint.getMyPaint().setColor(color);
    }
}
