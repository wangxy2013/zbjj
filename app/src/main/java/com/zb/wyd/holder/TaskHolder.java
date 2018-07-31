package com.zb.wyd.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.activity.BindEmailActivity;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.StyleInfo;
import com.zb.wyd.entity.TaskInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class TaskHolder extends RecyclerView.ViewHolder
{
    private TextView            mTitleTv;
    private TextView            mDescTv;
    private TextView            mTaskTv;
    private ImageView           mHotIv;
    private MyItemClickListener listener;
    private Context             context;

    public TaskHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        this.context = context;
        mTitleTv = (TextView) rootView.findViewById(R.id.tv_title);
        mDescTv = (TextView) rootView.findViewById(R.id.tv_desc);
        mTaskTv = (TextView) rootView.findViewById(R.id.tv_task);
        mHotIv = (ImageView) rootView.findViewById(R.id.iv_hot);
    }


    public void setTaskInfo(TaskInfo taskInfo, final int p)
    {
        mTitleTv.setText(taskInfo.getTname());
        mDescTv.setText(taskInfo.getDesc());

        StyleInfo styleInfo = taskInfo.getStyleInfo();
        if (null != styleInfo)
        {
            if ("1".equals(styleInfo.getHot()))
            {
                mHotIv.setVisibility(View.VISIBLE);
            }
            else
            {
                mHotIv.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(styleInfo.getColor()))
            {
                mTitleTv.setTextColor(Color.parseColor(styleInfo.getColor()));
            }
            if ("bold".equals(styleInfo.getFont()))
            {
                TextPaint tp = mTitleTv.getPaint();
                tp.setFakeBoldText(true);
            }
            else
            {
                TextPaint tp = mTitleTv.getPaint();

                tp.setFakeBoldText(false);
            }
        }

        if ("0".equals(taskInfo.getHas_finish()))
        {
            mTaskTv.setText("做任务");
            mTaskTv.setEnabled(true);
        }
        else
        {
            mTaskTv.setText("已完成");
            mTaskTv.setEnabled(false);
        }

        mTaskTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });

    }


}
