package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.HotHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class HotAdapter extends RecyclerView.Adapter<HotHolder>
{

    private MyItemClickListener listener;
    private List<LiveInfo>      list;
    private Context             mContext;

    public HotAdapter(List<LiveInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public HotHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot, parent, false);
        HotHolder mHolder = new HotHolder(itemView, parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(HotHolder holder, int position)
    {
        LiveInfo mLiveInfo = list.get(position);
        holder.setLiveInfo(mLiveInfo,position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
