package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.adapter.AnchorAdapter;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LiveInfoListHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.widget.list.refresh.PullToRefreshBase;
import com.zb.wyd.widget.list.refresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：主播列表
 */
public class AnchorListFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<RecyclerView>, IRequestListener, View.OnClickListener
{
    @BindView(R.id.tv_recommend)
    TextView                  tvRecommend;
    @BindView(R.id.tv_new)
    TextView                  tvNew;
    @BindView(R.id.tv_casual)
    TextView                  tvCasual;
    @BindView(R.id.iv_new)
    ImageView                 ivNew;
    @BindView(R.id.refreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;


    private List<TextView> tabList = new ArrayList<>();


    private RecyclerView mRecyclerView;
    private int pn = 1;
    private int mRefreshStatus;

    private List<LiveInfo> userInfoList = new ArrayList<>();
    private AnchorAdapter mAnchorAdapter;
    private View rootView = null;
    private Unbinder unbinder;
    private static final String GET_ANCHOR_LIST = "get_anchor_list";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL    = 0x02;

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
                    LiveInfoListHandler mOrderListHandler = (LiveInfoListHandler) msg.obj;

                    if (pn == 1)
                    {
                        userInfoList.clear();
                    }

                    userInfoList.addAll(mOrderListHandler.getUserInfoList());
                    mAnchorAdapter.notifyDataSetChanged();

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
            rootView = inflater.inflate(R.layout.fragment_anchor_list, null);
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


    private static AnchorListFragment instance = null;

    public static AnchorListFragment newInstance()
    {
        if (instance == null)
        {
            instance = new AnchorListFragment();
        }
        return instance;
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
        tvRecommend.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvCasual.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        for (int i = 0; i < 30; i++)
        {
            userInfoList.add(new LiveInfo());
        }


        tabList.add(tvRecommend);
        tabList.add(tvNew);
        tabList.add(tvCasual);
        setTabSelected(0);
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAnchorAdapter = new AnchorAdapter(userInfoList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {


            }
        });
        mRecyclerView.setAdapter(mAnchorAdapter);
        loadData();
    }


    private void loadData()
    {

    }


    private void setTabSelected(int p)
    {
        for (int i = 0; i < tabList.size(); i++)
        {
            if (i == p)
            {
                tabList.get(i).setSelected(true);
            }
            else
            {
                tabList.get(i).setSelected(false);
            }
        }
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

        if (GET_ANCHOR_LIST.equals(action))
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
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        userInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn = +1;
        mRefreshStatus = 1;
        loadData();
    }

    @Override
    public void onClick(View v)
    {
        if (v == tvRecommend)
        {
            setTabSelected(0);
        }
        else if (v == tvNew)
        {
            setTabSelected(1);
        }
        else if (v == tvCasual)
        {
            setTabSelected(2);
        }
    }
}
