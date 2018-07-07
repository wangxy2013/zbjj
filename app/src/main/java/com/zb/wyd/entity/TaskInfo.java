package com.zb.wyd.entity;

import org.json.JSONObject;

/**
 * 描述：一句话简单描述
 */
public class TaskInfo
{
    private String id;//15
    private String tname;//分享链接被访问
    private String desc;//分享出去的任意内容，只要被不同人访问，就会产生奖励。
    private String times;//20
    private String is_suspend;//0
    private String start_time;//0
    private String end_time;//0
    private String is_show;//1
    private String del_flag;//0
    private String desc_times;//每天只能获得20次积分
    private String active;//1,
    private String has_finish;//0
    private String action;



    public TaskInfo() {}

    public TaskInfo(JSONObject obj)
    {
        this.id = obj.optString("id");
        this.tname = obj.optString("tname");
        this.desc = obj.optString("desc");
        this.times = obj.optString("times");
        this.is_suspend = obj.optString("is_suspend");
        this.start_time = obj.optString("start_time");
        this.end_time = obj.optString("end_time");
        this.is_show = obj.optString("is_show");
        this.desc_times = obj.optString("desc_times");
        this.active = obj.optString("active");
        this.has_finish = obj.optString("has_finish");
        this.action =obj.optString("action");
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTname()
    {
        return tname;
    }

    public void setTname(String tname)
    {
        this.tname = tname;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getTimes()
    {
        return times;
    }

    public void setTimes(String times)
    {
        this.times = times;
    }

    public String getIs_suspend()
    {
        return is_suspend;
    }

    public void setIs_suspend(String is_suspend)
    {
        this.is_suspend = is_suspend;
    }

    public String getStart_time()
    {
        return start_time;
    }

    public void setStart_time(String start_time)
    {
        this.start_time = start_time;
    }

    public String getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }

    public String getIs_show()
    {
        return is_show;
    }

    public void setIs_show(String is_show)
    {
        this.is_show = is_show;
    }

    public String getDel_flag()
    {
        return del_flag;
    }

    public void setDel_flag(String del_flag)
    {
        this.del_flag = del_flag;
    }

    public String getDesc_times()
    {
        return desc_times;
    }

    public void setDesc_times(String desc_times)
    {
        this.desc_times = desc_times;
    }

    public String getActive()
    {
        return active;
    }

    public void setActive(String active)
    {
        this.active = active;
    }

    public String getHas_finish()
    {
        return has_finish;
    }

    public void setHas_finish(String has_finish)
    {
        this.has_finish = has_finish;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }
}
