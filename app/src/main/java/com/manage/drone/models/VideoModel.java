package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 18/12/2018.
 */

public class VideoModel extends BaseGalleryModel {
    private int pathVideo;
    private String name;
    public int getPathVideo() {
        return pathVideo;
    }

    public VideoModel(int imgThumb, String time,int pathVideo,String name) {
        super(imgThumb, time);
        this.pathVideo=pathVideo;
        this.name=name;
    }

    @Override
    public int getType() {
        return BaseGalleryModel.TYPE_VIDEO;
    }

    public String getName() {
        return name;
    }
}
