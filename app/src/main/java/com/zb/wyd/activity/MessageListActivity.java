package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MessageAdapter;
import com.zb.wyd.entity.MessageInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.MessageInfoListHandler;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.DividerDecoration;
import com.zb.wyd.widget.list.refresh.PullToRefreshBase;
import com.zb.wyd.widget.list.refresh.PullToRefreshRecyclerView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class MessageListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<RecyclerView>, View.OnClickListener, IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView                 mBackIv;
    @BindView(R.id.tv_title)
    TextView                  tvTitle;
    @BindView(R.id.tv_submit)
    TextView                  tvSubmit;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private RecyclerView mRecyclerView; //

    private MessageAdapter mMessageAdapter;
    private List<MessageInfo> mMessageInfoList = new ArrayList<>();

    private int pn = 1;
    private int mRefreshStatus;

    private static final String GET_MESSAGE_LIST = "get_message_list";

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
                    MessageInfoListHandler mMessageInfoListHandler = (MessageInfoListHandler) msg.obj;
                    mMessageInfoList.addAll(mMessageInfoListHandler.getMessageInfoList());
                    mMessageAdapter.notifyDataSetChanged();
                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(MessageListActivity.this, msg.obj.toString());

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
        setContentView(R.layout.activity_message);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.redC));
        StatusBarUtil.StatusBarLightMode(MessageListActivity.this, false);
    }


    @Override
    protected void initEvent()
    {
        mBackIv.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("消息中心");
        tvSubmit.setText("+");
        tvSubmit.setTextSize(22);
        tvSubmit.setVisibility(View.VISIBLE);
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerDecoration(this));
        mMessageAdapter = new MessageAdapter(mMessageInfoList);
        mRecyclerView.setAdapter(mMessageAdapter);
        getMessageList();

    }


    private void getMessageList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pn", pn + "");
        valuePairs.put("num", "15");
        DataRequest.instance().request(MessageListActivity.this, Urls.getMessageUrl(), this, HttpRequest.GET, GET_MESSAGE_LIST, valuePairs,
                new MessageInfoListHandler());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        mMessageInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        getMessageList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn += 1;
        mRefreshStatus = 1;
        getMessageList();
    }


    @Override
    public void onClick(View v)
    {
        if (v == mBackIv)
        {
            finish();
        }
        else if (v == tvSubmit)
        {
            startActivity(new Intent(MessageListActivity.this, QuestionActivity.class));
        }

    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (mRefreshStatus == 1)
        {
            mPullToRefreshRecyclerView.onPullUpRefreshComplete();
        }
        else
        {
            mPullToRefreshRecyclerView.onPullDownRefreshComplete();
        }

        if (GET_MESSAGE_LIST.equals(action))
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
