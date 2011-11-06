// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacewebWebView.java

package com.facebook.katana.webview;

import android.app.Activity;
import android.content.*;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.*;
import com.facebook.katana.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

// Referenced classes of package com.facebook.katana.webview:
//            FacebookWebView, MRoot, FacebookAuthentication, MRootVersion, 
//            FacebookRpcCall

public class FacewebWebView extends FacebookWebView
{
    protected static final class FacewebErrorType extends Enum
    {

        public static FacewebErrorType valueOf(String s)
        {
            return (FacewebErrorType)Enum.valueOf(com/facebook/katana/webview/FacewebWebView$FacewebErrorType, s);
        }

        public static FacewebErrorType[] values()
        {
            return (FacewebErrorType[])$VALUES.clone();
        }

        private static final FacewebErrorType $VALUES[];
        public static final FacewebErrorType AUTHENTICATION_ERROR;
        public static final FacewebErrorType NETWORK_ERROR;

        static 
        {
            NETWORK_ERROR = new FacewebErrorType("NETWORK_ERROR", 0);
            AUTHENTICATION_ERROR = new FacewebErrorType("AUTHENTICATION_ERROR", 1);
            FacewebErrorType afaceweberrortype[] = new FacewebErrorType[2];
            afaceweberrortype[0] = NETWORK_ERROR;
            afaceweberrortype[1] = AUTHENTICATION_ERROR;
            $VALUES = afaceweberrortype;
        }

        private FacewebErrorType(String s, int i)
        {
            super(s, i);
        }
    }

    class BroadcastScriptHandler extends FacebookWebView.NativeUICallHandler
    {

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            final String script = facebookrpccall.getParameterByName("script");
            int i = Integer.valueOf(facebookrpccall.getParameterByName("delay")).intValue();
            if(!com.facebook.katana.Constants.URL.isFacebookUrl(facebookwebview.getUrl()))
                Log.e("FacewebWebView", (new StringBuilder()).append("Page with Non-facebook URL (").append(facebookwebview.getUrl()).append(") attempting to invoke broadcastScript").toString());
            else
                mHandler.postDelayed(new Runnable() {

                    public void run()
                    {
                        Iterator iterator = FacewebWebView.getRegisteredFacewebWebViews().iterator();
                        do
                        {
                            if(!iterator.hasNext())
                                break;
                            FacewebWebView facewebwebview = (FacewebWebView)iterator.next();
                            if(com.facebook.katana.Constants.URL.isFacebookUrl(facewebwebview.getUrl()))
                                facewebwebview.executeJsWithReturn(script, null);
                        } while(true);
                    }

                    final BroadcastScriptHandler this$1;
                    final String val$script;

                
                {
                    this$1 = BroadcastScriptHandler.this;
                    script = s;
                    super();
                }
                }
, i);
        }

        public static final String PARAM_DELAY = "delay";
        public static final String PARAM_SCRIPT = "script";
        protected final Handler mHandler;
        final FacewebWebView this$0;

        public BroadcastScriptHandler(Handler handler)
        {
            this$0 = FacewebWebView.this;
            super(handler);
            mHandler = handler;
        }
    }

    protected class FacewebWebViewClient extends FacebookWebView.FacebookWebViewClient
    {

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            boolean flag;
            if(super.shouldOverrideUrlLoading(webview, s))
            {
                flag = true;
            } else
            {
                Uri uri = Uri.parse(s);
                if(!mJsReady && com.facebook.katana.Constants.URL.isFacebookUrl(uri))
                    flag = false;
                else
                if(IntentUriHandler.getIntentForUri(mContext, s) != null)
                {
                    IntentUriHandler.handleUri(mContext, s);
                    flag = true;
                } else
                {
                    Intent intent = new Intent("android.intent.action.VIEW", uri);
                    try
                    {
                        mContext.startActivity(intent);
                    }
                    catch(ActivityNotFoundException activitynotfoundexception) { }
                    flag = true;
                }
            }
            return flag;
        }

        final FacewebWebView this$0;

        public FacewebWebViewClient(Context context, FacebookAuthentication.Callback callback)
        {
            this$0 = FacewebWebView.this;
            super(context, callback);
        }
    }


    protected FacewebWebView(Context context)
    {
        super(context);
        setMobileRootUrl(context);
        mRootLoadListener = new MRoot.Listener() {

            public void onRootError(Tuple tuple)
            {
                if(tuple.d0 == MRoot.LoadError.UNEXPECTED_REDIRECT)
                {
                    if(tuple.d1 != null)
                    {
                        Uri uri = Uri.parse((String)tuple.d1);
                        String s = uri.getScheme();
                        String s1 = uri.getHost();
                        String s2 = uri.getPath();
                        if((StringUtils.saneStringEquals(s, "http") || StringUtils.saneStringEquals(s, "https")) && s1.endsWith(".facebook.com") && s2.equals("/login.php"))
                        {
                            if(FacebookAuthentication.isAuthenticated())
                                showError(FacewebErrorType.AUTHENTICATION_ERROR);
                        } else
                        {
                            mReloadNeeded = true;
                            Intent intent = new Intent("android.intent.action.VIEW", uri);
                            mContext.startActivity(intent);
                        }
                    }
                } else
                if(tuple.d0 == MRoot.LoadError.NETWORK_ERROR)
                    showError(FacewebErrorType.NETWORK_ERROR);
                else
                    showError(FacewebErrorType.AUTHENTICATION_ERROR);
            }

            public void onRootLoaded(Tuple tuple)
            {
                loadRoot();
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
;
        mScrollingEnabled = true;
        mDestroyed = false;
        loadRoot();
        setOnTouchListener(new android.view.View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionevent)
            {
                updateScrollingEnabled();
                boolean flag;
                if(mScrollingEnabled)
                    flag = false;
                else
                if(motionevent.getAction() == 2)
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
);
    }

    protected static void deregisterFacewebWebView(FacewebWebView facewebwebview)
    {
        Set set = mRegisteredWebviews;
        set;
        JVM INSTR monitorenter ;
        mRegisteredWebviews.remove(new WeakRef(facewebwebview));
        Log.v("FacewebWebView", (new StringBuilder()).append("mRegisteredWebviews has ").append(mRegisteredWebviews.size()).append(" items").toString());
        return;
    }

    static List getRegisteredFacewebWebViews()
    {
        ArrayList arraylist = new ArrayList();
        Set set = mRegisteredWebviews;
        set;
        JVM INSTR monitorenter ;
        int i;
        Iterator iterator;
        i = mRegisteredWebviews.size();
        iterator = mRegisteredWebviews.iterator();
_L1:
        FacewebWebView facewebwebview;
        do
        {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_93;
            facewebwebview = (FacewebWebView)((WeakRef)iterator.next()).get();
            if(facewebwebview != null)
                break MISSING_BLOCK_LABEL_81;
            iterator.remove();
        } while(true);
        Exception exception;
        exception;
        throw exception;
        arraylist.add(facewebwebview);
          goto _L1
        Log.v("FacewebWebView", (new StringBuilder()).append("mRegisteredWebviews gc'ed from ").append(i).append(" to ").append(mRegisteredWebviews.size()).append(" items").toString());
        set;
        JVM INSTR monitorexit ;
        return arraylist;
    }

    public static FacewebWebView newFaceWebView(Context context)
    {
        return new FacewebWebView(context);
    }

    protected static void registerFacewebWebView(FacewebWebView facewebwebview)
    {
        Set set = mRegisteredWebviews;
        set;
        JVM INSTR monitorenter ;
        mRegisteredWebviews.add(new WeakRef(facewebwebview));
        Log.v("FacewebWebView", (new StringBuilder()).append("mRegisteredWebviews has ").append(mRegisteredWebviews.size()).append(" items").toString());
        return;
    }

    protected void controlNativeProgressIndicatior(boolean flag)
    {
        if(mContext instanceof Activity)
        {
            View view = ((Activity)mContext).findViewById(0x7f0e006b);
            if(view != null)
            {
                int i;
                byte byte0;
                if(flag)
                    i = 0;
                else
                    i = 8;
                view.setVisibility(i);
                if(flag)
                    byte0 = 8;
                else
                    byte0 = 0;
                setVisibility(byte0);
            }
        }
    }

    protected void customizeWebView(Context context)
    {
        super.customizeWebView(context);
        WebSettings websettings = getSettings();
        websettings.setDomStorageEnabled(true);
        websettings.setUserAgentString((new StringBuilder()).append(websettings.getUserAgentString()).append(" ").append(UserAgent.getUserAgentString(context, Boolean.valueOf(true))).toString());
        mAuthListener = new FacebookAuthentication.Callback() {

            public void authenticationFailed()
            {
                showError(FacewebErrorType.AUTHENTICATION_ERROR);
            }

            public void authenticationNetworkFailed()
            {
                showError(FacewebErrorType.NETWORK_ERROR);
            }

            public void authenticationSuccessful()
            {
                loadRoot();
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
;
        setWebViewClient(new FacewebWebViewClient(context, mAuthListener));
        registerNativeCallHandler("rootComplete", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context1, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                mJsReady = true;
                Log.v("FacewebWebView", "facewebview js ready");
                loadMobilePage(mInitialMobilePage);
                FacewebWebView.registerFacewebWebView((FacewebWebView)facebookwebview);
                controlNativeProgressIndicatior(false);
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
);
        registerNativeCallHandler("resetCache", new FacebookWebView.NativeCallHandler() {

            /**
             * @deprecated Method handle is deprecated
             */

            public void handle(Context context1, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                this;
                JVM INSTR monitorenter ;
                boolean flag;
                flag = false;
                if(flag)
                    break MISSING_BLOCK_LABEL_22;
                if(facebookrpccall.getParameterByName("clearHttpCache") != null)
                    flag = true;
                long l;
                if(flag)
                    break MISSING_BLOCK_LABEL_130;
                l = System.currentTimeMillis();
                mRefreshTimestamps.add(Long.valueOf(l));
                for(; mRefreshTimestamps.size() > 3; mRefreshTimestamps.remove());
                break MISSING_BLOCK_LABEL_80;
                Exception exception;
                exception;
                throw exception;
                Long long1 = (Long)mRefreshTimestamps.peek();
                if(long1 != null && mRefreshTimestamps.size() == 3 && l - long1.longValue() < 0x1d4c0L)
                    flag = true;
                if(flag)
                {
                    clearCache(true);
                    MRoot.reset(mContext);
                    mRefreshTimestamps.clear();
                }
                if(facebookrpccall.getParameterByName("clearLocalStorage") != null)
                    WebStorage.getInstance().deleteAllData();
                if(facebookrpccall.getParameterByName("clearCookies") != null)
                {
                    FacebookAuthentication.startAuthentication(context1, null);
                    CookieManager.getInstance().removeAllCookie();
                }
                this;
                JVM INSTR monitorexit ;
            }

            protected Queue mRefreshTimestamps;
            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
                mRefreshTimestamps = new LinkedList();
            }
        }
);
        registerNativeCallHandler("setRootVersion", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context1, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                String s = facebookrpccall.getParameterByName("version");
                if(s != null)
                {
                    MRootVersion.set(context1, s);
                    setMobileRootUrl(context1);
                }
                MRoot.clearOldEntries(context1);
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
);
        registerNativeCallHandler("reloadCurrent", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context1, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                reset();
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
);
        registerNativeCallHandler("broadcastScript", new BroadcastScriptHandler(new Handler()));
    }

    public void destroy()
    {
        mDestroyed = true;
        deregisterFacewebWebView(this);
        super.destroy();
    }

    protected void disableScrollbars()
    {
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    protected void enableScrollbars()
    {
        setVerticalScrollBarEnabled(true);
        setHorizontalScrollBarEnabled(true);
    }

    public void ensureReadiness()
    {
        if(mReloadNeeded)
        {
            mReloadNeeded = false;
            loadRoot();
        }
    }

    protected String getCacheBuster(long l)
    {
        if(l == mHashedId) goto _L2; else goto _L1
_L1:
        String s;
        MessageDigest messagedigest;
        try
        {
            messagedigest = MessageDigest.getInstance("MD5");
            break MISSING_BLOCK_LABEL_16;
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            s = null;
        }
_L4:
        return s;
        messagedigest.reset();
        byte abyte0[];
        StringBuffer stringbuffer;
        try
        {
            Object aobj[] = new Object[1];
            aobj[0] = Long.valueOf(l);
            messagedigest.update(String.format("F8CEW7B4EV4R#!*%d", aobj).getBytes("UTF-8"));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            s = null;
            continue; /* Loop/switch isn't completed */
        }
        abyte0 = messagedigest.digest();
        stringbuffer = new StringBuffer();
        for(int i = 0; i < 6; i++)
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = Byte.valueOf(abyte0[i]);
            stringbuffer.append(String.format("%02x", aobj1));
        }

        mHashedId = l;
        mHash = stringbuffer.toString();
_L2:
        s = mHash;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void loadMobilePage(String s)
    {
        if(s != null)
            if(mJsReady)
            {
                FacebookWebView.JsReturnHandler jsreturnhandler = new FacebookWebView.JsReturnHandler() {

                    public void handle(FacebookWebView facebookwebview, String s1, boolean flag, String s2)
                    {
                        if(flag)
                            Log.e("FacewebWebView", "js exception during loadMobilePage");
                    }

                    final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
                }
;
                ArrayList arraylist = new ArrayList();
                arraylist.add(s);
                executeJsFunction("fwLoadPage", arraylist, jsreturnhandler);
            } else
            {
                if(mInitialMobilePage != null)
                {
                    StringBuilder stringbuilder = new StringBuilder();
                    stringbuilder.append("loadMobilePage called with \"").append(s).append("\" when page \"").append(mInitialMobilePage).append("\" is already queued");
                    Log.e("FacewebWebView", stringbuilder.toString());
                }
                mInitialMobilePage = s;
            }
    }

    protected void loadRoot()
    {
        if(!mDestroyed)
        {
            Tuple tuple = MRoot.get(mContext, new Tuple(mMobileRootUrl, getSettings().getUserAgentString()), mRootLoadListener);
            if(FacebookAuthentication.isAuthenticated())
            {
                if(tuple != null)
                {
                    String s = ((String)tuple.d1).toString();
                    loadDataWithBaseURL((String)tuple.d0, s, "text/html", "utf-8", (String)tuple.d0);
                } else
                {
                    controlNativeProgressIndicatior(true);
                }
            } else
            {
                FacebookAuthentication.startAuthentication(mContext, mAuthListener);
            }
        }
    }

    public void loadUrl(String s)
    {
        if(!mDestroyed)
            super.loadUrl(s);
    }

    public void refresh()
    {
        executeJsFunction("fwReload", new ArrayList(), null);
    }

    public void reset()
    {
        deregisterFacewebWebView(this);
        mJsReady = false;
        loadRoot();
        controlNativeProgressIndicatior(true);
    }

    protected void setMobileRootUrl(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        FacebookSessionInfo facebooksessioninfo = null;
        if(appsession != null)
            facebooksessioninfo = appsession.getSessionInfo();
        mMobileRootUrl = com.facebook.katana.Constants.URL.getFacewebRootUrl(context);
        if(facebooksessioninfo != null)
        {
            Uri uri = Uri.parse(mMobileRootUrl);
            String s = MRootVersion.get(context);
            android.net.Uri.Builder builder = uri.buildUpon().appendQueryParameter("fb_fw_cache_bust", getCacheBuster(facebooksessioninfo.userId));
            if(s != null)
                builder.appendQueryParameter("fb_fw_version", s);
            mMobileRootUrl = builder.build().toString();
        }
    }

    protected void showError(FacewebErrorType faceweberrortype)
    {
        if(faceweberrortype != FacewebErrorType.NETWORK_ERROR) goto _L2; else goto _L1
_L1:
        Toaster.toast(mContext, mContext.getString(0x7f0a004e));
_L4:
        return;
_L2:
        if(faceweberrortype == FacewebErrorType.AUTHENTICATION_ERROR)
        {
            AppSession appsession = AppSession.getActiveSession(mContext, false);
            String s = null;
            if(appsession != null)
            {
                FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
                if(facebooksessioninfo != null)
                    s = facebooksessioninfo.username;
            }
            Intent intent = new Intent(mContext, com/facebook/katana/LoginNotificationActivity);
            if(s != null)
                intent.putExtra("un", s);
            intent.addFlags(0x10000000);
            mContext.startActivity(intent);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void updateScrollingEnabled()
    {
        executeJsWithReturn("(function(){try {if (window.fwHaveLoadedPage && fwHaveLoadedPage()) {return '1';}} catch (e) {return '0';}})()", new FacebookWebView.JsReturnHandler() {

            public void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1)
            {
                if("1".equals(s1))
                {
                    mScrollingEnabled = true;
                    enableScrollbars();
                } else
                {
                    mScrollingEnabled = false;
                    disableScrollbars();
                }
            }

            final FacewebWebView this$0;

            
            {
                this$0 = FacewebWebView.this;
                super();
            }
        }
);
    }

    public static final int BUSTER_LENGTH = 12;
    public static final String ENTER_FOREGROUND_JS_FORMAT = "(function() { if (window.fwDidEnterForeground) { fwDidEnterForeground(%d, %s); } })()";
    public static final int MAX_REFRESH_COUNT = 3;
    public static final int MAX_REFRESH_INTERVAL_MS = 0x1d4c0;
    public static final String RPC_ADD_FRIEND = "addFriend";
    public static final String RPC_BROADCAST_SCRIPT = "broadcastScript";
    public static final String RPC_CLOSE = "close";
    public static final String RPC_DISMISS_MODAL_DIALOG = "dismissModalDialog";
    public static final String RPC_ENABLE_REFRESH = "enablePullToRefresh";
    public static final String RPC_MIN_PAUSE_SECONDS = "minPauseSeconds";
    public static final String RPC_PAGE_LOADED = "pageLoaded";
    public static final String RPC_PAGE_LOADING = "pageLoading";
    public static final String RPC_PERF_CACHED_RESPONSE_LOADED = "perf.cachedResponseLoaded";
    public static final String RPC_RELOAD_CURRENT = "reloadCurrent";
    public static final String RPC_REMOVE_PUBLISHER = "removePublisher";
    public static final String RPC_RESET_CACHE = "resetCache";
    public static final String RPC_RESET_CACHE_COOKIES = "clearCookies";
    public static final String RPC_RESET_CACHE_LOCAL_STORAGE = "clearLocalStorage";
    public static final String RPC_RESET_HTTP_CACHE = "clearHttpCache";
    public static final String RPC_ROOT_COMPLETE = "rootComplete";
    public static final String RPC_SET_ACTION_MENU = "setActionMenu";
    public static final String RPC_SET_NAV_BAR_BUTTON = "setNavBarButton";
    public static final String RPC_SET_NAV_BAR_HIDDEN = "setNavBarHidden";
    public static final String RPC_SET_NAV_BAR_TITLE = "setNavBarTitle";
    public static final String RPC_SET_ROOT_VERSION = "setRootVersion";
    public static final String RPC_SET_ROOT_VERSION_VERSION = "version";
    public static final String RPC_SET_TOOLBAR_SEGMENTS = "setToolbarSegments";
    public static final String RPC_SHOW_COMMENT_PUBLISHER = "showCommentPublisher";
    public static final String RPC_SHOW_MSG_COMPOSER = "showMsgComposer";
    public static final String RPC_SHOW_MSG_REPLY_PUBLISHER = "showMsgReplyPublisher";
    public static final String RPC_SHOW_PICKER_VIEW = "showPickerView";
    public static final String RPC_SHOW_PUBLISHER = "showPublisher";
    public static final String RPC_USER_ID = "userId";
    private static final String TAG = "FacewebWebView";
    protected static String mHash;
    protected static long mHashedId = -1L;
    protected static Set mRegisteredWebviews = new HashSet();
    protected FacebookAuthentication.Callback mAuthListener;
    protected boolean mDestroyed;
    protected String mInitialMobilePage;
    protected boolean mJsReady;
    protected String mMobileRootUrl;
    protected boolean mReloadNeeded;
    protected MRoot.Listener mRootLoadListener;
    protected boolean mScrollingEnabled;

}
