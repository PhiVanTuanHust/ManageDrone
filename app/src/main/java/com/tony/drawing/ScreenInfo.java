package com.tony.drawing;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenInfo {
    private Activity mActivity;
    private int mHeightPixels;
    private int mWidthPixels;

    public ScreenInfo(Activity activity) {
        this.mActivity = activity;
        getDisplayMetrics();
    }

    private void getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.mWidthPixels = dm.widthPixels;
        this.mHeightPixels = dm.heightPixels;
    }

    public int getWidthPixels() {
        return this.mWidthPixels;
    }

    public int getHeightPixels() {
        return this.mHeightPixels;
    }
}
