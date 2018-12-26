package com.manage.drone.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.manage.drone.R;
import com.manage.drone.adapter.ViewPagerAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private ProgressDialog progress;
    private int value=0;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView() {
        setTitle("File của bạn");
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress=new ProgressDialog(this);
        progress.setMessage("Đang tải");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.setMax(100);
        progress.setProgress(0);
    }

    @Override
    protected void initData() {
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public static void startGalleryActivity(Context context){
        Intent intent=new Intent(context,GalleryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.upload:
               showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload ");
        builder.setMessage("Bạn có muốn upload ảnh và video lên sever?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progress.show();
                update();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private class CheckTypesTask extends AsyncTask<Void, Integer, Void> {
        ProgressDialog progress = new ProgressDialog(GalleryActivity.this);



        @Override
        protected void onPreExecute() {
            //set message of the dialog
            progress.setMessage("Đang tải");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.setMax(100);
            progress.setProgress(0);
            progress.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //don't touch dialog here it'll break the application
            //do some lengthy stuff like calling login webservice
            byte data[] = new byte[1024];
            for (int i=0;i<10000000000L;i++){
                if (i % 3 == 0 )
                    publishProgress(i);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            progress.dismiss();
            Toast.makeText(GalleryActivity.this,"Upload thành công",Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);

            super.onProgressUpdate(values);
        }
    }
    private void update(){
       if(value==100){
           progress.dismiss();
           Toast.makeText(this,"Upload thanh cong",Toast.LENGTH_SHORT).show();
       }else {
           value=value+10;
           progress.setProgress(value);
           mHandler.sendEmptyMessageDelayed(0,200);

       }

    }
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    update();
                    break;
            }
        }
    };
}
