package com.zb.wyd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.wyd.widget.SmoothImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 描述：一句话简单描述
 */
public class SpaceImageDetailActivity extends Activity
{

    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    SmoothImageView imageView = null;
    private String mImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mImgUrl = getIntent().getStringExtra("url");
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
        imageView.setTag(mImgUrl);
        Log.e("Tag", "url:" + mImgUrl);
        ImageLoader.getInstance().displayImage(mImgUrl, imageView);
        //imageView.setImageResource(R.drawable.temp);
        // ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
        // 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        // 0.5f);
        // scaleAnimation.setDuration(300);
        // scaleAnimation.setInterpolator(new AccelerateInterpolator());
        // imageView.startAnimation(scaleAnimation);

        //
        //        imageView.setOnClickListener(new ViewGroup.OnClickListener()
        //        {
        //            @Override
        //            public void onClick(View v)
        //            {
        //                imageView.setOnTransformListener(new SmoothImageView.TransformListener()
        //                {
        //                    @Override
        //                    public void onTransformComplete(int mode)
        //                    {
        //                        if (mode == 2)
        //                        {
        //                            finish();
        //                        }
        //                    }
        //                });
        //                imageView.transformOut();
        //            }
        //        });

        imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener()
        {
            @Override
            public void onViewTap(View view, float x, float y)
            {
                imageView.setOnTransformListener(new SmoothImageView.TransformListener()
                {
                    @Override
                    public void onTransformComplete(int mode)
                    {
                        if (mode == 2)
                        {
                            finish();
                        }
                    }
                });
                imageView.transformOut();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener()
        {
            @Override
            public void onTransformComplete(int mode)
            {
                if (mode == 2)
                {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (isFinishing())
        {
            overridePendingTransition(0, 0);
        }
    }

}
