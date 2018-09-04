package com.zb.wyd.holder.chat;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.ChatInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.widget.CircleImageView;


/**
 * DESC: -聊天
 */
public class UserChatHolder extends ChatBaseHolder
{
    private TextView mContentTv;
    private CircleImageView mUserPicIv;
    private TextView mUsereNameTv;
    private Context mContext;
    private MyItemClickListener listener;

    public UserChatHolder(View rootView, Context mContext, MyItemClickListener listener)
    {
        super(rootView);
        mContentTv = (TextView) rootView.findViewById(R.id.tv_content);
        mUserPicIv = (CircleImageView) rootView.findViewById(R.id.iv_user_pic);
        mUsereNameTv = (TextView) rootView.findViewById(R.id.tv_user_name);
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public void setChatInfo(ChatInfo mChatInfo, int p)
    {

        UserInfo userInfo = mChatInfo.getUserInfo();

        if (null != userInfo)
        {
            if(Integer.parseInt(userInfo.getVip_level())>=2)
            {
                ImageLoader.getInstance().displayImage(userInfo.getUface(), mUserPicIv);
                mUserPicIv.setVisibility(View.VISIBLE);
            }
            else
            {
                mUserPicIv.setVisibility(View.GONE);
            }
            mUsereNameTv.setText(userInfo.getUnick() +":");

        }
        mContentTv.setText(mChatInfo.getData());

    }


}
