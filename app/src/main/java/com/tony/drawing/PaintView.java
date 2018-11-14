package com.tony.drawing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.manage.drone.R;
import com.tony.drawing.DrawingFactory.Eraser;

import java.util.ArrayList;

public class PaintView extends View {
    protected static final String TAG = "PaintView";
    public static boolean isDrawingShow = false;
    public static ArrayList<Coordinate> mFlightPath = new ArrayList();
    private Handler handler;
    private boolean isMoving = false;
    int lastX = 0;
    int lastY = 0;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context mContext;
    private Drawing mDrawing;
    private Thread reDrawFlightPathThread;
    private ScreenInfo screenInfo;
    private float tempX;
    private float tempY;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.screenInfo = new ScreenInfo((Activity) this.mContext);
        this.mBitmap = Bitmap.createBitmap(this.screenInfo.getWidthPixels(), this.screenInfo.getHeightPixels(), Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
        this.mCanvas.drawColor(getResources().getColor(R.color.transparent));
        this.isMoving = false;
    }

    public void setDrawing(Drawing drawing) {
        this.mDrawing = drawing;
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, new Paint(4));
        if (this.mDrawing != null && this.isMoving) {
            this.mDrawing.draw(canvas);
        }
        if (!(this.mDrawing instanceof Eraser)) {
            Bitmap pen = BitmapFactory.decodeResource(getResources(), R.drawable.pen);
            canvas.drawBitmap(pen, this.tempX, this.tempY - ((float) pen.getHeight()), new Paint(4));
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case 0:
                mFlightPath.clear();
                this.handler.sendEmptyMessage(Drawer.UPDATE_DRAW_STOP);
                clearCanvas();
                if (this.reDrawFlightPathThread.isAlive()) {
                    this.reDrawFlightPathThread.interrupt();
                }
                Log.e("", "action down");
                fingerDown(x, y);
                reDraw();
                break;
            case 1:
                Log.d("", "action up");
                fingerUp(x, y);
                reDraw();
                break;
            case 2:
                fingerMove(x, y);
                reDraw();
                break;
        }
        return true;
    }

    private void reDraw() {
        invalidate();
    }

    private int updateFlightPath(int indexOfPath) {
        int mX = (int) ((Coordinate) mFlightPath.get(indexOfPath)).mX;
        int mY = (int) ((Coordinate) mFlightPath.get(indexOfPath)).mY;
        if (indexOfPath == 0) {
            this.lastX = mX;
            this.lastY = mY;
        }
        if (mX == this.lastX && mY == this.lastY) {
            return 10;
        }
        int offsetX = mX - this.lastX;
        int offsetY = mY - this.lastY;
        int absX = Math.abs(offsetX);
        int absY = Math.abs(offsetY);
        if (offsetX >= 40) {
            FlyCtrl.rudderdata[1] = 248;
        } else if (offsetX < -40) {
            FlyCtrl.rudderdata[1] = 8;
        } else {
            FlyCtrl.rudderdata[1] = (offsetX * 3) + 128;
        }
        if (offsetY >= 40) {
            FlyCtrl.rudderdata[2] = 8;
        } else if (offsetY < -40) {
            FlyCtrl.rudderdata[2] = 248;
        } else {
            FlyCtrl.rudderdata[2] = 128 - (offsetY * 3);
        }
        this.lastX = mX;
        this.lastY = mY;
        return ((int) Math.sqrt((double) ((absX * absX) + (absY * absY)))) * 8;
    }

    private void reDrawFlightPath(Canvas canvas) {
        new Thread(new Runnable() {
            public void run() {
                int size = PaintView.mFlightPath.size();
                int index = 0;
                int sleepTime = 0;
                PaintView.this.handler.sendEmptyMessage(Drawer.UPDATE_DRAW_START);
                PaintView.this.msleep(100);
                while (size - index > 0) {
                    if (index < size) {
                        try {
                            sleepTime = PaintView.this.updateFlightPath(index);
                            Message msg = new Message();
                            msg.what = Drawer.UPDATE_DRAW_PATH;
                            msg.arg1 = index;
                            msg.arg2 = sleepTime;
                            PaintView.this.handler.sendMessage(msg);
                            if (index + 1 == size) {
                                FlyCtrl.rudderdata[1] = 128;
                                FlyCtrl.rudderdata[2] = 128;
                            }
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                    index++;
                    PaintView.this.msleep(sleepTime + 10);
                }
                PaintView.this.handler.sendEmptyMessage(Drawer.UPDATE_DRAW_STOP);
            }
        }).start();
    }

    private void msleep(int time) {
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fingerUp(float x, float y) {
        this.tempX = 0.0f;
        this.tempY = 0.0f;
        this.mDrawing.fingerUp(x, y, this.mCanvas);
        this.isMoving = false;
        this.mDrawing.getLength();
        reDrawFlightPath(this.mCanvas);
    }

    private void fingerMove(float x, float y) {
        this.tempX = x;
        this.tempY = y;
        this.isMoving = true;
        this.mDrawing.fingerMove(x, y, this.mCanvas);
    }

    private void fingerDown(float x, float y) {
        this.isMoving = false;
        clearCanvas();
        this.mDrawing.fingerDown(x, y, this.mCanvas);
    }

    public void clearCanvas() {
        if (this.mBitmap == null) {
            this.mBitmap = Bitmap.createBitmap(this.screenInfo.getWidthPixels(), this.screenInfo.getHeightPixels(), Config.ARGB_8888);
        }
        this.mBitmap.eraseColor(0);
        this.mCanvas.setBitmap(this.mBitmap);
    }
}
