package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class RankingInfo
{
    private String uid;//31
    private String coupon;//10
    private String uface;//1
    private String unick;//-

    public RankingInfo(JSONObject obj)
    {
        this.uid = obj.optString("uid");
        this.coupon = obj.optString("coupon");
        this.uface = obj.optString("uface");
        this.unick = obj.optString("unick");
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getCoupon()
    {
        return coupon;
    }

    public void setCoupon(String coupon)
    {
        this.coupon = coupon;
    }

    public String getUface()
    {
        return uface;
    }

    public void setUface(String uface)
    {
        this.uface = uface;
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
