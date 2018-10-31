package com.zb.wyd.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MyFragmentPagerAdapter;
import com.zb.wyd.fragment.AnchorCollectionFragment;
import com.zb.wyd.fragment.AnchorListFragment;
import com.zb.wyd.fragment.BaseFragment;
import com.zb.wyd.fragment.PhotoCollectionFragment;
import com.zb.wyd.fragment.VideoCollectionFragment;
import com.zb.wyd.widget.ViewPagerSlide;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class MyCollectionActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView      ivBack;
    @BindView(R.id.tv_title)
    TextView       tvTitle;
    @BindView(R.id.tv_anchor)
    TextView       tvAnchor;
    @BindView(R.id.tv_photo)
    TextView       tvPhoto;
    @BindView(R.id.tv_video)
    TextView       tvVideo;
    @BindView(R.id.tabLayout)
    TabLayout      mTabLayout;
    @BindView(R.id.viewPager)
    ViewPagerSlide mViewPager;

    private List<BaseFragment> fragmentList = new ArrayList<>();

    private List<TextView> tabList = new ArrayList() {};

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void initData()
    {
        fragmentList.add(new AnchorCollectionFragment());
        fragmentList.add(new PhotoCollectionFragment());
        fragmentList.add(new VideoCollectionFragment());
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_collection);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.redC));
        StatusBarUtil.StatusBarLightMode(MyCollectionActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        tvAnchor.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    protected void initViewData()
    {
        tabList.add(tvAnchor);
        tabList.add(tvPhoto);
        tabList.add(tvVideo);
        tvTitle.setText("我的收藏");
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        setTab(0);
    }


    private void setTab(int p)
    {

        for (int i = 0; i < tabList.size(); i++)
        {
            if (p == i)
            {
                tabList.get(p).setSelected(true);
            }
            else
            {
                tabList.get(i).setSelected(false);
            }
        }
        mViewPager.setCurrentItem(p);
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (v == tvAnchor)
        {
            setTab(0);
        }
        else if (v == tvPhoto)
        {
            setTab(1);
        }
        else if (v == tvVideo)
        {
            setTab(2);
        }
        else if (v == ivBack)
        {
            finish();
        }
    }

}
