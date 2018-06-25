package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.LiveInfo;
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

    }


    public void setTaskInfo(TaskInfo taskInfo, final int p)
    {
        mTitleTv.setText(taskInfo.getTname());
        mDescTv.setText(taskInfo.getDesc());

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
    }


}
