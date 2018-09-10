package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.AnchorHolder;
import com.zb.wyd.holder.CollectionAnchorHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CollectionAnchorAdapter extends RecyclerView.Adapter<CollectionAnchorHolder>
{

    private MyItemClickListener listener;
    private MyItemClickListener delListener;
    private List<LiveInfo> list;
    private Context mContext;

    public CollectionAnchorAdapter(List<LiveInfo> list, Context mContext, MyItemClickListener listener, MyItemClickListener delListener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
        this.delListener = delListener;
    }

    @Override
    public CollectionAnchorHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_anchor, parent, false);
        CollectionAnchorHolder mHolder = new CollectionAnchorHolder(itemView, parent.getContext(), listener, delListener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(CollectionAnchorHolder holder, int position)
    {
        LiveInfo mLiveInfo = list.get(position);
        holder.setLiveInfo(mLiveInfo, position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
