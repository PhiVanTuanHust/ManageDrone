package com.manage.drone.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.manage.drone.BuildConfig;
import com.manage.drone.R;
import com.manage.drone.adapter.BaseRecycleViewAdapter;

import com.manage.drone.adapter.QuestionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener, TextWatcher {
    @BindView(R.id.edtSearch)
    EditText editSearch;
    @BindView(R.id.rcvList)
    ExpandableListView rcView;
    @BindView(R.id.viewFeedback)
    RelativeLayout viewFeedBack;
    public static QuestionFragment newInstance() {

        Bundle args = new Bundle();

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private QuestionAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.question_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        editSearch.setHint("Xin chào chúng tôi có thể giúp gì cho bạn");
        adapter = new QuestionAdapter(getActivity());
        rcView.setAdapter(adapter);
        setListViewHeight(rcView,-1);
        rcView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView,i);
                return false;
            }
        });
        editSearch.addTextChangedListener(this);
    }

    @Override
    public void onItemClick(int position) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    @Override
    public void afterTextChanged(Editable s) {
        adapter.filter(s.toString());
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
//        if (height < 10)
//            height = 200;
        Log.e("height",height+"");
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    @OnClick(R.id.viewFeedback)
    public void onFeedBack(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = {"tuongtacnguoimay20181.group14@gmail.com", ""};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack Manage Drone Android Version");
        try {
            String android_version = "Android Version:" + android.os.Build.VERSION.SDK_INT;
            String device_name = "\nTên thiết bị:" + getDeviceName();
            String version_code = "\nVersion App Code:" + BuildConfig.VERSION_CODE;
            String feedback = "\nHãy cho chúng tôi phản hồi của bạn tại đây:\n";
            intent.putExtra(Intent.EXTRA_TEXT, android_version + device_name + version_code + feedback);
        } catch (Exception e) {
            intent.putExtra(Intent.EXTRA_TEXT, "Hãy cho chúng tôi phản hồi của bạn tại đây:\n");
        }

        intent.setType("message/rfc822");
        Intent chooser = Intent.createChooser(intent, "Send email");
        startActivity(chooser);
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}