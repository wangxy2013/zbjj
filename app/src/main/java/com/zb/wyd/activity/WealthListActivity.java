package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.WealthAdapter;
import com.zb.wyd.entity.WealthInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.WealthInfoListHandler;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
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
public class WealthListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<RecyclerView>, View.OnClickListener, IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView                 mBackIv;
    @BindView(R.id.tv_title)
    TextView                  tvTitle;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.tv_fortune)
    TextView                  tvFortune;
    private RecyclerView mRecyclerView; //

    private WealthAdapter mWealthAdapter;
    private List<WealthInfo> mWealthInfoList = new ArrayList<>();

    private int pn = 1;
    private int mRefreshStatus;

    private static final String GET_WEALTH_LIST = "get_wealth_list";

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
                    WealthInfoListHandler mWealthInfoListHandler = (WealthInfoListHandler) msg.obj;
                    mWealthInfoList.addAll(mWealthInfoListHandler.getWealthInfoList());
                    mWealthAdapter.notifyDataSetChanged();
                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(WealthListActivity.this, msg.obj.toString());

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
        setContentView(R.layout.activity_my_wealth);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(WealthListActivity.this, false);
    }


    @Override
    protected void initEvent()
    {
        mBackIv.setOnClickListener(this);
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("我的财富");
        tvFortune.setText(getIntent().getStringExtra("fortune"));

        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mWealthAdapter = new WealthAdapter(mWealthInfoList);
        mRecyclerView.setAdapter(mWealthAdapter);
        getWealthList();

    }


    private void getWealthList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pn", pn + "");
        valuePairs.put("num", "15");
        DataRequest.instance().request(WealthListActivity.this, Urls.getFortuneDetailUrl(), this, HttpRequest.GET, GET_WEALTH_LIST, valuePairs,
                new WealthInfoListHandler());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        mWealthInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        getWealthList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn += 1;
        mRefreshStatus = 1;
        getWealthList();
    }


    @Override
    public void onClick(View v)
    {
        if (v == mBackIv)
        {
            finish();
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

        if (GET_WEALTH_LIST.equals(action))
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
