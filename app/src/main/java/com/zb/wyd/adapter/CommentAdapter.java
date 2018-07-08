package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.CommentInfo;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.holder.AnchorHolder;
import com.zb.wyd.holder.CommentHolder;
import com.zb.wyd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentHolder>
{

    private List<CommentInfo> list;

    public CommentAdapter(List<CommentInfo> list)
    {
        this.list = list;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        CommentHolder mHolder = new CommentHolder(itemView);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(CommentHolder holder, int position)
    {
        CommentInfo mCommentInfo = list.get(position);
        holder.setCommentInfo(mCommentInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
