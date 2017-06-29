package com.example.yls.newsclient.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.fragment.WebImageFragement;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowWebImageActivity extends BaseActivity {
    // private ImageView mImageView;
    private String[] imgUrl;
    private PhotoView mPhotoView;
    private PhotoViewAttacher mPhotoViewAttacher;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void initData() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {
        // mImageView = (ImageView) findViewById(R.id.iv_web);
        // mPhotoView = (PhotoView) findViewById(R.id.iv_web);
//        mPhotoViewAttacher = new PhotoViewAttacher(mPhotoView);

        initViewPager();


    }

    private void initViewPager() {
        imgUrl = getIntent().getStringArrayExtra("imgUrl");
        mViewPager = (ViewPager) findViewById(R.id.web_img_pager);

        // Picasso.with(this).load(imgUrl).into(mImageView);
        for (int i = 0; i < imgUrl.length; i++) {
            WebImageFragement webImageFragement = new WebImageFragement();
            webImageFragement.setImgUrl(imgUrl[i]);
            mFragments.add(webImageFragement);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return imgUrl.length;
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_web_image;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
