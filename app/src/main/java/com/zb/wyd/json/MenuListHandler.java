package com.zb.wyd.json;


import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.entity.MenuInfo;
import com.zb.wyd.entity.NoticeInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MenuListHandler extends JsonHandler
{
    private List<MenuInfo> menuInfoList = new ArrayList<>();


    public List<MenuInfo> getMenuInfoList()
    {
        return menuInfoList;
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

                    JSONArray childArr = arr.optJSONObject(i).optJSONArray("child");

                    MenuInfo mMenuInfo = new MenuInfo(arr.optJSONObject(i));

                    List<CataInfo> cateInfoList = new ArrayList<>();
                    if (null != childArr)
                    {
                        for (int j = 0; j < childArr.length(); j++)
                        {
                            CataInfo cataInfo = new CataInfo(childArr.optJSONObject(j));
                            cateInfoList.add(cataInfo);
                        }

                    }

                    mMenuInfo.setChildMenuList(cateInfoList);
                    menuInfoList.add(mMenuInfo);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
