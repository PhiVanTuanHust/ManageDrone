package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 18/12/2018.
 */

public class ImageModel extends BaseGalleryModel {
    public ImageModel(int imgThumb, String time) {
        super(imgThumb, time);
    }

    @Override
    public int getType() {
        return BaseGalleryModel.TYPE_IMAGE;
    }
}
