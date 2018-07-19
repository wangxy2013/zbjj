package com.zb.wyd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class DomainNameActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView  tvTitle;
    @BindView(R.id.et_name)
    EditText  etName;
    @BindView(R.id.btn_submit)
    Button    btnSubmit;
    @BindView(R.id.tv_email)
    TextView  tvEmail;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_domian_name);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(DomainNameActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = etName.getText().toString();
                if (StringUtils.stringIsEmpty(name))
                {
                    ToastUtil.show(DomainNameActivity.this, "请输入正确的域名");
                    return;
                }

                ConfigManager.instance().setZdyDomainName(name);
                ToastUtil.show(DomainNameActivity.this, "保存成功!");
                finish();
            }
        });
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("域名设置");
        tvEmail.setText("防丢邮箱,发邮件到"+ConfigManager.instance().getSystemEmail()+"获取最新地址");
    }



}
