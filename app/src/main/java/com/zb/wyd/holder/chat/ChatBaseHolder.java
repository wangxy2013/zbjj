package com.zb.wyd.holder.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zb.wyd.entity.ChatInfo;
import com.zb.wyd.listener.MyItemClickListener;


public abstract class ChatBaseHolder extends RecyclerView.ViewHolder
{
    public ChatBaseHolder(View itemView)
    {
        super(itemView);
    }

    public abstract void setChatInfo(ChatInfo mChatInfo,int p);
}
