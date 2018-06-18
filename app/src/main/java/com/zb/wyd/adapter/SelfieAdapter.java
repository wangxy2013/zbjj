package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.SelfieInfo;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.holder.IntegerAreaHolder;
import com.zb.wyd.holder.SelfieHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class SelfieAdapter extends RecyclerView.Adapter<SelfieHolder>
{

    private MyItemClickListener listener;
    private List<SelfieInfo>    list;
    private Context             mContext;

    public SelfieAdapter(List<SelfieInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public SelfieHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selfie, parent, false);
        SelfieHolder mHolder = new SelfieHolder(itemView, parent.getContext(), listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(SelfieHolder holder, int position)
    {
        SelfieInfo mSelfieInfo = list.get(position);
        holder.setSelfieInfo(mSelfieInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
