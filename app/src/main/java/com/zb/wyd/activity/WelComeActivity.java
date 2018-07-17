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
import com.zb.wyd.MyApplication;
import com.zb.wyd.R;
import com.zb.wyd.entity.LocationInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LocationInfoHandler;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class WelComeActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_bg)
    ImageView      ivBg;
    @BindView(R.id.tv_tips)
    TextView       tvTips;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.tv_email)
    TextView       tvEmail;

    private int time = 5;
    private int count;

    private static final int         UPDATE_CODE_VIEW     = 0X03;
    private static final int         GET_LOCATION_CODE    = 0X15;
    private static final int         GET_LOCATION_FAIL    = 0X16;
    private static final int         GET_LOCATION_SUCCESS = 0x17;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler             = new BaseHandler(WelComeActivity.this)
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
                case GET_LOCATION_CODE:
                    getLocation();

                    break;

                case GET_LOCATION_SUCCESS:
                    LocationInfoHandler mLocationInfoHandler = (LocationInfoHandler) msg.obj;
                    LocationInfo locationInfo = mLocationInfoHandler.getLocationInfo();
                    String location = locationInfo.getProv() + "," + locationInfo.getCity() + "," + locationInfo.getDistrict();
                    MyApplication.getInstance().setLocation(location);

                    break;

                case GET_LOCATION_FAIL:
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

    private void getLocation()
    {
        Map<String, String> valuePairs = new HashMap<>();
        DataRequest.instance().request(this, ConfigManager.instance().getIpLookUp(), this, HttpRequest.GET, "GET_LOCATION",
                valuePairs,
                new LocationInfoHandler());
    }

    @Override
    protected void initViewData()
    {
        mHandler.sendEmptyMessage(GET_LOCATION_CODE);
        tvTips.setText("5s");
        mHandler.sendEmptyMessageDelayed(UPDATE_CODE_VIEW, 1000);

        if (!TextUtils.isEmpty(ConfigManager.instance().getSystemEmail()))
            tvEmail.setText("防丢邮箱,发邮件到" + ConfigManager.instance().getSystemEmail() + "获取最新地址");

        if (!TextUtils.isEmpty(ConfigManager.instance().getBgStartup()))
        {
            ImageLoader.getInstance().displayImage(ConfigManager.instance().getBgStartup(), ivBg);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mHandler.hasMessages(GET_LOCATION_CODE))
        {
            mHandler.removeMessages(GET_LOCATION_CODE);
        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if ("GET_LOCATION".equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_LOCATION_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_LOCATION_FAIL, resultMsg));
            }
        }
    }
}
