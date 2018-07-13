package com.zb.wyd.json;


import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.entity.PhotoInfo;
import com.zb.wyd.entity.UserInfo;

import org.json.JSONObject;

/**
 */
public class PhotoInfoHandler extends JsonHandler
{
    private PhotoInfo photoInfo;

    public PhotoInfo getPhotoInfo()
    {
        return photoInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            JSONObject obj = jsonObj.optJSONObject("data");

            if (null != obj)
            {
                photoInfo = new PhotoInfo(obj);
                if(null != obj.optJSONObject("price"))
                photoInfo.setPriceInfo(new PriceInfo(obj.optJSONObject("price")));

                if(null != obj.optJSONObject("userinfo"))
                photoInfo.setUserInfo(new UserInfo(obj.optJSONObject("userinfo")));
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
