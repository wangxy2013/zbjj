package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.PicInfo;
import com.zb.wyd.entity.TaskInfo;
import com.zb.wyd.holder.CataHolder;
import com.zb.wyd.holder.PhotoAddHolder;
import com.zb.wyd.holder.PhotoBaseHolder;
import com.zb.wyd.holder.PhotoItemHolder;
import com.zb.wyd.holder.TaskHolder;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.StringUtils;

import java.util.List;

/**
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskHolder>
{

    private MyItemClickListener listener;
    private List<TaskInfo>      list;
    private Context             mContext;

    public TaskAdapter(List<TaskInfo> list, MyItemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        TaskHolder mHolder = new TaskHolder(itemView,parent.getContext(),listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(TaskHolder holder, int position)
    {
        holder.setTaskInfo(list.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
