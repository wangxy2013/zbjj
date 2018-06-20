package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.RecommendHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 * 描述：推荐
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendHolder>
{

    private MyItemClickListener listener;
    private List<LiveInfo>      list;
    private Context             mContext;

    public RecommendAdapter(List<LiveInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public RecommendHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        RecommendHolder mHolder = new RecommendHolder(itemView, parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(RecommendHolder holder, int position)
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
