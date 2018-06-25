package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.SignInfo;

import org.json.JSONObject;

/**
 */
public class SignInfoHandler extends JsonHandler
{
    private SignInfo signInfo;

    public SignInfo getSignInfo()
    {
        return signInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            signInfo = new SignInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
