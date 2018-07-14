package com.zb.wyd.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.wyd.R;
import com.zb.wyd.utils.APPUtils;
import com.zb.wyd.utils.ConfigManager;
import com.zb.wyd.utils.LogUtil;
import com.zb.wyd.utils.StringUtils;
import com.zb.wyd.widget.statusbar.StatusBarUtil;


/**
 * DESC: H5界面跳转
 */
public class WebViewActivity extends Activity
{
    public static final String EXTRA_URL   = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String IS_SETTITLE = "isSetTitle";
    private WebView   mWebView;
    private String    mUrl;
    private boolean   isSetTitle;
    private ImageView mBackIv;
    private TextView  mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initViewData();
        initEvent();

    }

    protected void initView()
    {
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.yellow));
        StatusBarUtil.StatusBarLightMode(WebViewActivity.this, false);
        mBackIv = (ImageView) findViewById(R.id.iv_back);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSService(), "object");
        mWebView.setWebViewClient(new WebViewClient()
                                  {
                                      @Override
                                      public void onPageFinished(WebView view, String url)
                                      {
                                          super.onPageFinished(view, url);
                                          if (mWebView.getContentHeight() != 0)
                                          {
                                              // 网页显示完成
                                          }
                                      }

                                      @Override
                                      public void onLoadResource(WebView view, String url)
                                      {
                                          super.onLoadResource(view, url);
                                      }

                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url)
                                      {
                                          if (url != null && url.startsWith("appay"))
                                          {
                                              return false;
                                          }
                                          if (url.startsWith("http") || url.startsWith("https"))
                                          {
                                              return super.shouldOverrideUrlLoading(view, url);
                                          }
                                          //                                          else
                                          //                                          {
                                          //                                              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          //                                              startActivity(intent);
                                          //                                              finish();
                                          //                                              return true;
                                          //                                          }
                                          return true;
                                      }

                                      @Override
                                      public WebResourceResponse shouldInterceptRequest(WebView view, String url)
                                      {
                                          if (url.startsWith("http") || url.startsWith("https"))
                                          { //http和https协议开头的执行正常的流程
                                              return super.shouldInterceptRequest(view, url);
                                          }
                                          else
                                          {  //其他的URL则会开启一个Acitity然后去调用原生APP
                                              Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                              startActivity(in);
                                              return null;
                                          }
                                      }

                                  }


        );
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);

                if (!isSetTitle)
                    mTitleTv.setText(title);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed)
            {
                super.onReceivedTouchIconUrl(view, url, precomposed);
                //SLog.debug("onReceivedTouchIconUrl:" + url);
            }
        });

        mWebView.setDownloadListener(new DownloadListener()
        {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void initViewData()
    {
        mUrl = getIntent().getStringExtra(EXTRA_URL) + "?auth=" + ConfigManager.instance().getUniqueCode() + "&mobile_id=" + APPUtils.getDeviceId(this) +
                "&device=and";

        LogUtil.e("TAG","url-->" + mUrl);
        isSetTitle = getIntent().getBooleanExtra(IS_SETTITLE, true);

        if (isSetTitle)
        {
            mTitleTv.setText(getIntent().getStringExtra(EXTRA_TITLE));
        }
        if (!StringUtils.stringIsEmpty(mUrl))
        {
            mWebView.loadUrl(mUrl);
        }
    }


    private void initEvent()
    {
        mBackIv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mWebView.canGoBack())
                {
                    mWebView.goBack();
                }
                else
                {
                    finish();
                }
            }
        });
    }

    public class JSService
    {
        @JavascriptInterface
        public void onPayDone()
        {
        }

        public void onPayFail()
        {
        }

        @JavascriptInterface
        public void onClosed()
        {
            finish();
        }

        @JavascriptInterface
        public void onBuy(String groupId, String boxId, String productType)
        {

        }

        @JavascriptInterface
        public void onQQ(String qq)
        {
            if (!TextUtils.isEmpty(qq))
            {
                String qqurl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqurl)));
            }
        }

        @JavascriptInterface
        public void onSubscriptionSuccess(String groupId, String roomId)
        {
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            if (mWebView.canGoBack())
            {
                mWebView.goBack();
            }
            else
            {
                return super.onKeyDown(keyCode, event);
            }

            return false;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }

    }
}
