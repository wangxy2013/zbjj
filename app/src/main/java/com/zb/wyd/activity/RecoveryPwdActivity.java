package com.zb.wyd.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：重置密码
 */
public class RecoveryPwdActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView    ivBack;
    @BindView(R.id.iv_account)
    ImageView    ivAccount;
    @BindView(R.id.et_account)
    EditText     etAccount;
    @BindView(R.id.iv_invitation)
    ImageView    ivInvitation;
    @BindView(R.id.tv_code)
    TextView     tvCode;
    @BindView(R.id.et_invitation)
    EditText     etInvitation;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.iv_pwd)
    ImageView    ivPwd;
    @BindView(R.id.et_pwd)
    EditText     etPwd;
    @BindView(R.id.iv_pwd1)
    ImageView    ivPwd1;
    @BindView(R.id.et_pwd1)
    EditText     etPwd1;
    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.btn_submit)
    Button       btnSubmit;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.tv_email)
    TextView     tvEmail;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_recovery_pwd);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this, false);
    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
    }


}
