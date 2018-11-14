package com.manage.drone.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manage.drone.R;
import com.manage.drone.adapter.ProfileAdapter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phí Văn Tuấn on 14/11/2018.
 */

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.rcView)
    RecyclerView rcView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;

    private static final int REQUEST_AVATAR=1;
    private static final int REQUEST_STORAGE=2;

    private ProfileAdapter adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Thông tin tài khoản");
        ButterKnife.bind(this);
        adapter=new ProfileAdapter(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        rcView.addItemDecoration(new DividerItemDecoration(rcView.getContext(),DividerItemDecoration.VERTICAL));
        rcView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
    @OnClick(R.id.fab)
    public void onChangeAvatar(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_AVATAR);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_AVATAR){
            if (resultCode==RESULT_OK&&data!=null){
                Uri selectedImage = data.getData();
                Glide.with(this).load(selectedImage).into(imgAvatar);
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                File file=new File(picturePath);
//                if (file.exists()){
//                    Glide.with(this).load(file).into(imgAvatar);
//                }

            }
        }
    }
}
