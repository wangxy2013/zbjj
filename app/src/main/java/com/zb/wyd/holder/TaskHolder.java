package com.zb.wyd.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.activity.BindEmailActivity;
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
        final String taskId = taskInfo.getId();
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

        mTaskTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (Integer.parseInt(taskId))
                {
                    //激活邮箱
                    case 3:
                        context.startActivity(new Intent(context, BindEmailActivity.class));
                        break;
                    //上传头像
                    case 4:
                        break;
                    //观看直播
                    case 5:
                        break;
                    //观看点播
                    case 6:
                        break;
                    //分享给朋友
                    case 7:
                        break;
                    //分享到朋友圈
                    case 8:
                        break;
                    //发布自拍
                    case 9:
                        break;
                    //邀请注册
                    case 10:
                        break;
                    //推广APP
                    case 11:
                        break;
                    //节日福利
                    case 12:
                        break;
                    //举报
                    case 13:
                        break;
                    //关注QQ、微信
                    case 14:
                        break;

                    //"分享链接被访问
                    case 15:
                        break;

                }
            }
        });

    }


}
