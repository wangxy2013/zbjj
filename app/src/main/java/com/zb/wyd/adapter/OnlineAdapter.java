package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.holder.NewHolder;
import com.zb.wyd.holder.OnlinHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class OnlineAdapter extends RecyclerView.Adapter<OnlinHolder>
{

    private List<UserInfo> list;

    public OnlineAdapter(List<UserInfo> list)
    {
        this.list = list;
    }

    @Override
    public OnlinHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_online, parent, false);
        OnlinHolder mHolder = new OnlinHolder(itemView, parent.getContext());
        return mHolder;
    }


    @Override
    public void onBindViewHolder(OnlinHolder holder, int position)
    {
        UserInfo mUserInfo = list.get(position);
        holder.setUserInfo(mUserInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
