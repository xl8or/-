// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookAuthentication.java

package com.facebook.katana.webview;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.SslError;
import android.preference.PreferenceManager;
import android.webkit.*;
import android.widget.Toast;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.webview:
//            BaseWebView

public class FacebookAuthentication
{
    protected static class FacewebAuthenticationWebViewClient extends WebViewClient
    {

        public void onPageFinished(WebView webview, String s)
        {
            boolean flag;
            flag = false;
            if(s == null || !FacebookAuthentication.matchMSitePathIgnoreSSL(s, mExpectedUrlPath))
                break MISSING_BLOCK_LABEL_31;
            Log.d(FacebookAuthentication.TAG, "authenticated -> true");
            FacebookAuthentication.mAuthenticated = true;
            flag = true;
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorenter ;
            Set set;
            FacebookAuthentication.mAuthenticationInProgress = false;
            set = FacebookAuthentication.mFWAuthenticationListeners;
            FacebookAuthentication.mFWAuthenticationListeners = new HashSet();
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorexit ;
            for(Iterator iterator = set.iterator(); iterator.hasNext();)
            {
                Callback callback = (Callback)iterator.next();
                Exception exception;
                if(flag)
                    callback.authenticationSuccessful();
                else
                    callback.authenticationFailed();
            }

            break MISSING_BLOCK_LABEL_119;
            exception;
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public void onReceivedError(WebView webview, int i, String s, String s1)
        {
            Log.d(FacebookAuthentication.TAG, (new StringBuilder()).append("authentication error: ").append(s).toString());
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorenter ;
            Set set;
            FacebookAuthentication.mAuthenticationInProgress = false;
            set = FacebookAuthentication.mFWAuthenticationListeners;
            FacebookAuthentication.mFWAuthenticationListeners = new HashSet();
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorexit ;
            for(Iterator iterator = set.iterator(); iterator.hasNext(); ((Callback)iterator.next()).authenticationNetworkFailed());
            break MISSING_BLOCK_LABEL_95;
            Exception exception;
            exception;
            com/facebook/katana/webview/FacebookAuthentication;
            JVM INSTR monitorexit ;
            throw exception;
        }

        protected final Context mContext;
        protected final String mExpectedUrlPath;

        public FacewebAuthenticationWebViewClient(Context context, String s)
        {
            mContext = context;
            mExpectedUrlPath = Uri.parse(s).getPath();
        }
    }

    public static class AuthWebViewClient extends WebViewClient
    {

        public void onReceivedSslError(WebView webview, SslErrorHandler sslerrorhandler, SslError sslerror)
        {
            if(!PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("check_certs", true))
                sslerrorhandler.proceed();
            else
            if(Constants.isBetaBuild() || FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone())
                Toast.makeText(mContext, 0x7f0a0055, 1).show();
            else
                Log.d(FacebookAuthentication.TAG, mContext.getString(0x7f0a0054));
        }

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            Uri uri = Uri.parse(s);
            String s1 = uri.getScheme();
            String s2 = uri.getHost();
            String s3 = uri.getPath();
            boolean flag;
            if((StringUtils.saneStringEquals(s1, "http") || StringUtils.saneStringEquals(s1, "https")) && s2.endsWith(".facebook.com") && s3.equals("/login.php"))
            {
                FacebookAuthentication.startAuthentication(mContext, mCallback);
                flag = true;
            } else
            {
                flag = false;
            }
            return flag;
        }

        protected Callback mCallback;
        protected Context mContext;

        public AuthWebViewClient(Context context, Callback callback)
        {
            mContext = context;
            mCallback = callback;
        }
    }

    public static interface Callback
    {

        public abstract void authenticationFailed();

        public abstract void authenticationNetworkFailed();

        public abstract void authenticationSuccessful();
    }


    public FacebookAuthentication()
    {
    }

    public static boolean isAuthenticated()
    {
        return mAuthenticated;
    }

    static boolean matchMSitePathIgnoreSSL(String s, String s1)
    {
        Uri uri = Uri.parse(s);
        String s2 = uri.getScheme();
        boolean flag;
        if(!StringUtils.saneStringEquals(s2, "http") && !StringUtils.saneStringEquals(s2, "https"))
        {
            flag = false;
        } else
        {
            String s3 = uri.getHost();
            if(!s3.startsWith("m.") || !s3.endsWith(".facebook.com"))
                flag = false;
            else
                flag = StringUtils.saneStringEquals(uri.getPath(), s1);
        }
        return flag;
    }

    static boolean matchUrlLiberally(String s, String s1)
    {
        boolean flag2;
        if(s.equals(s1))
        {
            flag2 = true;
        } else
        {
            Uri uri = Uri.parse(s);
            Uri uri1 = Uri.parse(s1);
            String s2 = uri.getScheme();
            String s3 = uri1.getScheme();
            boolean flag;
            boolean flag1;
            if(StringUtils.saneStringEquals(s2, "http") || StringUtils.saneStringEquals(s2, "https"))
                flag = true;
            else
                flag = false;
            if(StringUtils.saneStringEquals(s3, "http") || StringUtils.saneStringEquals(s3, "https"))
                flag1 = true;
            else
                flag1 = false;
            if((!flag || !flag1) && !StringUtils.saneStringEquals(s2, s3))
                flag2 = false;
            else
                flag2 = rebuildUri(uri).equals(rebuildUri(uri1));
        }
        return flag2;
    }

    protected static Uri rebuildUri(Uri uri)
    {
        return uri.buildUpon().scheme("http").query(null).fragment(null).build();
    }

    public static void startAuthentication(Context context, Callback callback)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        com/facebook/katana/webview/FacebookAuthentication;
        JVM INSTR monitorenter ;
        if(callback == null)
            break MISSING_BLOCK_LABEL_23;
        mFWAuthenticationListeners.add(callback);
        if(!mAuthenticationInProgress && appsession != null && appsession.getSessionInfo() != null)
            break MISSING_BLOCK_LABEL_46;
        com/facebook/katana/webview/FacebookAuthentication;
        JVM INSTR monitorexit ;
        break MISSING_BLOCK_LABEL_179;
        mAuthenticationInProgress = true;
        com/facebook/katana/webview/FacebookAuthentication;
        JVM INSTR monitorexit ;
        Log.d(TAG, "authenticated -> false");
        mAuthenticated = false;
        BaseWebView basewebview = new BaseWebView(context);
        if(Integer.parseInt(android.os.Build.VERSION.SDK) < 7)
            basewebview.getSettings().setJavaScriptEnabled(false);
        basewebview.setWebViewClient(new FacewebAuthenticationWebViewClient(context, com.facebook.katana.Constants.URL.getMSuccessUrl(context)));
        AuthDeepLinkMethod authdeeplinkmethod = new AuthDeepLinkMethod(context, System.currentTimeMillis() / 1000L, appsession.getSessionInfo().userId, com.facebook.katana.Constants.URL.getMSuccessUrl(context), null, appsession.getSessionInfo().sessionKey, appsession.getSessionInfo().sessionSecret);
        authdeeplinkmethod.start();
        basewebview.loadUrl(authdeeplinkmethod.getUrl());
        break MISSING_BLOCK_LABEL_179;
        Exception exception;
        exception;
        com/facebook/katana/webview/FacebookAuthentication;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public static final String TAG = Utils.getClassName(com/facebook/katana/webview/FacebookAuthentication);
    protected static boolean mAuthenticated = true;
    protected static boolean mAuthenticationInProgress = false;
    protected static Set mFWAuthenticationListeners = new HashSet();

}
