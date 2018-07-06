package com.zb.wyd.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.entity.CataInfo;
import com.zb.wyd.listener.MyItemClickListener;


/**
 */
public class LabelChooseHolder extends RecyclerView.ViewHolder
{
    private TextView            mNameTv;

    public LabelChooseHolder(View rootView)
    {
        super(rootView);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
    }


    public void setCataInfo(CataInfo mCataInfo)
    {
        mNameTv.setText(mCataInfo.getName());

    }

}
