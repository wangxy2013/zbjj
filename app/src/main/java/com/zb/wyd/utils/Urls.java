package com.zb.wyd.utils;

/**
 * URL管理类
 *
 * @date 2014年9月16日 上午9:48:03
 * @since[产品/模块版本]
 * @seejlj
 */
public class Urls
{
    //http://api.crap.cn/project.do#/152894835244607000287/interface/list/152894841894709000288
    public static final String HTTP_IP = "http://www.883974.com/";

    public static final String BASE_URL = HTTP_IP;

    //获取版本信息
    public static String getVersionUrl()
    {
        return BASE_URL + "merchant/search";
    }


    //用戶登录
    public static String getLoginUrl()
    {
        return BASE_URL + "user/login";
    }


    //用戶注册
    public static String getRegisterUrl()
    {
        return BASE_URL + "user/register";
    }


    //限时免费
    public static String getFreeLive()
    {
        return BASE_URL + "live/free";
    }

    //新晋主播
    public static String getNewLive()
    {
        return BASE_URL + "live/index";
    }

    //点播
    public static String getVideoLive()
    {
        return BASE_URL + "video/list";
    }


}

