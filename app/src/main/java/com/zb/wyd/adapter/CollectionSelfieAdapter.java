package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.SelfieInfo;
import com.zb.wyd.holder.CollectionSelfieHolder;
import com.zb.wyd.holder.SelfieHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CollectionSelfieAdapter extends RecyclerView.Adapter<CollectionSelfieHolder>
{

    private MyItemClickListener listener;
    private MyItemClickListener delListener;
    private List<SelfieInfo>    list;
    private Context             mContext;

    public CollectionSelfieAdapter(List<SelfieInfo> list, Context mContext, MyItemClickListener listener, MyItemClickListener delListener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
        this.delListener=delListener;
    }

    @Override
    public CollectionSelfieHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_selfie, parent, false);
        CollectionSelfieHolder mHolder = new CollectionSelfieHolder(itemView, parent.getContext(), listener,delListener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(CollectionSelfieHolder holder, int position)
    {
        SelfieInfo mSelfieInfo = list.get(position);
        holder.setSelfieInfo(mSelfieInfo,position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
