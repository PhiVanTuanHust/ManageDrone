package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class GuideModel extends GroupModel{
    private int resColor;
    public GuideModel(String title, int resDrawable,int resColor) {
        super(title, resDrawable);
        this.resColor=resColor;
    }

    public int getResColor() {
        return resColor;
    }
}
