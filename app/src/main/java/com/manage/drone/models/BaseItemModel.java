package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public abstract class BaseItemModel {
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_HEADER = 0;
    private String title;
    private boolean isConnect;

    public BaseItemModel(String title,boolean isConnect) {
        this.title = title;
    }

    public abstract int getType();

    public String getTitle(){
        return title;
    }

    public BaseItemModel() {
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
