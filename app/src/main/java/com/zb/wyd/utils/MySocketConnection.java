package com.zb.wyd.utils;

import android.util.Log;

import com.zb.wyd.listener.SocketListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class MySocketConnection extends WebSocketClient
{
    private String TAG = "MySocketConnection";

    private boolean        isForced      = false;
    private SocketListener mChatListener = null;
    private String url;





    private static MySocketConnection single = null;

    public MySocketConnection(URI serverUri)
    {
        super(serverUri);
    }


    public static MySocketConnection getInstance(URI serverUri)
    {
        if (single == null)
        {
            single = new MySocketConnection(serverUri);

        }
        return single;
    }


    public void setSocketListener(SocketListener mSocketListener)
    {
        this.mChatListener = mSocketListener;
    }

    public void setForced(boolean isForced)
    {
        this.isForced = isForced;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata)
    {
        if (null != mChatListener)
        {
            mChatListener.OnOpend();
        }

    }

    @Override
    public void onMessage(String message)
    {
        if (null != mChatListener)
        {
            mChatListener.OnPushMsg(message);
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote)
    {

    }

    @Override
    public void onError(Exception ex)
    {
        mChatListener.OnError("聊天链接异常，重新连接中...");
    }
}
