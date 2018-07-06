package com.zb.wyd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.RankingInfo;
import com.zb.wyd.holder.RankingHolder;

import java.util.List;

/**
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingHolder>
{

    private List<RankingInfo> list;

    public RankingAdapter(List<RankingInfo> list)
    {
        this.list = list;
    }

    @Override
    public RankingHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        RankingHolder mHolder = new RankingHolder(itemView);
        return mHolder;
}


    @Override
    public void onBindViewHolder(RankingHolder holder, int position)
    {
        RankingInfo mRankingInfo = list.get(position);
        holder.setRankingInfo(mRankingInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
