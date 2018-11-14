package com.manage.drone.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.manage.drone.R;
import com.manage.drone.adapter.BaseRecycleViewAdapter;
import com.manage.drone.adapter.QuestionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener{
    @BindView(R.id.edtSearch)
    EditText editSearch;
    @BindView(R.id.rcvList)
    ExpandableListView rcView;

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
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initData() {
        editSearch.setHint("Xin chào chúng tôi có thể giúp gì cho bạn");
        adapter=new QuestionAdapter(getActivity());
        rcView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
