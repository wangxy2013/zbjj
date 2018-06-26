package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.WealthInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class WealthInfoListHandler extends JsonHandler
{
    private List<WealthInfo> wealthInfoList = new ArrayList<>();


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
                    WealthInfo mWealthInfo = new WealthInfo(arr.optJSONObject(i));
                    wealthInfoList.add(mWealthInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
