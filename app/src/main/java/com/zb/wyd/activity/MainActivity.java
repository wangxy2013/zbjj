package com.zb.wyd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.zb.wyd.MyApplication;
import com.zb.wyd.R;
import com.zb.wyd.fragment.LiveFragment;
import com.zb.wyd.fragment.MemberFragment;
import com.zb.wyd.fragment.SelfieFragment;
import com.zb.wyd.fragment.TaskFragment;
import com.zb.wyd.fragment.VideoFragment;
import com.zb.wyd.fragment.VideoFragment1;
import com.zb.wyd.utils.DialogUtils;
import com.zb.wyd.utils.VersionManager;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import butterknife.BindView;


public class MainActivity extends BaseActivity
{

    @BindView(android.R.id.tabhost)
    FragmentTabHost fragmentTabHost;


    private String texts[]       = {"直播", "视频", "自拍", "任务", "会员"};
    private int    imageButton[] = {
            R.drawable.ic_live_selector, R.drawable.ic_video_selector,
            R.drawable.ic_photo_selector, R.drawable.ic_task_selector, R.drawable.ic_member_selector};


    private Class fragmentArray[] = {LiveFragment.class, VideoFragment1.class, SelfieFragment.class, TaskFragment.class, MemberFragment.class};

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(MainActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                if ("会员".equals(tabId))
                {
                    if (!MyApplication.getInstance().isLogin())
                    {
                        fragmentTabHost.setCurrentTab(0);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }
            }
        });
    }

    public  void setTab(int p)
    {
        fragmentTabHost.setCurrentTab(p);
    }

    @Override
    protected void initViewData()
    {
        fragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.main_layout);

        for (int i = 0; i < texts.length; i++)
        {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

            fragmentTabHost.addTab(spec, fragmentArray[i], null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.main_tab_selector);
        }
        fragmentTabHost.getTabWidget().setDividerDrawable(R.color.transparent);

        new VersionManager(this).init();

    }


    private View getView(int i)
    {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabcontent, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(texts[i]);
        return view;
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            DialogUtils.showToastDialog2Button(MainActivity.this, "是否退出APP", v -> finish());

            return false;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }

    }
}
