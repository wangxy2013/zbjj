package com.zb.wyd.json;




import com.zb.wyd.entity.VersionInfo;

import org.json.JSONObject;

/**
 * 描述：版本更新
 */
public class VersionInfoHandler extends JsonHandler
{

    private VersionInfo mVersionInfo;

    public VersionInfo getVersionInfo()
    {
        return mVersionInfo;
    }

    @Override
    protected void parseJson(JSONObject obj) throws Exception
    {
        JSONObject jsonObject = obj.optJSONObject("data");


        try
        {
            mVersionInfo = new VersionInfo(jsonObject);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
