package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.NewHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class NewAdapter extends RecyclerView.Adapter<NewHolder>
{

    private MyItemClickListener listener;
    private List<LiveInfo>      list;
    private Context             mContext;

    public NewAdapter(List<LiveInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public NewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new, parent, false);
        NewHolder mHolder = new NewHolder(itemView, parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(NewHolder holder, int position)
    {
        LiveInfo mUserInfo = list.get(position);
        holder.setUserInfo(mUserInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
