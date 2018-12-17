package com.manage.drone.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.manage.drone.R;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewVideoActivity extends BaseActivity {
    private static final String TAG="TAG";
    @BindView(R.id.videoView) UniversalVideoView mVideoView;
    @BindView(R.id.media_controller) UniversalMediaController mMediaController;
    @BindView(R.id.video_layout)
    FrameLayout mVideoLayout;
    private int resVideo;
    private boolean isFullscreen=false;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_view_video;
    }

    @Override
    protected void initView() {
       resVideo=getIntent().getIntExtra("resVideo",R.raw.video);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mVideoView.setMediaController(mMediaController);
        String path = "android.resource://" + getPackageName() + "/" + resVideo;
        mVideoView.setVideoURI(Uri.parse(path));
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
//                this.isFullscreen = isFullscreen;
//                if (isFullscreen) {
//                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
//                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    mVideoLayout.setLayoutParams(layoutParams);
//                    //GONE the unconcerned views to leave room for video and controller
////                    mBottomLayout.setVisibility(View.GONE);
//                } else {
//                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
//                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    layoutParams.height = this.cachedHeight;
//                    mVideoLayout.setLayoutParams(layoutParams);
//
//                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
                Log.d(TAG, "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
                Log.d(TAG, "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
                Log.d(TAG, "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading
                Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
            }

        });
    }

public static void startVideo(Context context,int resVideo){
    Intent intent=new Intent(context,ViewVideoActivity.class);
    intent.putExtra("resVideo",resVideo);
    context.startActivity(intent);
}
}
