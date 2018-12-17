package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public abstract class BaseGalleryModel {
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_VIDEO = 4;
    private int imgThumb;

    private String time;

    public BaseGalleryModel(int imgThumb, String time) {
        this.imgThumb = imgThumb;
        this.time = time;
    }

    public abstract int getType();

    public int getImgThumb() {
        return imgThumb;
    }

    public BaseGalleryModel() {
    }

    public String getTime() {
        return time;
    }
}
