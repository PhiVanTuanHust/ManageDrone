package com.manage.drone.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.manage.drone.R;
import com.manage.drone.models.ChildModel;
import com.manage.drone.models.GroupModel;
import com.manage.drone.utils.Const;

import java.util.HashMap;
import java.util.List;

/**
 * Created by anupamchugh on 22/12/17.
 */


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GroupModel> lstGroupModels;
    private HashMap<GroupModel, List<ChildModel>> menuModel;
//    private List<MenuModel> listDataHeader;
//    private HashMap<MenuModel, List<MenuModel>> listDataChild;

    public ExpandableListAdapter(Context context) {
        this.context = context;
        this.lstGroupModels = Const.getMenuGroup(context);
        this.menuModel = Const.getMenu(context);
    }

    @Override
    public ChildModel getChild(int groupPosition, int childPosititon) {
        return this.menuModel.get(this.lstGroupModels.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getTitle();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size=0;
        List<ChildModel> childModels=menuModel.get(getGroup(groupPosition));
        if (this.menuModel.get(getGroup(groupPosition)) == null)
            size=0;
        else {
            size= menuModel.get(getGroup(groupPosition)).size();
        }
        return size;
    }

    @Override
    public GroupModel getGroup(int groupPosition) {
        return this.lstGroupModels.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.lstGroupModels.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getTitle();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        TextView txtMessage = convertView.findViewById(R.id.tvMessage);
        ImageView imgThumb=convertView.findViewById(R.id.imgThumb);
        imgThumb.setImageResource(getGroup(groupPosition).getResDrawable());
        ImageView imgDown = convertView.findViewById(R.id.imgDown);
        if (isExpanded){
            imgDown.setImageResource(R.drawable.ic_up);
        }else {
            imgDown.setImageResource(R.drawable.ic_down);
        }
        if (getChildrenCount(groupPosition) != 0) {
            imgDown.setVisibility(View.VISIBLE);
        } else {
            imgDown.setVisibility(View.GONE);
        }
        if (groupPosition == 5) {
            txtMessage.setVisibility(View.VISIBLE);
        } else {
            txtMessage.setVisibility(View.GONE);
        }
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}