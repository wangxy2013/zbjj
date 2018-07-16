package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_email)
    TextView       tvEmail;

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
                        tvTips.setText(result + "s");
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

        if (!TextUtils.isEmpty(ConfigManager.instance().getSystemEmail()))
            tvEmail.setText("防丢邮箱,发邮件到" + ConfigManager.instance().getSystemEmail() + "获取最新地址");

        if (!TextUtils.isEmpty(ConfigManager.instance().getBgStartup()))
        {
            ImageLoader.getInstance().displayImage(ConfigManager.instance().getBgStartup(), ivBg);
        }
    }


}
