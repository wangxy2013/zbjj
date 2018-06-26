package com.zb.wyd.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.WealthInfo;


/**
 */
public class WealthHolder extends RecyclerView.ViewHolder
{
    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mDescTv;

    public WealthHolder(View rootView)
    {
        super(rootView);
        mTitleTv = (TextView) rootView.findViewById(R.id.tv_title);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mDescTv = (TextView) rootView.findViewById(R.id.tv_desc);

    }


    public void setWealthInfo(WealthInfo mWealthInfo)
    {

        mTimeTv.setText(mWealthInfo.getTime());
        mTitleTv.setText(mWealthInfo.getTitle());
        mDescTv.setText(mWealthInfo.getDesc());
    }


}
