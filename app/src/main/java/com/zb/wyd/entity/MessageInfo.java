package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class MessageInfo
{
    private String type;
    private String time;
    private String content;

    public MessageInfo(JSONObject obj)
    {
        this.time = obj.optString("time");
        this.type = obj.optString("type");
        this.content = obj.optString("content");
    }


    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
