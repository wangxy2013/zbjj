package com.zb.wyd.json;

import com.zb.wyd.utils.ConfigManager;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class LoginHandler extends JsonHandler
{


    @Override
    protected void parseJson(JSONObject jsonObject) throws Exception
    {
        try
        {
            JSONObject obj = jsonObject.optJSONObject("data");

            if (null != obj)
            {
                String uniqueCode = obj.optString("auth");
                String uid = obj.optString("id");
                String uname = obj.optString("uname");
                ConfigManager.instance().setUniqueCode(uniqueCode);
                ConfigManager.instance().setUserId(uid);
                ConfigManager.instance().setUserName(uname);
                ConfigManager.instance().setUserNickName(obj.optString("unick"));
                ConfigManager.instance().setVipLevel(obj.optInt("vip_level"));
                ConfigManager.instance().setVipType(obj.optInt("vip_type"));
                ConfigManager.instance().setUserRole(obj.optInt("role"));
                ConfigManager.instance().setUserPic(obj.optString("uface"));
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}