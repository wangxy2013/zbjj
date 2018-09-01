package com.zb.wyd.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommissionRecordFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<RecyclerView>, IRequestListener
{
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private View rootView = null;
    private Unbinder unbinder;
    private RecyclerView mRecyclerView; //

    private WealthAdapter mWealthAdapter;
    private List<WealthInfo> mWealthInfoList = new ArrayList<>();

    private int pn = 1;
    private int mRefreshStatus;

    private static final String GET_WEALTH_LIST = "get_wealth_list";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(getActivity())
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
                    ToastUtil.show(getActivity(), msg.obj.toString());

                    break;


            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_integral_record, null);
            unbinder = ButterKnife.bind(this, rootView);
            initData();
            initViews();
            initViewData();
            initEvent();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews()
    {

    }

    @Override
    protected void initEvent()
    {
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
    }

    @Override
    protected void initViewData()
    {
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mWealthAdapter = new WealthAdapter(mWealthInfoList);
        mRecyclerView.setAdapter(mWealthAdapter);
       // getWealthList();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != unbinder)
        {
            unbinder.unbind();
            unbinder = null;
        }

    }

    private void getWealthList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pn", pn + "");
        valuePairs.put("num", "15");
        DataRequest.instance().request(getActivity(), Urls.getFortuneDetailUrl(), this, HttpRequest.GET, GET_WEALTH_LIST, valuePairs, new WealthInfoListHandler());
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

}
