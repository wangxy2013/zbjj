package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;

/**
 * 描述：一句话简单描述
 */
public class WelComeActivity extends BaseActivity
{
    @BindView(R.id.iv_bg)
    ImageView      ivBg;
    @BindView(R.id.tv_tips)
    TextView       tvTips;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;

    private int time = 3;
    private int count;

    private static final int UPDATE_CODE_VIEW = 0X03;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(WelComeActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {


                case UPDATE_CODE_VIEW:
                    count++;

                    int result = time - count;

                    if (result >= 0)
                    {
                        tvTips.setText(result + "S");
                        mHandler.sendEmptyMessageDelayed(UPDATE_CODE_VIEW, 1000);
                    }
                    else
                    {
                        startActivity(new Intent(WelComeActivity.this, MainActivity.class));
                        finish();
                    }

                    break;


            }
        }
    };

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.transparencyBar(WelComeActivity.this);
        StatusBarUtil.StatusBarLightMode(WelComeActivity.this, false);
    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        tvTips.setText("3s");
        mHandler.sendEmptyMessageDelayed(UPDATE_CODE_VIEW, 1000);
    }


}
