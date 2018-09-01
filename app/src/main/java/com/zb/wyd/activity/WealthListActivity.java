package com.zb.wyd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.adapter.MyViewPagerAdapter;
import com.zb.wyd.entity.FortuneInfo;
import com.zb.wyd.fragment.CashRecordFragment;
import com.zb.wyd.fragment.CommissionRecordFragment;
import com.zb.wyd.fragment.IntegralRecordFragment;
import com.zb.wyd.http.IRequestListener;
import com.zb.wyd.utils.ToastUtil;
import com.zb.wyd.widget.AutoFitTextView;
import com.zb.wyd.widget.statusbar.StatusBarUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zb.wyd.utils.APPUtils.dip2px;

/**
 */
public class WealthListActivity extends BaseActivity implements View.OnClickListener, IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView  tvTitle;


    private static final String GET_WEALTH_LIST = "get_wealth_list";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL    = 0x02;
    @BindView(R.id.tv_submit)
    TextView  tvSubmit;
    @BindView(R.id.tv_recharge_balance)
    TextView  tvRechargeBalance;
    @BindView(R.id.tv_income_balance)
    TextView  tvIncomeBalance;
    @BindView(R.id.tv_fortune)
    TextView  tvFortune;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private List<String> tabs = new ArrayList<>(); //标签名称

    private FortuneInfo fortuneInfo;
    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:

                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(WealthListActivity.this, msg.obj.toString());

                    break;


            }
        }
    };

    @Override
    protected void initData()
    {
        tabs.add("积分记录");
        tabs.add("现金记录");
        tabs.add("佣金记录");
        fortuneInfo = (FortuneInfo) getIntent().getSerializableExtra("fortune");
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_my_wealth);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(WealthListActivity.this, false);
    }


    @Override
    protected void initEvent()
    {
        mBackIv.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("我的财富");
        tvSubmit.setText("充值");
        tvSubmit.setVisibility(View.VISIBLE);

        if (null != fortuneInfo)
        {
            tvFortune.setText(fortuneInfo.getGift());
            tvRechargeBalance.setText(fortuneInfo.getCash());
            tvIncomeBalance.setText(fortuneInfo.getRebate());
        }

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new IntegralRecordFragment(), "积分记录");//添加Fragment
        viewPagerAdapter.addFragment(new CashRecordFragment(), "现金记录");
        viewPagerAdapter.addFragment(new CommissionRecordFragment(), "佣金记录");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.addTab(mTabLayout.newTab().setText("积分记录"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("现金记录"));
        mTabLayout.addTab(mTabLayout.newTab().setText("佣金记录"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        setTabView();
        reflex(mTabLayout);


    }

    private ViewHolder holder;

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
        }
    }


    class ViewHolder
    {
        AutoFitTextView tvTabName;

        public ViewHolder(View tabView)
        {
            tvTabName = (AutoFitTextView) tabView.findViewById(R.id.tv_tab_name);
        }
    }


    @Override
    public void onClick(View v)
    {
        if (v == mBackIv)
        {
            finish();
        }
        else if (v == tvSubmit)
        {
            startActivity(new Intent(WealthListActivity.this, MyMasonryActivity.class).putExtra("CASH", fortuneInfo.getCash()));
        }

    }


    public void reflex(final TabLayout tabLayout)
    {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++)
                    {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0)
                        {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {

    }


}
