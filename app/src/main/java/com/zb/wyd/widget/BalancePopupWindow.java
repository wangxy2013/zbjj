package com.zb.wyd.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zb.wyd.R;


/**
 * 描述：一句话简单描述
 */
public class BalancePopupWindow extends PopupWindow
{

    private View        mMenuView;
    private PopupWindow popupWindow;

    public BalancePopupWindow(Context context)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_balance, null);

        this.setContentView(mMenuView);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);
    }


}
