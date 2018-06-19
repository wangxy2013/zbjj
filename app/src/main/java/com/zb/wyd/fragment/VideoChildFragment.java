package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
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
import com.zb.wyd.adapter.FreeTimeAdapter;
import com.zb.wyd.adapter.IntegerAreaAdapter;
import com.zb.wyd.entity.VideoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
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

    private List<String>    picList         = new ArrayList<>();
    private List<VideoInfo> freeTimeList    = new ArrayList<>();
    private List<VideoInfo> integerAreaList = new ArrayList<>();
    private FreeTimeAdapter    mFreeTimeAdapter;
    private IntegerAreaAdapter mIntegerAreaAdapter;


    private static final String      GET_FREE_LIVE          = "get_free_live";
    private static final String      GET_VIDEO_LIST         = "get_video_list";
    private static final int         GET_FREE_LIVE_SUCCESS  = 0x01;
    private static final int         REQUEST_FAIL           = 0x02;
    private static final int         GET_VIDEO_LIST_SUCCESS = 0x03;
    private static final int         GET_FREE_LIVE_CODE     = 0X11;
    private static final int         GET_VIDEO_LIST_CODE    = 0X12;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler               = new BaseHandler(getActivity())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case GET_FREE_LIVE_SUCCESS:

                    break;


                case GET_VIDEO_LIST_SUCCESS:
                    VideoInfoListHandler mVideoInfoListHandler = (VideoInfoListHandler) msg.obj;
                    integerAreaList.clear();
                    integerAreaList.addAll(mVideoInfoListHandler.getVideoInfoList());
                    mIntegerAreaAdapter.notifyDataSetChanged();


                    break;

                case REQUEST_FAIL:
                    break;

                case GET_FREE_LIVE_CODE:
                    break;

                case GET_VIDEO_LIST_CODE:
                    getVideoList();
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
        picList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2869447915,2118394790&fm=27&gp=0.jpg");
        picList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2869447915,2118394790&fm=27&gp=0.jpg");
        picList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2869447915,2118394790&fm=27&gp=0.jpg");
        picList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2869447915,2118394790&fm=27&gp=0.jpg");
        picList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2869447915,2118394790&fm=27&gp=0.jpg");
        freeTimeList.add(new VideoInfo());
        freeTimeList.add(new VideoInfo());
        freeTimeList.add(new VideoInfo());
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

        mFreeTimeAdapter = new FreeTimeAdapter(freeTimeList, getContext(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {

            }
        });

        rvFreeTime.setLayoutManager(new FullyGridLayoutManager(getActivity(), 3));
        // rvRecommend.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvFreeTime.setAdapter(mFreeTimeAdapter);


        mIntegerAreaAdapter = new IntegerAreaAdapter(integerAreaList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {

            }
        });
        rvIntegerArea.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        rvIntegerArea.setAdapter(mIntegerAreaAdapter);

        loadData();
    }

    private void loadData()
    {
        mHandler.sendEmptyMessage(GET_FREE_LIVE_CODE);
        mHandler.sendEmptyMessage(GET_VIDEO_LIST_CODE);
    }

    private void getFreeLive()
    {
        Map<String, String> valuePairs = new HashMap<>();

        valuePairs.put("sort", "new");
        valuePairs.put("pn", "1");
        valuePairs.put("cta_id", "0");
        DataRequest.instance().request(getActivity(), Urls.getVideoLive(), this, HttpRequest.POST, GET_FREE_LIVE, valuePairs,
                new LiveInfoListHandler());
    }

    private void getVideoList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("sort", "new");
        valuePairs.put("pn", "1");
        valuePairs.put("cta_id", "0");
        DataRequest.instance().request(getActivity(), Urls.getVideoLive(), this, HttpRequest.POST, GET_VIDEO_LIST, valuePairs,
                new VideoInfoListHandler());
    }

    @Override
    public void onRefresh()
    {
        if (mSwipeRefreshLayout != null)
        {
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
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (GET_VIDEO_LIST.equals(action))
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
    }
}
