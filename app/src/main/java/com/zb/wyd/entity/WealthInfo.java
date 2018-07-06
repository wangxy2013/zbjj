package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class WealthInfo
{
    private String title;
    private String time;
    private int coupon;
    private String direct;
    private int cash;
    public WealthInfo(JSONObject object)
    {
        this.title = object.optString("remark");
        this.time = object.optString("add_time");
        this.coupon = object.optInt("coupon");
        this.direct =  object.optString("direct");
        this.cash =  object.optInt("cash");

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

    public int getCoupon()
    {
        return coupon;
    }

    public void setCoupon(int coupon)
    {
        this.coupon = coupon;
    }

    public String getDirect()
    {
        return direct;
    }

    public void setDirect(String direct)
    {
        this.direct = direct;
    }

    public int getCash()
    {
        return cash;
    }

    public void setCash(int cash)
    {
        this.cash = cash;
    }
}
