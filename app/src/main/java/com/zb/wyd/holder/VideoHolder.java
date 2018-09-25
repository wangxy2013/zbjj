package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gifloadlibrary.GifLoadUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.LogUtil;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class VideoHolder extends RecyclerView.ViewHolder
{
    private TextView            mFollowTv;
    private TextView            mPopularityTv;
    private TextView            mNameTv;
    private TextView            mStatusTv;
    private RoundAngleImageView mImgIv;
    private RelativeLayout      mItemLayout;
    private MyItemClickListener listener;
    private Context             context;

    public VideoHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        this.context = context;
        mFollowTv = (TextView) rootView.findViewById(R.id.tv_follow);
        mStatusTv = (TextView) rootView.findViewById(R.id.tv_status);
        mPopularityTv = (TextView) rootView.findViewById(R.id.tv_popularity);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mImgIv = (RoundAngleImageView) rootView.findViewById(R.id.iv_user_pic);
        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.rl_item);
        int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.dm_10) * 3;
        int width = (APPUtils.getScreenWidth(context) - spacingInPixels) / 2;
        mItemLayout.setLayoutParams(new LinearLayout.LayoutParams(width, width * 13 / 20));
        RelativeLayout.LayoutParams imgLayoutParams = new RelativeLayout.LayoutParams(width, width * 13 / 20);
        mImgIv.setLayoutParams(imgLayoutParams);
        mImgIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    public void setVideoInfo(VideoInfo mVideoInfo, final int p)
    {


        if (mVideoInfo.getCover().contains(".gif"))
        {
            LogUtil.e("TAG", "mVideoInfo.getCover()-->" + mVideoInfo.getCover());
            GifLoadUtils.getInstance().getImageLoader(context).loadUrl(mVideoInfo.getCover()).into(mImgIv);
        }
        else
        {
            ImageLoader.getInstance().displayImage(mVideoInfo.getCover(), mImgIv);
        }


        mFollowTv.setText(mVideoInfo.getFavour_count());
        mPopularityTv.setText(mVideoInfo.getCoupon());
        mNameTv.setText(mVideoInfo.getV_name());

        if (mVideoInfo.isNew() && null != mStatusTv)
        {
            mStatusTv.setText(mVideoInfo.getAdd_time());
        }
        else
        {
            mStatusTv.setText("");
        }
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
