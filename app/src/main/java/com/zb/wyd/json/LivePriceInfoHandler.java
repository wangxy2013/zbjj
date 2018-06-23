package com.zb.wyd.json;


import com.zb.wyd.entity.PriceInfo;

import org.json.JSONObject;

/**
 */
public class LivePriceInfoHandler extends JsonHandler
{
    private PriceInfo livePriceInfo;

    public PriceInfo getLivePriceInfo()
    {
        return livePriceInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            livePriceInfo = new PriceInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
