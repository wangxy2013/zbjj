package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.MenuInfo;
import com.zb.wyd.holder.CataHolder;
import com.zb.wyd.holder.MenuHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuHolder>
{

    private MyItemClickListener listener;
    private List<MenuInfo>      list;
    private Context             mContext;

    public MenuAdapter(List<MenuInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        MenuHolder mHolder = new MenuHolder(itemView,mContext,listener);
        return mHolder;
}


    @Override
    public void onBindViewHolder(MenuHolder holder, int position)
    {

        holder.setMenuInfo( list.get(position),position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
