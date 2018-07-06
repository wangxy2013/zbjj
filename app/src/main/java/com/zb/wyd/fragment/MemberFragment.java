package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.MyApplication;
import com.zb.wyd.R;
import com.zb.wyd.activity.BaseHandler;
import com.zb.wyd.activity.BindEmailActivity;
import com.zb.wyd.activity.LoginActivity;
import com.zb.wyd.activity.MainActivity;
import com.zb.wyd.activity.MessageListActivity;
import com.zb.wyd.activity.UserDetailActivity;
import com.zb.wyd.activity.WealthListActivity;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.LoginHandler;
import com.zb.wyd.json.ResultHandler;
import com.zb.wyd.json.UserInfoHandler;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：一句话简单描述
 */
public class MemberFragment extends BaseFragment implements IRequestListener, View.OnClickListener
{

    @BindView(R.id.iv_edit)
    ImageView       ivEdit;
    @BindView(R.id.iv_user_pic)
    CircleImageView ivUserPic;
    @BindView(R.id.tv_user_name)
    TextView        tvUserName;
    @BindView(R.id.tv_user_type)
    TextView        tvUserType;
    @BindView(R.id.tv_user_sex)
    TextView        tvUserSex;
    @BindView(R.id.tv_user_level)
    TextView        tvUserLevel;
    @BindView(R.id.rl_user)
    RelativeLayout  rlUser;
    @BindView(R.id.tv_wealth)
    TextView        tvWealth;
    @BindView(R.id.rl_wealth)
    RelativeLayout  rlWealth;
    @BindView(R.id.tv_email)
    TextView        tvEmail;
    @BindView(R.id.rl_email)
    RelativeLayout  rlEmail;
    @BindView(R.id.tv_message)
    TextView        tvMessage;
    @BindView(R.id.rl_message)
    RelativeLayout  rlMessage;
    @BindView(R.id.tv_collection)
    TextView        tvCollection;
    @BindView(R.id.rl_collection)
    RelativeLayout  rlCollection;
    @BindView(R.id.tv_customer)
    TextView        tvCustomer;
    @BindView(R.id.rl_customer)
    RelativeLayout  rlCustomer;
    @BindView(R.id.btn_logout)
    Button          btnLogout;

    private boolean isClick;

    private View rootView = null;
    private Unbinder unbinder;
    private String   role;
    private UserInfo userInfo;
    private static final String      GET_USER_DETAIL = "get_user_detail";
    private static final int         REQUEST_SUCCESS = 0x01;
    private static final int         REQUEST_FAIL    = 0x02;
    @SuppressLint("HandlerLeak")
    private              BaseHandler mHandler        = new BaseHandler(getActivity())
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    UserInfoHandler mUserInfoHandler = (UserInfoHandler) msg.obj;
                    userInfo = mUserInfoHandler.getUserInfo();

                    if (null != userInfo)
                    {
                        isClick = true;
                        String unick = userInfo.getUnick();
                        String fortune = userInfo.getFortune();
                        String vip_level = userInfo.getVip_level();

                        ImageLoader.getInstance().displayImage(userInfo.getUface(), ivUserPic);
                        if ("-".equals(unick) || StringUtils.stringIsEmpty(unick))
                        {
                            tvUserName.setText(userInfo.getUname());
                        }
                        else
                        {
                            tvUserName.setText(unick);
                        }

                        tvUserLevel.setText("V" + vip_level);
                        tvWealth.setText(fortune);

                        role = userInfo.getRole();
                        if ("0".equals(role))
                        {
                            tvEmail.setText("未认证");
                        }
                        else
                        {
                            tvEmail.setText("已认证");
                        }

                        tvCustomer.setText("QQ:" + ConfigManager.instance().getSystemQq());
                    }
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(getActivity(), msg.obj.toString());
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_member, null);
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
        btnLogout.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        rlEmail.setOnClickListener(this);
        rlMessage.setOnClickListener(this);
        rlWealth.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (MyApplication.getInstance().isLogin())
        {
            isClick = false;
            Map<String, String> valuePairs = new HashMap<>();
            DataRequest.instance().request(getActivity(), Urls.getUserInfoUrl(), this, HttpRequest.POST, GET_USER_DETAIL, valuePairs,
                    new UserInfoHandler());
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
        if (GET_USER_DETAIL.equals(action))
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
    public void onClick(View v)
    {
        if (v == btnLogout)
        {
            ConfigManager.instance().setUserId("");
            ((MainActivity) getActivity()).setTab(0);
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        else if (v == ivEdit)
        {
            Bundle b = new Bundle();
            b.putSerializable("USER_INFO", userInfo);
            startActivity(new Intent(getActivity(), UserDetailActivity.class).putExtras(b));
        }
        else if (v == rlEmail)
        {
            if ("0".equals(role))
            {
                startActivity(new Intent(getActivity(), BindEmailActivity.class));
            }
        }
        else if (v == rlMessage)
        {
            startActivity(new Intent(getActivity(), MessageListActivity.class));

        }

        else if (v == rlWealth)
        {
            if (isClick)
                startActivity(new Intent(getActivity(), WealthListActivity.class).putExtra("fortune", tvWealth.getText().toString()));
        }
    }
}
