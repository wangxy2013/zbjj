package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class SelfieInfo
{
    private String id;//11  
    private String pname;//
    private String pdesc;//
    private String location;//--
    private String cover;//
    private String favour_count;//0
    private String add_time;//1529655463"

    public SelfieInfo() {}

    public SelfieInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.pname = obj.optString("pname");
        this.pdesc = obj.optString("pdesc");
        this.location = obj.optString("location");
        this.favour_count = obj.optString("favour_count");
        this.cover = obj.optString("cover");
        this.add_time = obj.optString("add_time");
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPname()
    {
        return pname;
    }

    public void setPname(String pname)
    {
        this.pname = pname;
    }

    public String getPdesc()
    {
        return pdesc;
    }

    public void setPdesc(String pdesc)
    {
        this.pdesc = pdesc;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getFavour_count()
    {
        return favour_count;
    }

    public void setFavour_count(String favour_count)
    {
        this.favour_count = favour_count;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }
}
