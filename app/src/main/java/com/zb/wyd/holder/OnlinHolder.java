package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.widget.CircleImageView;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class OnlinHolder extends RecyclerView.ViewHolder
{
    private CircleImageView mUserPicIv;
    private Context         context;

    public OnlinHolder(View rootView, Context context)
    {
        super(rootView);
        this.context = context;
        mUserPicIv = (CircleImageView) rootView.findViewById(R.id.iv_user_pic);

    }


    public void setUserInfo(UserInfo mUserInfo)
    {
        ImageLoader.getInstance().displayImage(mUserInfo.getFace(), mUserPicIv);
    }


}
