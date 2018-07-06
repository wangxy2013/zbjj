package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.donkingliang.banner.CustomBanner;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.activity.VideoPlayActivity;
import com.zb.wyd.adapter.FreeTimeAdapter;
import com.zb.wyd.adapter.IntegerAreaAdapter;
import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.AdInfoListHandler;
import com.zb.wyd.json.LiveInfoListHandler;
import com.zb.wyd.json.VideoInfoListHandler;
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
public class VideoChildFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IRequestListener
{

    @BindView(R.id.banner)
    CustomBanner               mBanner;
    @BindView(R.id.rl_all_free)
    RelativeLayout             rlAllFree;
    @BindView(R.id.rv_free_time)
    MaxRecyclerView            rvFreeTime;
    @BindView(R.id.rl_all_integer)
    RelativeLayout             rlAllInteger;
    @BindView(R.id.rv_integer_area)
    MaxRecyclerView            rvIntegerArea;
    @BindView(R.id.swipeRefresh)
    VerticalSwipeRefreshLayout mSwipeRefreshLayout;
    private View rootView = null;
    private Unbinder unbinder;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    private List<String>    picList      = new ArrayList<>();
    private List<VideoInfo> newVideoList = new ArrayList<>();
    private List<VideoInfo> favVideoList = new ArrayList<>();
    private List<AdInfo>    adInfoList   = new ArrayList<>();


    private IntegerAreaAdapter mNewVideoAdapter;
    private IntegerAreaAdapter mFavVideoAdapter;


    private static final String GET_NEW_VIDEO          = "get_new_video";
    private static final String GET_FAV_VIDEO          = "get_fav_video";
    private static final String GET_AD_LIST            = "get_ad_list";
    private static final int    GET_NEW_LIVE_SUCCESS   = 0x01;
    private static final int    REQUEST_FAIL           = 0x02;
    private static final int    GET_VIDEO_LIST_SUCCESS = 0x03;
    private static final int    GET_AD_LIST_SUCCESS    = 0x04;

    private static final int         GET_NEW_VIDEO_CODE = 0X11;
    private static final int         GET_FAV_VIDEO_CODE = 0X12;
    private static final int         GET_AD_lIST_CODE   = 0X13;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler           = new BaseHandler(getActivity())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case GET_NEW_LIVE_SUCCESS:
                    VideoInfoListHandler mVideoInfoListHandler = (VideoInfoListHandler) msg.obj;
                    newVideoList.clear();
                    newVideoList.addAll(mVideoInfoListHandler.getVideoInfoList());
                    mNewVideoAdapter.notifyDataSetChanged();
                    break;


                case GET_VIDEO_LIST_SUCCESS:
                    VideoInfoListHandler mVideoInfoListHandler1 = (VideoInfoListHandler) msg.obj;
                    favVideoList.clear();
                    favVideoList.addAll(mVideoInfoListHandler1.getVideoInfoList());
                    mFavVideoAdapter.notifyDataSetChanged();


                    break;

                case REQUEST_FAIL:
                    break;

                case GET_NEW_VIDEO_CODE:
                    getNewLive();
                    break;

                case GET_FAV_VIDEO_CODE:
                    getVideoList();
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
                    initAd();
                    break;
                case GET_AD_lIST_CODE:
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
            rootView = inflater.inflate(R.layout.fragment_video_child, null);
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

    /**
     * 37      * fragment静态传值
     * 38
     */
    public static VideoChildFragment newInstance(String str)
    {
        VideoChildFragment fragment = new VideoChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        fragment.setArguments(bundle);

        return fragment;
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
    }

    @Override
    protected void initViewData()
    {

        mNewVideoAdapter = new IntegerAreaAdapter(newVideoList, getContext(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Bundle b = new Bundle();
                b.putSerializable("VideoInfo",newVideoList.get(position));
                startActivity(new Intent(getActivity(), VideoPlayActivity.class).putExtras(b));
            }
        });

        rvFreeTime.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        // rvRecommend.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvFreeTime.setAdapter(mNewVideoAdapter);


        mFavVideoAdapter = new IntegerAreaAdapter(favVideoList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Bundle b = new Bundle();
                b.putSerializable("VideoInfo",favVideoList.get(position));
                startActivity(new Intent(getActivity(), VideoPlayActivity.class).putExtras(b));
            }
        });
        rvIntegerArea.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        rvIntegerArea.setAdapter(mFavVideoAdapter);
        isViewCreated = true;
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser)
        {
            isUIVisible = true;
            loadData();
        }
        else
        {
            isUIVisible = false;
        }
    }

    private void loadData()
    {
        if (isViewCreated && isUIVisible)
        {
            mHandler.sendEmptyMessage(GET_NEW_VIDEO_CODE);
            mHandler.sendEmptyMessage(GET_FAV_VIDEO_CODE);
            mHandler.sendEmptyMessage(GET_AD_lIST_CODE);
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    private void getNewLive()
    {
        Map<String, String> valuePairs = new HashMap<>();

        valuePairs.put("sort", "new");
        valuePairs.put("pn", "1");
        valuePairs.put("cta_id", "0");
        DataRequest.instance().request(getActivity(), Urls.getVideoListUrl(), this, HttpRequest.POST, GET_NEW_VIDEO, valuePairs,
                new VideoInfoListHandler());
    }

    private void getVideoList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("sort", "fav");
        valuePairs.put("pn", "1");
        valuePairs.put("cta_id", "0");
        DataRequest.instance().request(getActivity(), Urls.getVideoListUrl(), this, HttpRequest.POST, GET_FAV_VIDEO, valuePairs,
                new VideoInfoListHandler());
    }

    private void getAdList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pos_id", "1");
        DataRequest.instance().request(getActivity(), Urls.getAdListUrl(), this, HttpRequest.POST, GET_AD_LIST, valuePairs,
                new AdInfoListHandler());
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
            }
        });

    }

    @Override
    public void onRefresh()
    {
        if (mSwipeRefreshLayout != null)
        {
            isViewCreated = true;
            isUIVisible = true;
            loadData();
            mSwipeRefreshLayout.setRefreshing(false);
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
    public void onDestroyView()
    {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;

    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (GET_FAV_VIDEO.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_VIDEO_LIST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_NEW_VIDEO.equals(action))
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
}
