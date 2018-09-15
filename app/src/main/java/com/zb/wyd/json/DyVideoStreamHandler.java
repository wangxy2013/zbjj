package com.zb.wyd.json;


import com.zb.wyd.entity.ChannelInfo;

import org.json.JSONObject;

/**
 */
public class DyVideoStreamHandler extends JsonHandler
{
    private String uri;
    private String has_favorite;
    private String biz_id;

    public String getUri()
    {
        return uri;
    }

    public String getBiz_id()
    {
        return biz_id;
    }

    public String getHas_favorite()
    {
        return has_favorite;
    }

    private boolean  pay_for;

    public boolean isPay_for()
    {
        return pay_for;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            JSONObject obj = jsonObj.optJSONObject("data");

            if (null != obj)
            {
                uri = obj.optString("host") +obj.optString("uri");
                has_favorite = obj.optString("has_favorite");
                biz_id = obj.optString("biz_id");
                pay_for=obj.optBoolean("pay_for");
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
