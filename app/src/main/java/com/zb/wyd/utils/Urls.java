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
    //UI https://lanhuapp.com/url/RzWd2
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

    //获取真实的直播地址
    public static String getLiveStreamUrl()
    {
        return BASE_URL + "live/stream";
    }

    //获取直播价格
    public static String getLivePriceUrl()
    {
        return BASE_URL + "live/get_price";
    }

    //购买直播
    public static String getBuyLiveUrl()
    {
        return BASE_URL + "fortune/buy";
    }

    //统计时长
    public static String getStatisticsUrl()
    {
        return BASE_URL + "data/statistics";
    }

    //获取广告
    public static String getAdListUrl()
    {
        return BASE_URL + "position/get_ad";
    }

    //获取在线人数
    public static String getOnlinerUrl()
    {
        return BASE_URL + "data/get_onliner";
    }


    //获取点播流
    public static String getVideoStreamUrl()
    {
        return BASE_URL + "video/stream";
    }


    //获取点播分类
    public static String getVideoCataUrl()
    {
        return BASE_URL + "video/get_cata";
    }


    //图片分类
    public static String getPhotoCataUrl()
    {
        return BASE_URL + "photo/get_cata";
    }

    //获取图集
    public static String getPhotoListUrl()
    {
        return BASE_URL + "photo/list";
    }


    //获取图集
    public static String getVideoPriceUrl()
    {
        return BASE_URL + "video/get_price";
    }



}

