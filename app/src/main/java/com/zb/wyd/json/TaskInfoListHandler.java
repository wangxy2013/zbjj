package com.zb.wyd.json;


import com.zb.wyd.entity.AdInfo;
import com.zb.wyd.entity.StyleInfo;
import com.zb.wyd.entity.TaskInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TaskInfoListHandler extends JsonHandler
{
    private List<TaskInfo> mIncompleteList = new ArrayList<>();
    private List<TaskInfo> mCompleteList   = new ArrayList<>();

    private TaskInfo mSiginTaskInfo;

    public TaskInfo getTaskInfo()
    {
        return mSiginTaskInfo;
    }

    public List<TaskInfo> getIncompleteList()
    {
        return mIncompleteList;
    }

    public List<TaskInfo> getCompleteList()
    {
        return mCompleteList;
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
                    TaskInfo mTaskInfo = new TaskInfo(arr.optJSONObject(i));

                    if(null !=arr.optJSONObject(i) && null !=arr.optJSONObject(i).optJSONObject("style"))
                    {
                        StyleInfo styleInfo = new StyleInfo(arr.optJSONObject(i).optJSONObject("style"));
                        mTaskInfo.setStyleInfo(styleInfo);
                    }

                    if ("1".equals(mTaskInfo.getId()))
                    {
                        mSiginTaskInfo = mTaskInfo;

                    }
                    else
                    {
                        if ("0".equals(mTaskInfo.getHas_finish()))
                        {
                            mIncompleteList.add(mTaskInfo);
                        }
                        else
                        {
                            mCompleteList.add(mTaskInfo);
                        }
                    }
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
