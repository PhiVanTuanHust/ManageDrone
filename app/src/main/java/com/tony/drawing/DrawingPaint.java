package com.tony.drawing;

import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;

public class DrawingPaint extends Paint {
    private static final DrawingPaint mPaint = new DrawingPaint();

    private DrawingPaint() {
    }

    public static DrawingPaint getMyPaint() {
        return mPaint;
    }

    public void reset(int strokeWidth) {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(-256);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setStrokeCap(Cap.ROUND);
        mPaint.setStrokeWidth((float) strokeWidth);
    }
}
