package com.zb.wyd.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.listener.MyItemClickListener;


/**
 * 描述：一句话简单描述
 */
public class ChannelPopupWindow extends PopupWindow
{

    private View mMenuView;
    private TextView mYdTv, mDxTv, mCmTv;

    private MyItemClickListener listener;

    public ChannelPopupWindow(Context context, MyItemClickListener listener)
    {
        super(context);
        this.listener = listener;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_channel, null);
        mYdTv = (TextView) mMenuView.findViewById(R.id.tv_yd);
        mDxTv = (TextView) mMenuView.findViewById(R.id.tv_dx);
        mCmTv = (TextView) mMenuView.findViewById(R.id.tv_cm);
        mCmTv.setSelected(true);
        mYdTv.setSelected(false);
        mDxTv.setSelected(false);
        this.setContentView(mMenuView);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(800);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);


        mCmTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, 1);
                mCmTv.setSelected(true);
                mYdTv.setSelected(false);
                mDxTv.setSelected(false);
            }
        });
        mYdTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, 2);
                mCmTv.setSelected(false);
                mYdTv.setSelected(true);
                mDxTv.setSelected(false);
            }
        });

        mDxTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, 3);
                mCmTv.setSelected(false);
                mYdTv.setSelected(false);
                mDxTv.setSelected(true);
            }
        });
    }


}
