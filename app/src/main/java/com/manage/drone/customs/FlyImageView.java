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

import com.manage.drone.control.GyroscopeObserver;

@SuppressLint("AppCompatCustomView")
public class FlyImageView extends ImageView {
    private int xTouch = 0;
    private int yTouch = 0;
    private float centerX = 0;
    private float centerY = 0;
    private static int r=54;
    private GyroscopeObserver observer;

    public void setObserver(GyroscopeObserver observer) {
        this.observer = observer;
    }

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
        if (xTouch == 0 && yTouch == 0) {
            init();
            canvas.drawCircle(centerX, centerY, r, paint);
        } else {
            canvas.drawCircle((int) xTouch, (int) yTouch, r, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int centX = getWidth() / 2;
        int centY = getHeight() / 2;
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
                } else {
                    xTouch = (int) getX1(centX, centY, event.getX(), event.getY(), centX - r);
                    yTouch = (int) getY1(centX, centY, event.getX(), event.getY(), centX - r);
                }
                observer.updateHeight(getCosineAOB(xTouch,yTouch));
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
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
        return a * (xM - xO) + xO;
    }

    private float getY1(float xO, float yO, float xM, float yM, float r) {
        float a = 0;
        a = (float) (r / (Math.sqrt((Math.pow(xM - xO, 2)) + Math.pow(yM - yO, 2))));
        return a * (yM - yO) + yO;
    }

    /**
     *
     * @param xA
     * @param yA
     * OA.OB.cosAOB=vecOA.vecOB
     * OA=(xA-x0)
     * @return
     */
    private float getCosineAOB(float xA,float yA){
        float xO=(getWidth()/2);
        float yO=(getHeight()/2);
        float xB=getWidth();
        float yB=yO;
        float cosine=(float)(((xA-xO)*(yA-yO)+(xB-xO)*(yB-yO))/(Math.sqrt(Math.pow(xA-xO,2)+Math.pow(yA-yO,2)))/(Math.sqrt(Math.pow(xB-xO,2)+Math.pow(yB-yO,2))));
        return cosine;
    }
}
