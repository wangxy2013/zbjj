package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.MessageInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MessageInfoListHandler extends JsonHandler
{
    private List<MessageInfo> messageInfoList = new ArrayList<>();

    public List<MessageInfo> getMessageInfoList()
    {
        return messageInfoList;
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
                    MessageInfo mMessageInfo = new MessageInfo(arr.optJSONObject(i));
                    messageInfoList.add(mMessageInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
