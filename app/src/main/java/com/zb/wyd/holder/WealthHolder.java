package com.zb.wyd.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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


        if (TextUtils.isEmpty(mWealthInfo.getDirect()))
        {
            if(mWealthInfo.getCash()<0)
            {
                mDescTv.setText(mWealthInfo.getCash() + "");
            }
            else
            {
                mDescTv.setText("+"+mWealthInfo.getCash());
            }

        }
        else
        {
            if ("-1".equals(mWealthInfo.getDirect()))
            {
                mDescTv.setText("-" + (mWealthInfo.getCash() + mWealthInfo.getCoupon()));
            }
            else
            {
                mDescTv.setText("+" + (mWealthInfo.getCash() + mWealthInfo.getCoupon()));
            }
        }

    }


}
