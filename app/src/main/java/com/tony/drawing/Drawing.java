package com.tony.drawing;

import android.graphics.Canvas;

public abstract class Drawing {
    protected float mLength;
    protected float startX;
    protected float startY;
    protected float stopX;
    protected float stopY;

    public abstract void draw(Canvas canvas);

    public void reset() {
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.stopX = 0.0f;
        this.stopY = 0.0f;
        this.mLength = 0.0f;
    }

    public void fingerDown(float x, float y, Canvas canvas) {
        reset();
        this.startX = x;
        this.startY = y;
    }

    public void fingerMove(float x, float y, Canvas canvas) {
        this.stopX = x;
        this.stopY = y;
    }

    public void fingerUp(float x, float y, Canvas canvas) {
        this.stopX = x;
        this.stopY = y;
        draw(canvas);
        reset();
    }

    public float getLength() {
        return this.mLength;
    }
}
