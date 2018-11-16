package com.zb.wyd.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.zb.wyd.listener.FileDownloadListener;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImgDownloadUtils
{


    private String filePath;
    private Bitmap mBitmap;
    private String mSaveMessage="";
    private final String TAG = "ImageActivity";
    private Context context;
    private ProgressDialog mSaveDialog = null;

    private FileDownloadListener mFileDownloadListener;

    public void donwloadImg()
    {

        mSaveDialog = ProgressDialog.show(context, "温馨提示", "正在为您制作分享内容...", true);
        new Thread(saveFileRunnable).start();
    }


    public ImgDownloadUtils(Context contexts, String filePaths, FileDownloadListener
            mFileDownloadListener)
    {
        context = contexts;
        filePath = filePaths;
        this.mFileDownloadListener = mFileDownloadListener;
    }

    private void saveUrlAs(String fileUrl, String savePath)/*fileUrl网络资源地址*/
    {

        try
        {
            URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/
            /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            /*此处也可用BufferedInputStream与BufferedOutputStream*/
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
            /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/
            {
                out.write(buffer, 0, count);
            }
            out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
            in.close();
            connection.disconnect();

            mFileDownloadListener.onSuccess(savePath);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e + fileUrl + savePath);
            mSaveMessage = "分享内容制作失败！";
        }
        messageHandler.sendMessage(messageHandler.obtainMessage());
    }

    private Runnable saveFileRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //            try
            //            {
            //                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
            //                String fileName = filePath.substring(filePath.lastIndexOf("/"));
            //                saveFile(mBitmap, fileName);
            //                // mSaveMessage = "图片保存成功！";
            //            }
            //            catch (IOException e)
            //            {
            //                mSaveMessage = "分享内容制作失败！";
            //                e.printStackTrace();
            //            }
            //            catch (Exception e)
            //            {
            //                e.printStackTrace();
            //            }
            //            messageHandler.sendMessage(messageHandler.obtainMessage());
            String fileName = filePath.substring(filePath.lastIndexOf("/"));
            String savefilePath = Environment.getExternalStorageDirectory().getPath() + "/yl/";

            if(!new File(savefilePath).exists())
            {
                new File(savefilePath).mkdir();
            }


            saveUrlAs(filePath, savefilePath + fileName);

        }
    };

    @SuppressLint("HandlerLeak")
    private Handler messageHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            mSaveDialog.dismiss();
            Log.d(TAG, mSaveMessage);
            Toast.makeText(context, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    private InputStream getImageStream(String path) throws Exception
    {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    private void saveFile(Bitmap bm, String fileName) throws IOException
    {
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if (!dirFile.exists())
        {
            dirFile.mkdir();
        }

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/yl/" + fileName;
        File myCaptureFile = new File(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();


        mFileDownloadListener.onSuccess(filePath);
    }
}