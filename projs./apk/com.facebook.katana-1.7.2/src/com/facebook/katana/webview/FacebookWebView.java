// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookWebView.java

package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebView;
import com.facebook.katana.Constants;
import com.facebook.katana.util.Log;
import java.util.*;

// Referenced classes of package com.facebook.katana.webview:
//            BaseWebView, FacebookJsBridge, FacebookRpcCall

public class FacebookWebView extends BaseWebView
{
    protected class RPCChromeClient extends BaseWebView.BaseWebChromeClient
    {

        public boolean onJsPrompt(WebView webview, String s, String s1, String s2, JsPromptResult jspromptresult)
        {
            boolean flag;
            if(!(webview instanceof FacebookWebView))
            {
                flag = false;
            } else
            {
                FacebookWebView facebookwebview = (FacebookWebView)webview;
                Uri uri = Uri.parse(s1);
                UrlHandler urlhandler = (UrlHandler)facebookwebview.mUrlHandlers.get(uri.getScheme());
                if(urlhandler != null)
                {
                    urlhandler.handle(mContext, facebookwebview, uri);
                    jspromptresult.cancel();
                    flag = true;
                } else
                {
                    flag = false;
                }
            }
            return flag;
        }

        final FacebookWebView this$0;

        protected RPCChromeClient()
        {
            this$0 = FacebookWebView.this;
            super();
        }
    }

    static class FacebookWebViewClient extends FacebookAuthentication.AuthWebViewClient
    {

        public void onReceivedError(WebView webview, int i, String s, String s1)
        {
            super.onReceivedError(webview, i, s, s1);
            if(Constants.isBetaBuild())
            {
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append("url ").append(s1).append(" failed (code: ").append(i).append("; description: ").append(s).append(")");
                Log.v("FacebookWebView", stringbuilder.toString());
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            boolean flag;
            if(super.shouldOverrideUrlLoading(webview, s))
                flag = true;
            else
            if(!(webview instanceof FacebookWebView))
            {
                flag = false;
            } else
            {
                FacebookWebView facebookwebview = (FacebookWebView)webview;
                Uri uri = Uri.parse(s);
                String s1 = uri.getScheme();
                UrlHandler urlhandler = (UrlHandler)facebookwebview.mUrlHandlers.get(s1);
                if(urlhandler != null)
                {
                    urlhandler.handle(mContext, (FacebookWebView)webview, uri);
                    flag = true;
                } else
                {
                    flag = false;
                }
            }
            return flag;
        }

        public FacebookWebViewClient(Context context, FacebookAuthentication.Callback callback)
        {
            super(context, callback);
        }
    }

    public static abstract class NativeUICallHandler
        implements NativeCallHandler
    {

        public final void handle(Context context, FacebookWebView facebookwebview, final FacebookRpcCall call)
        {
            handleNonUI(facebookwebview, call);
            mHandler.post(facebookwebview. new Runnable() {

                public void run()
                {
                    handleUI(wv, call);
                }

                final NativeUICallHandler this$0;
                final FacebookRpcCall val$call;
                final FacebookWebView val$wv;


// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
            }
);
        }

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
        }

        public abstract void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall);

        protected Handler mHandler;

        public NativeUICallHandler(Handler handler)
        {
            mHandler = handler;
        }
    }

    public static interface JsReturnHandler
    {

        public abstract void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1);
    }

    public static interface NativeCallHandler
    {

        public abstract void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall);
    }

    public static interface UrlHandler
    {

        public abstract void handle(Context context, FacebookWebView facebookwebview, Uri uri);
    }


    public FacebookWebView(Context context)
    {
        super(context);
        mContext = context;
    }

    public FacebookWebView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mContext = context;
    }

    public FacebookWebView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mContext = context;
    }

    protected void customizeWebView(Context context)
    {
        super.customizeWebView(context);
        mUrlHandlers = new HashMap();
        mJsInterface = new FacebookJsBridge("FacebookWebView");
        registerUrlHandler("fbrpc", mJsInterface.handler);
    }

    void executeJs(String s)
    {
        loadUrl((new StringBuilder("javascript:")).append(s).toString());
    }

    public void executeJs(String s, JsReturnHandler jsreturnhandler)
    {
        executeJsWithReturn(s, jsreturnhandler);
    }

    public void executeJsFunction(String s, List list, JsReturnHandler jsreturnhandler)
    {
        executeJsWithReturn(FacebookJsBridge.jsEncodeCall(s, list), jsreturnhandler);
    }

    public String executeJsWithReturn(String s, JsReturnHandler jsreturnhandler)
    {
        String s1 = mJsInterface.registerPendingJsCall(s, jsreturnhandler);
        String s2 = (new StringBuilder()).append("__android_injected_function_").append(s1).toString();
        Object aobj[] = new Object[2];
        aobj[0] = s2;
        aobj[1] = s;
        loadUrl(String.format("javascript:var %1$s = function() { return %2$s;};", aobj));
        HashMap hashmap = new HashMap();
        hashmap.put("callId", s1);
        hashmap.put("exc", new FacebookRpcCall.JsVariable("__android_exception"));
        hashmap.put("retval", new FacebookRpcCall.JsVariable("__android_retval"));
        String s3 = FacebookRpcCall.jsComposeFacebookRpcCall("fbrpc", "facebook", null, null, "call_return", hashmap);
        String s5;
        if(Constants.isBetaBuild())
        {
            HashMap hashmap1 = new HashMap();
            hashmap1.put("callId", s1);
            hashmap1.put("exc", new FacebookRpcCall.JsVariable("err"));
            String s4 = FacebookRpcCall.jsComposeFacebookRpcCall("fbrpc", "facebook", null, null, "call_return", hashmap1);
            Object aobj1[] = new Object[3];
            aobj1[0] = s2;
            aobj1[1] = s4;
            aobj1[2] = s3;
            s5 = String.format("javascript:(function() { var __android_exception = null; var __android_retval; try { __android_retval = %1$s();} catch (err) { window.prompt(%2$s);throw err; }window.prompt(%3$s);%1$s = null;})()", aobj1);
        } else
        {
            Object aobj2[] = new Object[2];
            aobj2[0] = s2;
            aobj2[1] = s3;
            s5 = String.format("javascript:(function() { var __android_exception = null; var __android_retval = null; try { __android_retval = %1$s();} catch (err) { __android_exception = true; }window.prompt(%2$s);%1$s = null;})()", aobj2);
        }
        loadUrl(s5);
        return s1;
    }

    public void registerNativeCallHandler(String s, NativeCallHandler nativecallhandler)
    {
        mJsInterface.registerNativeCallHandler(s, nativecallhandler);
    }

    public void registerUrlHandler(String s, UrlHandler urlhandler)
    {
        UrlHandler urlhandler1 = (UrlHandler)mUrlHandlers.put(s, urlhandler);
        if(!$assertionsDisabled && urlhandler1 != null)
            throw new AssertionError();
        else
            return;
    }

    protected void setChromeClient(Context context)
    {
        setWebChromeClient(new RPCChromeClient());
    }

    static final boolean $assertionsDisabled = false;
    public static final String RPC_APP_LOG = "appLog";
    private static final String TAG = "FacebookWebView";
    protected final Context mContext;
    protected FacebookJsBridge mJsInterface;
    protected Map mUrlHandlers;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/webview/FacebookWebView.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
