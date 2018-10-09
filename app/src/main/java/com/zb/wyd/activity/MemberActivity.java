package com.zb.wyd.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.entity.MemberPriceInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.MemberPriceInfoHandler;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    TextView        tvTitle;
    @BindView(R.id.iv_user_pic)
    CircleImageView ivUserPic;
    @BindView(R.id.tv_user_name)
    TextView        tvUserName;
    @BindView(R.id.ll_month)
    LinearLayout    llMonth;
    @BindView(R.id.ll_quarter)
    LinearLayout    llQuarter;
    @BindView(R.id.ll_year)
    LinearLayout    llYear;
    @BindView(R.id.btn_submit)
    Button          btnSubmit;
    @BindView(R.id.tv_month)
    TextView        tvMonth;
    @BindView(R.id.tv_season)
    TextView        tvSeason;
    @BindView(R.id.tv_year)
    TextView        tvYear;

    private int buyStyle = 0;


    private int monthPrice,seasonPrice,yearPrice;


    private static final String      GET_PRICE       = "get_price";
    private static final int         REQUEST_SUCCESS = 0x01;
    private static final int         REQUEST_FAIL    = 0x02;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler        = new BaseHandler(MemberActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    MemberPriceInfoHandler mMemberPriceInfoHandler = (MemberPriceInfoHandler)msg.obj;
                    MemberPriceInfo memberPriceInfo = mMemberPriceInfoHandler.getMemberPriceInfo();

                    if(null != memberPriceInfo)
                    {
                        monthPrice = memberPriceInfo.getMonth();
                        seasonPrice = memberPriceInfo.getSeason();
                        yearPrice = memberPriceInfo.getYear();

                        tvMonth.setText("¥"+monthPrice);
                        tvSeason.setText("¥"+seasonPrice);
                        tvYear.setText("¥"+yearPrice);
                    }
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(MemberActivity.this, msg.obj.toString());
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
        setContentView(R.layout.activity_member);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.white));
        StatusBarUtil.StatusBarLightMode(MemberActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        llMonth.setOnClickListener(this);
        llQuarter.setOnClickListener(this);
        llYear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("充值会员");
        llMonth.setSelected(true);
        llQuarter.setSelected(false);
        llYear.setSelected(false);
        ImageLoader.getInstance().displayImage(ConfigManager.instance().getUserPic(), ivUserPic);
        tvUserName.setText(ConfigManager.instance().getUserName());


        Map<String, String> valuePairs = new HashMap<>();
        DataRequest.instance().request(MemberActivity.this, Urls.getPaypalPriceUrl(), this, HttpRequest.POST, GET_PRICE, valuePairs,
                new MemberPriceInfoHandler());

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == llMonth)
        {
            llMonth.setSelected(true);
            llQuarter.setSelected(false);
            llYear.setSelected(false);
            buyStyle = 0;
        }
        else if (v == llQuarter)
        {
            llMonth.setSelected(false);
            llQuarter.setSelected(true);
            llYear.setSelected(false);
            buyStyle = 1;
        }
        else if (v == llYear)
        {
            llMonth.setSelected(false);
            llQuarter.setSelected(false);
            llYear.setSelected(true);
            buyStyle = 2;
        }
        else if (v == btnSubmit)
        {
            int money = 0;
            String product = "month";
            switch (buyStyle)
            {
                case 0:
                    money = monthPrice;
                    product = "month";
                    break;
                case 1:
                    money = seasonPrice;
                    product = "season";
                    break;
                case 2:
                    money = yearPrice;
                    product = "year";
                    break;
            }
            String url = Urls.getPayUrl(money, product, APPUtils.getDeviceId(this));
            //            Uri uri = Uri.parse(url);
            //            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //            startActivity(intent);

            startActivity(new Intent(MemberActivity.this, WebViewActivity.class)
                    .putExtra(WebViewActivity.EXTRA_TITLE, "会员")
                    .putExtra(WebViewActivity.IS_SETTITLE, true).putExtra(WebViewActivity.EXTRA_URL, url));
        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (GET_PRICE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }


}
