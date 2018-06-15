package com.zb.wyd.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：王先云 on 2018/6/14 14:59
 * 邮箱：wangxianyun1@163.com
 * 描述：一句话简单描述
 */
public class LiveFragment extends BaseFragment
{

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private View rootView = null;
    private Unbinder   unbinder;
    private ViewHolder holder;
    private List<String> tabs = new ArrayList<>(); //标签名称

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_live, null);
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
        tabs.add("直播首页");
        tabs.add("主播列表");
        tabs.add("直播平台");
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
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(LiveIndexFragment.newInstance(), "直播首页");//添加Fragment
        viewPagerAdapter.addFragment(AnchorListFragment.newInstance(), "主播列表");
        viewPagerAdapter.addFragment(LivePlatformFragment.newInstance(), "直播平台");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.addTab(mTabLayout.newTab().setText("直播首页"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("主播列表"));
        mTabLayout.addTab(mTabLayout.newTab().setText("直播平台"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        setTabView();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(true);
                //选中后字体变大
                holder.tvTabName.setTextSize(20);
                //让Viewpager跟随TabLayout的标签切换
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(false);
                //恢复为默认字体大小
                holder.tvTabName.setTextSize(16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });


    }

    /**
     * 设置Tab的样式
     */
    private void setTabView()
    {
        holder = null;
        for (int i = 0; i < 3; i++)
        {
            //依次获取标签
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.item_tab);
            holder = new ViewHolder(tab.getCustomView());
            //为标签填充数据
            holder.tvTabName.setText(tabs.get(i));
            //默认选择第一项
            if (i == 0)
            {
                holder.tvTabName.setSelected(true);
                holder.tvTabName.setTextSize(20);
            }
        }
    }


    class ViewHolder
    {
        TextView tvTabName;

        public ViewHolder(View tabView)
        {
            tvTabName = (TextView) tabView.findViewById(R.id.tv_tab_name);
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
}
