package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.holder.CollectionVideoHolder;
import com.zb.wyd.holder.IntegerAreaHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CollectionVideoAdapter extends RecyclerView.Adapter<CollectionVideoHolder>
{

    private MyItemClickListener listener;
    private MyItemClickListener delListener;
    private List<VideoInfo>     list;
    private Context             mContext;

    public CollectionVideoAdapter(List<VideoInfo> list, Context mContext, MyItemClickListener listener,MyItemClickListener delListener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
        this.delListener =delListener;
    }

    @Override
    public CollectionVideoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_video, parent, false);
        CollectionVideoHolder mHolder = new CollectionVideoHolder(itemView, parent.getContext(),listener,delListener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(CollectionVideoHolder holder, int position)
    {
        VideoInfo mVideoInfo = list.get(position);
        holder.setVideoInfo(mVideoInfo,position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
