package com.zb.wyd.entity;


import org.json.JSONObject;

import java.io.Serializable;

public class FortuneInfo implements Serializable
{
    private String cash;//是充值的现金
    private String gift;//是积分
    private String rebate;//是佣金
    private String earning;//暂时不用


    public FortuneInfo(JSONObject obj)
    {
        this.cash = obj.optString("cash");
        this.gift = obj.optString("gift");
        this.rebate = obj.optString("rebate");
        this.earning = obj.optString("earning");
    }

    public String getCash()
    {
        return cash;
    }

    public void setCash(String cash)
    {
        this.cash = cash;
    }

    public String getGift()
    {
        return gift;
    }

    public void setGift(String gift)
    {
        this.gift = gift;
    }

    public String getRebate()
    {
        return rebate;
    }

    public void setRebate(String rebate)
    {
        this.rebate = rebate;
    }

    public String getEarning()
    {
        return earning;
    }

    public void setEarning(String earning)
    {
        this.earning = earning;
    }
}
