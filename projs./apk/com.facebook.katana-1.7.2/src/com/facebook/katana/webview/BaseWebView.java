// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseWebView.java

package com.facebook.katana.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.*;
import com.facebook.katana.util.Log;

public class BaseWebView extends WebView
{
    protected static class BaseWebChromeClient extends WebChromeClient
    {

        public void onConsoleMessage(String s, int i, String s1)
        {
            Log.v(mCategory, (new StringBuilder()).append(s1).append(":").append(i).append(": ").append(s).toString());
        }

        protected String mCategory;

        public BaseWebChromeClient()
        {
            this("console");
        }

        public BaseWebChromeClient(String s)
        {
            mCategory = s;
        }
    }


    public BaseWebView(Context context)
    {
        super(context);
        customizeWebView(context);
    }

    public BaseWebView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        customizeWebView(context);
    }

    public BaseWebView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        customizeWebView(context);
    }

    protected void customizeWebView(Context context)
    {
        WebSettings websettings = getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setCacheMode(0);
        setChromeClient(context);
    }

    protected void setChromeClient(Context context)
    {
        setWebChromeClient(new BaseWebChromeClient());
    }

    private static final String TAG = "BaseWebView";
}
