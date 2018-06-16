package com.zb.wyd.json;


import com.zb.wyd.entity.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class UserInfoListHandler extends JsonHandler
{
    private List<UserInfo> userInfoList = new ArrayList<>();

    public List<UserInfo> getUserInfoList()
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
                    UserInfo mBillInfo = new UserInfo(arr.optJSONObject(i));
                    userInfoList.add(mBillInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
