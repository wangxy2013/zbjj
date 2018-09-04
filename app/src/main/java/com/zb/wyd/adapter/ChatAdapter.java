package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.ChatInfo;
import com.zb.wyd.entity.PicInfo;
import com.zb.wyd.holder.PhotoAddHolder;
import com.zb.wyd.holder.PhotoBaseHolder;
import com.zb.wyd.holder.PhotoItemHolder;
import com.zb.wyd.holder.chat.ChatBaseHolder;
import com.zb.wyd.holder.chat.LogChatHolder;
import com.zb.wyd.holder.chat.OtherChatHolder;
import com.zb.wyd.holder.chat.SystemChatHolder;
import com.zb.wyd.holder.chat.UserChatHolder;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.StringUtils;

import java.util.List;

/**
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatBaseHolder>
{

    private MyItemClickListener listener;
    private List<ChatInfo> list;
    private Context mContext;

    public ChatAdapter(List<ChatInfo> list, MyItemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ChatBaseHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ChatBaseHolder mChatHolder = null;

        switch (viewType)
        {
            case 0:
                mChatHolder = new SystemChatHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_system, parent, false), parent.getContext(), listener);
                break;
            case 1:
                mChatHolder = new LogChatHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_log, parent, false), parent.getContext(), listener);
                break;

            case 3:
                mChatHolder = new UserChatHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false), parent.getContext(), listener);

                break;

            default:
                mChatHolder = new OtherChatHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_other, parent, false), parent.getContext(), listener);
                break;
        }
        return mChatHolder;
    }


    @Override
    public void onBindViewHolder(ChatBaseHolder holder, int position)
    {
        holder.setChatInfo(list.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    @Override
    public int getItemViewType(int position)
    {
        if ("sys".equals(list.get(position).getType()))
        {
            return 0;

        }
        else if ("log".equals(list.get(position).getType()))
        {

            return 1;
        }
        else if ("sysay".equals(list.get(position).getType()))
        {

            return 2;
        }
        else if ("say".equals(list.get(position).getType()))
        {

            return 3;
        }
        return 99;
    }
}
