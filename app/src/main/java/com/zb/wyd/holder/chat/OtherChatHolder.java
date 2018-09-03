package com.zb.wyd.holder.chat;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.ChatInfo;
import com.zb.wyd.listener.MyItemClickListener;


/**
 * DESC: log--聊天
 */
public class OtherChatHolder extends ChatBaseHolder
{
    private TextView            mContentTv;
    private Context             mContext;
    private MyItemClickListener listener;

    public OtherChatHolder(View rootView, Context mContext, MyItemClickListener listener)
    {
        super(rootView);
        mContentTv = (TextView) rootView.findViewById(R.id.tv_content);
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public void setChatInfo(ChatInfo mChatInfo, int p)
    {
        mContentTv.setText(mChatInfo.getData());

    }


}
