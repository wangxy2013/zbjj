package com.zb.wyd.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MyFragmentPagerAdapter;

/**
 * 描述：一句话简单描述
 */
public class CollectionActivity1 extends AppCompatActivity
{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collection);

        //初始化视图
        initViews();
    }

    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
      //  myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
//        one = mTabLayout.getTabAt(0);
//        two = mTabLayout.getTabAt(1);
//        three = mTabLayout.getTabAt(2);

        //设置Tab的图标
        //        one.setIcon(R.mipmap.ic_launcher);
        //        two.setIcon(R.mipmap.ic_launcher);
        //        three.setIcon(R.mipmap.ic_launcher);
        //        four.setIcon(R.mipmap.ic_launcher);


    }
}