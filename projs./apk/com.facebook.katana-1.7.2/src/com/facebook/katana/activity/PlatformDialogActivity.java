// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlatformDialogActivity.java

package com.facebook.katana.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.*;
import android.widget.Toast;
import com.facebook.katana.Constants;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

// Referenced classes of package com.facebook.katana.activity:
//            BaseFacebookActivity

public abstract class PlatformDialogActivity extends BaseFacebookActivity
{
    private class PlatformDialogSpinner extends ProgressDialog
    {

        public void onBackPressed()
        {
            dismiss();
            finishWithResultCancel(null);
        }

        final PlatformDialogActivity this$0;

        PlatformDialogSpinner()
        {
            this$0 = PlatformDialogActivity.this;
            super(PlatformDialogActivity.this);
            requestWindowFeature(1);
            setMessage(getResources().getText(0x7f0a008c));
        }
    }

    private class PlatformDialogWebViewClient extends WebViewClient
    {

        private void handleSuccess(String s)
        {
            if(getIntent().getBooleanExtra("return_via_intent_url", false))
            {
                String s2 = getIntent().getStringExtra("client_id");
                String s3 = getIntent().getStringExtra("activity_id");
                if(s3 == null)
                    s3 = "";
                StringBuilder stringbuilder = (new StringBuilder("fb")).append(s2).append(s3).append("://authorize");
                String s4 = Uri.parse(s).getEncodedFragment();
                if(s4 != null)
                    stringbuilder.append("#").append(s4);
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringbuilder.toString()));
                startActivity(intent);
            } else
            {
                String s1 = s.replace("fbconnect", "http");
                Bundle bundle = parseUrlParameters(s1);
                finishWithResultOk(bundle);
            }
        }

        public void onPageFinished(WebView webview, String s)
        {
            super.onPageFinished(webview, s);
            mSpinner.dismiss();
_L2:
            return;
            IllegalArgumentException illegalargumentexception;
            illegalargumentexception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public void onPageStarted(WebView webview, String s, Bitmap bitmap)
        {
            super.onPageStarted(webview, s, bitmap);
            mSpinner.show();
_L2:
            return;
            IllegalStateException illegalstateexception;
            illegalstateexception;
            continue; /* Loop/switch isn't completed */
            android.view.WindowManager.BadTokenException badtokenexception;
            badtokenexception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public void onReceivedError(WebView webview, int i, String s, String s1)
        {
            super.onReceivedError(webview, i, s, s1);
            Bundle bundle = new Bundle();
            bundle.putString("error", s);
            bundle.putInt("error_code", i);
            bundle.putString("failing_url", s1);
            finishWithResultCancel(bundle);
        }

        public void onReceivedSslError(WebView webview, SslErrorHandler sslerrorhandler, SslError sslerror)
        {
            if(!PreferenceManager.getDefaultSharedPreferences(PlatformDialogActivity.this).getBoolean("check_certs", true))
                sslerrorhandler.proceed();
            else
            if(Constants.isBetaBuild() || FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone())
                Toast.makeText(PlatformDialogActivity.this, 0x7f0a0055, 1).show();
            else
                Log.d("PlatformDialogActivity", getString(0x7f0a0054));
        }

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            boolean flag;
            if(s.startsWith("fbconnect://success"))
            {
                handleSuccess(s);
                flag = true;
            } else
            if(s.contains("touch"))
            {
                flag = false;
            } else
            {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s)));
                flag = true;
            }
            return flag;
        }

        final PlatformDialogActivity this$0;

        private PlatformDialogWebViewClient()
        {
            this$0 = PlatformDialogActivity.this;
            super();
        }

    }


    public PlatformDialogActivity()
    {
        mUrl = null;
    }

    private void finishWithResult(boolean flag, Bundle bundle)
    {
        byte byte0;
        if(flag)
            byte0 = -1;
        else
            byte0 = 0;
        if(bundle == null)
        {
            setResult(byte0);
        } else
        {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(byte0, intent);
        }
        finish();
    }

    private void setupWebView(WebViewClient webviewclient)
    {
        mWebView = (WebView)findViewById(0x7f0e00fc);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(webviewclient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        validateLocaleSettings(null);
    }

    private void validateLocaleSettings(Configuration configuration)
    {
        CookieSyncManager.createInstance(this);
        Locale locale;
        if(configuration != null)
            locale = configuration.locale;
        else
            locale = getResources().getConfiguration().locale;
        if(sLocale == null || !locale.equals(sLocale))
        {
            sLocale = (Locale)locale.clone();
            CookieManager.getInstance().setCookie("facebook.com", "locale=");
        }
    }

    protected void finishWithResultCancel(Bundle bundle)
    {
        finishWithResult(false, bundle);
    }

    protected void finishWithResultOk(Bundle bundle)
    {
        finishWithResult(true, bundle);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        if(j == 0)
            finish();
        i;
        JVM INSTR tableswitch 2210 2210: default 28
    //                   2210 29;
           goto _L1 _L2
_L1:
        return;
_L2:
        runUI();
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03005e);
        mSpinner = new PlatformDialogSpinner();
        setupWebView(new PlatformDialogWebViewClient());
        setupDialogURL();
        if(mUrl == null)
            Log.e("PlatformDialogActivity", "mUrl was not set in setupDialogURL(). Any class inheriting from PlatformDialogActivity MUST set mUrl in this method.");
        AppSession appsession = AppSession.getActiveSession(this, false);
        if(appsession == null || appsession.getStatus() != com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_IN)
            LoginActivity.redirectThroughLogin(this);
        else
            runUI();
    }

    protected Bundle parseUrlParameters(String s)
    {
        Bundle bundle1;
        URL url = new URL(s);
        bundle1 = URLQueryBuilder.parseQueryString(url.getQuery());
        bundle1.putAll(URLQueryBuilder.parseQueryString(url.getRef()));
        Bundle bundle = bundle1;
_L2:
        return bundle;
        MalformedURLException malformedurlexception;
        malformedurlexception;
        Log.e("PlatformDialogActivity", (new StringBuilder()).append("Caught malformed URL: ").append(s).toString());
        bundle = new Bundle();
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected void runUI()
    {
        AppSession appsession = AppSession.getActiveSession(this, false);
        if(appsession == null || appsession.getSessionInfo() == null)
        {
            finishWithResultCancel(null);
        } else
        {
            AuthDeepLinkMethod authdeeplinkmethod = new AuthDeepLinkMethod(this, System.currentTimeMillis() / 1000L, appsession.getSessionInfo().userId, mUrl, null, appsession.getSessionInfo().sessionKey, appsession.getSessionInfo().sessionSecret);
            authdeeplinkmethod.start();
            mUrl = authdeeplinkmethod.getUrl();
            mWebView.loadUrl(mUrl);
        }
    }

    protected abstract void setupDialogURL();

    protected static final String DISPLAY = "touch";
    protected static final String REDIRECT_URI = "fbconnect://success";
    private static final String TAG = "PlatformDialogActivity";
    protected static Locale sLocale = null;
    private ProgressDialog mSpinner;
    protected String mUrl;
    private WebView mWebView;


}
