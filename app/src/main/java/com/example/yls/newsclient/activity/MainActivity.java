package com.example.yls.newsclient.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.fragment.MainFragment1;
import com.example.yls.newsclient.fragment.MainFragment2;
import com.example.yls.newsclient.fragment.MainFragment3;
import com.example.yls.newsclient.fragment.MainFragment4;
import com.example.yls.newsclient.fragment.MainFragment5;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
//1.点击单选时，viewpager显示对应的子界面
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_01:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_02:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_03:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_04:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_05:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        //2.当viewpager子界面发生改变时，选中并高亮对应的单选按钮
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(0);
                        mRadioGroup.check(R.id.rb_01);
                        break;
                    case 1:
                        mViewPager.setCurrentItem(1);
                        mRadioGroup.check(R.id.rb_02);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(2);
                        mRadioGroup.check(R.id.rb_03);
                        break;
                    case 3:
                        mViewPager.setCurrentItem(3);
                        mRadioGroup.check(R.id.rb_04);
                        break;
                    case 4:
                        mViewPager.setCurrentItem(4);
                        mRadioGroup.check(R.id.rb_05);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_friends:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_location:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_mail:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_task:
                        showToast(item.getTitle() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_01);
        initViewPager();
        initNavigationView();
        initToolBar();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("广交院实训");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initNavigationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment1());
        fragments.add(new MainFragment2());
        fragments.add(new MainFragment3());
        fragments.add(new MainFragment4());
        fragments.add(new MainFragment5());
//        设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.e("aaaaaaa", "getItem: " + position);
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_01:
                showToast("item_01");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
