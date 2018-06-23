package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.SelfieInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SelfieInfoListHandler extends JsonHandler
{
    private List<SelfieInfo> selfieInfoList = new ArrayList<>();

    public List<SelfieInfo> getSelfieInfoList()
    {
        return selfieInfoList;
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
                    SelfieInfo mSelfieInfo = new SelfieInfo(arr.optJSONObject(i));
                    selfieInfoList.add(mSelfieInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
