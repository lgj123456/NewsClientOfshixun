package com.example.yls.newsclient.activity;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yls.newsclient.R;

public class GuideActivity extends BaseActivity {

    private ImageView iv01;
    private Button btnGo;
    private int index = 0;
    private boolean mExitGuideActivity = false;
    private MediaPlayer mMediaPlayer;
    private int[] imagesArray = new int[]{
            R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7,
    };

    @Override
    protected void initData() {
        startAnimation();
    }

    private void startAnimation() {

        playBackgroundMusic();
        int imageId = imagesArray[index % imagesArray.length];
        iv01.setBackgroundResource(imageId);
        index++;
        iv01.setScaleX(1.0f);
        iv01.setScaleY(1.0f);
        iv01.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(3500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!mExitGuideActivity) {
                            startAnimation();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void playBackgroundMusic() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.new_version);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(1.0f, 1.0f);
        mMediaPlayer.start();

    }

    private void stopBackgroundMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    protected void initListener() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBackgroundMusic();
                enterMainActivity();
            }
        });
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        iv01 = (ImageView) findViewById(R.id.iv_01);
        btnGo = (Button) findViewById(R.id.btn_go);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExitGuideActivity = true;
        stopBackgroundMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopBackgroundMusic();
    }
}
