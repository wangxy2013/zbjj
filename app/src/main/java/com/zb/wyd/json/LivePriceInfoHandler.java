package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.LivePriceInfo;

import org.json.JSONObject;

/**
 */
public class LivePriceInfoHandler extends JsonHandler
{
    private LivePriceInfo livePriceInfo;

    public LivePriceInfo getLivePriceInfo()
    {
        return livePriceInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            livePriceInfo = new LivePriceInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
