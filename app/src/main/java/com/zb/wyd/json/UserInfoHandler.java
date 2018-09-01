package com.zb.wyd.json;


import com.zb.wyd.entity.FortuneInfo;
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

            JSONObject obj = jsonObj.optJSONObject("data");
            if (null != obj)
            {
                userInfo = new UserInfo(obj);

                FortuneInfo mFortuneInfo = new FortuneInfo(obj.optJSONObject("fortune"));
                userInfo.setFortuneInfo(mFortuneInfo);

            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
