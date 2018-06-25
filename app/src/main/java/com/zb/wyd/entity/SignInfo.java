package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class SignInfo
{
    private String val;
    private String series;


    public SignInfo(JSONObject obj)
    {
        this.val = obj.optString("val");
        this.series = obj.optString("series");
    }

    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
    }

    public String getSeries()
    {
        return series;
    }

    public void setSeries(String series)
    {
        this.series = series;
    }
}
