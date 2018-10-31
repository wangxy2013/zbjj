package com.zb.wyd.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;

public class PayActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView      ivBack;
    @BindView(R.id.tv_title)
    TextView       tvTitle;
    @BindView(R.id.tv_submit)
    TextView       tvSubmit;
    @BindView(R.id.tv_money)
    TextView       tvMoney;
    @BindView(R.id.iv_alipay_selected)
    ImageView      ivAlipaySelected;
    @BindView(R.id.rl_alipay)
    RelativeLayout rlAlipay;
    @BindView(R.id.iv_wxpay_selected)
    ImageView      ivWxpaySelected;
    @BindView(R.id.rl_wxpay)
    RelativeLayout rlWxpay;
    @BindView(R.id.btn_submit)
    Button         btnSubmit;
    @BindView(R.id.tv_masonry)
    TextView       tvMasonry;
    @BindView(R.id.iv_alipay)
    ImageView      ivAlipay;
    @BindView(R.id.iv_wxpay)
    ImageView      ivWxpay;
    private int money;
private int payStyle=0;
    @Override
    protected void initData()
    {
        money = getIntent().getIntExtra("MONEY", 0);
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_pay);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.redC));
        StatusBarUtil.StatusBarLightMode(PayActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
      rlAlipay.setOnClickListener(this);
      rlWxpay.setOnClickListener(this);
      ivBack.setOnClickListener(this);
      btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("确认付款");
        tvMoney.setText(money+"元");
        tvMasonry.setText(money*100 +"=");
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if(v ==ivBack)
        {
            finish();
        }
        else if(v == rlAlipay)
        {
            payStyle=0;
            ivAlipaySelected.setImageResource(R.drawable.ic_pay_on);
            ivWxpaySelected.setImageResource(R.drawable.ic_pay_off);
        }
        else if(v == rlWxpay)
        {
            payStyle=1;
            ivAlipaySelected.setImageResource(R.drawable.ic_pay_off);
            ivWxpaySelected.setImageResource(R.drawable.ic_pay_on);
        }
        else if(v == btnSubmit)
        {
//            Uri uri = Uri.parse(Urls.getPayUrl());
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
        }
    }
}
