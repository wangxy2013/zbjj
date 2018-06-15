package com.zb.wyd;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.StringUtils;


/**
 * 作者：王先云 on 2016/8/5 14:46
 * 邮箱：wangxianyun1@163.com
 * 描述：一句话简单描述
 */
public class MyApplication extends Application
{
    private static MyApplication instance;

    public static MyApplication getInstance() {return instance;}


    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        APPUtils.configImageLoader(getApplicationContext());
        ConfigManager.instance().init(this);
    }


    public boolean isLogin()
    {
        if (StringUtils.stringIsEmpty(ConfigManager.instance().getUserID()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static MyApplication getContext()
    {
        return instance;
    }

}
