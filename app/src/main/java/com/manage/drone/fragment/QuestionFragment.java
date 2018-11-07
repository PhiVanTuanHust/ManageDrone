package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.manage.drone.R;
import com.manage.drone.adapter.AdapterQuestion;
import com.manage.drone.adapter.BaseRecycleViewAdapter;
import com.manage.drone.models.QuestionModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener{
    @BindView(R.id.edtSearch)
    EditText editSearch;
    @BindView(R.id.rcvList)
    RecyclerView rcView;

    public static QuestionFragment newInstance() {

        Bundle args = new Bundle();

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private AdapterQuestion adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.connect_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initData() {
        editSearch.setHint("Xin chào chúng tôi có thể giúp gì cho bạn");
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.addItemDecoration(new DividerItemDecoration(rcView.getContext(),DividerItemDecoration.VERTICAL));
        adapter=new AdapterQuestion(getActivity(),this);
        rcView.setAdapter(adapter);

        adapter.loadQuestion(new QuestionModel().getQuestionSuggest());
    }

    @Override
    public void onItemClick(int position) {

    }
}
