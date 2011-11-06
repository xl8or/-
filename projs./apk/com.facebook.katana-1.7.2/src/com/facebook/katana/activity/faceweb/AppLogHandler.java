// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLogHandler.java

package com.facebook.katana.activity.faceweb;

import android.content.Context;
import com.facebook.katana.util.Log;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;

class AppLogHandler
    implements com.facebook.katana.webview.FacebookWebView.NativeCallHandler
{

    public AppLogHandler(String s)
    {
        TAG = s;
    }

    public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
    {
        String s = "";
        String s1 = facebookrpccall.getParameterByName("msg");
        if(s1 != null && (s1 instanceof String))
            s = (String)s1;
        Log.v(TAG, s);
    }

    private final String TAG;
}
