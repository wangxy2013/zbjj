package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.MessageInfo;
import com.zb.wyd.entity.WealthInfo;
import com.zb.wyd.holder.MessageHolder;
import com.zb.wyd.holder.WealthHolder;

import java.util.List;

/**
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>
{

    private List<MessageInfo> list;
    private Context           mContext;

    public MessageAdapter(List<MessageInfo> list)
    {
        this.list = list;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        MessageHolder mHolder = new MessageHolder(itemView);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(MessageHolder holder, int position)
    {
        holder.setMessageInfo(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}
