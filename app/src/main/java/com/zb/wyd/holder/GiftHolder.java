package com.zb.wyd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.GiftInfo;
import com.zb.wyd.listener.MyItemClickListener;


/**
 */
public class GiftHolder extends RecyclerView.ViewHolder
{
    private TextView            mGiftName;
    private TextView            mGiftPrice;
    private ImageView           mImgIv;
    private ImageView           mMasonryIv;
    private LinearLayout        mItemLayout;
    private MyItemClickListener listener;
    private Context             context;

    public GiftHolder(View rootView, Context context, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        this.context = context;
        mGiftName = (TextView) rootView.findViewById(R.id.tv_gift_name);
        mGiftPrice = (TextView) rootView.findViewById(R.id.tv_gift_price);
        mImgIv = (ImageView) rootView.findViewById(R.id.iv_gift_pic);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
        mMasonryIv = (ImageView) rootView.findViewById(R.id.iv_masonry);

    }


    public void setGiftInfo(GiftInfo giftInfo, final int p)
    {
        mGiftName.setText(giftInfo.getGiftName());
        mImgIv.setImageResource(giftInfo.getDrawableId());


        //0:积分 1：砖石
        if ("0".equals(giftInfo.getGiftStyle()))
        {
            mGiftPrice.setText(giftInfo.getGiftPrice() + "积分");
            mMasonryIv.setVisibility(View.GONE);
        }
        else
        {
            mGiftPrice.setText(giftInfo.getGiftPrice());
            mMasonryIv.setVisibility(View.VISIBLE);
        }

        mItemLayout.setSelected(giftInfo.isSelected());
        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.onItemClick(view, p);
            }
        });
    }


}
