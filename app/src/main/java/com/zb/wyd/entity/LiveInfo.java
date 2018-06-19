package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class LiveInfo
{
    private String id;//12570;//
    private String nick;//晓宝宝;//
    private String face;//http:\/\/img2.inke.cn\/MTUxMzUxNTM3MDU2NSM1MjQjanBn.jpg;//
    private String uri;//http:\/\/alsource.pull.inke.cn\/live\/1529036907860561.flv?ikDnsOp=1&ikHost=ali&ikOp=0&codecInfo=8192&ikLog=0&dpSrcG=-1&ikMinBuf
    // =3800&ikMaxBuf=4800&ikSlowRate=1.0&ikFastRate=1.0;//
    private String favour_count;//0;//
    private String update_time;//1529037601;//
    private String online;//12345,
    private String is_live;//1


    public LiveInfo() {}

    public LiveInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.nick = obj.optString("nick");
        this.face = obj.optString("face");
        this.uri = obj.optString("id");
        this.favour_count = obj.optString("favour_count");
        this.update_time = obj.optString("update_time");
        this.online = obj.optString("online");
        this.is_live = obj.optString("is_live");

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    public String getFace()
    {
        return face;
    }

    public void setFace(String face)
    {
        this.face = face;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getFavour_count()
    {
        return favour_count;
    }

    public void setFavour_count(String favour_count)
    {
        this.favour_count = favour_count;
    }

    public String getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(String update_time)
    {
        this.update_time = update_time;
    }

    public String getOnline()
    {
        return online;
    }

    public void setOnline(String online)
    {
        this.online = online;
    }

    public String getIs_live()
    {
        return is_live;
    }

    public void setIs_live(String is_live)
    {
        this.is_live = is_live;
    }
}
