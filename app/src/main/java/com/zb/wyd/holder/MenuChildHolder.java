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
import com.zb.wyd.listener.MyOnClickListener;


/**
 */
public class MenuChildHolder extends RecyclerView.ViewHolder
{
    private TextView     mNameTv;
    private LinearLayout mItemLayout;
    private Context      mContext;

    private MyOnClickListener.OnClickCallBackListener listener;

    public MenuChildHolder(View rootView, Context mContext, MyOnClickListener.OnClickCallBackListener listener)
    {
        super(rootView);
        this.mContext = mContext;
        this.listener = listener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
    }


    public void setCataInfo(final CataInfo mCataInfo, final int  p,final int i)
    {
        mNameTv.setText(mCataInfo.getName());

        if(mCataInfo.isSelected())
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
                listener.onSubmit(p,i);
            }
        });
    }


}
