package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：一句话简单描述
 */
public class PhoneRegisterActivity extends BaseActivity implements IRequestListener
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
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.et_code)
    EditText    etCode;
    @BindView(R.id.tv_code)
    TextView     tvCode;

    @BindView(R.id.iv_code)
    ImageView ivCode;
    private String account;
    private String pwd;

    private String token;
    private int time = 60;
    private int    count;
    private static final String      USER_REGISTER   = "user_register";
    private static final String GET_EMAIL_CODE         = "get_email_code";
    private static final int         REQUEST_SUCCESS = 0x01;
    private static final int         REQUEST_FAIL    = 0x02;
    private static final int    UPDATE_CODE_VIEW       = 0X03;
    private static final int    GET_EMAIL_CODE_SUCCESS = 0X04;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler        = new BaseHandler(PhoneRegisterActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(PhoneRegisterActivity.this, "注册成功");
                    ConfigManager.instance().setUserName(account);
                    ConfigManager.instance().setUserPwd(pwd);
                    finish();
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(PhoneRegisterActivity.this, msg.obj.toString());
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
                        etAccount.setEnabled(true);
                        tvCode.setEnabled(true);
                        tvCode.setText("发送验证码");
                    }

                    break;

                case GET_EMAIL_CODE_SUCCESS:
                    ToastUtil.show(PhoneRegisterActivity.this, "验证码已发送");
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
        setContentView(R.layout.activity_phone_register);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this, false);
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

        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                ivCode.setSelected(hasFocus);
            }
        });
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvCode.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {

        tvEmail.setText("防丢邮箱,发邮件到"+ConfigManager.instance().getSystemEmail()+"获取最新地址");
        ImageLoader.getInstance().displayImage(ConfigManager.instance().getBgLogin(), ivBg);
        boolean reg_closed = ConfigManager.instance().getRegClosed();
        if (reg_closed)
        {
            etInvitation.setHint("邀请码(必填)");
        }
        else
        {
            etInvitation.setHint("邀请码(选填)");
        }
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
        else if (v == tvCode)
        {
            String phone = etAccount.getText().toString();

            if (TextUtils.isEmpty(phone)||phone.length()<11)
            {
                ToastUtil.show(this, "请输入正确的手机");
                return;
            }

            tvCode.setEnabled(false);
            showProgressDialog();
            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("phone", phone);
            valuePairs.put("action", "bind");
            DataRequest.instance().request(this, Urls.getPhoneCodeUrl(), this, HttpRequest.POST, GET_EMAIL_CODE, valuePairs,
                    new ResultHandler());
        }
        else if (v == btnRegister)
        {
            account = etAccount.getText().toString();
            pwd = etPwd.getText().toString();


            if (TextUtils.isEmpty(account)||account.length()<11)
            {
                ToastUtil.show(this, "请输入正确的手机");
                return;
            }


            if (TextUtils.isEmpty(pwd) || pwd.length() < 6)
            {
                ToastUtil.show(PhoneRegisterActivity.this, "请输入6-18位密码");
                return;
            }

            String scode = etCode.getText().toString();

            if (TextUtils.isEmpty(scode))
            {
                ToastUtil.show(this, "请输入验证码");
                return;

            }

            String invite = etInvitation.getText().toString();
            boolean reg_closed = ConfigManager.instance().getRegClosed();

            if (reg_closed)
            {
                if (TextUtils.isEmpty(invite))
                {
                    ToastUtil.show(PhoneRegisterActivity.this, "请输入邀请码");
                    return;
                }
            }

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("user_name", account);
            valuePairs.put("password", pwd);
            valuePairs.put("password", pwd);
            valuePairs.put("repassword", pwd);
            valuePairs.put("token", token);
            valuePairs.put("scode", scode);
            valuePairs.put("invite", invite);
            DataRequest.instance().request(PhoneRegisterActivity.this, Urls.getRegisterUrl(), this, HttpRequest.POST, USER_REGISTER, valuePairs,
                    new ResultHandler());
        }


    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (USER_REGISTER.equals(action))
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
        else if (GET_EMAIL_CODE.equals(action))
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
    }

}
