package com.zb.wyd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zb.wyd.fragment.AnchorCollectionFragment;
import com.zb.wyd.fragment.BaseFragment;
import com.zb.wyd.fragment.PhotoCollectionFragment;
import com.zb.wyd.fragment.VideoCollectionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{

    private String[] mTitles = new String[]{"", "", ""};

    private List<BaseFragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList)
    {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position)
    {
        //        if (position == 1) {
        //            return new PhotoCollectionFragment();
        //        } else if (position == 2) {
        //            return new VideoCollectionFragment();
        //        }
        //        return new AnchorCollectionFragment();

        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles[position];
    }
}
