package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class LocationInfo
{
    private String nation;//中国 
    private String prov;//江苏
    private String city;//南京
    private String district;//栖霞
    private String lat;//32.150382
    private String lng;//118.946930
    private String isp;//0

    public LocationInfo(JSONObject obj)
    {
        this.nation = obj.optString("nation");
        this.prov = obj.optString("prov");
        this.city = obj.optString("city");
        this.district = obj.optString("district");
        this.lat = obj.optString("lat");
        this.lng = obj.optString("lng");
        this.isp = obj.optString("isp");
    }

    public String getNation()
    {
        return nation;
    }

    public void setNation(String nation)
    {
        this.nation = nation;
    }

    public String getProv()
    {
        return prov;
    }

    public void setProv(String prov)
    {
        this.prov = prov;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLng()
    {
        return lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getIsp()
    {
        return isp;
    }

    public void setIsp(String isp)
    {
        this.isp = isp;
    }
}
