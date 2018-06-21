package com.zb.wyd.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zb.wyd.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：一句话简单描述
 */
public class LivePlatformFragment extends BaseFragment
{

    private View rootView = null;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_live_platform, null);
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
    private static LivePlatformFragment instance = null;

    public static LivePlatformFragment newInstance()
    {
        if (instance == null)
        {
            instance = new LivePlatformFragment();
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

    }

    @Override
    protected void initViewData()
    {

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
}
