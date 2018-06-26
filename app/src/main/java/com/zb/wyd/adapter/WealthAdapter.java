package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.PicInfo;
import com.zb.wyd.entity.WealthInfo;
import com.zb.wyd.holder.AnchorHolder;
import com.zb.wyd.holder.PhotoAddHolder;
import com.zb.wyd.holder.PhotoBaseHolder;
import com.zb.wyd.holder.PhotoItemHolder;
import com.zb.wyd.holder.WealthHolder;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.StringUtils;

import java.util.List;

/**
 */
public class WealthAdapter extends RecyclerView.Adapter<WealthHolder>
{

    private List<WealthInfo>    list;
    private Context             mContext;

    public WealthAdapter(List<WealthInfo> list)
    {
        this.list = list;
    }

    @Override
    public WealthHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wealth, parent, false);
        WealthHolder mHolder = new WealthHolder(itemView);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(WealthHolder holder, int position)
    {
        holder.setWealthInfo(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
