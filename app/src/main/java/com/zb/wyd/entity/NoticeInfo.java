package com.zb.wyd.entity;

import org.json.JSONObject;

public class NoticeInfo
{
    private String link;
    private String frontContent;
    private String backContent;
    private String color;

    public NoticeInfo(){};
    public NoticeInfo(JSONObject obj)
    {
        this.link = obj.optString("link");
        this.frontContent = obj.optString("txt");
        this.color = obj.optString("color");
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getFrontContent()
    {
        return frontContent;
    }

    public void setFrontContent(String frontContent)
    {
        this.frontContent = frontContent;
    }

    public String getBackContent()
    {
        return backContent;
    }

    public void setBackContent(String backContent)
    {
        this.backContent = backContent;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
