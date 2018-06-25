package com.zb.wyd;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.StringUtils;


/**
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
