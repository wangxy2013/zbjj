package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.IntegerAreaAdapter;
import com.zb.wyd.adapter.MessageAdapter;
import com.zb.wyd.entity.MessageInfo;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.VideoInfoListHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.FullyGridLayoutManager;
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
public class VidoeListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<RecyclerView>, View.OnClickListener, IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView                 mBackIv;
    @BindView(R.id.tv_title)
    TextView                  tvTitle;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private RecyclerView mRecyclerView; //

    private IntegerAreaAdapter mAdapter;
    private List<VideoInfo> mVideoInfoList = new ArrayList<>();

    private int pn = 1;
    private int    mRefreshStatus;
    private String sort, cta_id;
    private static final String GET_VIDEO_LIST = "get_video_list";

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
                    VideoInfoListHandler mVideoInfoListHandler = (VideoInfoListHandler) msg.obj;
                    mVideoInfoList.addAll(mVideoInfoListHandler.getVideoInfoList());
                    mAdapter.notifyDataSetChanged();
                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(VidoeListActivity.this, msg.obj.toString());

                    break;


            }
        }
    };

    @Override
    protected void initData()
    {
        sort = getIntent().getStringExtra("sort");
        cta_id = getIntent().getStringExtra("cta_id");
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_video_list);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(VidoeListActivity.this, false);
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
        tvTitle.setText("视频列表");
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(VidoeListActivity.this, 2));

        mAdapter = new IntegerAreaAdapter(mVideoInfoList, VidoeListActivity.this, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Bundle b = new Bundle();
                b.putSerializable("VideoInfo", mVideoInfoList.get(position));
                startActivity(new Intent(VidoeListActivity.this, VideoPlayActivity.class).putExtras(b));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        getVideoList();

    }


    private void getVideoList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("sort", sort);
        valuePairs.put("pn", pn + "");
        valuePairs.put("num", "20");
        valuePairs.put("cta_id", cta_id);
        DataRequest.instance().request(VidoeListActivity.this, Urls.getVideoListUrl(), this, HttpRequest.GET, GET_VIDEO_LIST, valuePairs,
                new VideoInfoListHandler());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        mVideoInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        getVideoList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn += 1;
        mRefreshStatus = 1;
        getVideoList();
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

        if (GET_VIDEO_LIST.equals(action))
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
