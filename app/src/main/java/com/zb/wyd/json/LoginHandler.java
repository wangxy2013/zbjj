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
            String uniqueCode = obj.optString("auth");
            String uid = obj.optString("id");
            String mobile = obj.optString("mobile");
            String unick = obj.optString("unick");
            ConfigManager.instance().setUniqueCode(uniqueCode);
            ConfigManager.instance().setUserId(uid);
            ConfigManager.instance().setMobile(mobile);
            ConfigManager.instance().setUserNickName(unick);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}