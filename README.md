# NewsClientOfshixun

## 新闻列表显示（数据获取解析及显示）

### 1. 从服务器获取json数据

1. 请求服务器数据需要用到网络权限，还在读写sdcard，在清单文件AndroidManifest.xml中配置声明应用用到的权限：

	    <!--声明请求网络的权限-->
	    <uses-permission android:name="android.permission.INTERNET"/>
	    <!--读写取sdcard的权限-->
	    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

2. 添加xutils第三方网格请求库（如下）。另外，xUtils会用到httpclient包，但Android6.0 google把该httpclient从sdk中删除了，所以需要自己再添加进来。把老师提供的org.apache.http.legacy.jar添加到app/libs目录下，并右键jar包，选择add as library, 完成jar包添加。

		// 添加到app/build.gradle文件的dependency中
		compile 'com.jiechic.library:xUtils:2.6.14'

	如下图：
	<img src="img/09.png" width="730"/>

3. 了解请求服务器新闻数据的url地址, 定义url管理类

		/**
		 * url管理类
		 *
		 * @author WJQ
		 */
		public class URLManager {

		    // 请求的url格式：
		    // http://c.m.163.com/nc/article/headline/新闻类别id/偏移量-每页条数.html

		    // 请求的url示例：
		    // 头条： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
		    // 头条： 第2页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html
		    // 头条： 第3页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348647909107/40-20.html

		    // 社会： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348648037603/0-20.html
		    // 科技： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348649580692/0-20.html
		    // 财经： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348648756099/0-20.html
		    // 体育： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348649079062/0-20.html
		    // 汽车： 第1页，一页有20条
		    // http://c.m.163.com/nc/article/headline/T1348654060988/0-20.html

		    // 支持的一些新闻类别类别id如下：
		    public final String[] channelId = new String[] {
		            "T1348647909107",   // 头条
		            "T1348648037603",   // 社会
		            "T1348649580692",   // 科技
		            "T1348648756099",   // 财经
		            "T1348649079062",   // 体育
		            "T1348654060988",   // 汽车
		    };

		    /**
		     * 获取一页新闻数据
		     * @param channelId 新闻类别id
		     * @return
		     */
		    public static String getUrl(String channelId) {
		        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
		        return "http://c.m.163.com/nc/article/headline/" + channelId + "/0-20.html";
		    }

		    // 视频url路径
		    public static final String VideoURL = //
		            "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/0-20.html";
		}

4. 通过xutils请求服务器数据

		// NewsFragment.java
	    @Override
	    public void initData() {
	        getDataFromServer();
	    }

	    // 请求服务器获取页签详细数据
	    private void getDataFromServer() {
	        String url = URLManager.getUrl(channelId);

	        HttpUtils utils = new HttpUtils();
	        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
	            @Override
	            public void onSuccess(ResponseInfo<String> responseInfo) {
	                String json = responseInfo.result;
	                System.out.println("----服务器返回的json数据:" + json);
	            }

	            @Override
	            public void onFailure(HttpException error, String msg) {
	                error.printStackTrace();
	            }
	        });
	    }

### 2. 解析json数据

1. 通过AndroidStudio的gsonformat插件，根据json字符串，创建实体对象.

		/**
		 * 新闻数据实体
		 *
		 * @author WJQ
		 */
		public class NewsEntity {

		    private List<ResultBean> result;

		    public List<ResultBean> getResult() {
		        return result;
		    }

		    public void setResult(List<ResultBean> T1348647909107) {
		        this.result = T1348647909107;
		    }

		    ... (略)
		}

2. 解析json成javabean对象。此处会使用第三方库Gson来解析json字符，导入gson的依赖，添加到app/build.gradle中，如下：

		compile 'com.google.code.gson:gson:2.2.4'

	当获取到服务器返回来的json数据后，可以进行解析了，代码如下：

		// NewsFragment.java
	    // 请求服务器获取页签详细数据
	    private void getDataFromServer() {
	        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
	        String url = URLManager.getUrl(channelId);

	        HttpUtils utils = new HttpUtils();
	        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
	            @Override
	            public void onSuccess(ResponseInfo<String> responseInfo) {
	                String json = responseInfo.result;
	                System.out.println("----服务器返回的json数据:" + json);

	                json =  json.replace(channelId, "result");
	                Gson gson = new Gson();
	                NewsEntity newsDatas = gson.fromJson(json, NewsEntity.class);
	                System.out.println("----解析json:" + newsDatas.getResult().size());

	                // 显示数据到列表中
	                // showDatas(newsDatas);
	            }

	            @Override
	            public void onFailure(HttpException error, String msg) {
	                error.printStackTrace();
	            }
	        });
	    }


### 3. 显示列表

1. 定义列表项布局

		// item_news_1.xml
		<?xml version="1.0" encoding="utf-8"?>
		<RelativeLayout
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:padding="10dp">

		    <ImageView
		        android:id="@+id/iv_icon"
		        android:layout_width="90dp"
		        android:layout_height="70dp"
		        android:layout_marginRight="5dp"
		        android:padding=".5dp"
		        android:scaleType="centerCrop"
		        android:src="#11000000"/>

		    <TextView
		        android:id="@+id/tv_title"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignParentTop="true"
		        android:layout_toRightOf="@+id/iv_icon"
		        android:maxLines="2"
		        android:text="标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题"
		        android:textColor="@color/item_text_01"
		        android:textSize="16sp"/>

		    <TextView
		        android:id="@+id/tv_source"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignBottom="@+id/iv_icon"
		        android:layout_alignLeft="@+id/tv_title"
		        android:text="来源"
		        android:textColor="@color/item_text_02"
		        android:textSize="12sp"/>

		    <TextView
		        android:id="@+id/tv_comment"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignBottom="@+id/iv_icon"
		        android:layout_alignParentRight="true"
		        android:layout_marginRight="5dp"
		        android:text="120跟帖"
		        android:textColor="@color/item_text_02"
		        android:textSize="12sp"/>

		</RelativeLayout>


2. 定义列表适配器&列表项布局实现

		/**
		 * 新闻列表适配器
		 *
		 * @author WJQ
		 */
		public class NewsAdapter extends BaseAdapter {

		    private Context context;

		    /** 列表显示的新闻数据 */
		    private List<NewsEntity.ResultBean> listDatas;


		    public NewsAdapter(Context context, List<NewsEntity.ResultBean> listDatas) {
		        this.context = context;
		        this.listDatas = listDatas;
		    }

		    @Override
		    public int getCount() {
		        return (listDatas == null) ? 0 : listDatas.size();
		    }

		    @Override
		    public NewsEntity.ResultBean getItem(int position) {
		        return listDatas.get(position);
		    }

		    @Override
		    public long getItemId(int position) {
		        return position;
		    }

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		        // 列表项数据
		        NewsEntity.ResultBean info = (NewsEntity.ResultBean) getItem(position);

		        if (convertView == null) {
		                convertView = View.inflate(context, R.layout.item_news_1, null);
	            }

	            // 查找列表item中的子控件
	            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
	            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
	            TextView tvSource = (TextView) convertView.findViewById(R.id.tv_source);
	            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

	            // 显示列表item中的子控件
	            tvTitle.setText(info.getTitle());
	            tvSource.setText(info.getSource());
	            tvComment.setText(info.getReplyCount() + "跟帖");
	            Picasso.with(context).load(info.getImgsrc()).into(ivIcon);

		        return convertView;
		    }
		}

3. 显示列表

		// NewsFragments.java
	    // 请求服务器获取页签详细数据
	    private void getDataFromServer() {
	        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
	        String url = URLManager.getUrl(channelId);

	        HttpUtils utils = new HttpUtils();
	        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
	            @Override
	            public void onSuccess(ResponseInfo<String> responseInfo) {
	                String json = responseInfo.result;
	                System.out.println("----服务器返回的json数据:" + json);

	                json =  json.replace(channelId, "result");
	                Gson gson = new Gson();
	                NewsEntity newsDatas = gson.fromJson(json, NewsEntity.class);
	                System.out.println("----解析json:" + newsDatas.getResult().size());

	                // 显示数据到列表中
	                showDatas(newsDatas);
	            }

	            @Override
	            public void onFailure(HttpException error, String msg) {
	                error.printStackTrace();
	            }
	        });
	    }


	    // 显示服务器数据
	    private void showDatas(NewsEntity newsDatas) {
	        if (newsDatas == null
	                || newsDatas.getResult() == null
	                || newsDatas.getResult().size() == 0) {
	            System.out.println("----没有获取到服务器的新闻数据");
	            return;
	        }

	        // （1）显示轮播图
	        ...(后面实现)

	        // （2）显示新闻列表
	        NewsAdapter newsAdapter = new NewsAdapter(
	                mActivity, newsDatas.getResult());
	        listView.setAdapter(newsAdapter);
	    }


## 显示多种类型的item

1. 定义两种类型的item布局，列表项布局有两种，一种列表项只有1张图片，第2种类型的列表项有3张图片

		// item_news_1.xml
		<?xml version="1.0" encoding="utf-8"?>
		<RelativeLayout
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:padding="10dp">

		    <ImageView
		        android:id="@+id/iv_icon"
		        android:layout_width="90dp"
		        android:layout_height="70dp"
		        android:layout_marginRight="5dp"
		        android:padding=".5dp"
		        android:scaleType="centerCrop"
		        android:src="#11000000"/>

		    <TextView
		        android:id="@+id/tv_title"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignParentTop="true"
		        android:layout_toRightOf="@+id/iv_icon"
		        android:maxLines="2"
		        android:text="标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题"
		        android:textColor="@color/item_text_01"
		        android:textSize="16sp"/>

		    <TextView
		        android:id="@+id/tv_source"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignBottom="@+id/iv_icon"
		        android:layout_alignLeft="@+id/tv_title"
		        android:text="来源"
		        android:textColor="@color/item_text_02"
		        android:textSize="12sp"/>

		    <TextView
		        android:id="@+id/tv_comment"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignBottom="@+id/iv_icon"
		        android:layout_alignParentRight="true"
		        android:layout_marginRight="5dp"
		        android:text="120跟帖"
		        android:textColor="@color/item_text_02"
		        android:textSize="12sp"/>

		</RelativeLayout>

		// item_news_2.xml
		<?xml version="1.0" encoding="utf-8"?>
		<!--显示三张图片的列表项item-->
		<LinearLayout
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:orientation="vertical"
		    android:padding="10dp">

		    <LinearLayout
		        android:layout_width="match_parent"
		        android:layout_height="wrap_content"
		        android:orientation="horizontal">

		        <TextView
		            android:id="@+id/tv_title"
		            android:layout_width="0dp"
		            android:layout_height="wrap_content"
		            android:layout_weight="1"
		            android:ellipsize="end"
		            android:maxLines="1"
		            android:text="标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题"
		            android:textColor="#000"
		            android:textSize="16sp"/>

		        <TextView
		            android:id="@+id/tv_comment"
		            android:layout_width="wrap_content"
		            android:layout_height="wrap_content"
		            android:layout_marginLeft="10dp"
		            android:text="120跟帖"
		            android:textColor="#a000"
		            android:textSize="12sp"/>

		    </LinearLayout>


		    <LinearLayout
		        android:layout_width="match_parent"
		        android:layout_height="wrap_content"
		        android:layout_marginTop="5dp"
		        android:orientation="horizontal">

		        <ImageView
		            android:id="@+id/iv_01"
		            android:layout_width="0dp"
		            android:layout_height="70dp"
		            android:layout_weight="1"
		            android:scaleType="centerCrop"
		            android:src="#11000000"/>

		        <ImageView
		            android:id="@+id/iv_02"
		            android:layout_width="0dp"
		            android:layout_height="70dp"
		            android:layout_marginLeft="10dp"
		            android:layout_weight="1"
		            android:scaleType="centerCrop"
		            android:src="#11000000"/>

		        <ImageView
		            android:id="@+id/iv_03"
		            android:layout_width="0dp"
		            android:layout_height="70dp"
		            android:layout_marginLeft="10dp"
		            android:layout_weight="1"
		            android:scaleType="centerCrop"
		            android:src="#11000000"/>

		    </LinearLayout>

		</LinearLayout>


## 重写BaseAdapter的两个方法，getItemViewType和getViewTypeCount，参考以下代码：

	/**
	 * 新闻列表适配器
	 *
	 * @author WJQ
	 */
	public class NewsAdapter extends BaseAdapter {
	    ...

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // 列表项数据
	        NewsEntity.ResultBean info = (NewsEntity.ResultBean) getItem(position);

	        int itemViewType = getItemViewType(position);
	        if (itemViewType == ITEM_TYPE_WITH_1_IMAGE) {   // 第一种类型的列表项

	            if (convertView == null) {
	                convertView = View.inflate(context, R.layout.item_news_1, null);
	            }

	            // 查找列表item中的子控件
	            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
	            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
	            TextView tvSource = (TextView) convertView.findViewById(R.id.tv_source);
	            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

	            // 显示列表item中的子控件
	            tvTitle.setText(info.getTitle());
	            tvSource.setText(info.getSource());
	            tvComment.setText(info.getReplyCount() + "跟帖");
	            Picasso.with(context).load(info.getImgsrc()).into(ivIcon);

	        } else if (itemViewType == ITEM_TYPE_WITH_3_IMAGE) {    // 第二种类型的列表项

	            if (convertView == null) {
	                convertView = View.inflate(context, R.layout.item_news_2, null);
	            }

	            // 查找列表item中的子控件
	            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
	            TextView  tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
	            ImageView iv01 = (ImageView) convertView.findViewById(R.id.iv_01);
	            ImageView iv02 = (ImageView) convertView.findViewById(R.id.iv_02);
	            ImageView iv03 = (ImageView) convertView.findViewById(R.id.iv_03);

	            // 显示列表item中的子控件
	            tvTitle.setText(info.getTitle());
	            tvComment.setText(info.getReplyCount() + "跟帖");
	            try {
	                Picasso.with(context).load(info.getImgsrc()).into(iv01);
	                Picasso.with(context).load(info.getImgextra().get(0).getImgsrc())
	                        .into(iv02);
	                Picasso.with(context).load(info.getImgextra().get(1).getImgsrc())
	                        .into(iv03);
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return convertView;
	    }

	    //================多种item的列表显示(begin)=======================
	    private static final int ITEM_TYPE_WITH_1_IMAGE = 0;
	    private static final int ITEM_TYPE_WITH_3_IMAGE = 1;

	    @Override
	    public int getItemViewType(int position) {
	        NewsEntity.ResultBean item = getItem(position);
	        if (item.getImgextra() == null || item.getImgextra().size() == 0) {
	            // 只有一张图片的item
	            return ITEM_TYPE_WITH_1_IMAGE;
	        } else {
	            // item中有三张图片
	            return ITEM_TYPE_WITH_3_IMAGE;
	        }
	    }

	    @Override
	    public int getViewTypeCount() {
	        return 2;
	    }
	    //================多种item的列表显示(end)=========================
	}

## 轮播图显示及点击
1. 我们会使用第三方库SliderLayout实现轮播图显示功能, 需要在app/build.gradle的dependencies中，先添加SliderLayout第三方库。

		// 轮播图实现
	    compile 'com.daimajia.slider:library:1.1.5@aar'
	    compile 'com.squareup.picasso:picasso:2.3.2'
	    compile 'com.nineoldandroids:library:2.4.0'

	如下图所示：
 	<img src="img/08.png" width="730"/>


2. 定义列表项头布局文件，轮播图会添加到ListView的头部，随着列表可以上下滑动。

		// layout/list_header.xml
		<?xml version="1.0" encoding="utf-8"?>
		<LinearLayout
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:orientation="vertical">

		    <com.daimajia.slider.library.SliderLayout
		        android:id="@+id/slider_layout"
		        android:layout_width="match_parent"
		        android:layout_height="150dp"/>

		</LinearLayout>

3. 通过ListView的addHeaderView方法，添加列表头部布局，显示轮播图

		// NewsFragment.java
		// 显示服务器数据
	    private void showDatas(NewsEntity newsDatas) {
	        if (newsDatas == null
	                || newsDatas.getResult() == null
	                || newsDatas.getResult().size() == 0) {
	            System.out.println("----没有获取到服务器的新闻数据");
	            return;
	        }

	        // （1）显示轮播图
	        List<NewsEntity.ResultBean.AdsBean> ads
	                = newsDatas.getResult().get(0).getAds();

	        // 有轮播图广告
	        if (ads != null && ads.size() > 0) {
	            View headerView = View.inflate(mActivity, R.layout.list_header, null);
	            SliderLayout sliderLayout = (SliderLayout)
	                    headerView.findViewById(R.id.slider_layout);

	            for (int i = 0; i < ads.size(); i++) {
	                // 一则广告数据
	                NewsEntity.ResultBean.AdsBean adBean = ads.get(i);

	                TextSliderView sliderView = new TextSliderView(mActivity);
	                sliderView.description(adBean.getTitle()).image(adBean.getImgsrc());
	                // 添加子界面
	                sliderLayout.addSlider(sliderView);
	                // 设置点击事件
	                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
	                    @Override
	                    public void onSliderClick(BaseSliderView slider) {
	                        showToast(slider.getDescription());
	                    }
	                });
	            }
				// 添加列表头部布局
	            listView.addHeaderView(headerView);
	        }

	        // （2）显示新闻列表
	        NewsAdapter newsAdapter = new NewsAdapter(
	                mActivity, newsDatas.getResult());
	        listView.setAdapter(newsAdapter);
	    }

## 新闻详情显示(WebView显示网页)

1. 创键新闻详情界面NewsDetailActivity和布局文件, 当点击列表项时，跳转进入，并通过意图传递参数：


		/**
		 * 新闻详情界面，使用一个WebView进行展示
		 *
		 * @author WJQ
		 */
		public class NewsDetailActivity extends BaseActivity {

		    private WebView webView;
		    private ProgressBar progressBar;

		    @Override
		    public int getLayoutRes() {
		        return R.layout.activity_news_detail;
		    }

		    @Override
		    public void initView() {
		        progressBar = (ProgressBar) findViewById(R.id.pb_01);

		        initWebView();
		    }

		    private void initWebView() {
		        webView = (WebView) findViewById(R.id.web_view);

		        // 当点击WebView显示的网页的链接时，禁止使用其它浏览器打开
		        webView.setWebViewClient(new WebViewClient());

		        //设置webview支持javascript脚本
		        webView.getSettings().setJavaScriptEnabled(true);

		        // 当WebView加载网页时，显示加载网页的进度
		        webView.setWebChromeClient(new WebChromeClient() {
		            @Override
		            public void onProgressChanged(WebView view, int newProgress) {
		                if (newProgress == 100) {       // 加载完成，隐藏进度条
		                    progressBar.setVisibility(View.GONE);
		                } else {                        // 显示加载进度
		                    progressBar.setVisibility(View.VISIBLE);
		                    progressBar.setProgress(newProgress);
		                    System.out.println("---------percent: " + newProgress);
		                }
		            }
		        });
		    }

		    @Override
		    public void initListener() {
		    }

		    @Override
		    public void initData() {
		        NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean)
		                getIntent().getSerializableExtra("news");
		        webView.loadUrl(newsBean.getUrl());

		        // 显示标题栏左上角的返回图标
		        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		        // 显示标题栏
		        getSupportActionBar().setTitle(newsBean.getTitle());
		    }

		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        if (item.getItemId() == android.R.id.home) {
		            finish();   // 标题栏左上角的返回按钮，退出当前界面
		            return true;
		        }
		        return super.onOptionsItemSelected(item);
		    }
		}

		// activity_news_detail.xml
		<?xml version="1.0" encoding="utf-8"?>
		<LinearLayout
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:background="@color/white"
		    android:orientation="vertical">

		    <ProgressBar
		        android:id="@+id/pb_01"
		        style="@style/Widget.AppCompat.ProgressBar.Horizontal"
		        android:layout_width="match_parent"
		        android:layout_height="3dp"/>

		    <WebView
		        android:id="@+id/web_view"
		        android:background="@color/white"
		        android:layout_width="match_parent"
		        android:layout_height="match_parent"/>

		</LinearLayout>


		// value/styles.xml
		<resources>
		    <!--有标题栏的主题-->
		    <style name="NewDetailActivity" parent="AppTheme">
		        <item name="windowNoTitle">false</item>
		    </style>
		</resources>


		// AndroidManifest.xml
		<!--使用有标题栏的主题-->
		<activity android:name=".ui.activity.NewsDetailActivity"
		          android:theme="@style/NewDetailActivity"/>

2. 点击进入新闻详情界面

		// NewsFragment.java
	    @Override
	    public void initListener() {
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                // 用户点击的新闻
	                NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean)
	                        parent.getItemAtPosition(position);

	                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
	                intent.putExtra("news", newsBean);
	                startActivity(intent);
	            }
	        });
	    }

3. 注意：NewsEntity通过Intent传递时，需要实现序列化接口

		public static class ResultBean implements Serializable {
			// ResultBean所有的内部类也要实现Serializable序列化接口
		｝


