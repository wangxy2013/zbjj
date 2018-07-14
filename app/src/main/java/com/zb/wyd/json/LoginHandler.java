package com.zb.wyd.json;

import com.zb.wyd.MyApplication;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.ToastUtil;

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
                ConfigManager.instance().setUserNickName(obj.optString("unick"));
                ConfigManager.instance().setVipLevel(obj.optInt("vip_level"));

            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}