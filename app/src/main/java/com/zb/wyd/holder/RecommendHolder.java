package com.zb.wyd.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class RecommendHolder extends RecyclerView.ViewHolder
{
    private TextView            mTitleTv;
    private TextView            mIndexTv;
    private TextView            mNameTv;
    private TextView            mBrowseTv;
    private RoundAngleImageView mImgIv;
    private RelativeLayout      mItemLayout;
    private MyItemClickListener listener;

    public RecommendHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        mImgIv = (RoundAngleImageView) rootView.findViewById(R.id.iv_user_pic);

        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.rl_item);
        int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.dm_10) * 4;
        int width = (APPUtils.getScreenWidth(context) - spacingInPixels) / 3;
        mItemLayout.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        mItemLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
    }


    public void setUserInfo(UserInfo mUserInfo)
    {

    }


}
