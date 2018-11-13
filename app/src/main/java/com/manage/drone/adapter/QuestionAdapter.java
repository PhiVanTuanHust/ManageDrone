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
import com.manage.drone.models.QuestionModel;

import java.util.List;

public class QuestionAdapter extends BaseExpandableListAdapter {
    private List<QuestionModel> lstQuestion;
    private Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
        lstQuestion = new QuestionModel().getQuestionSuggest();

    }

    @Override
    public int getGroupCount() {
        return lstQuestion.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public QuestionModel getGroup(int groupPosition) {
        return lstQuestion.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return lstQuestion.get(groupPosition).getAnswer();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getTitle();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_question, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        TextView txtMessage = convertView.findViewById(R.id.tvMessage);
        ImageView imgDown = convertView.findViewById(R.id.imgDown);
        ImageView imgThumb = convertView.findViewById(R.id.imgThumb);
        imgThumb.setImageResource(R.drawable.ic_trend);
        if (isExpanded) {
            imgDown.setImageResource(R.drawable.ic_up);
        } else {
            imgDown.setImageResource(R.drawable.ic_down);
        }
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_answer, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
