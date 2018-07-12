package com.zb.wyd.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * 描述：一句话简单描述
 */
public class PlatformUtil
{
    public static final String PACKAGE_WECHAT    = "com.tencent.mm";
    public static final String PACKAGE_MOBILE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QZONE     = "com.qzone";
    public static final String PACKAGE_SINA      = "com.sina.weibo";

    // 判断是否安装指定app
    public static boolean isInstallApp(Context context, String app_package)
    {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        if (pInfo != null)
        {
            for (int i = 0; i < pInfo.size(); i++)
            {
                String pn = pInfo.get(i).packageName;
                if (app_package.equals(pn))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

