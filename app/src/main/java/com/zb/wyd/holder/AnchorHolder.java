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
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class AnchorHolder extends RecyclerView.ViewHolder
{
    private TextView mFollowTv;
    private TextView mNameTv;
    private TextView mStatusTv;
    private ImageView mImgIv;
    private RelativeLayout mItemLayout;
    private MyItemClickListener listener;
    private Context context;
    private TextView mLocationTv;

    public AnchorHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        this.context = context;
        mStatusTv = (TextView) rootView.findViewById(R.id.tv_status);
        mFollowTv = (TextView) rootView.findViewById(R.id.tv_follow);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mImgIv = (ImageView) rootView.findViewById(R.id.iv_user_pic);

        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.rl_item);
        mLocationTv = (TextView) rootView.findViewById(R.id.tv_location);

    }


    public void setLiveInfo(LiveInfo mLiveInfo, final int p)
    {
        ImageLoader.getInstance().displayImage(mLiveInfo.getFace(), mImgIv);


        if ("1".equals(mLiveInfo.getIs_live()))
        {
            mStatusTv.setBackgroundResource(R.drawable.bg_live_status);
        }
        else
        {
            mStatusTv.setBackgroundResource(R.drawable.bg_live_status_off);
        }

        if (StringUtils.stringIsEmpty(mLiveInfo.getLocation()))
        {
            mLocationTv.setText("保密");
        }
        else
        {
            mLocationTv.setText(mLiveInfo.getLocation());
        }
        mFollowTv.setText(mLiveInfo.getFavour_count());
        mNameTv.setText(mLiveInfo.getNick());
        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });
    }


}
