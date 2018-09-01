package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.GiftInfo;
import com.zb.wyd.holder.GiftHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class GiftAdapter extends RecyclerView.Adapter<GiftHolder>
{

    private MyItemClickListener listener;
    private List<GiftInfo>      list;
    private Context             mContext;

    public GiftAdapter(Context mContext, List<GiftInfo> list, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public GiftHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift, parent, false);
        GiftHolder mHolder = new GiftHolder(itemView, parent.getContext(), listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(GiftHolder holder, int position)
    {
        GiftInfo mGiftInfo = list.get(position);
        holder.setGiftInfo(mGiftInfo, position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
