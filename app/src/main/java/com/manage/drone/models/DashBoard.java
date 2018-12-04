package com.manage.drone.models;

public class DashBoard {
    public DashBoard(int icon, String title, String des) {
        this.icon = icon;
        this.title = title;
        this.des = des;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    private int icon;
    private String title;
    private String des;
}
