package com.zb.wyd.json;


import com.zb.wyd.entity.ChannelInfo;
import com.zb.wyd.entity.LiveInfo;

import org.json.JSONObject;

/**
 */
public class VideoStreamHandler extends JsonHandler
{
    private ChannelInfo channelInfo;
    private String      uri;
    private String has_favorite;
    private Boolean 	stand;
    public ChannelInfo getChannelInfo()
    {
        return channelInfo;
    }

    public String getUri()
    {
        return uri;
    }

    public String getHas_favorite()
    {
        return has_favorite;
    }

    public Boolean getStand()
    {
        return stand;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            JSONObject obj = jsonObj.optJSONObject("data");

            if (null != obj)
            {
                uri = obj.optString("uri");
                stand= obj.optBoolean("stand");
                has_favorite = obj.optString("has_favorite");
                channelInfo = new ChannelInfo(obj.optJSONObject("channel"));
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
