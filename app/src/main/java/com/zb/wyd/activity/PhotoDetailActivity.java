package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.CommentAdapter;
import com.zb.wyd.adapter.PhotoAdapter;
import com.zb.wyd.entity.CommentInfo;
import com.zb.wyd.entity.PhotoInfo;
import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.CommentInfoListHandler;
import com.zb.wyd.json.PhotoInfoHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.DialogUtils;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.DividerDecoration;
import com.zb.wyd.widget.MaxRecyclerView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：自拍详情
 */
public class PhotoDetailActivity extends BaseActivity implements IRequestListener
{

    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    TextView        tvTitle;
    @BindView(R.id.tv_name)
    TextView        tvName;
    @BindView(R.id.tv_add_time)
    TextView        tvAddTime;
    @BindView(R.id.rv_photo)
    MaxRecyclerView rvPhoto;
    @BindView(R.id.btn_buy)
    Button          btnBuy;
    @BindView(R.id.iv_collection)
    ImageView       ivCollection;
    @BindView(R.id.rv_comment)
    MaxRecyclerView rvComment;
    @BindView(R.id.tv_send)
    TextView        tvSend;
    @BindView(R.id.et_content)
    EditText        etContent;
    private List<String>      freePic         = new ArrayList<>();
    private List<String>      chargePic       = new ArrayList<>();
    private List<String>      allPic          = new ArrayList<>();
    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private PhotoAdapter   mPhotoAdapter;
    private CommentAdapter mCommentAdapter;


    private String biz_id;

    private PriceInfo priceInfo;
    private static final String BUY_PHOTO                = "buy_photo";
    private static final String FAVORITE_LIKE            = "favorite_like";
    private static final String SEND_COMMENT             = "send_comment";
    private static final String GET_PH0TO_DETAIL         = "get_photo_detail";
    private static final String GET_COMMENT_LIST         = " get_comment_list";
    private static final int    REQUEST_SUCCESS          = 0x01;
    private static final int    REQUEST_FAIL             = 0x02;
    private static final int    BUY_PHOTO_SUCCESS        = 0x05;
    private static final int    GET_COMMENT_LIST_CODE    = 0x06;
    private static final int    GET_COMMENT_LIST_SUCCESS = 0x07;
    private static final int    GET_PHOTO_LIST_CODE      = 0x08;
    private static final int    FAVORITE_LIKE_SUCCESS    = 0x09;
    private static final int    SEND_COMMENT_SUCCESS     = 0x10;

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(PhotoDetailActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    PhotoInfoHandler mPhotoInfoHandler = (PhotoInfoHandler) msg.obj;
                    PhotoInfo photoInfo = mPhotoInfoHandler.getPhotoInfo();

                    if (null != photoInfo)
                    {
                        priceInfo = photoInfo.getPriceInfo();
                        tvName.setText(photoInfo.getPname());
                        tvAddTime.setText("发布于：" + photoInfo.getAdd_time());
                        freePic.clear();
                        freePic.addAll(photoInfo.getFreePic());

                        chargePic.clear();
                        chargePic.addAll(photoInfo.getChargePic());

                        if (null != priceInfo)
                        {
                            btnBuy.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            btnBuy.setVisibility(View.GONE);
                        }

                        allPic.addAll(freePic);
                        allPic.addAll(chargePic);
                        mPhotoAdapter.notifyDataSetChanged();

                    }


                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(PhotoDetailActivity.this, msg.obj.toString());

                    break;

                case BUY_PHOTO_SUCCESS:
                    loadData();
                    break;

                case GET_COMMENT_LIST_CODE:
                    getCommentList();
                    break;

                case GET_COMMENT_LIST_SUCCESS:
                    CommentInfoListHandler mCommentInfoListHandler = (CommentInfoListHandler) msg.obj;
                    commentInfoList.clear();
                    commentInfoList.addAll(mCommentInfoListHandler.getCommentInfoList());
                    mCommentAdapter.notifyDataSetChanged();

                    break;

                case GET_PHOTO_LIST_CODE:
                    getPhotoList();
                    break;
                case FAVORITE_LIKE_SUCCESS:
                    ToastUtil.show(PhotoDetailActivity.this, "收藏成功");
                    break;

                case SEND_COMMENT_SUCCESS:
                    etContent.setText("");
                    ToastUtil.show(PhotoDetailActivity.this, "评论成功");
                    getCommentList();
                    break;
            }
        }
    };

    @Override
    protected void initData()
    {
        biz_id = getIntent().getStringExtra("biz_id");
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_photo_detail);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(PhotoDetailActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("照片详情");
        rvPhoto.setLayoutManager(new LinearLayoutManager(PhotoDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rvPhoto.setNestedScrollingEnabled(false);

        mPhotoAdapter = new PhotoAdapter(allPic, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {

            }
        });

        rvPhoto.setAdapter(mPhotoAdapter);

        rvComment.setLayoutManager(new LinearLayoutManager(PhotoDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rvComment.addItemDecoration(new DividerDecoration(this));
        mCommentAdapter = new CommentAdapter(commentInfoList);
        rvComment.setAdapter(mCommentAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadData();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == btnBuy)
        {
            if (null != priceInfo)
            {
                DialogUtils.showLivePriceDialog(PhotoDetailActivity.this, priceInfo, new View.OnClickListener()
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

                            buyPhoto(priceInfo.getFinger());


                        }
                        else//去做任务
                        {
                            sendBroadcast(new Intent(MainActivity.TAB_TASK));
                            finish();
                        }
                    }
                }).show();
            }
        }
        else if (v == ivCollection)
        {
            favoriteLike();

        }
        else if (v == tvSend)
        {
            sendComment();
        }
    }

    private void loadData()
    {
        mHandler.sendEmptyMessage(GET_PHOTO_LIST_CODE);
        mHandler.sendEmptyMessage(GET_COMMENT_LIST_CODE);
    }

    private void getPhotoList()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getPhotoDetailUrl(), this, HttpRequest.GET, GET_PH0TO_DETAIL, valuePairs,
                new PhotoInfoHandler());
    }

    private void getCommentList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pid", biz_id);
        valuePairs.put("pn", "1");
        valuePairs.put("num", "999");
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getCommentlistUrl(), this, HttpRequest.GET, GET_COMMENT_LIST, valuePairs,
                new CommentInfoListHandler());
    }

    //兑换
    private void buyPhoto(String finger)
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "photo");
        valuePairs.put("finger", finger);
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getBuyLiveUrl(), this, HttpRequest.POST, BUY_PHOTO, valuePairs,
                new ResultHandler());
    }


    private void favoriteLike()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        valuePairs.put("co_biz", "photo");
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getCollectionRequestUrl(), this, HttpRequest.POST, FAVORITE_LIKE, valuePairs,
                new ResultHandler());
    }


    private void sendComment()
    {
        String text = etContent.getText().toString();

        if (StringUtils.stringIsEmpty(text))
        {
            ToastUtil.show(this, "请输入评论内容");
            return;
        }
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("pid", biz_id);
        valuePairs.put("text", text);
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getSendCommentUrl(), this, HttpRequest.POST, SEND_COMMENT, valuePairs,
                new ResultHandler());
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (GET_PH0TO_DETAIL.equals(action))
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
        else if (BUY_PHOTO.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(BUY_PHOTO_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_COMMENT_LIST.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_COMMENT_LIST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (FAVORITE_LIKE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(FAVORITE_LIKE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (SEND_COMMENT.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(SEND_COMMENT_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }


}
