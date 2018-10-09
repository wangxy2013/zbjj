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
public class HotHolder extends RecyclerView.ViewHolder
{
    private TextView mFollowTv;
    private TextView mNameTv;
    private RoundAngleImageView mImgIv;
    private RelativeLayout mItemLayout;
    private MyItemClickListener listener;
    private Context context;
    private TextView mLocationTv;
    private ImageView mLocationIv;

    public HotHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        this.context = context;
        mFollowTv = (TextView) rootView.findViewById(R.id.tv_follow);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mImgIv = (RoundAngleImageView) rootView.findViewById(R.id.iv_user_pic);
        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.rl_item);
        mLocationTv = (TextView) rootView.findViewById(R.id.tv_location);
        mLocationIv = (ImageView) rootView.findViewById(R.id.iv_location);

        int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.dm_10) * 3;
        int width = (APPUtils.getScreenWidth(context) - spacingInPixels) / 2;

        LinearLayout.LayoutParams params=  new LinearLayout.LayoutParams(width, width);
        params.bottomMargin=30;
        params.leftMargin=12;
        params.rightMargin=10;
        mItemLayout.setLayoutParams(params);
        RelativeLayout.LayoutParams imgLayoutParams = new RelativeLayout.LayoutParams(width, width);
        mImgIv.setLayoutParams(imgLayoutParams);
        mImgIv.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }


    public void setLiveInfo(LiveInfo mLiveInfo, final int p)
    {

        ImageLoader.getInstance().displayImage(mLiveInfo.getFace(), mImgIv);


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
