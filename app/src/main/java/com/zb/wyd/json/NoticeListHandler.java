package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.NoticeInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class NoticeListHandler extends JsonHandler
{
    private List<NoticeInfo> noticeInfoList = new ArrayList<>();

    public List<NoticeInfo> getNoticeInfoList()
    {
        return noticeInfoList;
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
                    NoticeInfo mAdInfo = new NoticeInfo(arr.optJSONObject(i));
                    noticeInfoList.add(mAdInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
