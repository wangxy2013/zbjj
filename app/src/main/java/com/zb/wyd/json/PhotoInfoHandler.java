package com.zb.wyd.json;


import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.entity.PhotoInfo;

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
            photoInfo = new PhotoInfo(obj);

            if(null != obj)
            {
                PriceInfo priceInfo = new PriceInfo(obj.optJSONObject("price"));
                photoInfo.setPriceInfo(priceInfo);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
