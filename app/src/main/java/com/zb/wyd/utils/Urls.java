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
    public static final String HTTP_IP = "http://www.shanglvbuluo.com";

    public static final String BASE_URL = HTTP_IP + "/api/";

    //获取版本信息
    public static String getVersionUrl()
    {
        return BASE_URL + "merchant/search";
    }

    public static String getImgUrl(String url)
    {
        return HTTP_IP + url;
    }

    //用戶登录
    public static String getLoginUrl()
    {
        return BASE_URL + "user/login";
    }





}

