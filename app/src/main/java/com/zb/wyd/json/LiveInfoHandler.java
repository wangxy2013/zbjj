package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class LiveInfoHandler extends JsonHandler
{
    private LiveInfo liveInfo;

    public LiveInfo getLiveInfo()
    {
        return liveInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            liveInfo = new LiveInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
