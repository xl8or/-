// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SetNavBarTitleHandler.java

package com.facebook.katana.activity.faceweb;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;

class SetNavBarTitleHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
{

    public SetNavBarTitleHandler(Activity activity, Handler handler)
    {
        super(handler);
        mActivity = activity;
    }

    public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
    {
        String s = facebookrpccall.getParameterByName("title");
        if(s == null)
            s = "";
        ((TextView)mActivity.findViewById(0x7f0e0067)).setText(s);
    }

    protected Activity mActivity;
}
