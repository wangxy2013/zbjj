package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.MyApplication;
import com.zb.wyd.R;
import com.zb.wyd.entity.SelfieInfo;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.widget.RoundAngleImageView;


/**
 */
public class CollectionSelfieHolder extends RecyclerView.ViewHolder
{
    private TextView            mTimeTv;
    private TextView            mDelTv;
    private TextView            mNameTv;
    private ImageView           mLocationIv;
    private TextView            mFavTv;
    private RoundAngleImageView mImgIv;
    private RelativeLayout      mItemLayout;
    private MyItemClickListener listener;
    private MyItemClickListener delListener;
    private Context             context;

    public CollectionSelfieHolder(View rootView, Context context, MyItemClickListener listener,MyItemClickListener delListener)
    {
        super(rootView);
        this.listener = listener;
        this.delListener = listener;
        this.context = context;
        mImgIv = (RoundAngleImageView) rootView.findViewById(R.id.iv_user_pic);
        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.rl_item);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mFavTv = (TextView) rootView.findViewById(R.id.tv_fav);
        mLocationIv = (ImageView) rootView.findViewById(R.id.iv_location);
        mDelTv = (TextView) rootView.findViewById(R.id.tv_del);
    }


    public void setSelfieInfo(SelfieInfo mSelfieInfo, final int p)
    {

        int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.dm_10) * 3;
        int width = (APPUtils.getScreenWidth(context) - spacingInPixels) / 2;
        int height = width + MyApplication.getInstance().getPhotoDataList().get(p);
        mItemLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        ImageLoader.getInstance().displayImage(mSelfieInfo.getCover(), mImgIv);

        mNameTv.setText(mSelfieInfo.getPname());
        mTimeTv.setText(mSelfieInfo.getAdd_time());
        mFavTv.setText(mSelfieInfo.getFavour_count());


        if (StringUtils.stringIsEmpty(mSelfieInfo.getLocation()))
        {
            mLocationIv.setVisibility(View.GONE);
        }
        else
        {
            mLocationIv.setVisibility(View.VISIBLE);
        }

        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });

        mDelTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delListener.onItemClick(v, p);
            }
        });
    }


}
