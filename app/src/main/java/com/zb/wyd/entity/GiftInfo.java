package com.zb.wyd.entity;

public class GiftInfo
{
    private int drawableId;

    private String giftName;

    private String giftStyle;

    private String giftPrice;

    private boolean isSelected;


    public int getDrawableId()
    {
        return drawableId;
    }

    public void setDrawableId(int drawableId)
    {
        this.drawableId = drawableId;
    }

    public String getGiftName()
    {
        return giftName;
    }

    public void setGiftName(String giftName)
    {
        this.giftName = giftName;
    }

    public String getGiftStyle()
    {
        return giftStyle;
    }

    public void setGiftStyle(String giftStyle)
    {
        this.giftStyle = giftStyle;
    }

    public String getGiftPrice()
    {
        return giftPrice;
    }

    public void setGiftPrice(String giftPrice)
    {
        this.giftPrice = giftPrice;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
}
