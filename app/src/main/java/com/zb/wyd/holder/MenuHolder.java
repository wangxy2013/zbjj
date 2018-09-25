package com.zb.wyd.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.MenuInfo;
import com.zb.wyd.listener.MyItemClickListener;


/**
 */
public class MenuHolder extends RecyclerView.ViewHolder
{
    private TextView mNameTv;
    private LinearLayout mItemLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private MyItemClickListener listener;

    public MenuHolder(View rootView, Context mContext, MyItemClickListener listener)
    {
        super(rootView);
        this.mContext = mContext;
        this.listener = listener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.tv_name);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
    }


    public void setMenuInfo(MenuInfo mMenuInfo, final int p)
    {
        mNameTv.setText(mMenuInfo.getName());
        if (mMenuInfo.isSelected())
        {
            mNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
        }
        else
        {
            mNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }







        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });
    }


}
