package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zb.wyd.entity.PicInfo;
import com.zb.wyd.listener.MyItemClickListener;

/**
 * 描述：一句话简单描述
 */
public abstract class PhotoBaseHolder extends RecyclerView.ViewHolder
{
    public PhotoBaseHolder(View itemView,Context context,MyItemClickListener listener)
    {
        super(itemView);
    }

    public abstract void setPhoto(PicInfo mPicInfo, int p);
}
