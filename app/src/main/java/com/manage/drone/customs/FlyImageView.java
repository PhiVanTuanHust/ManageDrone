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
        if (xTouch == 0 && yTouch == 0) return;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((int) xTouch, (int) yTouch, 30, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int centX = getWidth() / 2;
        int centY = getHeight() / 2;
        Log.e("onTouchEvent: ", "Left:" + getX() + " Top:" + getY() + " X: " + event.getX() + " Y:" + event.getY() + " RawX:" + event.getRawY() + " RawY:" + event.getRawY());
        xTouch = (int) event.getX();
        yTouch = (int) event.getY();
        int ptx = (int) Math.pow(xTouch - centX, 2);
        int pty = (int) Math.pow(yTouch - centY, 2);
        int radius = (int) Math.pow(getWidth() / 2, 2);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                xTouch = 0;
                yTouch = 0;
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                if (ptx + pty - radius < 0)
                    invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (ptx + pty - radius < 0)
                    invalidate();
                break;

        }
        return true;

    }
}
