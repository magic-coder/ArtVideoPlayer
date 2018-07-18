package org.salient.videoplayerdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.salient.ControlPanel;
import org.salient.VideoView;
import org.salient.videoplayerdemo.R;
import org.salient.videoplayerdemo.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * > Created by Mai on 2018/7/17
 * *
 * > Description:
 * *
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VideoViewHolder> {

    private List<VideoBean> mList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setList(List<VideoBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_view, parent, false);
        final VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, videoViewHolder.getAdapterPosition());
                }
            }
        });
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean videoBean = mList.get(holder.getAdapterPosition());
        holder.videoView.setUp(videoBean.getUrl(),
                VideoView.WindowType.NORMAL,
                videoBean.getTitle());
        holder.videoView.setControlPanel(new ControlPanel(holder.videoView.getContext()));
        ControlPanel controlPanel = (ControlPanel) holder.videoView.getControlPanel();
        if (controlPanel != null) {
            ImageView coverView = controlPanel.getCoverView();
            Glide.with(coverView.getContext())
                    .load(videoBean.getImage())
                    .into(coverView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.mOnItemClickListener = onItemClickListener;
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }

}