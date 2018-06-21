package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class AdInfo
{

    private String id;//1
    private String aname;//主播
    private String image;//http://m.kejet.net/ms/18317/b46b02d45.jpg
    private String link;//video://12 private String

    public AdInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.aname = obj.optString("aname");
        this.image = obj.optString("image");
        this.link = obj.optString("link");

    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAname()
    {
        return aname;
    }

    public void setAname(String aname)
    {
        this.aname = aname;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
