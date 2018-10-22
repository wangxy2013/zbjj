package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.AnchorHolder;
import com.zb.wyd.holder.CataHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CataAdapter extends RecyclerView.Adapter<CataHolder>
{

    private MyItemClickListener listener;
    private List<CataInfo>      list;
    private Context             mContext;

    public CataAdapter(List<CataInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public CataHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cata, parent, false);
        CataHolder mHolder = new CataHolder(itemView,listener);
        return mHolder;
}


    @Override
    public void onBindViewHolder(CataHolder holder, int position)
    {
        CataInfo mCataInfo = list.get(position);
        holder.setCataInfo(mCataInfo,position,mContext);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
