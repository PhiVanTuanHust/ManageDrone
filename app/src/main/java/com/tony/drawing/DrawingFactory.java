package com.tony.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;


public class DrawingFactory {
    public static final int DRAWING_CIRCLE = 4;
    public static final int DRAWING_ERASER = 6;
    public static final int DRAWING_OVAL = 3;
    public static final int DRAWING_PATHLINE = 0;
    public static final int DRAWING_POINTS = 5;
    public static final int DRAWING_RECT = 2;
    public static final int DRAWING_STRAIGHTLINE = 1;
    private DrawingPath drawingPath;
    private int id = 0;
    public Drawing mDrawing = null;

    public class Circle extends Drawing {
        public void draw(Canvas canvas) {
            canvas.drawCircle(this.startX + ((this.stopX - this.startX) / 2.0f), this.startY + ((this.stopY - this.startY) / 2.0f), Math.abs(this.startX - this.stopX) / 2.0f, DrawingPaint.getMyPaint());
        }
    }

    public class Eraser extends Drawing {
        private static final float TOUCH_TOLERANCE = 4.0f;
        private Paint eraser;
        private Path mPath;
        private float mX;
        private float mY;

        public Eraser() {
            this.mPath = null;
            this.mPath = new Path();
            this.eraser = new Paint();
            this.eraser.setColor(-1);
            this.eraser.setStrokeWidth(5.0f);
        }

        public void draw(Canvas canvas) {
            System.out.println("drawing earser");
            canvas.drawPath(this.mPath, this.eraser);
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            this.mPath.reset();
            this.mPath.moveTo(x, y);
            this.mX = x;
            this.mY = y;
        }

        public void fingerMove(float x, float y, Canvas canvas) {
            float dx = Math.abs(x - this.mX);
            float dy = Math.abs(y - this.mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                this.mPath.quadTo(this.mX, this.mY, (this.mX + x) / 2.0f, (this.mY + y) / 2.0f);
                this.mX = x;
                this.mY = y;
            }
            draw(canvas);
        }

        public void fingerUp(float x, float y, Canvas canvas) {
            this.mPath.lineTo(this.mX, this.mY);
            draw(canvas);
            this.mPath.reset();
        }
    }

    public class Oval extends Drawing {
        private RectF mRectF = null;

        public void draw(Canvas canvas) {
            if (this.startX >= this.stopX) {
                if (this.startY >= this.stopY) {
                    this.mRectF.left = this.stopX;
                    this.mRectF.top = this.stopY;
                    this.mRectF.right = this.startX;
                    this.mRectF.bottom = this.startY;
                } else {
                    this.mRectF.left = this.stopX;
                    this.mRectF.top = this.startY;
                    this.mRectF.right = this.startX;
                    this.mRectF.bottom = this.stopY;
                }
            } else if (this.startY >= this.stopY) {
                this.mRectF.left = this.startX;
                this.mRectF.top = this.stopY;
                this.mRectF.right = this.stopX;
                this.mRectF.bottom = this.startY;
            } else {
                this.mRectF.left = this.startX;
                this.mRectF.top = this.startY;
                this.mRectF.right = this.stopX;
                this.mRectF.bottom = this.stopY;
            }
            canvas.drawOval(this.mRectF, DrawingPaint.getMyPaint());
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            super.fingerDown(x, y, canvas);
            this.mRectF = new RectF();
            DrawingFactory.this.drawingPath = new DrawingPath();
            DrawingFactory.this.drawingPath.coordinate = new Coordinate();
            DrawingFactory.this.drawingPath.id = 3;
            DrawingFactory.this.drawingPath.color = DrawingPaint.getMyPaint().getColor();
            DrawingFactory.this.drawingPath.paint = DrawingPaint.getMyPaint();
            DrawingFactory.this.drawingPath.strokeWidth = DrawingPaint.getMyPaint().getStrokeWidth();
            DrawingFactory.this.drawingPath.path = null;
            DrawingFactory.this.drawingPath.rectF = this.mRectF;
            DrawingFactory.this.drawingPath.coordinate.startX = x;
            DrawingFactory.this.drawingPath.coordinate.startY = y;
        }

        public void fingerUp(float x, float y, Canvas canvas) {
            super.fingerUp(x, y, canvas);
            DrawingFactory.this.drawingPath.coordinate.stopX = x;
            DrawingFactory.this.drawingPath.coordinate.stopY = y;
        }
    }

    public class PathLine extends Drawing {
        private static final float TOUCH_TOLERANCE = 4.0f;
        private float mLength;
        private Path mPath;
        private float mX;
        private float mY;

        public void draw(Canvas canvas) {
            canvas.drawPath(this.mPath, DrawingPaint.getMyPaint());
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            this.mPath = new Path();
            this.mPath.reset();
            this.mPath.moveTo(x, y);
            this.mX = x;
            this.mY = y;
            this.mLength = 0.0f;
            DrawingFactory.this.drawingPath = new DrawingPath();
            DrawingFactory.this.drawingPath.coordinate = new Coordinate();
            DrawingFactory.this.drawingPath.id = 0;
            DrawingFactory.this.drawingPath.color = DrawingPaint.getMyPaint().getColor();
            DrawingFactory.this.drawingPath.paint = DrawingPaint.getMyPaint();
            DrawingFactory.this.drawingPath.strokeWidth = DrawingPaint.getMyPaint().getStrokeWidth();
            DrawingFactory.this.drawingPath.path = this.mPath;
            DrawingFactory.this.drawingPath.rectF = null;
            DrawingFactory.this.drawingPath.coordinate.startX = x;
            DrawingFactory.this.drawingPath.coordinate.startY = y;
            Coordinate mCoordinate = new Coordinate();
            mCoordinate.mX = x;
            mCoordinate.mY = y;
            Rudder.mFlightPath.add(mCoordinate);
        }

        public void fingerMove(float x, float y, Canvas canvas) {
            float dx = Math.abs(x - this.mX);
            float dy = Math.abs(y - this.mY);
            this.mLength = (float) (((double) this.mLength) + Math.sqrt(Math.pow((double) dx, 2.0d) + Math.pow((double) dy, 2.0d)));
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                this.mPath.quadTo(this.mX, this.mY, (this.mX + x) / 2.0f, (this.mY + y) / 2.0f);
                this.mX = x;
                this.mY = y;
                Coordinate mCoordinate = new Coordinate();
                mCoordinate.mX = x;
                mCoordinate.mY = y;
                Rudder.mFlightPath.add(mCoordinate);
            }
            draw(canvas);
        }

        public void fingerUp(float x, float y, Canvas canvas) {
            this.mPath.lineTo(this.mX, this.mY);
            draw(canvas);
            DrawingFactory.this.drawingPath.coordinate.stopX = x;
            DrawingFactory.this.drawingPath.coordinate.stopY = y;
            Coordinate mCoordinate = new Coordinate();
            mCoordinate.mX = x;
            mCoordinate.mY = y;
            Rudder.mFlightPath.add(mCoordinate);
        }

        public float getLength() {
            return this.mLength;
        }
    }

    public class Points extends Drawing {
        private Paint mPaint = new Paint(DrawingPaint.getMyPaint());

        public Points() {
            this.mPaint.setStyle(Style.FILL);
        }

        public void draw(Canvas canvas) {
            canvas.drawCircle(this.stopX, this.stopY, DrawingPaint.getMyPaint().getStrokeWidth() + 1.0f, this.mPaint);
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            canvas.drawCircle(x, y, DrawingPaint.getMyPaint().getStrokeWidth() + 1.0f, this.mPaint);
        }

        public void fingerMove(float x, float y, Canvas canvas) {
            canvas.drawCircle(x, y, DrawingPaint.getMyPaint().getStrokeWidth() + 1.0f, this.mPaint);
        }
    }

    public class Rect extends Drawing {
        public void draw(Canvas canvas) {
            if (this.startX >= this.stopX) {
                if (this.startY >= this.stopY) {
                    canvas.drawRect(this.stopX, this.stopY, this.startX, this.startY, DrawingPaint.getMyPaint());
                    return;
                }
                canvas.drawRect(this.stopX, this.startY, this.startX, this.stopY, DrawingPaint.getMyPaint());
            } else if (this.startY >= this.stopY) {
                canvas.drawRect(this.startX, this.stopY, this.stopX, this.startY, DrawingPaint.getMyPaint());
            } else {
                canvas.drawRect(this.startX, this.startY, this.stopX, this.stopY, DrawingPaint.getMyPaint());
            }
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            super.fingerDown(x, y, canvas);
            DrawingFactory.this.drawingPath = new DrawingPath();
            DrawingFactory.this.drawingPath.coordinate = new Coordinate();
            DrawingFactory.this.drawingPath.id = 2;
            DrawingFactory.this.drawingPath.color = DrawingPaint.getMyPaint().getColor();
            DrawingFactory.this.drawingPath.paint = DrawingPaint.getMyPaint();
            DrawingFactory.this.drawingPath.strokeWidth = DrawingPaint.getMyPaint().getStrokeWidth();
            DrawingFactory.this.drawingPath.path = null;
            DrawingFactory.this.drawingPath.rectF = null;
            DrawingFactory.this.drawingPath.coordinate.startX = x;
            DrawingFactory.this.drawingPath.coordinate.startY = y;
        }

        public void fingerUp(float x, float y, Canvas canvas) {
            super.fingerUp(x, y, canvas);
            DrawingFactory.this.drawingPath.coordinate.stopX = x;
            DrawingFactory.this.drawingPath.coordinate.stopY = y;
        }
    }

    public class StraightLine extends Drawing {
        public void draw(Canvas canvas) {
            canvas.drawLine(this.startX, this.startY, this.stopX, this.stopY, DrawingPaint.getMyPaint());
        }

        public void fingerDown(float x, float y, Canvas canvas) {
            super.fingerDown(x, y, canvas);
            DrawingFactory.this.drawingPath = new DrawingPath();
            DrawingFactory.this.drawingPath.coordinate = new Coordinate();
            DrawingFactory.this.drawingPath.id = 1;
            DrawingFactory.this.drawingPath.color = DrawingPaint.getMyPaint().getColor();
            DrawingFactory.this.drawingPath.paint = DrawingPaint.getMyPaint();
            DrawingFactory.this.drawingPath.strokeWidth = DrawingPaint.getMyPaint().getStrokeWidth();
            DrawingFactory.this.drawingPath.path = null;
            DrawingFactory.this.drawingPath.rectF = null;
            DrawingFactory.this.drawingPath.coordinate.startX = x;
            DrawingFactory.this.drawingPath.coordinate.startY = y;
        }

        public void fingerUp(float x, float y, Canvas canvas) {
            super.fingerUp(x, y, canvas);
            DrawingFactory.this.drawingPath.coordinate.stopX = x;
            DrawingFactory.this.drawingPath.coordinate.stopY = y;
        }
    }

    public Drawing createDrawing(int id) {
        this.id = id;
        switch (id) {
            case 0:
                this.mDrawing = new PathLine();
                break;
            case 1:
                this.mDrawing = new StraightLine();
                break;
            case 2:
                this.mDrawing = new Rect();
                break;
            case 3:
                this.mDrawing = new Oval();
                break;
            case 4:
                this.mDrawing = new Circle();
                break;
            case 5:
                this.mDrawing = new Points();
                break;
            case 6:
                this.mDrawing = new Eraser();
                break;
            default:
                this.mDrawing = new StraightLine();
                break;
        }
        return this.mDrawing;
    }

    public int getDrawingID() {
        return this.id;
    }
}
