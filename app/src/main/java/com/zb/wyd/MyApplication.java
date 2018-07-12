package com.zb.wyd;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.LogUtil;
import com.zb.wyd.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：一句话简单描述
 */
public class MyApplication extends Application
{
    private List<Integer> photoDataList = new ArrayList<>();
    private static MyApplication instance;

    public static MyApplication getInstance() {return instance;}


    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        APPUtils.configImageLoader(getApplicationContext());
        ConfigManager.instance().init(this);
        initPhotoData();
    }

    public List<Integer> getPhotoDataList()
    {
        return photoDataList;
    }

    private void initPhotoData()
    {
        for (int i = 0; i < 1000; i++)
        {
            int randmon = (int) (Math.random() * 200);
            photoDataList.add(randmon);
        }
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
