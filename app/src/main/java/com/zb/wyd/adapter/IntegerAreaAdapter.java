package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.holder.IntegerAreaHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class IntegerAreaAdapter extends RecyclerView.Adapter<IntegerAreaHolder>
{

    private MyItemClickListener listener;
    private List<VideoInfo>     list;
    private Context             mContext;

    public IntegerAreaAdapter(List<VideoInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public IntegerAreaHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integral_area, parent, false);
        IntegerAreaHolder mHolder = new IntegerAreaHolder(itemView, parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(IntegerAreaHolder holder, int position)
    {
        VideoInfo mVideoInfo = list.get(position);
        holder.setVideoInfo(mVideoInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
