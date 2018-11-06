package com.journaldev.navigationviewexpandablelistview.utils;


import android.content.Context;

import com.google.android.gms.maps.model.PolygonOptions;
import com.journaldev.navigationviewexpandablelistview.R;
import com.journaldev.navigationviewexpandablelistview.models.ChildModel;
import com.journaldev.navigationviewexpandablelistview.models.GroupModel;
import com.journaldev.navigationviewexpandablelistview.models.GuideModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 24/10/2018.
 */

public class Const {
    private static List<GroupModel> groupModels;

    public static List<PolygonOptions> lstPolygonOptions=new ArrayList<>();
    public static HashMap<GroupModel, List<ChildModel>> getMenu(Context context) {
        HashMap<GroupModel, List<ChildModel>> hashMap = new HashMap<>();
        List<GroupModel> lstGroups = getMenuGroup(context);

        List<ChildModel> childModels = new ArrayList<>();
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_1_1)));
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_1_2)));
        hashMap.put(lstGroups.get(1), childModels);

        childModels = new ArrayList<>();
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_6_1)));
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_6_2)));
        hashMap.put(lstGroups.get(6), childModels);

        return hashMap;
    }

    public static List<GroupModel> getMenuGroup(Context context) {
        if (groupModels==null){
            groupModels=new ArrayList<>();
            groupModels.add(new GroupModel("Điều khiển drone",R.drawable.ic_control));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_1), R.drawable.icon));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_2), R.drawable.ic_zoning));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_3), R.drawable.ic_observe));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_4), R.drawable.ic_mail));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_5), R.drawable.ic_setting));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_6), R.drawable.ic_help));
        }

        return groupModels;
    }

    public static List<GuideModel> getGuide(Context context){
        List<GuideModel> lst=new ArrayList<>();
        lst.add(new GuideModel(context.getResources().getString(R.string.group_1_1), R.drawable.ic_connect_guide,R.color.google_red));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_1_2), R.drawable.ic_operation_guide,R.color.google_blue));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_2), R.drawable.ic_zone_guide,R.color.google_yellow));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_3), R.drawable.ic_obsever_guide,R.color.google_green));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_4), R.drawable.ic_message_guide,R.color.colorSwitch));
        lst.add(new GuideModel("Điều khiển drone", R.drawable.ic_control_guide,R.color.google_red));

        return lst;
    }

}
