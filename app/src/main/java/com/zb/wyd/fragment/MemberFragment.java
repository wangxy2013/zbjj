package com.zb.wyd.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import com.zb.wyd.activity.DomainNameActivity;
import com.zb.wyd.activity.LoginActivity;
import com.zb.wyd.activity.MainActivity;
import com.zb.wyd.activity.MessageListActivity;
import com.zb.wyd.activity.MyCollectionActivity;
import com.zb.wyd.activity.UserDetailActivity;
import com.zb.wyd.activity.WealthListActivity;
import com.zb.wyd.activity.WebViewActivity;
import com.zb.wyd.entity.FortuneInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.http.DataRequest;
import com.zb.wyd.http.HttpRequest;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.json.UserInfoHandler;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.utils.Urls;
import com.zb.wyd.widget.CircleImageView;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.rl_extension)
    RelativeLayout  rlExtension;
    @BindView(R.id.tv_version)
    TextView        tvVersion;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;

    private View rootView = null;
    private Unbinder unbinder;
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
                        String unick = userInfo.getUnick();
                        FortuneInfo fortune = userInfo.getFortuneInfo();
                        String vip_level = userInfo.getVip_level();
                        ConfigManager.instance().setUserRole(Integer.parseInt(userInfo.getRole()));
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
                        if(null !=fortune)
                        {
                            tvWealth.setText(fortune.getGift());
                        }

                        if ("-".equals(userInfo.getEmail()))
                        {
                            tvEmail.setText("未认证");
                        }
                        else
                        {
                            tvEmail.setText(userInfo.getEmail());
                        }

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
        rlCollection.setOnClickListener(this);
        rlCustomer.setOnClickListener(this);
        rlUser.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        rlExtension.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvCustomer.setText(ConfigManager.instance().getSystemQq());
        tvVersion.setText("版本:V" + APPUtils.getVersionName(getActivity()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (MyApplication.getInstance().isLogin())
        {
            Map<String, String> valuePairs = new HashMap<>();
            DataRequest.instance().request(getActivity(), Urls.getUserInfoUrl(), this, HttpRequest.GET, GET_USER_DETAIL, valuePairs,
                    new UserInfoHandler());
            btnLogout.setVisibility(View.VISIBLE);
        }
        else
        {
            btnLogout.setVisibility(View.GONE);
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
        mHandler.removeCallbacksAndMessages(null);
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
            if (MyApplication.getInstance().isLogin())
            {
                Bundle b = new Bundle();
                b.putSerializable("USER_INFO", userInfo);
                startActivity(new Intent(getActivity(), UserDetailActivity.class).putExtras(b));
            }
        }
        else if (v == rlEmail)
        {
            if (MyApplication.getInstance().isLogin())
            {
                if ("未认证".equals(tvEmail.getText().toString()))
                {

                    startActivity(new Intent(getActivity(), BindEmailActivity.class));

                }

            }
            else
            {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
        else if (v == rlMessage)
        {
            if (MyApplication.getInstance().isLogin())
            {
                startActivity(new Intent(getActivity(), MessageListActivity.class));
            }
            else
            {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
        else if (v == rlWealth)
        {
            if (MyApplication.getInstance().isLogin())
            {
                startActivity(new Intent(getActivity(), WealthListActivity.class)
                        .putExtra("fortune",userInfo.getFortuneInfo()));
            }
            else
            {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
        else if (v == rlCollection)
        {
            if (MyApplication.getInstance().isLogin())
            {
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
            }
            else
            {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
        else if (v == rlCustomer)
        {
            //            if (!TextUtils.isEmpty(ConfigManager.instance().getSystemQq()))
            //            {
            //                // 跳转之前，可以先判断手机是否安装QQ
            //                if (isQQClientAvailable(getActivity()))
            //                {
            //                    // 跳转到客服的QQ
            //                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + ConfigManager.instance().getSystemQq();
            //                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //                    // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
            //                    if (isValidIntent(getActivity(), intent))
            //                    {
            //                        startActivity(intent);
            //                    }
            //                }
            //                else
            //                {
            //                    ToastUtil.show(getActivity(),"请先安装QQ");
            //                }
            //            }

            startActivity(new Intent(getActivity(), WebViewActivity.class)
                    .putExtra(WebViewActivity.EXTRA_TITLE, "帮助中心")
                    .putExtra(WebViewActivity.IS_SETTITLE, true)
                    .putExtra(WebViewActivity.EXTRA_URL, Urls.getSrviceUrl())
            );


        }
        else if (v == rlUser)
        {
            if (!MyApplication.getInstance().isLogin())
                startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        else if (v == rlSetting)
        {
            startActivity(new Intent(getActivity(), DomainNameActivity.class));
        }
        else if (v == rlExtension)
        {
            if (MyApplication.getInstance().isLogin())
            {
                startActivity(new Intent(getActivity(), WebViewActivity.class)
                        .putExtra(WebViewActivity.EXTRA_TITLE, "我的推广")
                        .putExtra(WebViewActivity.IS_SETTITLE, true)
                        .putExtra(WebViewActivity.EXTRA_URL, Urls.getInviteUrl())
                );
            }
            else
            {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        }
    }

    public boolean isQQClientAvailable(Context context)
    {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null)
        {
            for (int i = 0; i < pinfo.size(); i++)
            {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq"))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 Uri是否有效
     */
    public boolean isValidIntent(Context context, Intent intent)
    {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();


    }
}
