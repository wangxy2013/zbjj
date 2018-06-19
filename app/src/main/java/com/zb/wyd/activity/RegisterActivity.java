package com.zb.wyd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class RegisterActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_account)
    ImageView ivAccount;
    @BindView(R.id.et_account)
    EditText  etAccount;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(R.id.et_pwd)
    EditText  etPwd;
    @BindView(R.id.iv_invitation)
    ImageView ivInvitation;
    @BindView(R.id.et_invitation)
    EditText  etInvitation;
    @BindView(R.id.btn_register)
    Button    btnRegister;
    @BindView(R.id.tv_login)
    TextView  tvLogin;
    @BindView(R.id.tv_email)
    TextView  tvEmail;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_register);
        setTranslucentStatusPadding();
    }

    @Override
    protected void initEvent()
    {
        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivAccount.setSelected(hasFocus);
            }
        });
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivPwd.setSelected(hasFocus);
            }
        });

        etInvitation.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivInvitation.setSelected(hasFocus);
            }
        });
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        setStatusBarTextDeep(true);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvLogin)
        {
            finish();
        }
        else if (v == btnRegister)
        {
            String account = etAccount.getText().toString();
            String pwd = etPwd.getText().toString();


            if(TextUtils.isEmpty(account))
            {
                ToastUtil.show(RegisterActivity.this,"请输入账号");
                return;
            }

            if(TextUtils.isEmpty(pwd))
            {
                ToastUtil.show(RegisterActivity.this,"请输入账号");
                return;
            }
        }

    }
}
