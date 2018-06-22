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

    public ChannelInfo getChannelInfo()
    {
        return channelInfo;
    }

    public String getUri()
    {
        return uri;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            JSONObject obj = jsonObj.optJSONObject("data");
            uri = obj.optString("uri");
            if (null != obj)
            {
                channelInfo = new ChannelInfo(obj.optJSONObject("channel"));
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
