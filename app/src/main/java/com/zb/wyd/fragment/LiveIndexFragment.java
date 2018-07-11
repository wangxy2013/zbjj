package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.donkingliang.banner.CustomBanner;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.MyApplication;
import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.activity.LiveActivity;
import com.zb.wyd.activity.LoginActivity;
import com.zb.wyd.activity.PhotoDetailActivity;
import com.zb.wyd.activity.VideoPlayActivity;
import com.zb.wyd.activity.VidoeListActivity;
import com.zb.wyd.adapter.NewAdapter;
import com.zb.wyd.adapter.RecommendAdapter;
import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.AdInfoListHandler;
import com.zb.wyd.json.LiveInfoListHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.FullyGridLayoutManager;
import com.zb.wyd.widget.MaxRecyclerView;
import com.zb.wyd.widget.VerticalSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：一句话简单描述
 */
public class LiveIndexFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IRequestListener, View.OnClickListener
{

    @BindView(R.id.banner)
    CustomBanner               mBanner;
    @BindView(R.id.rl_all_recommend)
    RelativeLayout             rlAllRecommend;
    @BindView(R.id.rv_recommend)
    MaxRecyclerView            rvRecommend;
    @BindView(R.id.rl_all_new)
    RelativeLayout             rlAllHotLayout;
    @BindView(R.id.rv_new)
    MaxRecyclerView            rvHot;
    @BindView(R.id.swipeRefresh)
    VerticalSwipeRefreshLayout mSwipeRefreshLayout;
    private View rootView = null;
    private Unbinder unbinder;
    private List<String>   picList      = new ArrayList<>();
    private List<LiveInfo> freeLiveList = new ArrayList<>();
    private List<LiveInfo> newLiveList  = new ArrayList<>();
    private List<AdInfo>   adInfoList   = new ArrayList<>();
    private RecommendAdapter mRecommendAdapter;

    private NewAdapter mNewAdapter;


    private static final String GET_FREE_LIVE         = "get_free_live";
    private static final String GET_NEW_LIVE          = "get_new_live";
    private static final String GET_AD_LIST           = "get_ad_list";
    private static final int    GET_FREE_LIVE_SUCCESS = 0x01;
    private static final int    REQUEST_FAIL          = 0x02;
    private static final int    GET_NEW_LIVE_SUCCESS  = 0x03;

    private static final int GET_AD_LIST_CODE   = 0X10;
    private static final int GET_FREE_LIVE_CODE = 0X11;
    private static final int GET_NEW_LIVE_CODE  = 0X12;

    private static final int GET_AD_LIST_SUCCESS = 0x04;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(getActivity())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case GET_FREE_LIVE_SUCCESS:

                    LiveInfoListHandler mLiveInfoListHandler = (LiveInfoListHandler) msg.obj;
                    freeLiveList.clear();
                    freeLiveList.addAll(mLiveInfoListHandler.getUserInfoList());
                    mRecommendAdapter.notifyDataSetChanged();
                    break;


                case GET_NEW_LIVE_SUCCESS:
                    LiveInfoListHandler mLiveInfoListHandler1 = (LiveInfoListHandler) msg.obj;
                    newLiveList.clear();
                    newLiveList.addAll(mLiveInfoListHandler1.getUserInfoList());
                    mNewAdapter.notifyDataSetChanged();
                    break;

                case REQUEST_FAIL:
                    break;

                case GET_FREE_LIVE_CODE:
                    getFreeLive();
                    break;

                case GET_NEW_LIVE_CODE:
                    getNewLive();
                    break;

                case GET_AD_LIST_SUCCESS:
                    AdInfoListHandler mAdInfoListHandler = (AdInfoListHandler) msg.obj;
                    adInfoList.clear();
                    adInfoList.addAll(mAdInfoListHandler.getAdInfoList());

                    picList.clear();

                    for (int i = 0; i < adInfoList.size(); i++)
                    {
                        picList.add(adInfoList.get(i).getImage());
                    }

                    if (!picList.isEmpty())
                    {
                        initAd();
                    }
                    break;

                case GET_AD_LIST_CODE:
                    getAdList();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_live_index, null);
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

    private static LiveIndexFragment instance = null;

    public static LiveIndexFragment newInstance()
    {
        if (instance == null)
        {
            instance = new LiveIndexFragment();
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
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rlAllHotLayout.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        mRecommendAdapter = new RecommendAdapter(freeLiveList, getContext(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if (MyApplication.getInstance().isLogin())
                {
                    LiveInfo mLiveInfo = freeLiveList.get(position);
                    //                    Bundle b = new Bundle();
                    //                    b.putSerializable("LiveInfo", mLiveInfo);
                    startActivity(new Intent(getActivity(), LiveActivity.class).putExtra("biz_id", mLiveInfo.getId()));

                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        rvRecommend.setLayoutManager(new FullyGridLayoutManager(getActivity(), 3));
        rvRecommend.setAdapter(mRecommendAdapter);


        mNewAdapter = new NewAdapter(newLiveList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if (MyApplication.getInstance().isLogin())
                {
                    LiveInfo mLiveInfo = newLiveList.get(position);

                    //                    Bundle b = new Bundle();
                    //                    b.putSerializable("LiveInfo", mLiveInfo);
                    //                    startActivity(new Intent(getActivity(), LiveActivity.class).putExtras(b));
                    startActivity(new Intent(getActivity(), LiveActivity.class).putExtra("biz_id", mLiveInfo.getId()));

                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        rvHot.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        rvHot.setAdapter(mNewAdapter);
        mHandler.sendEmptyMessage(GET_AD_LIST_CODE);
        loadData();
    }

    private void loadData()
    {
        mHandler.sendEmptyMessage(GET_FREE_LIVE_CODE);
        mHandler.sendEmptyMessage(GET_NEW_LIVE_CODE);
    }

    private void getAdList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pos_id", "1");
        DataRequest.instance().request(getActivity(), Urls.getAdListUrl(), this, HttpRequest.GET, GET_AD_LIST, valuePairs,
                new AdInfoListHandler());
    }

    private void getFreeLive()
    {
        Map<String, String> valuePairs = new HashMap<>();
        DataRequest.instance().request(getActivity(), Urls.getFreeLive(), this, HttpRequest.GET, GET_FREE_LIVE, valuePairs,
                new LiveInfoListHandler());
    }

    private void getNewLive()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pn", "1");
        valuePairs.put("num", "20");
        valuePairs.put("sort", "hot");
        DataRequest.instance().request(getActivity(), Urls.getNewLive(), this, HttpRequest.GET, GET_NEW_LIVE, valuePairs,
                new LiveInfoListHandler());
    }


    private void initAd()
    {
        int width = APPUtils.getScreenWidth(getActivity());
        int height = (int) (width * 0.4);
        mBanner.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        mBanner.setVisibility(View.VISIBLE);
        mBanner.setPages(new CustomBanner.ViewCreator<String>()
        {
            @Override
            public View createView(Context context, int position)
            {
                //这里返回的是轮播图的项的布局 支持任何的布局
                //position 轮播图的第几个项
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String data)
            {
                //在这里更新轮播图的UI
                //position 轮播图的第几个项
                //view 轮播图当前项的布局 它是createView方法的返回值
                //data 轮播图当前项对应的数据
                //   Glide.with(context).load(data).into((ImageView) view);
                ImageLoader.getInstance().displayImage(picList.get(position), (ImageView) view);
            }
        }, picList);


        //设置指示器类型，有普通指示器(ORDINARY)、数字指示器(NUMBER)和没有指示器(NONE)三种类型。
        //这个方法跟在布局中设置app:indicatorStyle是一样的
        mBanner.setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY);

        //设置两个点图片作为翻页指示器，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorSelectRes、app:indicatorUnSelectRes是一样的。
        //第一个参数是指示器的选中的样式，第二个参数是指示器的未选中的样式。
        // mBanner.setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect);

        //设置指示器的指示点间隔，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorInterval是一样的。
        mBanner.setIndicatorInterval(20);

        //设置指示器的方向。
        //这个方法跟在布局中设置app:indicatorGravity是一样的。
        //        mBanner.setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL);
        //设置轮播图自动滚动轮播，参数是轮播图滚动的间隔时间
        //轮播图默认是不自动滚动的，如果不调用这个方法，轮播图将不会自动滚动。
        mBanner.startTurning(3600);

        //停止轮播图的自动滚动
        // mBanner.stopTurning();

        //设置轮播图的滚动速度
        mBanner.setScrollDuration(500);

        //设置轮播图的点击事件
        mBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener<String>()
        {
            @Override
            public void onPageClick(int position, String str)
            {
                //position 轮播图的第几个项
                //str 轮播图当前项对应的数据
                AdInfo mAdInfo = adInfoList.get(position);
                if (!TextUtils.isEmpty(mAdInfo.getLink()))
                {
                    if (mAdInfo.getLink().startsWith("video://"))
                    {
                        String id = mAdInfo.getLink().replace("video://", "");
                        VideoInfo mVideoInfo = new VideoInfo();
                        mVideoInfo.setId(id);
                        mVideoInfo.setV_name("点播");
                        Bundle b = new Bundle();
                        b.putSerializable("VideoInfo",mVideoInfo);
                        startActivity(new Intent(getActivity(), VideoPlayActivity.class).putExtras(b));
                    }
                    else if (mAdInfo.getLink().startsWith("live://"))
                    {
                        String id = mAdInfo.getLink().replace("live://", "");
                        startActivity(new Intent(getActivity(), LiveActivity.class).putExtra("biz_id",id));
                    }
                    else if (mAdInfo.getLink().startsWith("photo://"))
                    {
                        String id = mAdInfo.getLink().replace("photo://", "");
                        startActivity(new Intent(getActivity(), PhotoDetailActivity.class).putExtra("biz_id", id));
                    }
                }
            }
        });

    }

    @Override
    public void onRefresh()
    {
        if (mSwipeRefreshLayout != null)
        {
            loadData();
            mSwipeRefreshLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
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
        if (GET_FREE_LIVE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_FREE_LIVE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_NEW_LIVE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_NEW_LIVE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_AD_LIST.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_AD_LIST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == rlAllHotLayout)
        {
            ((LiveFragment) getParentFragment()).setTabIndex(1);
        }
    }
}
