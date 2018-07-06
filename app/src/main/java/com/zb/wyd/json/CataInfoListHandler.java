package com.zb.wyd.json;


import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.LiveInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CataInfoListHandler extends JsonHandler
{
    private List<CataInfo> cataInfoList = new ArrayList<>();

    public List<CataInfo> getCataInfoList()
    {
        return cataInfoList;
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
                    CataInfo mCataInfo = new CataInfo(arr.optJSONObject(i));
                    cataInfoList.add(mCataInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
