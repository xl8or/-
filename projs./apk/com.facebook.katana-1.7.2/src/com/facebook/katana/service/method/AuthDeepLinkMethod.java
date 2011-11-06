// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthDeepLinkMethod.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import java.util.Map;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class AuthDeepLinkMethod extends ApiMethod
{

    public AuthDeepLinkMethod(Context context, long l, long l1, String s, ApiMethodListener apimethodlistener, 
            String s1, String s2)
    {
        super(context, null, null, null, com.facebook.katana.Constants.URL.getMAuthUrl(context), apimethodlistener);
        mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
        mParams.put("t", (new StringBuilder()).append("").append(l).toString());
        mParams.put("uid", (new StringBuilder()).append("").append(l1).toString());
        mParams.put("url", s);
        mParams.put("session_key", s1);
        mSessionSecret = s2;
    }

    public static String getDeepLinkUrl(Context context, String s)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        AuthDeepLinkMethod authdeeplinkmethod = new AuthDeepLinkMethod(context, System.currentTimeMillis() / 1000L, appsession.getSessionInfo().userId, s, null, appsession.getSessionInfo().sessionKey, appsession.getSessionInfo().sessionSecret);
        authdeeplinkmethod.start();
        return authdeeplinkmethod.getUrl();
    }

    public String getUrl()
    {
        return mUrl;
    }

    protected String signatureKey()
    {
        return mSessionSecret;
    }

    public void start()
    {
        addSignature();
        mUrl = buildGETUrl(mBaseUrl);
        if(mListener != null)
            mListener.onOperationComplete(this, 200, "OK", null);
_L1:
        return;
        Exception exception;
        exception;
        if(mListener != null)
            mListener.onOperationComplete(this, 0, null, exception);
          goto _L1
    }

    private String mSessionSecret;
    private String mUrl;
}
