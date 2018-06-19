package com.zb.wyd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class LoginActivity extends BaseActivity
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
    @BindView(R.id.btn_login)
    Button    btnLogin;
    @BindView(R.id.btn_register)
    Button    btnRegister;
    @BindView(R.id.tv_recovery_pwd)
    TextView  tvRecoveryPwd;
    @BindView(R.id.tv_email)
    TextView  tvEmail;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_login);
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

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvRecoveryPwd.setOnClickListener(this);
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

        if (v == btnLogin)
        {

        }
        else if (v == btnRegister)
        {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        else if (v == ivBack)
        {
            finish();
        }
        else if(v ==tvRecoveryPwd)
        {

        }
    }


}
