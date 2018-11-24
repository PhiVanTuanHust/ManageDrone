package com.manage.drone.customs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ViewPagerDisable extends ViewPager {
    public ViewPagerDisable(@NonNull Context context) {
        super(context);
    }
    public ViewPagerDisable (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }
}
