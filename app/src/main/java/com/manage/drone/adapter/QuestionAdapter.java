package com.manage.drone.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.QuestionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionAdapter extends BaseExpandableListAdapter {
    private List<QuestionModel> lstQuestion;
    private List<QuestionModel> searchLst;
    private Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
        lstQuestion = new QuestionModel().getQuestionSuggest();
        searchLst = new ArrayList<>();
        searchLst.addAll(lstQuestion);

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
    public List<String> getChild(int groupPosition, int childPosition) {
        return lstQuestion.get(groupPosition).getLstAnswer();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getTitle();
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_question, null);
            holder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            holder.imgDown = (ImageView) convertView.findViewById(R.id.imgDown);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (isExpanded) {
            holder.imgDown.setImageResource(R.drawable.ic_up);
        } else {
            holder.imgDown.setImageResource(R.drawable.ic_down);
        }
        holder.lblListHeader.setTypeface(null, Typeface.BOLD);
        holder.lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        List<String> childText = lstQuestion.get(groupPosition).getLstAnswer();
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_answer, null);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.v);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.linearLayout.removeAllViews();
        for (int i = 0; i < childText.size(); i++) {
            int step=i+1;
            addView(context, "Bước "+step+" : "+childText.get(i), holder.linearLayout);
//            if (i == childText.size() - 1) {
//                addView(context, childText.get(i), holder.linearLayout);
//            } else {
//                addView(context, childText.get(i), holder.linearLayout);
//            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstQuestion.clear();
        if (charText.length() == 0) {
            lstQuestion.addAll(searchLst);
        } else {
            for (QuestionModel wp : searchLst) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    lstQuestion.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void addView(Context context, String answer, LinearLayout linearLayout) {
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = vi.inflate(R.layout.item_answer, linearLayout, false);
        TextView tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);

        tvAnswer.setText(answer);
        linearLayout.addView(convertView);
    }

    private class Holder {
        LinearLayout linearLayout;
        TextView lblListHeader;
        ImageView imgDown;
    }

}
