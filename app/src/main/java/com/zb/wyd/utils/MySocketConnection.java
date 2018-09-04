package com.zb.wyd.utils;

import android.util.Log;


import com.zb.wyd.listener.SocketListener;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketOptions;


public class MySocketConnection extends WebSocketConnection
{
    private String TAG = "MySocketConnection";

    private boolean isForced = false;
    private SocketListener mChatListener = null;


    private String url;

    private MySocketConnection()
    {
    }

    private static MySocketConnection single = null;

    private static WebSocketOptions mWebSocketOptions;

    public static MySocketConnection getInstance()
    {
        if (single == null)
        {
            single = new MySocketConnection();
            mWebSocketOptions = new WebSocketOptions();
            mWebSocketOptions.setSocketReceiveTimeout(3000);
        }
        return single;
    }


    public void setSocketListener(SocketListener mSocketListener)
    {
        this.mChatListener = mSocketListener;
    }


    public void startConnection(final String url)
    {
        this.url = url;
        try
        {
            LogUtil.e("TAG", url);
            connect(url, new WebSocketConnectionHandler()
            {
                @Override
                public void onOpen()
                {

                }

                @Override
                public void onTextMessage(String payload)//监听服务端推送的消息
                {
                    Log.e(TAG, payload);

                    if (null != mChatListener)
                    {
                        mChatListener.OnPushMsg(payload);
                    }


                }

                @Override
                public void onClose(int code, String reason)//断开链接
                {
                    if (!isForced)
                    {
                        LogUtil.e("TAG", "重新连接");

                        try
                        {
                            Thread.sleep(30000);
                            startConnection(url);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                    }

                }
            }, mWebSocketOptions);
        }
        catch (WebSocketException e)
        {
            e.printStackTrace();
            // ContextUtil.toast("WebSocketException");
            if (!isForced)
            {
                LogUtil.e("TAG", "重新连接");

                try
                {
                    Thread.sleep(30000);
                    startConnection(url);
                }
                catch (InterruptedException e1)
                {
                    e.printStackTrace();
                }

            }

        }
    }

    private void aa()
    {
        startConnection(url);
    }

    public void setForced(boolean isForced)
    {
        this.isForced = isForced;
    }
}
