package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.zb.wyd.R;
import com.zb.wyd.adapter.OnlineAdapter;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LiveInfoHandler;
import com.zb.wyd.json.LivePriceInfoHandler;
import com.zb.wyd.json.OnlinerListHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.DialogUtils;
import com.zb.wyd.utils.LogUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;
import com.zb.wyd.widget.LiveVideoPlayer;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：一句话简单描述
 */
public class LiveActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.detail_player)
    LiveVideoPlayer videoPlayer;
    @BindView(R.id.iv_user_pic)
    CircleImageView ivUserPic;
    @BindView(R.id.tv_user_name)
    TextView        tvUserName;
    @BindView(R.id.tv_favour_count)
    TextView        tvFavourCount;
    @BindView(R.id.tv_follow)
    TextView        tvFollow;
    @BindView(R.id.rv_online)
    RecyclerView    rvOnline;

    private String   biz_id;
    private LiveInfo mLiveInfo;
    private long     startTime, endTime;


    private List<UserInfo> onlineList = new ArrayList<>();
    private OnlineAdapter mOnlineAdapter;


    private static final String      GET_LIVE_PRICE         = "get_live_price";
    private static final String      GET_LIVE_STREAM        = "get_live_stream";
    private static final String      GET_ONLINER            = "get_onliner";
    private static final String      BUY_LIVE               = "buy_live";
    private static final int         REQUEST_SUCCESS        = 0x01;
    private static final int         REQUEST_FAIL           = 0x02;
    private static final int         GET_LIVE_PRICE_SUCCESS = 0x03;
    private static final int         BUY_LIVE_SUCCESS       = 0x05;
    private static final int         SET_STATISTICS         = 0x06;
    private static final int         GET_ONLINER_SUCCESS    = 0x07;
    private static final int         GET_ONLINER_REQUEST    = 0x08;
    private static final int         GET_STREAM_REQUEST     = 0x09;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler               = new BaseHandler(LiveActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    LiveInfoHandler mLiveInfoHandler = (LiveInfoHandler) msg.obj;
                    LiveInfo mLiveInfo = mLiveInfoHandler.getLiveInfo();
                    if (null != mLiveInfo)
                    {
                        String uri = mLiveInfo.getUri();
                        LogUtil.e("TAG", uri);
                        videoPlayer.setUp(uri, false, "");
                        videoPlayer.startPlayLogic();
                    }
                    break;


                case REQUEST_FAIL:
                    DialogUtils.showPromptDialog(LiveActivity.this, "当前主播不在线", new MyItemClickListener()
                    {
                        @Override
                        public void onItemClick(View view, int position)
                        {
                            finish();
                        }
                    });
                    break;

                case GET_LIVE_PRICE_SUCCESS:
                    LivePriceInfoHandler mLivePriceInfoHandler = (LivePriceInfoHandler) msg.obj;

                    PriceInfo mLivePriceInfo = mLivePriceInfoHandler.getLivePriceInfo();

                    if (null != mLivePriceInfo)
                    {
                        DialogUtils.showLivePriceDialog(LiveActivity.this, mLivePriceInfo, new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                finish();
                            }
                        }, new MyOnClickListener.OnSubmitListener()
                        {
                            @Override
                            public void onSubmit(String content)
                            {
                                if ("1".equals(content))//兑换
                                {

                                    buyLive(mLivePriceInfo.getFinger());


                                }
                                else//去做任务
                                {

                                }
                            }
                        }).show();
                    }
                    break;

                case BUY_LIVE_SUCCESS:
                    getLiveStream();
                    break;

                case SET_STATISTICS:
                    setStatistics(0);
                    break;

                case GET_ONLINER_SUCCESS:
                    OnlinerListHandler mOnlinerListHandler = (OnlinerListHandler) msg.obj;
                    onlineList.clear();
                    onlineList.addAll(mOnlinerListHandler.getUserInfoList());
                    mOnlineAdapter.notifyDataSetChanged();

                    break;

                case GET_ONLINER_REQUEST:
                    getOnLiner();
                    break;

                case GET_STREAM_REQUEST:
                    getLiveStream();
                    break;
            }
        }
    };

    @Override
    protected void initData()
    {
        mLiveInfo = (LiveInfo) getIntent().getSerializableExtra("LiveInfo");
        if (null != mLiveInfo)
        {
            biz_id = mLiveInfo.getId();
        }


    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_live);
    }

    @Override
    protected void initEvent()
    {
    }

    @Override
    protected void initViewData()
    {
        if (null != mLiveInfo)
        {
            ImageLoader.getInstance().displayImage(mLiveInfo.getFace(), ivUserPic);
            tvUserName.setText(mLiveInfo.getNick());
            tvFavourCount.setText(mLiveInfo.getFavour_count());
        }


        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvOnline.setLayoutManager(layoutmanager);
        mOnlineAdapter = new OnlineAdapter(onlineList);
        rvOnline.setAdapter(mOnlineAdapter);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(false);
        videoPlayer.setVideoAllCallBack(new VideoAllCallBack()
        {
            //开始加载，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onStartPrepared(String s, Object... objects)
            {

            }

            //加载成功，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onPrepared(String s, Object... objects)
            {

            }

            //点击了开始按键播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickStartIcon(String s, Object... objects)
            {

            }

            //点击了错误状态下的开始按键，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickStartError(String s, Object... objects)
            {

            }

            //点击了播放状态下的开始按键--->停止，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickStop(String s, Object... objects)
            {

            }

            //点击了暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickStopFullscreen(String s, Object... objects)
            {

            }

            //点击了暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickResume(String s, Object... objects)
            {

            }

            //点击了全屏暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickResumeFullscreen(String s, Object... objects)
            {

            }

            //点击了空白弹出seekbar，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickSeekbar(String s, Object... objects)
            {

            }

            //点击了全屏的seekbar，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickSeekbarFullscreen(String s, Object... objects)
            {

            }


            //播放完了，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onAutoComplete(String s, Object... objects)
            {

            }


            //进去全屏，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onEnterFullscreen(String s, Object... objects)
            {

            }

            //退出全屏，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onQuitFullscreen(String s, Object... objects)
            {

            }

            //进入小窗口，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onQuitSmallWidget(String s, Object... objects)
            {

            }

            //退出小窗口，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onEnterSmallWidget(String s, Object... objects)
            {

            }

            //触摸调整声音，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onTouchScreenSeekVolume(String s, Object... objects)
            {

            }

            //触摸调整进度，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onTouchScreenSeekPosition(String s, Object... objects)
            {

            }

            //触摸调整亮度，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onTouchScreenSeekLight(String s, Object... objects)
            {

            }

            //播放错误，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onPlayError(String s, Object... objects)
            {
                LogUtil.e("TAG", "播放错误111111111111111111111111111111111111111111111111111");

                DialogUtils.showPromptDialog(LiveActivity.this, "当前主播不在线", new MyItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        finish();
                    }
                });
            }

            //点击了空白区域开始播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickStartThumb(String s, Object... objects)
            {

            }

            //点击了播放中的空白区域，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickBlank(String s, Object... objects)
            {

            }

            //点击了全屏播放中的空白区域，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
            @Override
            public void onClickBlankFullscreen(String s, Object... objects)
            {

            }
        });
        startTime = System.currentTimeMillis();
        mHandler.sendEmptyMessage(GET_STREAM_REQUEST);
        mHandler.sendEmptyMessage(GET_ONLINER_REQUEST);
    }


    private void getLivePrice()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getLivePriceUrl(), this, HttpRequest.POST, GET_LIVE_PRICE, valuePairs,
                new LivePriceInfoHandler());

    }

    //获取直播地址
    private void getLiveStream()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        DataRequest.instance().request(LiveActivity.this, Urls.getLiveStreamUrl(), this, HttpRequest.POST, GET_LIVE_STREAM, valuePairs,
                new LiveInfoHandler());
    }

    //兑换
    private void buyLive(String finger)
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        valuePairs.put("finger", finger);
        DataRequest.instance().request(LiveActivity.this, Urls.getBuyLiveUrl(), this, HttpRequest.POST, BUY_LIVE, valuePairs,
                new ResultHandler());
    }


    //通知单服务器
    private void setStatistics(long duration)
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        valuePairs.put("duration", String.valueOf(duration));
        DataRequest.instance().request(LiveActivity.this, Urls.getStatisticsUrl(), this, HttpRequest.POST, "SET_STATISTICS", valuePairs,
                new ResultHandler());
    }


    private void getOnLiner()
    {

        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getOnlinerUrl(), this, HttpRequest.GET, GET_ONLINER, valuePairs,
                new OnlinerListHandler());


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        endTime = System.currentTimeMillis();

        long duration = (endTime - startTime) / 100;

        setStatistics(duration);

    }

    @Override
    public void onBackPressed()
    {
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (GET_LIVE_STREAM.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUCCESS, obj));
            }
            else if ("1502".endsWith(resultCode))
            {
                getLivePrice();
            }
            else
            {

                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_LIVE_PRICE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_LIVE_PRICE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (BUY_LIVE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(BUY_LIVE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_ONLINER.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_ONLINER_SUCCESS, obj));
            }
            else
            {
                // mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }


}
