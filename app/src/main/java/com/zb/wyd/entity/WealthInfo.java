package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class WealthInfo
{
    private String  title;
    private String  time;
    private  String  desc;


    public  WealthInfo(JSONObject object)
    {
        this.title = object.optString("title");
        this.time = object.optString("time");
        this.desc = object.optString("desc");
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
