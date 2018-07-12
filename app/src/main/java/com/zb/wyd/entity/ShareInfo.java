package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class ShareInfo
{
    private String  title;//分享标题",
    private String  cover;//https:\/\/www.baidu.com\/img\/bd_logo1.png",
    private String  url;//https:\/\/www.baidu.com\/?token=qvHO&co_biz=photo"


    public ShareInfo(JSONObject obj)
    {
        this.title =obj.optString("title");
        this.cover =obj.optString("cover");
        this.url =obj.optString("url");
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
