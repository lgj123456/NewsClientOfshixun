package com.example.yls.newsclient.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.bean.NewsEntity;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private List<String> urlList = new ArrayList<>();

    @Override
    protected void initData() {
        NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean) getIntent().getSerializableExtra("news");
        Log.e("aaaaaaaaaa", "initData: " + newsBean.getUrl());
        Log.e("aaaaaaaaaaa", "initData: " + newsBean.getUrl_3w());
        mWebView.loadUrl(newsBean.getUrl());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(newsBean.getTitle());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pb_01);
        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.addJavascriptInterface(new JsInterface(this), "imagelistner");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("aaaaaaaaa", "加载完成-----" + url);
                view.getSettings().setBlockNetworkImage(false);
                addLocalJs();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_news_detail;
    }


    // 添加自定义的js
    public void addLocalJs() {
        mWebView.loadUrl("javascript:(function(){ " + "var objs = document.getElementsByTagName(\"img\");"
                + " var array=new Array(); " + " for(var j=0;j<objs.length;j++){ " + "array[j]=objs[j].src;" + " }  "
                + "for(var i=0;i<objs.length;i++){"
                + "objs[i].onclick=function(){  window.imagelistner.openImage(this.src,array);" + "}  " + "}    })()");
    }

    // 自定义的本地js方法
    private class JsInterface {
        private Context context;

        public JsInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void getImage(String[] urls) {

            Log.i("aaaaaaaaaa", "========进入js方法========");

            if (urls != null && urls.length > 0) {
                for (int i = 0; i < urls.length; i++) {
                    Log.i("===" + i + "===", urls[i]);
                }
            }
        }

        @JavascriptInterface
        public void openImage(String nowUrl, String[] urls) {
            //nowUrl是点击的图片的url，urls是当前界面的所有图片的url


            String[] temp = new String[urls.length - 1];
            for (int i = 0; i < urls.length - 1; i++) {
                temp[i] = urls[i + 1];
            }
            Intent intent = new Intent(context, ShowWebImageActivity.class);
            intent.putExtra("imgUrl", temp);
            context.startActivity(intent);
        }
    }
}
