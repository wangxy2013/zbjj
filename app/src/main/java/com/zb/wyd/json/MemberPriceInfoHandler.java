package com.zb.wyd.json;


import com.zb.wyd.entity.MemberPriceInfo;
import com.zb.wyd.entity.PhotoInfo;
import com.zb.wyd.entity.PriceInfo;
import com.zb.wyd.entity.UserInfo;

import org.json.JSONObject;

/**
 */
public class MemberPriceInfoHandler extends JsonHandler
{

    private MemberPriceInfo memberPriceInfo;

    public MemberPriceInfo getMemberPriceInfo()
    {
        return memberPriceInfo;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {

            JSONObject obj = jsonObj.optJSONObject("data");

            if (null != obj)
            {
                memberPriceInfo = new MemberPriceInfo(obj);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
