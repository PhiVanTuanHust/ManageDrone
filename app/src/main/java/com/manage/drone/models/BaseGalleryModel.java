package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 17/12/2018.
 */

public abstract class BaseGalleryModel {
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_VIDEO = 4;
    //da up load
    public static final int STATE_DONE = 0;
    //chua up load
    public static final int STATE_UPLOAD = 1;
    //chuan bi up load
    public static final int STATE_PREPARE = 2;
    private int imgThumb;
    private int state;
    private String time;

    public BaseGalleryModel(int imgThumb, String time,int state) {
        this.imgThumb = imgThumb;
        this.time = time;
        this.state=state;
    }

    public abstract int getType();

    public int getImgThumb() {
        return imgThumb;
    }

    public BaseGalleryModel() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }
}
