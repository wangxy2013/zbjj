package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.activity.LiveActivity;
import com.zb.wyd.activity.VideoPlayActivity;
import com.zb.wyd.adapter.AnchorAdapter;
import com.zb.wyd.adapter.CollectionVideoAdapter;
import com.zb.wyd.adapter.IntegerAreaAdapter;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LiveInfoListHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.json.VideoInfoListHandler;
import com.zb.wyd.listener.MyItemClickListener;
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

/**
 * 描述：收藏视频
 */
public class VideoCollectionFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<RecyclerView>, IRequestListener, View
        .OnClickListener
{
    @BindView(R.id.refreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private int pn = 1;
    private int mRefreshStatus;


    private CollectionVideoAdapter mAdapter;
    private List<VideoInfo> mVideoInfoList = new ArrayList<>();
    private View rootView = null;
    private Unbinder unbinder;
    private static final String GET_ANCHOR_LIST = "get_anchor_list";
    private static final String UN_FAVORITE_LIKE = "un_favorite_like";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    private static final int UN_FAVORITE_LIKE_SUCCESS = 0x03;
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
                    VideoInfoListHandler mVideoInfoListHandler = (VideoInfoListHandler) msg.obj;
                    mVideoInfoList.addAll(mVideoInfoListHandler.getVideoInfoList());
                    mAdapter.notifyDataSetChanged();

                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(getActivity(), msg.obj.toString());

                    break;

                case UN_FAVORITE_LIKE_SUCCESS:
                    ToastUtil.show(getActivity(), "操作成功");
                    mVideoInfoList.clear();
                    loadData();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_collect_video, null);
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
    }

    @Override
    protected void initViewData()
    {
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAdapter = new CollectionVideoAdapter(mVideoInfoList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Bundle b = new Bundle();
                b.putSerializable("VideoInfo", mVideoInfoList.get(position));
                startActivity(new Intent(getActivity(), VideoPlayActivity.class).putExtras(b));
            }
        }, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                unFavoriteLike(mVideoInfoList.get(position).getId());
            }
        }); mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onResume()
    {
        super.onResume();
        mVideoInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        loadData();
    }

    private void unFavoriteLike(String biz_id)
    {
        showProgressDialog(getActivity());
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "video");
        DataRequest.instance().request(getActivity(), Urls.getFavoriteUnLikeUrl(), this, HttpRequest.POST, UN_FAVORITE_LIKE, valuePairs, new
                ResultHandler());
    }

    private void loadData()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pn", pn + "");
        valuePairs.put("num", "20");
        valuePairs.put("co_biz", "video");
        DataRequest.instance().request(getActivity(), Urls.getFavoritUrl(), this, HttpRequest.GET, GET_ANCHOR_LIST, valuePairs, new
                VideoInfoListHandler());
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
        else if (UN_FAVORITE_LIKE.equals(action))
        {
            hideProgressDialog(getActivity());
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(UN_FAVORITE_LIKE_SUCCESS, obj));
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
        mVideoInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn = pn + 1;
        mRefreshStatus = 1;
        loadData();
    }

    @Override
    public void onClick(View v)
    {
    }
}
