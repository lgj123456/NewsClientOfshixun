package com.example.yls.newsclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yls.newsclient.R;
import com.example.yls.newsclient.bean.VideoEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by yls on 2017/6/29.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context mContext;
    private List<VideoEntity.ResultBean> listDatas;

    public VideoAdapter(Context context, List<VideoEntity.ResultBean> listDatas) {
        this.mContext = context;
        this.listDatas = listDatas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(item);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VideoEntity.ResultBean video = listDatas.get(position);
        //Picasso.with(mContext).load(video.getCover()).into(holder.ivVideoImage);
        Picasso.with(mContext).load(video.getCover()).into(holder.jcVideoPlayerStandard.thumbImageView);
        holder.tvVideoTitle.setText(listDatas.get(position).getTitle());
        String durationStr = DateFormat.format("mm:ss", video.getLength() * 1000).toString();
        holder.tvVideoDuration.setText(durationStr);
        holder.tvPlayCount.setText(String.valueOf(video.getPlayCount()));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, VideoPlayActivity.class);
//                intent.putExtra("video_url", video.getMp4_url());
//                mContext.startActivity(intent);
        //  Log.e("aaaaaaaaaaaaa", "onClick: " + video.getMp4_url() );
        holder.jcVideoPlayerStandard.setUp(video.getMp4_url()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivVideoImage;
        private TextView tvVideoTitle;
        private TextView tvVideoDuration;
        private TextView tvPlayCount;
        private JCVideoPlayerStandard jcVideoPlayerStandard;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivVideoImage = (ImageView) itemView.findViewById(R.id.iv_video_image);
            tvVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            tvVideoDuration = (TextView) itemView.findViewById(R.id.tv_video_duration);
            tvPlayCount = (TextView) itemView.findViewById(R.id.tv_play_count);
            jcVideoPlayerStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }
}
