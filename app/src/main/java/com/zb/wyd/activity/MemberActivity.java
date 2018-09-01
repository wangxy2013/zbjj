package com.zb.wyd.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;

public class MemberActivity extends BaseActivity
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

    private int buyStyle=0;
    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_member);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
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
        ImageLoader.getInstance().displayImage(ConfigManager.instance().getUserPic(),ivUserPic);
        tvUserName.setText(ConfigManager.instance().getUserName());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if(v == ivBack)
        {
            finish();
        }
        else if(v == llMonth)
        {
            llMonth.setSelected(true);
            llQuarter.setSelected(false);
            llYear.setSelected(false);
            buyStyle=0;
        }
        else if(v == llQuarter)
        {
            llMonth.setSelected(false);
            llQuarter.setSelected(true);
            llYear.setSelected(false);
            buyStyle=1;
        }
        else if(v == llYear)
        {
            llMonth.setSelected(false);
            llQuarter.setSelected(false);
            llYear.setSelected(true);
            buyStyle=2;
        }
        else  if(v == btnSubmit)
        {
            int money = 60;
            String product = "month";
            switch (buyStyle)
            {
                case 0:
                    money=60;
                    product = "month";
                    break;
                case 1:
                    money=156;
                    product = "season";
                    break;
                case 2:
                    money=566;
                    product = "year";
                    break;
            }
            String url = Urls.getPayUrl(money,product, APPUtils.getDeviceId(this));
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
