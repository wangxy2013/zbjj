package com.zb.wyd.utils;

/**
 * URL管理类
 *
 * @since[产品/模块版本]
 * @seejlj
 */
public class Urls
{
    //UI https://lanhuapp.com/web/#/item/board?pid=3f958e36-6866-49c2-9934-24a0edd9ca7c
    //http://api.crap.cn/project.do#/152894835244607000287/interface/list/152894841894709000288
    public static final String HTTP_IP = "http://www.883974.com/";

    public static final String BASE_URL = HTTP_IP;

    //获取版本信息
    public static String getVersionUrl()
    {
        return BASE_URL + "index/config";
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
    public static String getVideoListUrl()
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

    //获取自拍详情
    public static String getPhotoDetailUrl()
    {
        return BASE_URL + "photo/show";
    }

    //获取自拍详情
    public static String getPhotoUploadUrl()
    {
        return BASE_URL + "photo/upload";
    }

    //获取自拍详情
    public static String getAddPhotoUrl()
    {
        return BASE_URL + "photo/create";
    }

    //获取定位
    public static String getIplookupUrl()
    {
        return "http://ip.ccydj.com/lbs/iplookup";
    }

    public static String getUserInfoUrl()
    {
        return BASE_URL + "user/index";
    }

    public static String getUserSignUrl()
    {
        return BASE_URL + "task/sign";
    }

    public static String getTaskUrl()
    {
        return BASE_URL + "task/index";
    }


    public static String getEmailCodeUrl()
    {
        return BASE_URL + "task/vcode";
    }

    public static String getTaskprofileUrl()
    {
        return BASE_URL + "task/profile";
    }

    public static String getFortuneDetailUrl()
    {
        return BASE_URL + "fortune/order_log";
    }


    public static String getRankingUrl()
    {
        return BASE_URL + "fortune/rank";
    }

    public static String getMessageUrl()
    {
        return BASE_URL + "msg/index";
    }

    public static String getFavoritUrl()
    {
        return BASE_URL + "favorite/index";
    }
    public static String getCollectionRequestUrl()
    {
        return BASE_URL + "favorite/like";
    }

    public static String getCommentlistUrl()
    {
        return BASE_URL + "photo/list_say";
    }


    public static String getSendCommentUrl()
    {
        return BASE_URL + "photo/say";
    }
    public static String getAnchorDetailUrl()
    {
        return BASE_URL + "live/biuty";
    }




}

