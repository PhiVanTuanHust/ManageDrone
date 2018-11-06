package com.journaldev.navigationviewexpandablelistview.models;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class GroupModel extends ChildModel {
    private int resDrawable;

    public GroupModel(String title,int resDrawable) {
        super(title);
        this.resDrawable=resDrawable;
    }

    public int getResDrawable() {
        return resDrawable;
    }
}
