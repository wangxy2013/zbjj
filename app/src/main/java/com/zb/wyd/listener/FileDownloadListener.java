package com.zb.wyd.listener;


public interface FileDownloadListener
{
    void onSuccess(String filePath);

    void onFail();

}