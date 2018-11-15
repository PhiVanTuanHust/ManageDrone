package com.tony.drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable.Callback;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.manage.drone.R;
import com.tony.drawing.Coordinate;
import com.tony.drawing.Drawer;
import com.tony.drawing.Drawing;
import com.tony.drawing.DrawingFactory.Eraser;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Phí Văn Tuấn on 13/11/2018.
 */

public class Rudder extends GLSurfaceView implements Callback {
    public static ArrayList<Coordinate> mFlightPath = new ArrayList();
    public static Point p_left = new Point();
    public static Point p_left_default = new Point();
    public static int p_left_start_x = 0;
    public static int p_left_start_y = 0;
    public static Point p_right = new Point();
    public static Point p_right_default = new Point();
    public static int p_right_start_x = 0;
    public static int p_right_start_y = 0;
    private static final int KEYCODE_MEDIA_PAUSE=127;
    private int FlyMaxPower = MotionEventCompat.ACTION_MASK;
    private int FlyMaxRotate = 127;
    private int FlyMaxSpeed = this.MinSpeed;
    private int MaxSpeed = 127;
    private int MidSpeed = 60;
    private int MinSpeed = 40;
    private int RightMaxRight = 42;
    private int _id_left = -1;
    private int _id_right = -1;
    private Bitmap bLeft = null;
    private Bitmap bRight = null;
    private int bWidth = 0;
    private Handler handler;
    private boolean isDrawThreadRun = false;
    private boolean isDraweOK = false;
    private boolean isDrawing = false;
    private boolean isMoving = false;
    private boolean isRightBallShow = true;
    private boolean isStop = false;
    private int l_left = 0;
    private int l_right = 0;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mDistance = 0;
    private Drawing mDrawing;
    private int mFlightIndex = 0;
    private SurfaceHolder mHolder = null;
    private Paint mPaint = null;
    private Thread mThread = null;
    private OnRudderListener onRudderListener = null;
    private int p_left_bottom = 0;
    private int p_left_left = 0;
    private int p_left_right = 0;
    private int p_left_up = 0;
    private int p_right_bottom = 0;
    private int p_right_left = 0;
    private int p_right_right = 0;
    private int p_right_up = 0;
    private Renderer renderer;
    private int screenHeight = 0;
    private int screenWidth = 0;
    private float tempX;
    private float tempY;

    public class MyRenderer implements Renderer {
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }

        public void onDrawFrame(GL10 gl) {
        }
    }

    public interface OnRudderListener {
        void OnLeftRudder(int i, int i2);

        void OnRightRudder(int i, int i2, boolean z);

        void OnRightRudderUp();
    }

    public Rudder(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dmMetrics = getResources().getDisplayMetrics();
        this.screenWidth = dmMetrics.widthPixels;
        this.screenHeight = dmMetrics.heightPixels;
        this.mDistance = this.screenWidth / 30;
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mPaint = new Paint();
        this.mPaint.setColor(-16776961);
        this.mPaint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        float f_round = ((float) this.screenWidth) / ((float) (bitmap.getWidth() * 16));
        Matrix matrix = new Matrix();
        matrix.setScale(f_round, f_round);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        this.bLeft = createBitmap;
        this.bRight = createBitmap;
        this.bWidth = this.bLeft.getWidth() / 2;
        this.mHolder.setFormat(-2);
        this.renderer = new MyRenderer();
        setRenderer(this.renderer);
        System.gc();
        initPosition();
        this.mBitmap = Bitmap.createBitmap(this.screenWidth, this.screenHeight, Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
        this.mCanvas.drawColor(getResources().getColor(R.color.transparent));
        this.isMoving = false;
    }

    public void setSpeedLevel(int isHigh) {
        switch (isHigh) {
            case 1:
                this.FlyMaxSpeed = this.MinSpeed;
                return;
            case 2:
                this.FlyMaxSpeed = this.MidSpeed;
                return;
            case 3:
                this.FlyMaxSpeed = this.MaxSpeed;
                return;
            default:
                return;
        }
    }

    public void setOnRudderListener(OnRudderListener onRudderListener) {
        this.onRudderListener = onRudderListener;
    }

    private void initPosition() {
        Point point;
        int i;
        if (Applications.isRightHandMode) {
            point = p_left;
            i = (this.screenWidth * 240) / 960;
            p_left_default.x = i;
            point.x = i;
            point = p_left;
            i = (((this.screenHeight + 50) - ((this.screenWidth * 360) / 960)) / 2) + ((this.screenWidth * 180) / 960);
            p_right_default.y = i;
            point.y = i;
            p_left_default.y = (this.screenHeight + 50) / 2;
            point = p_right;
            i = (this.screenWidth * 720) / 960;
            p_right_default.x = i;
            point.x = i;
            p_right.y = ((((this.screenHeight + 50) - ((this.screenWidth * 360) / 960)) / 2) + ((this.screenWidth * 360) / 960)) - (this.bWidth * 2);
        } else {
            point = p_left;
            i = (this.screenWidth * 260) / 960;
            p_left_default.x = i;
            point.x = i;
            p_left.y = ((((this.screenHeight + 50) - ((this.screenWidth * 360) / 960)) / 2) + ((this.screenWidth * 360) / 960)) - (this.bWidth * 2);
            p_left_default.y = (this.screenHeight + 50) / 2;
            point = p_right;
            i = (this.screenWidth * 690) / 960;
            p_right_default.x = i;
            point.x = i;
            point = p_right;
            i = (((this.screenHeight + 50) - ((this.screenWidth * 360) / 960)) / 2) + ((this.screenWidth * 180) / 960);
            p_right_default.y = i;
            point.y = i;
        }
        int i2 = (this.screenWidth * 115) / 960;
        this.l_left = i2;
        this.l_right = i2;
        p_left_start_x = p_left_default.x - this.l_left;
        p_left_start_y = p_left_default.y + this.l_left;
        p_right_start_x = p_right_default.x - this.l_right;
        p_right_start_y = p_right_default.y + this.l_right;
        this.p_left_up = (p_left_default.y - ((this.screenWidth * 125) / 960)) - this.bWidth;
        this.p_left_bottom = (p_left_default.y + ((this.bWidth * 2) / 3)) + ((this.screenWidth * 125) / 960);
        this.p_left_left = (p_left_default.x - ((this.screenWidth * 125) / 960)) - (this.bWidth * 2);
        this.p_left_right = (p_left_default.x + ((this.screenWidth * 125) / 960)) + (this.bWidth * 2);
        this.p_right_up = (p_right_default.y - ((this.screenWidth * 125) / 960)) - this.bWidth;
        this.p_right_bottom = (p_right_default.y + ((this.bWidth * 2) / 3)) + ((this.screenWidth * 125) / 960);
        this.p_right_left = (p_right_default.x - ((this.screenWidth * 125) / 960)) - (this.bWidth * 2);
        this.p_right_right = (p_right_default.x + ((this.screenWidth * 125) / 960)) + (this.bWidth * 2);
    }

    public void startRudder() {
        if (this.mThread == null || !this.mThread.isAlive()) {
            this.mThread = new Thread() {
                public void run() {
                    super.run();
                    Rudder.this.isStop = false;
                    Rudder.this.msleep(100);
                    while (!Rudder.this.isStop) {
                        if (Rudder.this.isDraweOK) {
                            Canvas canvas = Rudder.this.mHolder.lockCanvas();
                            if (canvas == null) {
                                Rudder.this.msleep(5);
                            } else {
                                canvas.drawColor(0, Mode.CLEAR);
                                canvas.drawBitmap(Rudder.this.bLeft, (float) (Rudder.p_left.x - Rudder.this.bWidth), (float) (Rudder.p_left.y - Rudder.this.bWidth), Rudder.this.mPaint);
                                if (!Rudder.this.isRightBallShow) {
                                    canvas.drawBitmap(Rudder.this.bRight, (float) (Rudder.p_right.x - Rudder.this.bWidth), (float) (Rudder.p_right.y - Rudder.this.bWidth), Rudder.this.mPaint);
                                }
                                if (Rudder.this.mHolder != null) {
                                    Rudder.this.mHolder.unlockCanvasAndPost(canvas);
                                }
                            }
                        }
                        Rudder.this.msleep(20);
                    }
                }
            };
            this.mThread.start();
        }
    }

    public void setRightRudderVisible(boolean show) {
        this.isRightBallShow = show;
    }

    public void stopRudder() {
        this.isStop = true;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopRudder();
        msleep(120);
        this.isDraweOK = false;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        startRudder();
        this.isDraweOK = true;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        int count = event.getPointerCount();
        int pointerId = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8;
        int x;
        int y;
        switch (event.getAction()) {
            case 0:
                Applications.isTouchup = false;
                x = (int) event.getX(pointerId);
                y = (int) event.getY(pointerId);
                if (x < this.p_left_left || x > this.p_left_right || y < this.p_left_up || y > this.p_left_bottom) {
                    if (!this.isDrawing) {
                        if (x >= this.p_right_left && x <= this.p_right_right && y >= this.p_right_up && y <= this.p_right_bottom) {
                            this._id_right = pointerId;
                            dealRight(x, y);
                            break;
                        }
                    }
                    this._id_right = pointerId;
                    fingerDown((float) x, (float) y);
                    reDraw();
                    break;
                }
                this._id_left = pointerId;
                dealLeft(x, y);
                break;

            case 1:
                Applications.isTouchup = true;
                if (Applications.isRightHandMode) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else if (Applications.isLimitedHigh) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else {
                    dealLeft(p_left_default.x, p_left.y);
                }
                if (this.isDrawing && this._id_right == pointerId) {
                    fingerUp((float) ((int) event.getX(pointerId)), (float) ((int) event.getY(pointerId)));
                    reDraw();
                } else if (!Applications.isRightHandMode) {

                   this.onRudderListener.OnRightRudderUp();
                    dealRight(p_right_default.x, p_right_default.y);
                } else if (Applications.isLimitedHigh) {
                    dealRight(p_right_default.x, p_right_default.y);
                } else {
                    dealRight(p_right_default.x, p_right.y);
                }
                this._id_left = -1;
                this._id_right = -1;
                break;
            case 2:
                Applications.isTouchup = false;
                for (int i = 0; i < count; i++) {
                    x = (int) event.getX(i);
                    y = (int) event.getY(i);
                    if (this._id_left == i) {
                        dealLeft(x, y);
                    } else if (this._id_right == i) {
                        if (this.isDrawing) {
                            fingerMove((float) x, (float) y);
                            reDraw();
                        } else {
                            dealRight(x, y);
                        }
                    }
                }
                break;
            case 5:
                x = (int) event.getX(pointerId);
                y = (int) event.getY(pointerId);
                if (x < this.p_left_left || x > this.p_left_right || y < this.p_left_up || y > this.p_left_bottom) {
                    if (!this.isDrawing) {
                        if (x >= this.p_right_left && x <= this.p_right_right && y >= this.p_right_up && y <= this.p_right_bottom) {
                            this._id_right = pointerId;
                            dealRight(x, y);
                            break;
                        }
                    }
                    this._id_right = pointerId;
                    fingerDown((float) x, (float) y);
                    reDraw();
                    break;
                }
                this._id_left = pointerId;
                dealLeft(x, y);
                break;

            case 6:
                if (this._id_left != pointerId) {
                    if (this._id_right == pointerId) {
                        if (this.isDrawing) {
                            fingerUp((float) ((int) event.getX(pointerId)), (float) ((int) event.getY(pointerId)));
                            reDraw();
                        } else if (!Applications.isRightHandMode) {
                            this.onRudderListener.OnRightRudderUp();
                            dealRight(p_right_default.x, p_right_default.y);
                        } else if (Applications.isLimitedHigh) {
                            dealRight(p_right_default.x, p_right_default.y);
                        } else {
                            dealRight(p_right_default.x, p_right.y);
                        }
                        this._id_right = -1;
                        break;
                    }
                }
                if (Applications.isRightHandMode) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else if (Applications.isLimitedHigh) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else {
                    dealLeft(p_left_default.x, p_left.y);
                }
                this._id_left = -1;
                break;

            case 261:
                x = (int) event.getX(pointerId);
                y = (int) event.getY(pointerId);
                if (x < this.p_left_left || x > this.p_left_right || y < this.p_left_up || y > this.p_left_bottom) {
                    if (!this.isDrawing) {
                        if (x >= this.p_right_left && x <= this.p_right_right && y >= this.p_right_up && y <= this.p_right_bottom) {
                            this._id_right = pointerId;
                            dealRight(x, y);
                            break;
                        }
                    }
                    this._id_right = pointerId;
                    fingerDown((float) x, (float) y);
                    reDraw();
                    break;
                }
                this._id_left = pointerId;
                dealLeft(x, y);
                break;

            case 262:
                if (this._id_left != pointerId) {
                    if (this._id_right == pointerId) {
                        if (this.isDrawing) {
                            fingerUp((float) ((int) event.getX(pointerId)), (float) ((int) event.getY(pointerId)));
                            reDraw();
                        } else if (!Applications.isRightHandMode) {
                           this.onRudderListener.OnRightRudderUp();
                            dealRight(p_right_default.x, p_right_default.y);
                        } else if (Applications.isLimitedHigh) {
                            dealRight(p_right_default.x, p_right_default.y);
                        } else {
                            dealRight(p_right_default.x, p_right.y);
                        }
                        this._id_right = -1;
                        break;
                    }
                }
                if (Applications.isRightHandMode) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else if (Applications.isLimitedHigh) {
                    dealLeft(p_left_default.x, p_left_default.y);
                } else {
                    dealLeft(p_left_default.x, p_left.y);
                }
                this._id_left = -1;
                break;

        }
        return true;
    }

    private void dealLeft(int x, int y) {
        if (Applications.isSensorOn && Applications.isRightHandMode) {
            y = p_left_default.y;
        }
        if (RudderUtils.getLineLength(x, y, p_left_default.x, p_left_default.y) >= this.l_left) {
            p_left = RudderUtils.getPoint(x, y, p_left_default.x, p_left_default.y, this.l_left);
        } else {
            p_left.x = x;
            p_left.y = y;
        }
        int xDistance;
        int xLocation;
        int rotate;
        if (Applications.isRightHandMode) {
            xDistance = p_left.x - p_left_default.x;
            if (Math.abs(xDistance) < this.mDistance) {
                xLocation = p_left_default.x;
            } else if (xDistance - this.mDistance < 0) {
                xLocation = p_left.x + this.mDistance;
            } else {
                xLocation = p_left.x - this.mDistance;
            }
            rotate = RudderUtils.GetLR(xLocation, this.l_left - this.mDistance, p_left_default.x, this.FlyMaxRotate);
            int frontAndBack = RudderUtils.GetUpDown(p_left.y, this.l_left, p_left_default.y, this.FlyMaxSpeed);
            if (p_left.x < p_left_default.x) {
                rotate = 128 - rotate;
            } else {
                rotate += 128;
            }
            if (p_left.y < p_left_default.y) {
                frontAndBack += 128;
            } else {
                frontAndBack = 128 - frontAndBack;
            }
           this.onRudderListener.OnLeftRudder(rotate, frontAndBack);
            return;
        }
        xDistance = p_left.x - p_left_default.x;
        if (Math.abs(xDistance) < this.mDistance) {
            xLocation = p_left_default.x;
        } else if (xDistance - this.mDistance < 0) {
            xLocation = p_left.x + this.mDistance;
        } else {
            xLocation = p_left.x - this.mDistance;
        }
        rotate = RudderUtils.GetLR(xLocation, this.l_left - this.mDistance, p_left_default.x, this.FlyMaxRotate);
        int power = RudderUtils.GetUpDown(p_left.y, this.l_left * 2, p_left_start_y, this.FlyMaxPower);
        if (p_left.x < p_left_default.x) {
            rotate = 128 - rotate;
        } else {
            rotate += 128;
        }
        this.onRudderListener.OnLeftRudder(rotate, power);
    }

    private void dealRight(int x, int y) {
        if (Applications.isSensorOn) {
            if (Applications.isRightHandMode) {
                x = p_right_default.x;
            } else {
                return;
            }
        }
        if (RudderUtils.getLineLength(x, y, p_right_default.x, p_right_default.y) >= this.l_right) {
            p_right = RudderUtils.getPoint(x, y, p_right_default.x, p_right_default.y, this.l_right);
        } else {
            p_right.x = x;
            p_right.y = y;
        }
        boolean isHighSpeed;
        int leftAndRight;
        if (Applications.isRightHandMode) {
            isHighSpeed = false;
            leftAndRight = RudderUtils.GetLR(p_right.x, this.l_right, p_right_default.x, this.FlyMaxSpeed);
            int power = RudderUtils.GetUpDown(p_right.y, this.l_right * 2, p_right_start_y, this.FlyMaxPower);
            if (p_right.x < p_right_default.x) {
                leftAndRight = 128 - leftAndRight;
            } else {
                leftAndRight += 128;
            }
            if (this.RightMaxRight == KEYCODE_MEDIA_PAUSE && ((double) RudderUtils.getLineLength(p_right.x, p_right.y, p_right_default.x, p_right_default.y)) >= ((double) this.l_right) * 0.7d) {
                isHighSpeed = true;
            }
            this.onRudderListener.OnRightRudder(leftAndRight, power, isHighSpeed);
            return;
        }
        isHighSpeed = false;
        leftAndRight = RudderUtils.GetLR(p_right.x, this.l_right, p_right_default.x, this.FlyMaxSpeed);
        int frontAndBack = RudderUtils.GetUpDown(p_right.y, this.l_right, p_right_default.y, this.FlyMaxSpeed);
        if (p_right.x < p_right_default.x) {
            leftAndRight = 128 - leftAndRight;
        } else {
            leftAndRight += 128;
        }
        if (p_right.y < p_right_default.y) {
            frontAndBack += 128;
        } else {
            frontAndBack = 128 - frontAndBack;
        }
        if (this.RightMaxRight == KEYCODE_MEDIA_PAUSE && ((double) RudderUtils.getLineLength(p_right.x, p_right.y, p_right_default.x, p_right_default.y)) >= ((double) this.l_right) * 0.7d) {
            isHighSpeed = true;
        }
        this.onRudderListener.OnRightRudder(leftAndRight, frontAndBack, isHighSpeed);
    }

    private void msleep(int time) {
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDrawing(Drawing drawing) {
        this.mDrawing = drawing;
    }

    public void startDrawing() {
        this.isDrawing = true;
    }

    public void stopDrawing() {
        this.isDrawing = false;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, new Paint(4));
        if (this.mDrawing != null && this.isMoving) {
            this.mDrawing.draw(canvas);
        }
        boolean z = this.mDrawing instanceof Eraser;
    }

    private void reDraw() {
        invalidate();
    }

    private void fingerUp(float x, float y) {
        this.tempX = 0.0f;
        this.tempY = 0.0f;
        this.mDrawing.fingerUp(x, y, this.mCanvas);
        this.isMoving = false;
        this.mDrawing.getLength();
        this.handler.sendEmptyMessage(Drawer.UPDATE_DRAW_START);
    }

    private void fingerMove(float x, float y) {
        this.tempX = x;
        this.tempY = y;
        this.isMoving = true;
        this.mDrawing.fingerMove(x, y, this.mCanvas);
    }

    private void fingerDown(float x, float y) {
        Applications.isHandTrack = true;
        this.handler.sendEmptyMessage(146);
        this.isMoving = true;
        this.handler.sendEmptyMessage(Drawer.UPDATE_DRAW_STOP);
        mFlightPath.clear();
        clearCanvas();
        this.mDrawing.fingerDown(x, y, this.mCanvas);
    }

    public void clearCanvas() {
        if (this.mBitmap == null) {
            this.mBitmap = Bitmap.createBitmap(this.screenWidth, this.screenHeight, Config.ARGB_8888);
        }
        this.mBitmap.eraseColor(0);
        this.mCanvas.setBitmap(this.mBitmap);
    }
}
