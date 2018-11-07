package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.manage.drone.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ControlActivity extends BaseActivity {
    @BindView(R.id.frame_back)
    FrameLayout frameBack;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_control;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.frame_back)
    public void onBack(){
        onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public static void startControl(Context context){
        Intent intent=new Intent(context,ControlActivity.class);
        context.startActivity(intent);
    }

}
