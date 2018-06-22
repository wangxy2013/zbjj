package com.zb.wyd.entity;

<<<<<<< HEAD
/**
 * 作者：王先云 on 2018/6/21 16:53
 * 邮箱：wangxianyun1@163.com
=======
import org.json.JSONObject;

/**
>>>>>>> a0cb36f1436cd3569b15b90ab071979961148208
 * 描述：一句话简单描述
 */
public class CataInfo
{
<<<<<<< HEAD
    private String pid;
    private String name;




=======
    private String id;

    private String  name;

    private boolean isSelected;

    public  CataInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
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
>>>>>>> a0cb36f1436cd3569b15b90ab071979961148208
}
