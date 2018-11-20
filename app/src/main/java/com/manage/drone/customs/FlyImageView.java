package com.manage.drone.customs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class FlyImageView extends ImageView {
    private int xTouch = 0;
    private int yTouch = 0;
    private float centerX = 0;
    private float centerY = 0;


    public FlyImageView(Context context) {
        super(context);
    }

    public FlyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        Log.e("xTouch", xTouch + "  yTouch  " + yTouch);
        if (xTouch == 0 && yTouch == 0) {
            init();
            canvas.drawCircle(centerX, centerY, 50, paint);
        } else {
            canvas.drawCircle((int) xTouch, (int) yTouch, 50, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int centX = getWidth() / 2;
        int centY = getHeight() / 2;
//        Log.e("onTouchEvent: ", "Left:" + getX() + " Top:" + getY() + " X: " + event.getX() + " Y:" + event.getY() + " RawX:" + event.getRawY() + " RawY:" + event.getRawY());
        xTouch = (int) event.getX();
        yTouch = (int) event.getY();

        int ptx = (int) Math.pow(xTouch - centX, 2);
        int pty = (int) Math.pow(yTouch - centY, 2);
        int radius = (int) Math.pow(getWidth() / 2, 2);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.e("action", "up");
                xTouch = 0;
                yTouch = 0;
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                Log.e("action", "down");
                if (ptx + pty - radius < 0) {
                    invalidate();
                } else {
                    xTouch = (int) getX1(centX, centY, event.getX(), event.getY(), centX);
                    yTouch = (int) getY1(centX, centY, event.getX(), event.getY(), centX);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (ptx + pty - radius < 0) {
                    invalidate();
                } else {
                    xTouch = (int) getX1(centX, centY, event.getX(), event.getY(), centX - 50);
                    yTouch = (int) getY1(centX, centY, event.getX(), event.getY(), centX - 50);
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e("action", "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.e("action", "ACTION_POINTER_UP");
                break;

        }
        return true;

    }

    private void init() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    private float getX1(float xO, float yO, float xM, float yM, float r) {
        float a = 0;
        a = (float) (r / (Math.sqrt((Math.pow(xM - xO, 2)) + Math.pow(yM - yO, 2))));
        Log.e("x", (a * (xM - xO) + xO) + "    " + xM + "    " + yM + "   " + a);
        return a * (xM - xO) + xO;
    }

    private float getY1(float xO, float yO, float xM, float yM, float r) {
        float a = 0;
        a = (float) (r / (Math.sqrt((Math.pow(xM - xO, 2)) + Math.pow(yM - yO, 2))));
        return a * (yM - yO) + yO;
    }
}
