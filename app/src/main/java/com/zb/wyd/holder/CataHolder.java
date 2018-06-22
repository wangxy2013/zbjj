package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class CataHolder extends RecyclerView.ViewHolder
{
    private TextView            mNameTv;
    private View                mLine;
    private LinearLayout        mItemLayout;
    private MyItemClickListener listener;

    public CataHolder(View rootView, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
        mLine = (View) rootView.findViewById(R.id.line);
    }


    public void setCataInfo(CataInfo mCataInfo, final int p)
    {
        mNameTv.setText(mCataInfo.getName());
        if (mCataInfo.isSelected())
        {
            mNameTv.setTextSize(16);
            mLine.setVisibility(View.VISIBLE);
        }
        else
        {
            mNameTv.setTextSize(14);
            mLine.setVisibility(View.INVISIBLE);
        }
        mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v,p);
            }
        });
    }


}
