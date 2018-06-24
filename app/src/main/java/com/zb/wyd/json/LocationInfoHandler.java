package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.LocationInfo;

import org.json.JSONObject;

/**
 */
public class LocationInfoHandler extends JsonHandler
{
    private LocationInfo locationInfo;

    public LocationInfo getLocationInfo()
    {
        return locationInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            locationInfo = new LocationInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
