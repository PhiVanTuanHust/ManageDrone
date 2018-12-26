package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.manage.drone.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WarningActivity extends BaseActivity {
   @BindView(R.id.imgWarning)
   PhotoView imgWarning;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_warning;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cảnh báo");
    }

    @Override
    protected void initData() {
        Glide.with(this).load(R.drawable.chayrung).into(imgWarning);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startWarning(Context context){
        Intent intent=new Intent(context,WarningActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btnViewMap)
    public void onViewMap(){
        MapActivity.startMap(WarningActivity.this);
    }
}
