// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookWebViewActivity.java

package com.facebook.katana.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FacebookWebViewActivity extends Activity
{

    public FacebookWebViewActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03008f);
        mUrl = getIntent().getStringExtra(URL);
        mWebView = (WebView)findViewById(0x7f0e0160);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
    }

    public static String URL = "url";
    private String mUrl;
    private WebView mWebView;

}
