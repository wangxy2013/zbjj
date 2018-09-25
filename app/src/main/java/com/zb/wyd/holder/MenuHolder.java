package com.zb.wyd.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MenuChildAdapter;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.MenuInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;
import com.zb.wyd.widget.FullyLinearLayoutManager;


/**
 */
public class MenuHolder extends RecyclerView.ViewHolder
{
    private TextView mNameTv;
    private LinearLayout mItemLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private MyItemClickListener                listener;
    private MyOnClickListener.OnClickCallBackListener childListener;
    public MenuHolder(View rootView, Context mContext, MyItemClickListener listener,MyOnClickListener.OnClickCallBackListener childListener)
    {
        super(rootView);
        this.mContext = mContext;
        this.listener = listener;
        this.childListener = childListener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_child);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
    }


    public void setMenuInfo(MenuInfo mMenuInfo, final int p)
    {
        mNameTv.setText(mMenuInfo.getName());

        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));

        if(null !=mMenuInfo.getChildMenuList())
        {
            mRecyclerView.setAdapter(new MenuChildAdapter(p,mMenuInfo.getChildMenuList(), mContext, childListener));

        }


        if (mMenuInfo.isSelected())
        {
            mNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
          if(null != mMenuInfo.getChildMenuList() &&!mMenuInfo.getChildMenuList().isEmpty())
          {
              mRecyclerView.setVisibility(View.VISIBLE);
          }
        }
        else
        {
            mNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mRecyclerView.setVisibility(View.GONE);
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
