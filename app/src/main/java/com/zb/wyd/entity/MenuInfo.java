package com.zb.wyd.entity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：一句话简单描述
 */
public class MenuInfo implements Serializable
{
    private String id;

    private String name;

    private boolean isSelected;


    private List<CataInfo> childMenuList = new ArrayList<>();


    public MenuInfo()
    {
    }

    public MenuInfo(JSONObject obj)
    {
        this.id = obj.optString("pid");
        this.name = obj.optString("name");
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }


    public List<CataInfo> getChildMenuList()
    {
        return childMenuList;
    }

    public void setChildMenuList(List<CataInfo> childMenuList)
    {
        this.childMenuList = childMenuList;
    }
}
