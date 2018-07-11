package com.zb.wyd.json;


import android.content.Context;
import android.content.Intent;


import com.zb.wyd.activity.LoginActivity;
import com.zb.wyd.utils.ConstantUtil;
import com.zb.wyd.utils.ToastUtil;

import org.json.JSONObject;


public abstract class JsonHandler
{

    private String resultCode = null;
    private String resultMsg  = null;

    protected abstract void parseJson(JSONObject obj) throws Exception;

    public void parseJson(Context mContext, String jsonString)
    {
        try
        {
            if (null == jsonString)
            {
                setResultCode(ConstantUtil.RESULT_FAIL);
            }
            else
            {
                JSONObject jsonObject = new JSONObject(jsonString);

                if ("true".equals(jsonObject.optString("status")) || "true".equals(jsonObject.optString("count")))
                {
                    setResultCode(ConstantUtil.RESULT_SUCCESS);
                }
                else
                {
                    if ("1904".equals(jsonObject.optString("code")))
                    {
                        if (null != mContext)
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }

                    setResultCode(jsonObject.optString("code"));
                }
                setResultMsg(jsonObject.optString("message"));
                parseJson(jsonObject);
            }
        } catch (Exception e)
        {
            if(jsonString.contains("baidu.com"))
            {
                setResultCode(ConstantUtil.RESULT_SUCCESS);
                try
                {
                    parseJson(new JSONObject().put("baidu",jsonString));
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
            else
            {
                setResultCode(ConstantUtil.RESULT_FAIL);
                setResultMsg("网络请求失败...");
            }

        }

    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultMsg()
    {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg)
    {
        this.resultMsg = resultMsg;
    }

}
