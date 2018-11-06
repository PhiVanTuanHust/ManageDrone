package com.journaldev.navigationviewexpandablelistview.customs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Phí Văn Tuấn on 6/11/2018.
 */

public class BrightImageView extends android.support.v7.widget.AppCompatImageView {
    private Context context;

    public BrightImageView(Context context) {
        super(context);
        this.context = context;
        initAlphaView();
    }

    public BrightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAlphaView();
    }

    public BrightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAlphaView();
    }

    private void initAlphaView() {
        setImageDrawable(newSelector());
    }

    private Drawable changeBrightnessBitmap(Bitmap srcBitmap) {
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) -67, 0.0f, 1.0f, 0.0f, 0.0f, (float) -67, 0.0f, 0.0f, 1.0f, 0.0f, (float) -67, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        new Canvas(bmp).drawBitmap(srcBitmap, 0.0f, 0.0f, paint);
        return new BitmapDrawable(this.context.getResources(), bmp);
    }

    private StateListDrawable newSelector() {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = getDrawable();
        if (normal != null) {
            bg.addState(new int[]{16842919, 16842910}, changeBrightnessBitmap(((BitmapDrawable) normal).getBitmap()));
            bg.addState(new int[]{16842910}, normal);
            bg.addState(new int[0], normal);
        }
        return bg;
    }
}
