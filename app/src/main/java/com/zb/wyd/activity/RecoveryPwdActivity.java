package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：重置密码
 */
public class RecoveryPwdActivity extends BaseActivity implements IRequestListener
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
    @BindView(R.id.iv_bg)
    ImageView    ivBg;


    //private boolean isFitst = true;

    private int time = 60;
    private int    count;
    private String token;
    private String pwd;
    private static final String USER_FORGOT            = "user_forgot";
    private static final String GET_EMAIL_CODE         = "get_email_code";
    private static final int    REQUEST_SUCCESS        = 0x01;
    private static final int    REQUEST_FAIL           = 0x02;
    private static final int    UPDATE_CODE_VIEW       = 0X03;
    private static final int    GET_EMAIL_CODE_SUCCESS = 0X04;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(RecoveryPwdActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(RecoveryPwdActivity.this, "密码找回成功");
                    ConfigManager.instance().setUserPwd(pwd);
                    finish();
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(RecoveryPwdActivity.this, msg.obj.toString());
                    break;


                case UPDATE_CODE_VIEW:
                    count++;

                    int result = time - count;

                    if (result >= 0)
                    {
                        tvCode.setText(result + "S后发送");
                        mHandler.sendEmptyMessageDelayed(UPDATE_CODE_VIEW, 1000);
                    }
                    else
                    {
                        tvCode.setEnabled(true);
                        tvCode.setText("发送验证码");
                    }

                    break;

                case GET_EMAIL_CODE_SUCCESS:
                    ToastUtil.show(RecoveryPwdActivity.this, "验证码已发送至邮箱");
                    ResultHandler resultHandler = (ResultHandler) msg.obj;
                    token = resultHandler.getContent();
                    etAccount.setEnabled(false);
                    mHandler.sendEmptyMessage(UPDATE_CODE_VIEW);
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
        setContentView(R.layout.activity_recovery_pwd);
        StatusBarUtil.transparencyBar(RecoveryPwdActivity.this);
        StatusBarUtil.StatusBarLightMode(RecoveryPwdActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        tvCode.setOnClickListener(this);
        llNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        llQq.setOnClickListener(this);
        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivAccount.setSelected(hasFocus);
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

        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivAccount.setSelected(hasFocus);
            }
        });
        etPwd1.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivAccount.setSelected(hasFocus);
            }
        });


    }

    @Override
    protected void initViewData()
    {
        btnSubmit.setText("提交");
        ImageLoader.getInstance().displayImage(ConfigManager.instance().getBgLogin(), ivBg);
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvCode)
        {
            String email = etAccount.getText().toString();

            if (!StringUtils.checkEmail(email))
            {
                ToastUtil.show(this, "请输入正确的邮箱");
                return;
            }

            tvCode.setEnabled(false);
            showProgressDialog();
            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("email", email);
            valuePairs.put("action", "forgot");
            DataRequest.instance().request(this, Urls.getEmailCodeUrl(), this, HttpRequest.POST, GET_EMAIL_CODE, valuePairs,
                    new ResultHandler());
        }
        else if (v == btnSubmit)
        {
//            if (isFitst)
//            {
                String email = etAccount.getText().toString();

                if (!StringUtils.checkEmail(email))
                {
                    ToastUtil.show(this, "请输入正确的邮箱");
                    return;
                }

                String code = etInvitation.getText().toString();

                if (TextUtils.isEmpty(code))
                {
                    ToastUtil.show(this, "请输入验证码");
                    return;

                }
//                llFirst.setVisibility(View.GONE);
//                llNext.setVisibility(View.VISIBLE);
              //  isFitst = false;


//            }
//            else
//            {

//                String email = etAccount.getText().toString();
//
//                if (!StringUtils.checkEmail(email))
//                {
//                    ToastUtil.show(this, "请输入正确的邮箱");
//                    return;
//                }
//
//
//                String code = etInvitation.getText().toString();
//
//                if (TextUtils.isEmpty(code))
//                {
//                    if (!StringUtils.checkEmail(email))
//                    {
//                        ToastUtil.show(this, "请输入验证码");
//                        return;
//                    }
//                }

                pwd = etPwd.getText().toString();
                String pwd1 = etPwd1.getText().toString();


                if (TextUtils.isEmpty(pwd) || pwd.length() < 6)
                {
                    ToastUtil.show(RecoveryPwdActivity.this, "请输入6-18位密码");
                    return;
                }

                if (!pwd.equals(pwd1))
                {
                    ToastUtil.show(RecoveryPwdActivity.this, "请保持2次密码输入一致");
                    return;
                }

                showProgressDialog();
                Map<String, String> valuePairs = new HashMap<>();
                valuePairs.put("email", email);
                valuePairs.put("vcode", code);
                valuePairs.put("token", token);
                valuePairs.put("passwd", pwd);
                valuePairs.put("repasswd", pwd);
                DataRequest.instance().request(this, Urls.getUserForgotUrl(), this, HttpRequest.POST, USER_FORGOT, valuePairs,
                        new ResultHandler());
       //     }

        }
        else if (v == llQq)
        {
            if (!TextUtils.isEmpty(ConfigManager.instance().getSystemQq()))
            {
                // 跳转之前，可以先判断手机是否安装QQ
                if (isQQClientAvailable(this))
                {
                    // 跳转到客服的QQ
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + ConfigManager.instance().getSystemQq();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
                    if (isValidIntent(this, intent))
                    {
                        startActivity(intent);
                    }
                }
                else
                {
                    ToastUtil.show(RecoveryPwdActivity.this,"请先安装QQ");
                }
            }
        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (GET_EMAIL_CODE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_EMAIL_CODE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (USER_FORGOT.equals(action))
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


    public boolean isQQClientAvailable(Context context)
    {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null)
        {
            for (int i = 0; i < pinfo.size(); i++)
            {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq"))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 Uri是否有效
     */
    public boolean isValidIntent(Context context, Intent intent)
    {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mHandler.hasMessages(UPDATE_CODE_VIEW))
            mHandler.removeMessages(UPDATE_CODE_VIEW);
    }
}
