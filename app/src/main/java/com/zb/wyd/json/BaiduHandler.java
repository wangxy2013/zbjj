package com.zb.wyd.json;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class BaiduHandler extends JsonHandler
{
    private String content;

    public String getContent()
    {
        return content;
    }

    @Override
    protected void parseJson(JSONObject obj) throws Exception
    {
        content = obj.getString("baidu");
    }
}
