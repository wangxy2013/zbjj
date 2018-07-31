package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.holder.DyHolder;
import com.zb.wyd.holder.VideoHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class DyVideoAdapter extends RecyclerView.Adapter<DyHolder>
{

    private MyItemClickListener listener;
    private List<VideoInfo>     list;
    private Context             mContext;


    public DyVideoAdapter(List<VideoInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public DyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dy_layout, parent, false);
        DyHolder mHolder = new DyHolder(itemView, parent.getContext(), listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(DyHolder holder, int position)
    {
        VideoInfo mVideoInfo = list.get(position);
        holder.setVideoInfo(mVideoInfo, position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
