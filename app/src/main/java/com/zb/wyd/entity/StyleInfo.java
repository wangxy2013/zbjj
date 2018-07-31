package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class StyleInfo
{
    private String font;//bold",
    private String color;//#000000",
    private String hot;// 1

    public StyleInfo (JSONObject obj)
    {
        this.font = obj.optString("font");
        this.color = obj.optString("color");
        this.hot = obj.optString("hot");
    }


    public String getFont()
    {
        return font;
    }

    public void setFont(String font)
    {
        this.font = font;
    }



    public String getHot()
    {
        return hot;
    }

    public void setHot(String hot)
    {
        this.hot = hot;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
