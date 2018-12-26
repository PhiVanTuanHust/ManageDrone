package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.manage.drone.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewImageActivity extends BaseActivity {
    @BindView(R.id.iv_photo)
    PhotoView photoView;
    @BindView(R.id.imgBack) ImageView imgBack;
    private int resDrawable;
    private boolean isTab = false;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_view_image;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        resDrawable=getIntent().getIntExtra("resDrawable",R.drawable.home_bg);
    }

    @Override
    protected void initData() {
        Glide.with(this).load(resDrawable).into(photoView);
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (isTab) {
                    imgBack.setVisibility(View.VISIBLE);
                } else {
                    imgBack.setVisibility(View.GONE);
                }
                isTab=!isTab;
            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onBack(){
        onBackPressed();
    }
    public static void openImage(Context context,int resDrawable){
        Intent intent=new Intent(context,ViewImageActivity.class);
        intent.putExtra("resDrawable",resDrawable);
        context.startActivity(intent);
    }
}
