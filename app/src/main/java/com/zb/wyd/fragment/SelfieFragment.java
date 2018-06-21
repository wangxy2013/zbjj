package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.banner.CustomBanner;
import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.adapter.AnchorAdapter;
import com.zb.wyd.adapter.CataAdapter;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.CataInfoListHandler;
import com.zb.wyd.json.LiveInfoListHandler;
import com.zb.wyd.listener.MyItemClickListener;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.MaxRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：自拍
 */
public class SelfieFragment extends BaseFragment implements IRequestListener
{

    @BindView(R.id.banner)
    CustomBanner    banner;
    @BindView(R.id.rv_photo)
    MaxRecyclerView rvPhoto;
    @BindView(R.id.iv_show)
    ImageView       ivShow;
    @BindView(R.id.rv_cata)
    RecyclerView    rvCata;
    @BindView(R.id.tv_new)
    TextView        tvNew;
    @BindView(R.id.tv_fav)
    TextView        tvFav;
    @BindView(R.id.tv_add)
    TextView        tvAdd;
    private List<CataInfo> cataInfoList = new ArrayList<>();
    private CataAdapter mCataAdapter;
    private View rootView = null;
    private Unbinder unbinder;
    private static final String GET_CATA_LIST = "get_cata_list";

    private static final int REQUEST_SUCCESS       = 0x01;
    private static final int REQUEST_FAIL          = 0x02;
    private static final int GET_CATA_LIST_SUCCESS = 0x04;

    private static final int         GET_CATA_LIST_CODE = 0x10;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler           = new BaseHandler(getActivity())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    LiveInfoListHandler mOrderListHandler = (LiveInfoListHandler) msg.obj;

                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(getActivity(), msg.obj.toString());

                    break;
                case GET_CATA_LIST_SUCCESS:
                    CataInfoListHandler mCataInfoListHandler = (CataInfoListHandler) msg.obj;
                    cataInfoList.clear();
                    cataInfoList.addAll(mCataInfoListHandler.getCataInfoList());
                    mCataAdapter.notifyDataSetChanged();
                    break;

                case GET_CATA_LIST_CODE:
                    getPhotoCata();
                    break;

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_selfie, null);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCata.setLayoutManager(linearLayoutManager);

        mCataAdapter = new CataAdapter(cataInfoList, getActivity(), new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                for (int i = 0; i < cataInfoList.size(); i++)
                {
                    if (i == position)
                    {
                        cataInfoList.get(position).setSelected(true);
                    }
                    else
                    {
                        cataInfoList.get(i).setSelected(false);
                    }
                }
                mCataAdapter.notifyDataSetChanged();
            }
        });
        rvCata.setAdapter(mCataAdapter);


        mHandler.sendEmptyMessage(GET_CATA_LIST_CODE);
    }


    private void getPhotoCata()
    {
        Map<String, String> valuePairs = new HashMap<>();
        DataRequest.instance().request(getActivity(), Urls.getPhotoCataUrl(), this, HttpRequest.POST, GET_CATA_LIST, valuePairs,
                new CataInfoListHandler());
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

        if (GET_CATA_LIST.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_CATA_LIST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }
}
