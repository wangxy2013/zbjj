package com.zb.wyd.entity;

import org.json.JSONObject;

public class ChatInfo
{

    private String data;
    private String action;
    private String type;

    private UserInfo userInfo;


    public ChatInfo(JSONObject obj)
    {
        this.data = obj.optString("data");
        this.action = obj.optString("action");
        this.type = obj.optString("type");

        JSONObject userObj = obj.optJSONObject("userinfo");

        if(null != userObj)
        {
            UserInfo mUserInfo = new UserInfo(userObj);
            setUserInfo(mUserInfo);
        }
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

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
}
