package com.zb.wyd.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyMasonryActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_recharge_balance)
    TextView tvRechargeBalance;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.rl_money30)
    RelativeLayout rlMoney30;
    @BindView(R.id.rl_money50)
    RelativeLayout rlMoney50;
    @BindView(R.id.rl_money100)
    RelativeLayout rlMoney100;
    @BindView(R.id.rl_money200)
    RelativeLayout rlMoney200;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_masonry30)
    TextView tvMasonry30;
    @BindView(R.id.tv_masonry50)
    TextView tvMasonry50;
    @BindView(R.id.tv_masonry100)
    TextView tvMasonry100;
    @BindView(R.id.tv_masonry200)
    TextView tvMasonry200;

    private int money;
    private List<TextView> tvMasonryList = new ArrayList<TextView>();

    @Override
    protected void initData()
    {
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_my_masonry);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(MyMasonryActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        rlMoney30.setOnClickListener(this);
        rlMoney50.setOnClickListener(this);
        rlMoney100.setOnClickListener(this);
        rlMoney200.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvMember.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        etMoney.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if (!TextUtils.isEmpty(s))
                {
                    changMoney(4);
                    money = Integer.parseInt(etMoney.getText().toString());
                }

            }
        });
    }

    @Override
    protected void initViewData()
    {
        tvMasonryList.add(tvMasonry30);
        tvMasonryList.add(tvMasonry50);
        tvMasonryList.add(tvMasonry100);
        tvMasonryList.add(tvMasonry200);
        tvTitle.setText("我的砖石");
        tvRechargeBalance.setText(getIntent().getStringExtra("CASH"));
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvMember)
        {
            startActivity(new Intent(this, MemberActivity.class));
        }
        else if (v == rlMoney30)
        {
            money = 30;
            changMoney(0);
        }
        else if (v == rlMoney50)
        {
            money = 50;
            changMoney(1);
        }
        else if (v == rlMoney100)
        {
            money = 100;
            changMoney(2);
        }
        else if (v == rlMoney200)
        {
            money = 200;
            changMoney(3);
        }
        else if (v == btnSubmit)
        {
            pay(money);
        }
    }


    private void pay(int money)
    {
        if (money < 10 || money > 1000)
        {
            ToastUtil.show(this, "充值金额为10-1000");
            return;
        }
        String url = Urls.getPayUrl(money, "payin", APPUtils.getDeviceId(this));
        //        Uri uri = Uri.parse(url);
        //        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //        startActivity(intent);


        startActivity(new Intent(MyMasonryActivity.this, WebViewActivity.class).putExtra(WebViewActivity.EXTRA_TITLE, "充值").putExtra(WebViewActivity.IS_SETTITLE, true).putExtra(WebViewActivity.EXTRA_URL, url));


    }

    private void changMoney(int index)
    {
        if (index != 4) etMoney.setText("");
        for (int i = 0; i < tvMasonryList.size(); i++)
        {
            if (i == index)
            {
                tvMasonryList.get(index).setSelected(true);
            }
            else
            {
                tvMasonryList.get(i).setSelected(false);
            }
        }
    }
}
