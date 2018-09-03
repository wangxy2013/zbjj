package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.zb.wyd.R;
import com.zb.wyd.adapter.GiftAdapter;
import com.zb.wyd.adapter.OnlineAdapter;
import com.zb.wyd.entity.ChatInfo;
import com.zb.wyd.entity.GiftInfo;
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
import com.zb.wyd.json.UserInfoHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.DialogUtils;
import com.zb.wyd.utils.LogUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;
import com.zb.wyd.widget.FullyGridLayoutManager;
import com.zb.wyd.widget.LiveVideoPlayer;
import com.zb.wyd.widget.gift.AnimMessage;
import com.zb.wyd.widget.gift.LPAnimationManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    TextView tvUserName;
    @BindView(R.id.tv_favour_count)
    TextView tvFavourCount;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.rv_online)
    RecyclerView rvOnline;

    @BindView(R.id.rl_content)
    RelativeLayout mContentLayout;
    @BindView(R.id.iv_closed)
    ImageView ivClosed;
    @BindView(R.id.tv_dm)
    TextView tvDm;
    @BindView(R.id.iv_report)
    ImageView ivReport;
    @BindView(R.id.iv_gift)
    ImageView ivGift;
    @BindView(R.id.tv_system)
    TextView tvSystem;
    @BindView(R.id.tv_welcome_name)
    TextView tvWelcomeName;
    @BindView(R.id.tv_say)
    TextView tvSay;

    @BindView(R.id.tv_location)
    TextView tvLocation;


    @BindView(R.id.rl_live_bottom)
    RelativeLayout mLiveBottomLayout;
    @BindView(R.id.ll_gift_container)
    LinearLayout llGiftContainer;

    private String biz_id, location;
    private long startTime, endTime;
    private String playUrl;

    private String has_favorite;

    private List<UserInfo> onlineList = new ArrayList<>();
    private OnlineAdapter mOnlineAdapter;

    private List<GiftInfo> giftInfoList = new ArrayList<>();


    private static final String GET_USER_INFO = "get_user_info";
    private static final String GET_LIVE_PRICE = "get_live_price";
    private static final String GET_LIVE_STREAM = "get_live_stream";
    private static final String GET_ONLINER = "get_onliner";
    private static final String BUY_LIVE = "buy_live";
    private static final String FAVORITE_LIKE = "favorite_like";
    private final String UN_FAVORITE_LIKE = "un_favorite_like";
    private final String GET_FORTUNE_GIFT = "get_fortune_gift";
    private final String FORTUNE_BUY = "fortune_buy";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    private static final int GET_LIVE_PRICE_SUCCESS = 0x03;
    private static final int BUY_LIVE_SUCCESS = 0x05;
    private static final int SET_STATISTICS = 0x06;
    private static final int GET_ONLINER_SUCCESS = 0x07;
    private static final int GET_ONLINER_REQUEST = 0x08;
    private static final int GET_STREAM_REQUEST = 0x09;
    private static final int GET_ANCHOR_REQUEST = 0x10;
    private static final int FAVORITE_LIKE_SUCCESS = 0x11;
    private static final int UN_FAVORITE_LIKE_SUCCESS = 0x15;
    private static final int FAVORITE_LIKE_FAIL = 0x14;
    private static final int GET_ANCHOR_SUCCESS = 0x12;
    private static final int SHOW_SYSTEM_TV = 0x13;
    private static final int GET_FORTUNE_GIFT_SUCCESS = 0x16;
    private static final int FORTUNE_BUY_SUCCESS = 0x17;
    private static final int GET_FORTUNE_GIFT_FAIL = 0x18;
    private static final int FORTUNE_BUY_SUCCESS_FAIL = 0x19;

    @SuppressLint("HandlerLeak")
    private NoLeakHandler mHandler = new NoLeakHandler(LiveActivity.this)
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

                        playUrl = mLiveInfo.getUri();
                        LogUtil.e("TAG", playUrl);
                        videoPlayer.setUp(playUrl, false, "");
                        videoPlayer.startPlayLogic();
                    }
                    break;


                case REQUEST_FAIL:
                    DialogUtils.showPromptDialog(LiveActivity.this, msg.obj.toString(), new MyItemClickListener()
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

                                    // sendBroadcast(new Intent(MainActivity.TAB_TASK));
                                    startActivity(new Intent(LiveActivity.this, TaskActivity.class));
                                    finish();
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

                case GET_ANCHOR_REQUEST:
                    getUserInfo();

                    break;

                case FAVORITE_LIKE_SUCCESS:
                    has_favorite = "1";
                    tvFollow.setText("已关注");
                    ToastUtil.show(LiveActivity.this, "关注成功");
                    break;
                case UN_FAVORITE_LIKE_SUCCESS:
                    has_favorite = "0";
                    tvFollow.setText("关注");
                    ToastUtil.show(LiveActivity.this, "取消关注成功");
                    break;
                case FAVORITE_LIKE_FAIL:
                    ToastUtil.show(LiveActivity.this, "操作失败");
                    break;
                case GET_ANCHOR_SUCCESS:

                    UserInfoHandler mUserInfoHandler = (UserInfoHandler) msg.obj;

                    UserInfo mUserInfo = mUserInfoHandler.getUserInfo();

                    if (null != mUserInfo)
                    {
                        if (null != ivUserPic)
                        {
                            ImageLoader.getInstance().displayImage(mUserInfo.getFace(), ivUserPic);
                        }
                        if (null != tvUserName) tvUserName.setText(mUserInfo.getNick());
                        if (null != tvFavourCount)
                            tvFavourCount.setText(mUserInfo.getFavour_count());
                        has_favorite = mUserInfo.getHas_favorite();
                        if (null != tvFollow)
                        {

                            if ("1".equals(mUserInfo.getHas_favorite()))
                            {
                                tvFollow.setText("已关注");
                            }
                            else
                            {
                                tvFollow.setText("关注");
                            }
                        }
                    }
                    break;

                case SHOW_SYSTEM_TV:
                    dmShow = true;
                    tvSystem.setVisibility(View.VISIBLE);
                    break;

                case GET_FORTUNE_GIFT_SUCCESS:

                    LivePriceInfoHandler mHandler1 = (LivePriceInfoHandler) msg.obj;
                    PriceInfo mPriceInfo = mHandler1.getLivePriceInfo();
                    if (null != mPriceInfo) buyGift(mPriceInfo.getId(), mPriceInfo.getFinger());
                    break;

                case GET_FORTUNE_GIFT_FAIL:

                    LivePriceInfoHandler resultHandler = (LivePriceInfoHandler) msg.obj;
                    String resultCode = resultHandler.getResultCode();

                    //1102  余额不足，引导充值
                    //1112  需要购买会员（此刻引导购买会员）
                    //1113 需要邀请5人（此刻需要引导去任务大厅）
                    if ("1102".equals(resultCode))
                    {
                        DialogUtils.showToastDialog2Button(LiveActivity.this, resultHandler.getResultMsg(), new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                startActivity(new Intent(LiveActivity.this, MyMasonryActivity.class));
                            }
                        });
                    }
                    else if ("1112".equals(resultCode))
                    {
                        DialogUtils.showToastDialog2Button(LiveActivity.this, resultHandler.getResultMsg(), new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                startActivity(new Intent(LiveActivity.this, MemberActivity.class));
                            }
                        });
                    }
                    else if ("1113".equals(resultCode))
                    {
                        DialogUtils.showToastDialog2Button(LiveActivity.this, resultHandler.getResultMsg(), new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                startActivity(new Intent(LiveActivity.this, TaskActivity.class));
                            }
                        });
                    }
                    break;

                case FORTUNE_BUY_SUCCESS:

                    ToastUtil.show(LiveActivity.this, "感谢您的支持");


                    break;
                case FORTUNE_BUY_SUCCESS_FAIL:
                    ToastUtil.show(LiveActivity.this, msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void initData()
    {
        biz_id = getIntent().getStringExtra("biz_id");
        location = getIntent().getStringExtra("location");


        String[] giftNameArr = getResources().getStringArray(R.array.gift_name);
        String[] giftPriceArr = getResources().getStringArray(R.array.gift_price);
        String[] giftStyleArr = getResources().getStringArray(R.array.gift_style);
        String[] giftIdArr = getResources().getStringArray(R.array.gift_id);
        int[] giftDrawableArr = new int[]{R.drawable.ic_gift_pear, R.drawable.ic_gift_666, R.drawable.ic_gift_blanana, R.drawable.ic_gift_cannon, R.drawable.ic_gift_ring, R.drawable.ic_gift_car, R.drawable.ic_gift_car1, R.drawable.ic_gift_love};

        for (int i = 0; i < giftNameArr.length; i++)
        {
            GiftInfo giftInfo = new GiftInfo();
            giftInfo.setDrawableId(giftDrawableArr[i]);
            giftInfo.setGiftName(giftNameArr[i]);
            giftInfo.setGiftPrice(giftPriceArr[i]);
            giftInfo.setGiftStyle(giftStyleArr[i]);
            giftInfo.setGiftId(giftIdArr[i]);
            giftInfoList.add(giftInfo);

        }


    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_live);
    }

    @Override
    protected void initEvent()
    {
        mContentLayout.setOnClickListener(this);
        tvFollow.setOnClickListener(this);
        ivClosed.setOnClickListener(this);
        ivGift.setOnClickListener(this);
        ivReport.setOnClickListener(this);
        tvSay.setOnClickListener(this);
        tvDm.setOnClickListener(this);
        ivUserPic.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        if (StringUtils.stringIsEmpty(location))
        {
            tvLocation.setVisibility(View.GONE);
        }
        else
        {
            tvLocation.setText("主播正在" + location + "进行直播");
            tvLocation.setVisibility(View.VISIBLE);
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
                setStatistics(0);
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

        tvWelcomeName.setText("欢迎 " + ConfigManager.instance().getUserNickName() + " 进入直播间");

        tvDm.setSelected(true);
        startTime = System.currentTimeMillis();
        mHandler.sendEmptyMessage(GET_STREAM_REQUEST);
        mHandler.sendEmptyMessage(GET_ONLINER_REQUEST);
        mHandler.sendEmptyMessage(GET_ANCHOR_REQUEST);

        mHandler.sendEmptyMessageDelayed(SHOW_SYSTEM_TV, 60 * 1000);
        initWebSocket();


        LPAnimationManager.init(this);
        LPAnimationManager.addGiftContainer(llGiftContainer);


    }


    private void getLivePrice()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getLivePriceUrl(), this, HttpRequest.GET, GET_LIVE_PRICE, valuePairs, new LivePriceInfoHandler());

    }

    //获取直播地址
    private void getLiveStream()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        DataRequest.instance().request(LiveActivity.this, Urls.getLiveStreamUrl(), this, HttpRequest.GET, GET_LIVE_STREAM, valuePairs, new LiveInfoHandler());
    }

    //兑换
    private void buyLive(String finger)
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        valuePairs.put("finger", finger);
        DataRequest.instance().request(LiveActivity.this, Urls.getBuyLiveUrl(), this, HttpRequest.POST, BUY_LIVE, valuePairs, new ResultHandler());
    }


    //通知单服务器
    private void setStatistics(long duration)
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        valuePairs.put("duration", String.valueOf(duration));
        DataRequest.instance().request(LiveActivity.this, Urls.getStatisticsUrl(), this, HttpRequest.POST, "SET_STATISTICS", valuePairs, new ResultHandler());
    }

    private void getUserInfo()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        DataRequest.instance().request(LiveActivity.this, Urls.getAnchorDetailUrl(), this, HttpRequest.GET, GET_USER_INFO, valuePairs, new UserInfoHandler());
    }

    private void getOnLiner()
    {

        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getOnlinerUrl(), this, HttpRequest.GET, GET_ONLINER, valuePairs, new OnlinerListHandler());


    }

    private void favoriteLike()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getCollectionRequestUrl(), this, HttpRequest.POST, FAVORITE_LIKE, valuePairs, new ResultHandler());
    }

    private void unFavoriteLike()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "live");
        DataRequest.instance().request(LiveActivity.this, Urls.getFavoriteUnLikeUrl(), this, HttpRequest.POST, UN_FAVORITE_LIKE, valuePairs, new ResultHandler());
    }

    private boolean dmShow = true;

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tvFollow)
        {
            if ("1".equals(has_favorite))
            {
                unFavoriteLike();
            }
            else
            {
                favoriteLike();
            }

        }
        else if (v == tvDm)
        {
            if (dmShow)
            {
                dmShow = false;
                tvSystem.setVisibility(View.GONE);
                tvWelcomeName.setVisibility(View.GONE);
                tvDm.setSelected(false);
            }
            else
            {
                dmShow = true;
                tvSystem.setVisibility(View.VISIBLE);
                tvWelcomeName.setVisibility(View.VISIBLE);
                tvDm.setSelected(true);
            }
        }
        else if (v == tvSay || v == ivReport)
        {

            ToastUtil.show(LiveActivity.this, "该功能内测中，只能内测用户使用");
        }
        else if (v == ivClosed)
        {
            DialogUtils.showToastDialog2Button(LiveActivity.this, "是否退出观看该直播", new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });
        }
        else if (v == ivUserPic)
        {
            if (!tvFollow.isShown())
            {
                tvFollow.setVisibility(View.VISIBLE);
            }
        }
        else if (v == ivGift)
        {
            if (null == mGiftDialog)
            {
                mGiftDialog = showGiftDialog(LiveActivity.this, giftInfoList);

                mGiftDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface)
                    {
                        mLiveBottomLayout.setVisibility(View.VISIBLE);
                    }
                });
            }

            mLiveBottomLayout.setVisibility(View.GONE);
            mGiftDialog.show();

        }
        //WS sendMsg
        // mSocketClient.send("");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //videoPlayer.onVideoResume();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        //        videoPlayer.onVideoReset();
        //        if (!TextUtils.isEmpty(playUrl))
        //        {
        //            videoPlayer.setUp(playUrl, false, "");
        //            videoPlayer.startPlayLogic();
        //        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 100;
        setStatistics(duration);
        mHandler.removeCallbacksAndMessages(null);

        if (mSocketClient != null)
        {
            mSocketClient.close();
        }
        LPAnimationManager.release();
    }


    private WebSocketClient mSocketClient;

    private Timer timer = new Timer();
    private TimerTask webSocketTask;


    private void initWebSocket()
    {
        webSocketTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if (null != mSocketClient)
                {
                    mSocketClient.send("ping");
                }
            }
        };

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String wsUri = ConfigManager.instance().getChatUrl() + "/" + ConfigManager.instance().getUniqueCode() + "?biz_id=" + biz_id;

                    Log.e("wsUri", "wsUri-->" + wsUri);
                    mSocketClient = new WebSocketClient(new URI(wsUri), new Draft_10())
                    {
                        @Override
                        public void onOpen(ServerHandshake handshakedata)
                        {
                            Log.d("picher_log", "打开通道" + handshakedata.getHttpStatus());
                            // handler.obtainMessage(0, message).sendToTarget();
                            timer.schedule(webSocketTask, 0, 30000);
                        }

                        @Override
                        public void onMessage(String message)
                        {
                            Log.d("picher_log", "接收消息" + message);
                            // handler.obtainMessage(0, message).sendToTarget();

                            if (!TextUtils.isEmpty(message))
                            {
                                try
                                {
                                    ChatInfo chatInfo = new ChatInfo(new JSONObject(message));

                                    if (null != chatInfo)
                                    {


                                        String action = chatInfo.getAction();
                                        String data = chatInfo.getData();
                                        //礼物
                                        if ("gift".equals(action))
                                        {
                                            LPAnimationManager.addAnimalMessage(new AnimMessage("text888", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535970862163&di=0377e4e36736def1164bcf979dd5099d&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170926%2F0I51033O-0.jpg", 10, "666",R.drawable.ic_gift_blanana));
                                        }

                                    }

                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote)
                        {
                            Log.d("picher_log", "通道关闭");
                            // handler.obtainMessage(0, message).sendToTarget();
                        }

                        @Override
                        public void onError(Exception ex)
                        {
                            Log.d("picher_log", "链接错误");
                        }
                    }; mSocketClient.connect();

                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
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
        if (GET_LIVE_STREAM.equals(action))
        {
            hideProgressDialog();
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
            hideProgressDialog();
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
        else if (FAVORITE_LIKE.equals(action))
        {
            hideProgressDialog();
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(FAVORITE_LIKE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(FAVORITE_LIKE_FAIL, resultMsg));
            }
        }
        else if (UN_FAVORITE_LIKE.equals(action))
        {
            hideProgressDialog();
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(UN_FAVORITE_LIKE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(FAVORITE_LIKE_FAIL, resultMsg));
            }
        }
        else if (GET_USER_INFO.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_ANCHOR_SUCCESS, obj));
            }
            else
            {
                //mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_FORTUNE_GIFT.equals(action))
        {
            hideProgressDialog();
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_FORTUNE_GIFT_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_FORTUNE_GIFT_FAIL, obj));
            }
        }

        else if (FORTUNE_BUY.equals(action))
        {
            hideProgressDialog();
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(FORTUNE_BUY_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(FORTUNE_BUY_SUCCESS_FAIL, resultMsg));
            }
        }


    }


    private static class NoLeakHandler extends Handler
    {
        private WeakReference<LiveActivity> mActivity;

        public NoLeakHandler(LiveActivity activity)
        {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
        }
    }


    /***************** 礼物 ***********************************************/

    private Dialog mGiftDialog;
    private GiftAdapter giftAdapter;

    private int giftSelectedItem = -1;

    /**
     * 礼物
     *
     * @return
     */
    private Dialog showGiftDialog(Context mContext, List<GiftInfo> giftInfoList)
    {
        final Dialog dialog = new Dialog(mContext, R.style.DialogStyle);
        dialog.setCancelable(true);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_gift, null);
        dialog.setContentView(v);

        TextView tvGiftPrice = (TextView) v.findViewById(R.id.tv_gift_price);
        ImageView ivMasonry = (ImageView) v.findViewById(R.id.iv_masonry);
        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_submit);

        tvSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                giveGift();
            }
        });


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext, 4));


        changeGiftPrice(tvGiftPrice, ivMasonry);
        giftAdapter = new GiftAdapter(mContext, giftInfoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                giftSelectedItem = position;
                for (int i = 0; i < giftInfoList.size(); i++)
                {

                    if (i == position)
                    {
                        giftInfoList.get(position).setSelected(true);
                    }
                    else
                    {
                        giftInfoList.get(i).setSelected(false);
                    }
                }

                if (null != giftAdapter)
                {
                    giftAdapter.notifyDataSetChanged();
                }

                changeGiftPrice(tvGiftPrice, ivMasonry);

            }
        });
        recyclerView.setAdapter(giftAdapter);


        //Dialog部分
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mWindow.setAttributes(lp);
        return dialog;

    }


    private void changeGiftPrice(TextView mGiftPriceTv, ImageView mMasonryIv)
    {
        if (giftSelectedItem < 0)
        {
            return;
        }


        GiftInfo giftInfo = giftInfoList.get(giftSelectedItem);


        if ("0".equals(giftInfo.getGiftStyle()))
        {
            mMasonryIv.setVisibility(View.GONE);
            mGiftPriceTv.setText(giftInfo.getGiftPrice() + "积分");
        }
        else
        {
            mMasonryIv.setVisibility(View.VISIBLE);
            mGiftPriceTv.setText(giftInfo.getGiftPrice());
        }
    }


    private void giveGift()
    {
        if (giftSelectedItem < 0)
        {
            ToastUtil.show(this, "请选择您要赠送的你礼物");
            return;
        }
        mGiftDialog.dismiss();

        GiftInfo giftInfo = giftInfoList.get(giftSelectedItem);
        getFortuneGift(giftInfo.getGiftId());


        //
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("yu11", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("hellokitty11", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("我是小学生11", "", 20, "红包"));
        //
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("yu22", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("hellokitty22", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("我是小学生22", "", 20, "红包"));
        //
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("yu33", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("hellokitty33", "", 10, "飞机"));
        //        LPAnimationManager.addAnimalMessage(new AnimMessage("我是小学生33", "", 20, "红包"));
    }


    private void getFortuneGift(String giftid)
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("giftid", giftid);
        DataRequest.instance().request(LiveActivity.this, Urls.getFortuneGiftUrl(), this, HttpRequest.POST, GET_FORTUNE_GIFT, valuePairs, new LivePriceInfoHandler());
    }


    private void buyGift(String giftId, String finger)
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", giftId);
        valuePairs.put("co_biz", "gift");
        valuePairs.put("finger", finger);
        DataRequest.instance().request(LiveActivity.this, Urls.getFortuneBuyUrl(), this, HttpRequest.POST, FORTUNE_BUY, valuePairs, new ResultHandler());
    }
    /***************** 礼物 ***********************************************/
}
