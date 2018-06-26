package com.zb.wyd.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.MessageInfo;
import com.zb.wyd.entity.WealthInfo;


/**
 */
public class MessageHolder extends RecyclerView.ViewHolder
{
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mContenTv;

    public MessageHolder(View rootView)
    {
        super(rootView);
        mTypeTv = (TextView) rootView.findViewById(R.id.tv_type);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mContenTv = (TextView) rootView.findViewById(R.id.tv_content);

    }


    public void setMessageInfo(MessageInfo mMessageInfo)
    {

        mTimeTv.setText(mMessageInfo.getTime());
        mTypeTv.setText(mMessageInfo.getType());
        mContenTv.setText(mMessageInfo.getContent());
    }


}
