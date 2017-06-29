package com.example.yls.newsclient.activity;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.yls.newsclient.R;

public class VideoPlayActivity extends BaseActivity {
    private VideoView mVideoView;
    private ProgressBar mProgressBar;

    @Override
    protected void initData() {
        String videoUrl = getIntent().getStringExtra("video_url");
        mVideoView.setVideoPath(videoUrl);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_01);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_video_play;
    }
}
