package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.LiveInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AdInfoListHandler extends JsonHandler
{
    private List<AdInfo> adInfoList = new ArrayList<>();

    public List<AdInfo> getAdInfoList()
    {
        return adInfoList;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {
            JSONArray arr = jsonObj.optJSONArray("data");


            if (null != arr)
            {
                for (int i = 0; i < arr.length(); i++)
                {
                    AdInfo mAdInfo = new AdInfo(arr.optJSONObject(i));
                    adInfoList.add(mAdInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
