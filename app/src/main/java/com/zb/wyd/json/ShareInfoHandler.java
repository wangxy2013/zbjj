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


    private String sharePicUrl;

    public String getSharePicUrl()
    {
        return sharePicUrl;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            sharePicUrl = jsonObj.optString("data");

            //shareInfo = new ShareInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
