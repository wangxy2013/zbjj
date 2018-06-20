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
        String pic="http:\\/\\/os.tanzhelang.com\\/public\\/attachment\\/201806\\/20\\/10\\/origin\\/1529434583745452.jpg?x-oss-process=image\\/resize,m_mfit,h_260,w_260";
        ImageLoader.getInstance().displayImage(pic, mUserPicIv);
    }


}
