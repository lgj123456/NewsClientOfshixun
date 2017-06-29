package com.example.yls.newsclient.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yls.newsclient.activity.NewsDetailActivity;
import com.example.yls.newsclient.R;
import com.example.yls.newsclient.adapter.NewsAdapter;
import com.example.yls.newsclient.base.URLManager;
import com.example.yls.newsclient.bean.NewsEntity;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by yls on 2017/6/27.
 */

public class NewsFragment extends BaseFragment {
    private String channelId;

    private Gson mGson;
    private ListView mListView;
    private NewsAdapter mNewsAdapter;
    private SpringView mSpringView;
    private boolean isAddListViewHeader = false;
    private int pageNum = 0;
    private NewsEntity data = new NewsEntity();

    public void setChannelId(String channelId) {
        Log.e("aaaaaaaaaa", "setChannelId: " + channelId);
        this.channelId = channelId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
//        TextView textView = (TextView) super.mRootView.findViewById(R.id.tv_01);
//        textView.setText(channelId);
        mListView = (ListView) super.mRootView.findViewById(R.id.list_view);
        //新闻
        mNewsAdapter = new NewsAdapter(getActivity(), null);
        mListView.setAdapter(mNewsAdapter);
        initSpringView();
    }

    private void initSpringView() {
        mSpringView = (SpringView) super.mRootView.findViewById(R.id.spring_view);
        mSpringView.setType(SpringView.Type.FOLLOW);
        mSpringView.setHeader(new MeituanHeader(getContext()));
        mSpringView.setFooter(new MeituanFooter(getContext()));
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news", newsBean);
                startActivity(intent);
            }
        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                getDataFromServer();
            }

            @Override
            public void onLoadmore() {
                /*上拉*/
                pageNum++;
                getDataFromServer();

            }
        });
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        String url = URLManager.getUrl(channelId, pageNum);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;

                //  Log.e("aaaaaaaa", "onSuccess: " + json);
                json = json.replace(channelId, "result");
                //  System.out.println("-----------" + json);
                mGson = new Gson();
                NewsEntity newsData = mGson.fromJson(json, NewsEntity.class);
                Log.e("aaaaaaaaaa", "onSuccess: size" + newsData.getResult().size());
                showDatas(newsData);
                mSpringView.onFinishFreshAndLoad();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });


    }


    private void showDatas(NewsEntity newsData) {
        if (newsData == null || newsData.getResult() == null || newsData.getResult().size() == 0) {
            Log.e("aaaaaaa", "showDatas: " + "没有获取到服务器的新闻数据");
            return;
        }
        //轮播图
        List<NewsEntity.ResultBean.AdsBean> ads = newsData.getResult().get(0).getAds();
        if (ads != null && ads.size() != 0) {
            View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.list_header, mListView, false);
            SliderLayout sliderLayout = (SliderLayout) headerView.findViewById(R.id.slider_layout);
            for (int i = 0; i < ads.size(); i++) {
                final NewsEntity.ResultBean.AdsBean adsBean = ads.get(i);
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.description(adsBean.getTitle()).image(adsBean.getImgsrc());
                sliderLayout.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Log.e("aaaaaaaaaaa", "onSliderClick: " + adsBean.getUrl());
                        showToast(slider.getDescription());
                    }
                });
            }
            if (!isAddListViewHeader) {
                mListView.addHeaderView(headerView);
                isAddListViewHeader = true;
            }

        }
        if (pageNum == 0) {
            mNewsAdapter.setDatas(newsData);
        }
        if (pageNum > 0) {
            mNewsAdapter.appendDatas(newsData);
        }


//        if (pageNum > 0) {
//            mListView.smoothScrollToPosition(pageNum * URLManager.perPageSize);
//            mListView.setSelection((pageNum) * URLManager.perPageSize);
//            mNewsAdapter.notifyDataSetChanged();
//        }
    }
}
