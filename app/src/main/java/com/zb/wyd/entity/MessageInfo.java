package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class MessageInfo
{
    private String id;//3
    private String uid;//0
    private String to_uid;//20
    private String message;//这是系统发送给test003的消息
    private String status;//0
    private String expire_time;//1530980592
    private String add_time;//2018-07-06 20:36:32
    private String unick;//

    public MessageInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.uid = obj.optString("uid");
        this.to_uid = obj.optString("to_uid");
        this.message = obj.optString("message");
        this.status = obj.optString("status");
        this.add_time = obj.optString("add_time");
        this.expire_time = obj.optString("expire_time");
        this.unick = obj.optString("unick");

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getTo_uid()
    {
        return to_uid;
    }

    public void setTo_uid(String to_uid)
    {
        this.to_uid = to_uid;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getExpire_time()
    {
        return expire_time;
    }

    public void setExpire_time(String expire_time)
    {
        this.expire_time = expire_time;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }

    public String getUnick()
    {
        return unick;
    }

    public void setUnick(String unick)
    {
        this.unick = unick;
    }
}
