package com.facebook.katana.webview;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.facebook.katana.Constants;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.LoginNotificationActivity;
import com.facebook.katana.UserAgent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.WeakRef;
import com.facebook.katana.webview.FacebookAuthentication;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;
import com.facebook.katana.webview.MRoot;
import com.facebook.katana.webview.MRootVersion;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FacewebWebView extends FacebookWebView {

   public static final int BUSTER_LENGTH = 12;
   public static final String ENTER_FOREGROUND_JS_FORMAT = "(function() { if (window.fwDidEnterForeground) { fwDidEnterForeground(%d, %s); } })()";
   public static final int MAX_REFRESH_COUNT = 3;
   public static final int MAX_REFRESH_INTERVAL_MS = 120000;
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
   protected static long mHashedId = 65535L;
   protected static Set<WeakRef<FacewebWebView>> mRegisteredWebviews = new HashSet();
   protected FacebookAuthentication.Callback mAuthListener;
   protected boolean mDestroyed;
   protected String mInitialMobilePage;
   protected boolean mJsReady;
   protected String mMobileRootUrl;
   protected boolean mReloadNeeded;
   protected MRoot.Listener mRootLoadListener;
   protected boolean mScrollingEnabled;


   protected FacewebWebView(Context var1) {
      super(var1);
      this.setMobileRootUrl(var1);
      FacewebWebView.7 var2 = new FacewebWebView.7();
      this.mRootLoadListener = var2;
      this.mScrollingEnabled = (boolean)1;
      this.mDestroyed = (boolean)0;
      this.loadRoot();
      FacewebWebView.8 var3 = new FacewebWebView.8();
      this.setOnTouchListener(var3);
   }

   protected static void deregisterFacewebWebView(FacewebWebView var0) {
      Set var1 = mRegisteredWebviews;
      synchronized(var1) {
         Set var2 = mRegisteredWebviews;
         WeakRef var3 = new WeakRef(var0);
         var2.remove(var3);
         StringBuilder var5 = (new StringBuilder()).append("mRegisteredWebviews has ");
         int var6 = mRegisteredWebviews.size();
         String var7 = var5.append(var6).append(" items").toString();
         Log.v("FacewebWebView", var7);
      }
   }

   static List<FacewebWebView> getRegisteredFacewebWebViews() {
      ArrayList var0 = new ArrayList();
      Set var1 = mRegisteredWebviews;
      synchronized(var1) {
         int var2 = mRegisteredWebviews.size();
         Iterator var3 = mRegisteredWebviews.iterator();

         while(var3.hasNext()) {
            FacewebWebView var4 = (FacewebWebView)((WeakRef)var3.next()).get();
            if(var4 == null) {
               var3.remove();
            } else {
               var0.add(var4);
            }
         }

         StringBuilder var7 = (new StringBuilder()).append("mRegisteredWebviews gc\'ed from ").append(var2).append(" to ");
         int var8 = mRegisteredWebviews.size();
         String var9 = var7.append(var8).append(" items").toString();
         Log.v("FacewebWebView", var9);
         return var0;
      }
   }

   public static FacewebWebView newFaceWebView(Context var0) {
      return new FacewebWebView(var0);
   }

   protected static void registerFacewebWebView(FacewebWebView var0) {
      Set var1 = mRegisteredWebviews;
      synchronized(var1) {
         Set var2 = mRegisteredWebviews;
         WeakRef var3 = new WeakRef(var0);
         var2.add(var3);
         StringBuilder var5 = (new StringBuilder()).append("mRegisteredWebviews has ");
         int var6 = mRegisteredWebviews.size();
         String var7 = var5.append(var6).append(" items").toString();
         Log.v("FacewebWebView", var7);
      }
   }

   protected void controlNativeProgressIndicatior(boolean var1) {
      if(this.mContext instanceof Activity) {
         View var2 = ((Activity)this.mContext).findViewById(2131624043);
         if(var2 != null) {
            byte var3;
            if(var1) {
               var3 = 0;
            } else {
               var3 = 8;
            }

            var2.setVisibility(var3);
            byte var4;
            if(var1) {
               var4 = 8;
            } else {
               var4 = 0;
            }

            this.setVisibility(var4);
         }
      }
   }

   protected void customizeWebView(Context var1) {
      super.customizeWebView(var1);
      WebSettings var2 = this.getSettings();
      var2.setDomStorageEnabled((boolean)1);
      StringBuilder var3 = new StringBuilder();
      String var4 = var2.getUserAgentString();
      StringBuilder var5 = var3.append(var4).append(" ");
      Boolean var6 = Boolean.valueOf((boolean)1);
      String var7 = UserAgent.getUserAgentString(var1, var6);
      String var8 = var5.append(var7).toString();
      var2.setUserAgentString(var8);
      FacewebWebView.2 var9 = new FacewebWebView.2();
      this.mAuthListener = var9;
      FacebookAuthentication.Callback var10 = this.mAuthListener;
      FacewebWebView.FacewebWebViewClient var11 = new FacewebWebView.FacewebWebViewClient(var1, var10);
      this.setWebViewClient(var11);
      FacewebWebView.3 var12 = new FacewebWebView.3();
      this.registerNativeCallHandler("rootComplete", var12);
      FacewebWebView.4 var13 = new FacewebWebView.4();
      this.registerNativeCallHandler("resetCache", var13);
      FacewebWebView.5 var14 = new FacewebWebView.5();
      this.registerNativeCallHandler("setRootVersion", var14);
      FacewebWebView.6 var15 = new FacewebWebView.6();
      this.registerNativeCallHandler("reloadCurrent", var15);
      Handler var16 = new Handler();
      FacewebWebView.BroadcastScriptHandler var17 = new FacewebWebView.BroadcastScriptHandler(var16);
      this.registerNativeCallHandler("broadcastScript", var17);
   }

   public void destroy() {
      this.mDestroyed = (boolean)1;
      deregisterFacewebWebView(this);
      super.destroy();
   }

   protected void disableScrollbars() {
      this.setVerticalScrollBarEnabled((boolean)0);
      this.setHorizontalScrollBarEnabled((boolean)0);
   }

   protected void enableScrollbars() {
      this.setVerticalScrollBarEnabled((boolean)1);
      this.setHorizontalScrollBarEnabled((boolean)1);
   }

   public void ensureReadiness() {
      if(this.mReloadNeeded) {
         this.mReloadNeeded = (boolean)0;
         this.loadRoot();
      }
   }

   protected String getCacheBuster(long var1) {
      long var3 = mHashedId;
      String var18;
      if(var1 != var3) {
         MessageDigest var5;
         try {
            var5 = MessageDigest.getInstance("MD5");
         } catch (NoSuchAlgorithmException var21) {
            var18 = null;
            return var18;
         }

         MessageDigest var6 = var5;
         var5.reset();

         try {
            Object[] var7 = new Object[1];
            Long var8 = Long.valueOf(var1);
            var7[0] = var8;
            byte[] var9 = String.format("F8CEW7B4EV4R#!*%d", var7).getBytes("UTF-8");
            var6.update(var9);
         } catch (UnsupportedEncodingException var20) {
            var18 = null;
            return var18;
         }

         byte[] var10 = var5.digest();
         StringBuffer var11 = new StringBuffer();

         for(int var12 = 0; var12 < 6; ++var12) {
            Object[] var13 = new Object[1];
            Byte var14 = Byte.valueOf(var10[var12]);
            var13[0] = var14;
            String var15 = String.format("%02x", var13);
            var11.append(var15);
         }

         mHashedId = var1;
         mHash = var11.toString();
      }

      var18 = mHash;
      return var18;
   }

   public void loadMobilePage(String var1) {
      if(var1 != null) {
         if(this.mJsReady) {
            FacewebWebView.1 var2 = new FacewebWebView.1();
            ArrayList var3 = new ArrayList();
            var3.add(var1);
            this.executeJsFunction("fwLoadPage", var3, var2);
         } else {
            if(this.mInitialMobilePage != null) {
               StringBuilder var5 = new StringBuilder();
               StringBuilder var6 = var5.append("loadMobilePage called with \"").append(var1).append("\" when page \"");
               String var7 = this.mInitialMobilePage;
               StringBuilder var8 = var6.append(var7).append("\" is already queued");
               String var9 = var5.toString();
               Log.e("FacewebWebView", var9);
            }

            this.mInitialMobilePage = var1;
         }
      }
   }

   protected void loadRoot() {
      if(!this.mDestroyed) {
         Context var1 = this.mContext;
         String var2 = this.mMobileRootUrl;
         String var3 = this.getSettings().getUserAgentString();
         Tuple var4 = new Tuple(var2, var3);
         MRoot.Listener var5 = this.mRootLoadListener;
         Tuple var6 = MRoot.get(var1, var4, var5);
         if(FacebookAuthentication.isAuthenticated()) {
            if(var6 != null) {
               String var7 = ((String)var6.d1).toString();
               String var8 = (String)var6.d0;
               String var9 = (String)var6.d0;
               this.loadDataWithBaseURL(var8, var7, "text/html", "utf-8", var9);
            } else {
               this.controlNativeProgressIndicatior((boolean)1);
            }
         } else {
            Context var10 = this.mContext;
            FacebookAuthentication.Callback var11 = this.mAuthListener;
            FacebookAuthentication.startAuthentication(var10, var11);
         }
      }
   }

   public void loadUrl(String var1) {
      if(!this.mDestroyed) {
         super.loadUrl(var1);
      }
   }

   public void refresh() {
      ArrayList var1 = new ArrayList();
      this.executeJsFunction("fwReload", var1, (FacebookWebView.JsReturnHandler)null);
   }

   public void reset() {
      deregisterFacewebWebView(this);
      this.mJsReady = (boolean)0;
      this.loadRoot();
      this.controlNativeProgressIndicatior((boolean)1);
   }

   protected void setMobileRootUrl(Context var1) {
      AppSession var2 = AppSession.getActiveSession(var1, (boolean)0);
      FacebookSessionInfo var3 = null;
      if(var2 != null) {
         var3 = var2.getSessionInfo();
      }

      String var4 = Constants.URL.getFacewebRootUrl(var1);
      this.mMobileRootUrl = var4;
      if(var3 != null) {
         Uri var5 = Uri.parse(this.mMobileRootUrl);
         String var6 = MRootVersion.get(var1);
         Builder var7 = var5.buildUpon();
         long var8 = var3.userId;
         String var10 = this.getCacheBuster(var8);
         Builder var11 = var7.appendQueryParameter("fb_fw_cache_bust", var10);
         if(var6 != null) {
            var11.appendQueryParameter("fb_fw_version", var6);
         }

         String var13 = var11.build().toString();
         this.mMobileRootUrl = var13;
      }
   }

   protected void showError(FacewebWebView.FacewebErrorType var1) {
      FacewebWebView.FacewebErrorType var2 = FacewebWebView.FacewebErrorType.NETWORK_ERROR;
      if(var1 == var2) {
         Context var3 = this.mContext;
         String var4 = this.mContext.getString(2131361870);
         Toaster.toast(var3, var4);
      } else {
         FacewebWebView.FacewebErrorType var5 = FacewebWebView.FacewebErrorType.AUTHENTICATION_ERROR;
         if(var1 == var5) {
            AppSession var6 = AppSession.getActiveSession(this.mContext, (boolean)0);
            String var7 = null;
            if(var6 != null) {
               FacebookSessionInfo var8 = var6.getSessionInfo();
               if(var8 != null) {
                  var7 = var8.username;
               }
            }

            Context var9 = this.mContext;
            Intent var10 = new Intent(var9, LoginNotificationActivity.class);
            if(var7 != null) {
               var10.putExtra("un", var7);
            }

            Intent var12 = var10.addFlags(268435456);
            this.mContext.startActivity(var10);
         }
      }
   }

   public void updateScrollingEnabled() {
      FacewebWebView.9 var1 = new FacewebWebView.9();
      this.executeJsWithReturn("(function(){try {if (window.fwHaveLoadedPage && fwHaveLoadedPage()) {return \'1\';}} catch (e) {return \'0\';}})()", var1);
   }

   protected class FacewebWebViewClient extends FacebookWebView.FacebookWebViewClient {

      public FacewebWebViewClient(Context var2, FacebookAuthentication.Callback var3) {
         super(var2, var3);
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         boolean var3;
         if(super.shouldOverrideUrlLoading(var1, var2)) {
            var3 = true;
         } else {
            Uri var4 = Uri.parse(var2);
            if(!FacewebWebView.this.mJsReady && Constants.URL.isFacebookUrl(var4)) {
               var3 = false;
            } else if(IntentUriHandler.getIntentForUri(this.mContext, var2) != null) {
               IntentUriHandler.handleUri(this.mContext, var2);
               var3 = true;
            } else {
               Intent var6 = new Intent("android.intent.action.VIEW", var4);

               try {
                  this.mContext.startActivity(var6);
               } catch (ActivityNotFoundException var8) {
                  ;
               }

               var3 = true;
            }
         }

         return var3;
      }
   }

   class BroadcastScriptHandler extends FacebookWebView.NativeUICallHandler {

      public static final String PARAM_DELAY = "delay";
      public static final String PARAM_SCRIPT = "script";
      protected final Handler mHandler;


      public BroadcastScriptHandler(Handler var2) {
         super(var2);
         this.mHandler = var2;
      }

      public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
         String var3 = var2.getParameterByName("script");
         int var4 = Integer.valueOf(var2.getParameterByName("delay")).intValue();
         if(!Constants.URL.isFacebookUrl(var1.getUrl())) {
            StringBuilder var5 = (new StringBuilder()).append("Page with Non-facebook URL (");
            String var6 = var1.getUrl();
            String var7 = var5.append(var6).append(") attempting to invoke broadcastScript").toString();
            Log.e("FacewebWebView", var7);
         } else {
            Handler var8 = this.mHandler;
            FacewebWebView.BroadcastScriptHandler.1 var9 = new FacewebWebView.BroadcastScriptHandler.1(var3);
            long var10 = (long)var4;
            var8.postDelayed(var9, var10);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final String val$script;


         1(String var2) {
            this.val$script = var2;
         }

         public void run() {
            Iterator var1 = FacewebWebView.getRegisteredFacewebWebViews().iterator();

            while(var1.hasNext()) {
               FacewebWebView var2 = (FacewebWebView)var1.next();
               if(Constants.URL.isFacebookUrl(var2.getUrl())) {
                  String var3 = this.val$script;
                  var2.executeJsWithReturn(var3, (FacebookWebView.JsReturnHandler)null);
               }
            }

         }
      }
   }

   class 6 implements FacebookWebView.NativeCallHandler {

      6() {}

      public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         FacewebWebView.this.reset();
      }
   }

   class 7 implements MRoot.Listener {

      7() {}

      public void onRootError(Tuple<MRoot.LoadError, String> var1) {
         Object var2 = var1.d0;
         MRoot.LoadError var3 = MRoot.LoadError.UNEXPECTED_REDIRECT;
         if(var2 == var3) {
            if(var1.d1 != null) {
               Uri var4 = Uri.parse((String)var1.d1);
               String var5 = var4.getScheme();
               String var6 = var4.getHost();
               String var7 = var4.getPath();
               if((StringUtils.saneStringEquals(var5, "http") || StringUtils.saneStringEquals(var5, "https")) && var6.endsWith(".facebook.com") && var7.equals("/login.php")) {
                  if(FacebookAuthentication.isAuthenticated()) {
                     FacewebWebView var8 = FacewebWebView.this;
                     FacewebWebView.FacewebErrorType var9 = FacewebWebView.FacewebErrorType.AUTHENTICATION_ERROR;
                     var8.showError(var9);
                  }
               } else {
                  FacewebWebView.this.mReloadNeeded = (boolean)1;
                  Intent var10 = new Intent("android.intent.action.VIEW", var4);
                  FacewebWebView.this.mContext.startActivity(var10);
               }
            }
         } else {
            Object var11 = var1.d0;
            MRoot.LoadError var12 = MRoot.LoadError.NETWORK_ERROR;
            if(var11 == var12) {
               FacewebWebView var13 = FacewebWebView.this;
               FacewebWebView.FacewebErrorType var14 = FacewebWebView.FacewebErrorType.NETWORK_ERROR;
               var13.showError(var14);
            } else {
               FacewebWebView var15 = FacewebWebView.this;
               FacewebWebView.FacewebErrorType var16 = FacewebWebView.FacewebErrorType.AUTHENTICATION_ERROR;
               var15.showError(var16);
            }
         }
      }

      public void onRootLoaded(Tuple<String, String> var1) {
         FacewebWebView.this.loadRoot();
      }
   }

   class 8 implements OnTouchListener {

      8() {}

      public boolean onTouch(View var1, MotionEvent var2) {
         FacewebWebView.this.updateScrollingEnabled();
         boolean var3;
         if(FacewebWebView.this.mScrollingEnabled) {
            var3 = false;
         } else if(var2.getAction() == 2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   class 9 implements FacebookWebView.JsReturnHandler {

      9() {}

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         if("1".equals(var4)) {
            FacewebWebView.this.mScrollingEnabled = (boolean)1;
            FacewebWebView.this.enableScrollbars();
         } else {
            FacewebWebView.this.mScrollingEnabled = (boolean)0;
            FacewebWebView.this.disableScrollbars();
         }
      }
   }

   class 2 implements FacebookAuthentication.Callback {

      2() {}

      public void authenticationFailed() {
         FacewebWebView var1 = FacewebWebView.this;
         FacewebWebView.FacewebErrorType var2 = FacewebWebView.FacewebErrorType.AUTHENTICATION_ERROR;
         var1.showError(var2);
      }

      public void authenticationNetworkFailed() {
         FacewebWebView var1 = FacewebWebView.this;
         FacewebWebView.FacewebErrorType var2 = FacewebWebView.FacewebErrorType.NETWORK_ERROR;
         var1.showError(var2);
      }

      public void authenticationSuccessful() {
         FacewebWebView.this.loadRoot();
      }
   }

   class 3 implements FacebookWebView.NativeCallHandler {

      3() {}

      public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         FacewebWebView.this.mJsReady = (boolean)1;
         Log.v("FacewebWebView", "facewebview js ready");
         FacewebWebView var4 = FacewebWebView.this;
         String var5 = FacewebWebView.this.mInitialMobilePage;
         var4.loadMobilePage(var5);
         FacewebWebView.registerFacewebWebView((FacewebWebView)var2);
         FacewebWebView.this.controlNativeProgressIndicatior((boolean)0);
      }
   }

   class 4 implements FacebookWebView.NativeCallHandler {

      protected Queue<Long> mRefreshTimestamps;


      4() {
         LinkedList var2 = new LinkedList();
         this.mRefreshTimestamps = var2;
      }

      public void handle(Context param1, FacebookWebView param2, FacebookRpcCall param3) {
         // $FF: Couldn't be decompiled
      }
   }

   protected static enum FacewebErrorType {

      // $FF: synthetic field
      private static final FacewebWebView.FacewebErrorType[] $VALUES;
      AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", 1),
      NETWORK_ERROR("NETWORK_ERROR", 0);


      static {
         FacewebWebView.FacewebErrorType[] var0 = new FacewebWebView.FacewebErrorType[2];
         FacewebWebView.FacewebErrorType var1 = NETWORK_ERROR;
         var0[0] = var1;
         FacewebWebView.FacewebErrorType var2 = AUTHENTICATION_ERROR;
         var0[1] = var2;
         $VALUES = var0;
      }

      private FacewebErrorType(String var1, int var2) {}
   }

   class 5 implements FacebookWebView.NativeCallHandler {

      5() {}

      public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         String var4 = var3.getParameterByName("version");
         if(var4 != null) {
            MRootVersion.set(var1, var4);
            FacewebWebView.this.setMobileRootUrl(var1);
         }

         MRoot.clearOldEntries(var1);
      }
   }

   class 1 implements FacebookWebView.JsReturnHandler {

      1() {}

      public void handle(FacebookWebView var1, String var2, boolean var3, String var4) {
         if(var3) {
            Log.e("FacewebWebView", "js exception during loadMobilePage");
         }
      }
   }
}
