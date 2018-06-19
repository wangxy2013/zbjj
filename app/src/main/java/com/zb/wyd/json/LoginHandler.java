package com.zb.wyd.json;

import com.zb.wyd.utils.ConfigManager;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class LoginHandler extends JsonHandler
{

    private String need_bind;//0:正常登录 1：需要绑定手机号

    public String getNeed_bind()
    {
        return need_bind;
    }

    @Override
    protected void parseJson(JSONObject obj) throws Exception
    {
        try
        {
            need_bind = obj.optString("need_bind");
            String uniqueCode = obj.optString("uniqueCode");
            String uid = obj.optString("uid");
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