package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class LiveInfoListHandler extends JsonHandler
{
    private List<LiveInfo> userInfoList = new ArrayList<>();

    public List<LiveInfo> getUserInfoList()
    {
        return userInfoList;
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
                    LiveInfo mBillInfo = new LiveInfo(arr.optJSONObject(i));
                    userInfoList.add(mBillInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
