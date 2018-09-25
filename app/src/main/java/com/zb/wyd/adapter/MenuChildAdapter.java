package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.MenuInfo;
import com.zb.wyd.holder.MenuChildHolder;
import com.zb.wyd.holder.MenuHolder;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;

import java.util.List;

/**
 */
public class MenuChildAdapter extends RecyclerView.Adapter<MenuChildHolder>
{

    private MyOnClickListener.OnClickCallBackListener listener;
    private List<CataInfo>                     list;
    private Context                            mContext;
    private int  p;

    public MenuChildAdapter(int p,List<CataInfo> list, Context mContext, MyOnClickListener.OnClickCallBackListener listener)
    {
        this.p=p;
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MenuChildHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_child, parent, false);
        MenuChildHolder mHolder = new MenuChildHolder(itemView,mContext,listener);
        return mHolder;
}


    @Override
    public void onBindViewHolder(MenuChildHolder holder, int position)
    {

        holder.setCataInfo( list.get(position),p,position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
