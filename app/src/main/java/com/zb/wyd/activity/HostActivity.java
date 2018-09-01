package com.zb.wyd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cc.droid.visitor.EasyTap;

public class HostActivity extends AppCompatActivity
{

    private boolean mStopping;

    private EasyTap mEasyTap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mStopping = false;

        mEasyTap = new EasyTap(this);

        startActivity(new Intent(this, MainActivity.class));

        mEasyTap.onCreate();

        mFinishCastReceiver = new FinishCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("EXIT_APP");
        registerReceiver(mFinishCastReceiver, intentFilter);
    }

//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//
//        if (mStopping)
//        {
//           finish();
//        }
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//
//        mStopping = true;
//    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mEasyTap.onDestroy();
        if(null !=mFinishCastReceiver)
        unregisterReceiver(mFinishCastReceiver);
    }

    private FinishCastReceiver mFinishCastReceiver;

    class FinishCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            if ("EXIT_APP".contentEquals(intent.getAction()))
            {
               finish();

            }
        }
    }
}
