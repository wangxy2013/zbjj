package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LoginHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：一句话简单描述
 */
public class BindEmailActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView  tvTitle;
    @BindView(R.id.et_email)
    EditText  etEmail;
    @BindView(R.id.tv_code)
    TextView  tvCode;
    @BindView(R.id.et_code)
    EditText  etCode;
    @BindView(R.id.btn_submit)
    Button    btnSubmit;

    private int time = 60;
    private int    count;
    private String token;

    private static final String BIND_EMAIL             = "bind_email";
    private static final String GET_EMAIL_CODE         = "get_email_code";
    private static final int    REQUEST_SUCCESS        = 0x01;
    private static final int    REQUEST_FAIL           = 0x02;
    private static final int    UPDATE_CODE_VIEW       = 0X03;
    private static final int    GET_EMAIL_CODE_SUCCESS = 0X04;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(BindEmailActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(BindEmailActivity.this, "认证成功");

                    if (mHandler.hasMessages(UPDATE_CODE_VIEW))
                        mHandler.removeMessages(UPDATE_CODE_VIEW);
                    finish();
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(BindEmailActivity.this, msg.obj.toString());
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
                    ToastUtil.show(BindEmailActivity.this, "验证码已发送至邮箱");
                    ResultHandler resultHandler = (ResultHandler) msg.obj;
                    token = resultHandler.getContent();
                    etEmail.setEnabled(false);
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
        setContentView(R.layout.activity_bind_email);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(BindEmailActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        btnSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvCode.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("认证邮箱");
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == btnSubmit)
        {
            String email = etEmail.getText().toString();
            String code = etCode.getText().toString();

            if (!StringUtils.checkEmail(email))
            {
                ToastUtil.show(this, "请输入正确的邮箱");
                return;
            }
            if (StringUtils.stringIsEmpty(code))
            {
                ToastUtil.show(this, "请输入验证码");
                return;
            }

            showProgressDialog();
            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("email", email);
            valuePairs.put("vcode", code);
            valuePairs.put("token", token);
            DataRequest.instance().request(this, Urls.getTaskprofileUrl(), this, HttpRequest.POST, BIND_EMAIL, valuePairs,
                    new ResultHandler());
        }
        else if (v == tvCode)
        {
            String email = etEmail.getText().toString();

            if (!StringUtils.checkEmail(email))
            {
                ToastUtil.show(this, "请输入正确的邮箱");
                return;
            }

            tvCode.setEnabled(false);

            showProgressDialog();
            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("email", email);
            valuePairs.put("action", "email");
            DataRequest.instance().request(this, Urls.getEmailCodeUrl(), this, HttpRequest.POST, GET_EMAIL_CODE, valuePairs,
                    new ResultHandler());

        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (BIND_EMAIL.equals(action))
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mHandler.hasMessages(UPDATE_CODE_VIEW))
            mHandler.removeMessages(UPDATE_CODE_VIEW);
    }
}
