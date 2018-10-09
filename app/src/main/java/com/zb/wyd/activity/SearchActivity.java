package com.zb.wyd.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MyFragmentPagerAdapter;
import com.zb.wyd.fragment.AnchorSearchFragment;
import com.zb.wyd.fragment.BaseFragment;
import com.zb.wyd.fragment.PhotoCollectionFragment;
import com.zb.wyd.fragment.PhotoSearchFragment;
import com.zb.wyd.fragment.VideoCollectionFragment;
import com.zb.wyd.fragment.VideoSearchFragment;
import com.zb.wyd.listener.SearchListener;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.widget.ViewPagerSlide;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：一句话简单描述
 */
public class SearchActivity extends BaseActivity
{
    @BindView(R.id.iv_back)
    ImageView      ivBack;
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
    @BindView(R.id.et_keyword)
    EditText       etKeyword;

    private List<BaseFragment> fragmentList = new ArrayList<>();

    private List<TextView> tabList = new ArrayList() {};

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void initData()
    {
        fragmentList.add(new AnchorSearchFragment());
        fragmentList.add(new PhotoSearchFragment());
        fragmentList.add(new VideoSearchFragment());
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_search);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.white));
        StatusBarUtil.StatusBarLightMode(SearchActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        tvAnchor.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        etKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    String keyword = etKeyword.getText().toString();

                    if (!TextUtils.isEmpty(keyword))
                    {

                        switch (mViewPager.getCurrentItem())
                        {
                            case 0:
                                mAnchorSearchListener.setKeyword(keyword);
                                break;

                            case 1:
                                mPhotoSearchListener.setKeyword(keyword);
                                break;

                            case 2:
                                mVideoSearchListener.setKeyword(keyword);
                                break;
                        }


                    }
                    else
                    {
                        ToastUtil.show(SearchActivity.this, "请输入搜索关键字");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initViewData()
    {
        tabList.add(tvAnchor);
        tabList.add(tvPhoto);
        tabList.add(tvVideo);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
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


    private SearchListener mAnchorSearchListener;
    private SearchListener mPhotoSearchListener;
    private SearchListener mVideoSearchListener;

    public void setAnchorSearchListener(SearchListener mAnchorSearchListener)
    {
        this.mAnchorSearchListener = mAnchorSearchListener;
    }

    public void setPhotoSearchListener(SearchListener mPhotoSearchListener)
    {
        this.mPhotoSearchListener = mPhotoSearchListener;
    }

    public void setVideoSearchListener(SearchListener mVideoSearchListener)
    {
        this.mVideoSearchListener = mVideoSearchListener;
    }
}
