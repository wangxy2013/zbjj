package com.zb.wyd.json;


import com.zb.wyd.entity.LiveInfo;
import com.zb.wyd.entity.UserInfo;
import com.zb.wyd.entity.VideoInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class VideoInfoListHandler extends JsonHandler
{
    private List<VideoInfo> videoInfoList = new ArrayList<>();

    public List<VideoInfo> getVideoInfoList()
    {
        return videoInfoList;
    }


    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {
            JSONArray arr = jsonObj.optJSONArray("data");


            if (null != arr)
            {
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject videoObj = arr.optJSONObject(i);
                    VideoInfo mVideoInfo = new VideoInfo(videoObj);

                   if (null !=videoObj&&null!=videoObj.optJSONObject("userinfo"))
                   {
                       UserInfo userInfo= new UserInfo(arr.optJSONObject(i).optJSONObject("userinfo"));
                       mVideoInfo.setUserInfo(userInfo);
                   }
                    videoInfoList.add(mVideoInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
