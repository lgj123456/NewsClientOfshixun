package com.example.yls.newsclient.activity;

import android.content.Intent;
import android.os.SystemClock;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.util.SharedPreUtil;

public class StartActivity extends BaseActivity {


    @Override
    protected void initData() {

        new Thread() {
            public void run() {
                SystemClock.sleep(1500);
                boolean firstRun = SharedPreUtil.getBoolean(getApplicationContext(), "firstRun", true);
                if (firstRun) {
                    SharedPreUtil.saveBoolean(StartActivity.this, "firstRun", false);
                    enterGuideActivity();
                } else {
                    enterMainActivity();
                }
            }
        }.start();
    }

    private void enterGuideActivity() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.start_activity;
    }
}
