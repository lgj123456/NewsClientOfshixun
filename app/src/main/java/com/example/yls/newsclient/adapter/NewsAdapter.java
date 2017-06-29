package com.example.yls.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.bean.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yls on 2017/6/28.
 */

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsEntity.ResultBean> listDatas = new ArrayList<>();
    private static final int ITEM_TYPE_WITH_1_IMAGE = 0;
    private static final int ITEM_TYPE_WITH_3_IMAGE = 1;

    public NewsAdapter(Context context, List<NewsEntity.ResultBean> listDatas) {
        this.mContext = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return (listDatas == null) ? 0 : listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsEntity.ResultBean info = (NewsEntity.ResultBean) getItem(position);
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TYPE_WITH_1_IMAGE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news_1,parent,false);
            }
            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

            tvTitle.setText(info.getTitle());
            tvSource.setText(info.getSource());
            tvComment.setText(info.getReplyCount() + "跟帖");
            Picasso.with(mContext).load(info.getImgsrc()).into(ivIcon);

        } else if (itemViewType == ITEM_TYPE_WITH_3_IMAGE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news_2,parent,false);
            }
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
            ImageView iv01 = (ImageView) convertView.findViewById(R.id.iv_01);
            ImageView iv02 = (ImageView) convertView.findViewById(R.id.iv_02);
            ImageView iv03 = (ImageView) convertView.findViewById(R.id.iv_03);
            tvTitle.setText(info.getTitle());
            tvComment.setText(info.getReplyCount() + "跟帖");
            try {
                Picasso.with(mContext).load(info.getImgsrc()).into(iv01);
                Picasso.with(mContext).load(info.getImgextra().get(0).getImgsrc())
                        .into(iv02);
                Picasso.with(mContext).load(info.getImgextra().get(1).getImgsrc())
                        .into(iv03);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        NewsEntity.ResultBean item = (NewsEntity.ResultBean) getItem(position);
        if (item.getImgextra() == null || item.getImgextra().size() == 0) {
            return ITEM_TYPE_WITH_1_IMAGE;
        } else {
            return ITEM_TYPE_WITH_3_IMAGE;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setDatas(NewsEntity newsData) {
        this.listDatas = newsData.getResult();
        notifyDataSetChanged();
    }

    public void appendDatas(NewsEntity newsData){
        this.listDatas.addAll(newsData.getResult());
        notifyDataSetChanged();
    }
}
