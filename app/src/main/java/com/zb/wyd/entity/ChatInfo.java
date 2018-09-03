package com.zb.wyd.entity;

import org.json.JSONObject;

public class ChatInfo
{

    private String data;
    private String action;
    private String type;


    public ChatInfo(JSONObject obj)
    {
        this.data = obj.optString("data");
        this.action = obj.optString("action");
        this.type = obj.optString("type");
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
