package com.manage.drone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manage.drone.R;
import com.manage.drone.models.GuideModel;
import com.manage.drone.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class GuideFragment extends BaseFragment {
    @BindView(R.id.imgGuide)
    ImageView imgGuide;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    GuideModel guide;
    @BindView(R.id.view)
    RelativeLayout view;

    public static GuideFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position",position);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.guide_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initData() {


        guide= Const.getGuide(getActivity()).get(getArguments().getInt("position"));
        Glide.with(this).load(guide.getResDrawable()).into(imgGuide);
        tvTitle.setText(guide.getTitle());
        view.setBackgroundColor(getResources().getColor(guide.getResColor()));
    }
}
