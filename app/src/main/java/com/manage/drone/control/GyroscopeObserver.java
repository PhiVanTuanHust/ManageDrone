package com.manage.drone.control;

import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Phí Văn Tuấn on 30/11/2018.
 */

public class GyroscopeObserver {
    private LinkedList<PanoramaImageView> mViews = new LinkedList<>();

    void addPanoramaImageView(PanoramaImageView view) {
        if (view != null && !mViews.contains(view)) {
            mViews.addFirst(view);
        }
    }

    public void updateHeight(float height) {
        for (PanoramaImageView view : mViews) {
            if (view != null && view.getOrientation() == PanoramaImageView.ORIENTATION_VERTICAL) {
                view.updateProgress(height);
            }
        }
    }
}
