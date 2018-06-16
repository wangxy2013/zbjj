package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.holder.AnchorHolder;
import com.zb.wyd.holder.NewHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class AnchorAdapter extends RecyclerView.Adapter<AnchorHolder>
{

    private MyItemClickListener listener;
    private List<UserInfo>      list;
    private Context             mContext;

    public AnchorAdapter(List<UserInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public AnchorHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anchor, parent, false);
        AnchorHolder mHolder = new AnchorHolder(itemView, parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(AnchorHolder holder, int position)
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
