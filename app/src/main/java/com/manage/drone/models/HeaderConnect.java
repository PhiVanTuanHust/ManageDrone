package com.manage.drone.models;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class HeaderConnect extends BaseItemModel {
    public HeaderConnect(String title) {
        super(title,false);

    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }


}
