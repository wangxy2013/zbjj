package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.MessageInfoListHandler;
import com.zb.wyd.json.ResultHandler;
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
public class QuestionActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView  tvTitle;
    @BindView(R.id.tv_submit)
    TextView  tvSubmit;
    @BindView(R.id.et_content)
    EditText  etContent;


    private static final String POST_QUESTION = "post_question";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL    = 0x02;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(QuestionActivity.this, "问题反馈成功");
                    finish();
                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(QuestionActivity.this, msg.obj.toString());

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
        setContentView(R.layout.activity_question);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.redC));
        StatusBarUtil.StatusBarLightMode(QuestionActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("问题反馈");
        tvSubmit.setText("提交问题");
        tvSubmit.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if(v == ivBack)
        {
            finish();
        }
        else if(v == tvSubmit)
        {
            String content = etContent.getText().toString();
            if(TextUtils.isEmpty(content))
            {
                ToastUtil.show(this,"请输入反馈的问题");
                return;
            }

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("content", content);
            DataRequest.instance().request(this, Urls.getMessageReportUrl(), this, HttpRequest.POST, POST_QUESTION, valuePairs,
                    new ResultHandler());

        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (POST_QUESTION.equals(action))
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
}
