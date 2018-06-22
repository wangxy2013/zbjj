package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 作者：王先云 on 2018/6/22 15:25
 * 邮箱：wangxianyun1@163.com
 * 描述：一句话简单描述
 */
public class ChannelInfo
{
    private String yd;//http://vod.vxinda.com
    private String dx;//http://vod2.vxinda.com
    private String cm;//http://vod3.vxinda.com


    public ChannelInfo(JSONObject obj)
    {
        this.yd = obj.optString("yd");
        this.dx = obj.optString("dx");
        this.cm = obj.optString("cm");
    }

    public String getYd()
    {
        return yd;
    }

    public void setYd(String yd)
    {
        this.yd = yd;
    }

    public String getDx()
    {
        return dx;
    }

    public void setDx(String dx)
    {
        this.dx = dx;
    }

    public String getCm()
    {
        return cm;
    }

    public void setCm(String cm)
    {
        this.cm = cm;
    }
}
