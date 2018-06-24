package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.PhotoAdapter;
import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.entity.PhotoInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.PhotoInfoHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.listener.MyOnClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.DialogUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.MaxRecyclerView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：自拍详情
 */
public class PhotoDetailActivity extends BaseActivity implements IRequestListener
{

    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    TextView        tvTitle;
    @BindView(R.id.tv_submit)
    TextView        tvSubmit;
    @BindView(R.id.tv_name)
    TextView        tvName;
    @BindView(R.id.tv_add_time)
    TextView        tvAddTime;
    @BindView(R.id.rv_photo)
    MaxRecyclerView rvPhoto;
    @BindView(R.id.btn_buy)
    Button          btnBuy;
    private List<String> freePic   = new ArrayList<>();
    private List<String> chargePic = new ArrayList<>();
    private List<String> allPic    = new ArrayList<>();

    private PhotoAdapter mPhotoAdapter;
    private String       biz_id;

    private PriceInfo priceInfo;
    private static final String BUY_PHOTO         = "BUY_PHOTO";
    private static final String GET_PH0TO_DETAIL  = "get_ph0to_detail";
    private static final int    REQUEST_SUCCESS   = 0x01;
    private static final int    REQUEST_FAIL      = 0x02;
    private static final int    BUY_PHOTO_SUCCESS = 0x05;

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

                        if (chargePic.isEmpty())
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

                        }
                    }
                }).show();
            }
        }
    }

    private void loadData()
    {
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("biz_id", biz_id);
        DataRequest.instance().request(PhotoDetailActivity.this, Urls.getPhotoDetailUrl(), this, HttpRequest.POST, GET_PH0TO_DETAIL, valuePairs,
                new PhotoInfoHandler());
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
    }


}
