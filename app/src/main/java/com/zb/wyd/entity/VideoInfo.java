package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class VideoInfo
{
    private String id;//5
    private String cata_id;//10
    private String v_name;//Caribbean)(122912-222)元芸能人のリアルアクメ 優希まこと
    private String cover;//https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg
    private String favour_count;//0
    private String fee_cfg;//0
    private String fee;//3 private String

    public VideoInfo() {}

    public VideoInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.cata_id = obj.optString("cata_id");
        this.v_name = obj.optString("v_name");
        this.cover = obj.optString("cover");
        this.favour_count = obj.optString("favour_count");
        this.fee_cfg = obj.optString("fee_cfg");
        this.fee = obj.optString("fee");

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCata_id()
    {
        return cata_id;
    }

    public void setCata_id(String cata_id)
    {
        this.cata_id = cata_id;
    }

    public String getV_name()
    {
        return v_name;
    }

    public void setV_name(String v_name)
    {
        this.v_name = v_name;
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

    public String getFee_cfg()
    {
        return fee_cfg;
    }

    public void setFee_cfg(String fee_cfg)
    {
        this.fee_cfg = fee_cfg;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }
}