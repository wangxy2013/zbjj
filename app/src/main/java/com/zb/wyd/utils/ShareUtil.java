package com.zb.wyd.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.zb.wyd.R;

public class ShareUtil
{
    public static final String AUTHORITY = "com.zb.wyd.fileprovider";

    /**
     * 直接分享图片到微信好友
     *
     * @param picFile
     */
    public static void shareWechatFriend(Context mContext, String content, File picFile)
    {
        if (PlatformUtil.isInstallApp(mContext, PlatformUtil.PACKAGE_WECHAT))
        {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            if (picFile != null)
            {
                if (picFile.isFile() && picFile.exists())
                {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        uri = FileProvider.getUriForFile(mContext, AUTHORITY, picFile);
                    }
                    else
                    {
                        uri = Uri.fromFile(picFile);
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            }
//            intent.putExtra(Intent.EXTRA_TEXT, "分享标题");
//            intent.putExtra("Kdescription", "wwwwwwwwwwwwwwwwwwww");
//            intent.setType("image/*");
//            mContext.startActivity(intent);

            intent.putExtra(Intent.EXTRA_SUBJECT, "111111111111");
            intent.putExtra(Intent.EXTRA_TEXT, "2222222222");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("image/*;text/plain");
            mContext.startActivity(Intent.createChooser(intent, "333333333"));
        }
        else
        {
            Toast.makeText(mContext, "您需要安装微信客户端", Toast.LENGTH_LONG).show();
        }
    }


    private static String sharePicName = "share_pic.jpg";
    private static String sharePicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wyd" + File.separator +
            "sharepic" + File.separator;

    /**
     * 保存图片，并返回一个File类型的文件
     */
    public static File saveSharePic(Context context, Bitmap bitmap)
    {
        File file = new File(sharePicPath);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File filePic = new File(sharePicPath, sharePicName);
        if (filePic.exists())
        {
            filePic.delete();
        }
        try
        {
            FileOutputStream out = new FileOutputStream(filePic);
            if (bitmap == null)
            {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            try
            {
                out.flush();
                out.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return filePic;
    }


}