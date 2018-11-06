package com.journaldev.navigationviewexpandablelistview.models;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ItemConnect extends BaseItemModel {

    public  ItemConnect(String title){
        super(title);
    }

    @Override
    public int getType() {
        return TYPE_ITEM;
    }


}
