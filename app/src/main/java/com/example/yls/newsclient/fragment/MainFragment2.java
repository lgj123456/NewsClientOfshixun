package com.example.yls.newsclient.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.adapter.VideoAdapter;
import com.example.yls.newsclient.base.URLManager;
import com.example.yls.newsclient.bean.VideoEntity;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by yhdj on 2017/6/26.
 */

public class MainFragment2 extends BaseFragment {
    private Gson mGson;
    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_02;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) super.mRootView.findViewById(R.id.recycle_view);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getVideoDatas();
    }

    private void getVideoDatas() {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, URLManager.VideoURL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                json = json.replace("V9LG4B3A0", "result");
                mGson = new Gson();
                VideoEntity newsData = mGson.fromJson(json, VideoEntity.class);
                Log.e("aaaaaaaaa", "onSuccess22222222: " + newsData.getResult().size());
                showDatas(newsData);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
    }

    private void showDatas(VideoEntity newsData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new VideoAdapter(getActivity(), newsData.getResult()));
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
