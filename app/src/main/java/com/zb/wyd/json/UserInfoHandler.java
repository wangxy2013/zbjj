package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.UserInfo;

import org.json.JSONObject;

/**
 */
public class UserInfoHandler extends JsonHandler
{
    private UserInfo userInfo;

    public UserInfo getUserInfo()
    {
        return userInfo;
    }


    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {


            userInfo = new UserInfo(jsonObj.optJSONObject("data"));


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
