package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.ShareInfo;

import org.json.JSONObject;

/**
 */
public class ShareInfoHandler extends JsonHandler
{
    private ShareInfo  shareInfo;

    public ShareInfo getShareInfo()
    {
        return shareInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            shareInfo = new ShareInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
