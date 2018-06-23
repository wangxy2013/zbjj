package com.zb.wyd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.entity.PicInfo;
import com.zb.wyd.holder.PhotoAddHolder;
import com.zb.wyd.holder.PhotoBaseHolder;
import com.zb.wyd.holder.PhotoHolder;
import com.zb.wyd.holder.PhotoItemHolder;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.StringUtils;

import java.util.List;

/**
 */
public class AddPhotoAdapter extends RecyclerView.Adapter<PhotoBaseHolder>
{

    private MyItemClickListener listener;
    private List<PicInfo>        list;
    private Context             mContext;

    public AddPhotoAdapter(List<PicInfo> list, MyItemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public PhotoBaseHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        PhotoBaseHolder mPhotoBaseHolder = null;

        switch (viewType)
        {
            case 0:
                mPhotoBaseHolder = new PhotoAddHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_add, parent, false),parent.getContext(), listener);
                break;
            case 1:
                mPhotoBaseHolder = new PhotoItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo1, parent, false), parent.getContext(),listener);
                break;
        }
        return mPhotoBaseHolder;
    }


    @Override
    public void onBindViewHolder(PhotoBaseHolder holder, int position)
    {
        holder.setPhoto(list.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    @Override
    public int getItemViewType(int position)
    {
        if (StringUtils.stringIsEmpty(list.get(position).getAbsolutelyPath()))
        {
            return 0;

        }
        else
        {
            return 1;
        }
    }
}
