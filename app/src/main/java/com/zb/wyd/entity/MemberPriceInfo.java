package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class MemberPriceInfo
{
    private int month;
    private int season;
    private int year;

    public MemberPriceInfo(JSONObject obj)
    {
        this.month = obj.optInt("month");
        this.season = obj.optInt("season");
        this.year = obj.optInt("year");
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getSeason()
    {
        return season;
    }

    public void setSeason(int season)
    {
        this.season = season;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
}
