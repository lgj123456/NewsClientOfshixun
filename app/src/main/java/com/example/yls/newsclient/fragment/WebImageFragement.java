package com.example.yls.newsclient.fragment;

import android.util.Log;

import com.example.yls.newsclient.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by yls on 2017/6/29.
 */

public class WebImageFragement extends BaseFragment {
    private String mImgUrl;
    private PhotoView mPhotoView;
    private PhotoViewAttacher mPhotoViewAttacher;

    public void setImgUrl(String url) {
        this.mImgUrl = url;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_web_img;
    }

    @Override
    public void initView() {
        mPhotoView = (PhotoView) super.mRootView.findViewById(R.id.photo_view);
        mPhotoViewAttacher = new PhotoViewAttacher(mPhotoView);
        Picasso.with(getActivity()).load(mImgUrl).into(mPhotoView);
        Log.e("aaaaaaaaaaa", "initView: " + mImgUrl + "         ");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
