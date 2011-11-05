package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebView;
import com.facebook.katana.Constants;
import com.facebook.katana.util.Log;
import com.facebook.katana.webview.BaseWebView;
import com.facebook.katana.webview.FacebookAuthentication;
import com.facebook.katana.webview.FacebookJsBridge;
import com.facebook.katana.webview.FacebookRpcCall;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FacebookWebView extends BaseWebView {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final String RPC_APP_LOG = "appLog";
   private static final String TAG = "FacebookWebView";
   protected final Context mContext;
   protected FacebookJsBridge mJsInterface;
   protected Map<String, FacebookWebView.UrlHandler> mUrlHandlers;


   static {
      byte var0;
      if(!FacebookWebView.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FacebookWebView(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   public FacebookWebView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   public FacebookWebView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mContext = var1;
   }

   protected void customizeWebView(Context var1) {
      super.customizeWebView(var1);
      HashMap var2 = new HashMap();
      this.mUrlHandlers = var2;
      FacebookJsBridge var3 = new FacebookJsBridge("FacebookWebView");
      this.mJsInterface = var3;
      FacebookJsBridge.UriHandler var4 = this.mJsInterface.handler;
      this.registerUrlHandler("fbrpc", var4);
   }

   void executeJs(String var1) {
      String var2 = "javascript:" + var1;
      this.loadUrl(var2);
   }

   public void executeJs(String var1, FacebookWebView.JsReturnHandler var2) {
      this.executeJsWithReturn(var1, var2);
   }

   public void executeJsFunction(String var1, List<?> var2, FacebookWebView.JsReturnHandler var3) {
      String var4 = FacebookJsBridge.jsEncodeCall(var1, var2);
      this.executeJsWithReturn(var4, var3);
   }

   public String executeJsWithReturn(String var1, FacebookWebView.JsReturnHandler var2) {
      FacebookJsBridge var3 = this.mJsInterface;
      String var6 = var3.registerPendingJsCall(var1, var2);
      String var7 = "__android_injected_function_" + var6;
      Object[] var8 = new Object[]{var7, var1};
      String var9 = String.format("javascript:var %1$s = function() { return %2$s;};", var8);
      this.loadUrl(var9);
      HashMap var12 = new HashMap();
      var12.put("callId", var6);
      FacebookRpcCall.JsVariable var14 = new FacebookRpcCall.JsVariable("__android_exception");
      var12.put("exc", var14);
      FacebookRpcCall.JsVariable var16 = new FacebookRpcCall.JsVariable("__android_retval");
      var12.put("retval", var16);
      String var18 = FacebookRpcCall.jsComposeFacebookRpcCall("fbrpc", "facebook", (UUID)null, (UUID)null, "call_return", var12);
      String var25;
      if(Constants.isBetaBuild()) {
         HashMap var19 = new HashMap();
         var19.put("callId", var6);
         FacebookRpcCall.JsVariable var21 = new FacebookRpcCall.JsVariable("err");
         var19.put("exc", var21);
         String var23 = FacebookRpcCall.jsComposeFacebookRpcCall("fbrpc", "facebook", (UUID)null, (UUID)null, "call_return", var19);
         Object[] var24 = new Object[]{var7, var23, var18};
         var25 = String.format("javascript:(function() { var __android_exception = null; var __android_retval; try { __android_retval = %1$s();} catch (err) { window.prompt(%2$s);throw err; }window.prompt(%3$s);%1$s = null;})()", var24);
      } else {
         Object[] var28 = new Object[]{var7, var18};
         var25 = String.format("javascript:(function() { var __android_exception = null; var __android_retval = null; try { __android_retval = %1$s();} catch (err) { __android_exception = true; }window.prompt(%2$s);%1$s = null;})()", var28);
      }

      this.loadUrl(var25);
      return var6;
   }

   public void registerNativeCallHandler(String var1, FacebookWebView.NativeCallHandler var2) {
      this.mJsInterface.registerNativeCallHandler(var1, var2);
   }

   public void registerUrlHandler(String var1, FacebookWebView.UrlHandler var2) {
      FacebookWebView.UrlHandler var3 = (FacebookWebView.UrlHandler)this.mUrlHandlers.put(var1, var2);
      if(!$assertionsDisabled) {
         if(var3 != null) {
            throw new AssertionError();
         }
      }
   }

   protected void setChromeClient(Context var1) {
      FacebookWebView.RPCChromeClient var2 = new FacebookWebView.RPCChromeClient();
      this.setWebChromeClient(var2);
   }

   protected class RPCChromeClient extends BaseWebView.BaseWebChromeClient {

      protected RPCChromeClient() {}

      public boolean onJsPrompt(WebView var1, String var2, String var3, String var4, JsPromptResult var5) {
         boolean var6;
         if(!(var1 instanceof FacebookWebView)) {
            var6 = false;
         } else {
            FacebookWebView var7 = (FacebookWebView)var1;
            Uri var8 = Uri.parse(var3);
            Map var9 = var7.mUrlHandlers;
            String var10 = var8.getScheme();
            FacebookWebView.UrlHandler var11 = (FacebookWebView.UrlHandler)var9.get(var10);
            if(var11 != null) {
               Context var12 = FacebookWebView.this.mContext;
               var11.handle(var12, var7, var8);
               var5.cancel();
               var6 = true;
            } else {
               var6 = false;
            }
         }

         return var6;
      }
   }

   public interface UrlHandler {

      void handle(Context var1, FacebookWebView var2, Uri var3);
   }

   public abstract static class NativeUICallHandler implements FacebookWebView.NativeCallHandler {

      protected Handler mHandler;


      public NativeUICallHandler(Handler var1) {
         this.mHandler = var1;
      }

      public final void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         this.handleNonUI(var2, var3);
         Handler var4 = this.mHandler;
         FacebookWebView.NativeUICallHandler.1 var5 = new FacebookWebView.NativeUICallHandler.1(var2, var3);
         var4.post(var5);
      }

      public void handleNonUI(FacebookWebView var1, FacebookRpcCall var2) {}

      public abstract void handleUI(FacebookWebView var1, FacebookRpcCall var2);

      class 1 implements Runnable {

         // $FF: synthetic field
         final FacebookRpcCall val$call;
         // $FF: synthetic field
         final FacebookWebView val$wv;


         1(FacebookWebView var2, FacebookRpcCall var3) {
            this.val$wv = var2;
            this.val$call = var3;
         }

         public void run() {
            FacebookWebView.NativeUICallHandler var1 = NativeUICallHandler.this;
            FacebookWebView var2 = this.val$wv;
            FacebookRpcCall var3 = this.val$call;
            var1.handleUI(var2, var3);
         }
      }
   }

   public interface NativeCallHandler {

      void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3);
   }

   static class FacebookWebViewClient extends FacebookAuthentication.AuthWebViewClient {

      public FacebookWebViewClient(Context var1, FacebookAuthentication.Callback var2) {
         super(var1, var2);
      }

      public void onReceivedError(WebView var1, int var2, String var3, String var4) {
         super.onReceivedError(var1, var2, var3, var4);
         if(Constants.isBetaBuild()) {
            StringBuilder var5 = new StringBuilder();
            StringBuilder var6 = var5.append("url ").append(var4).append(" failed (code: ").append(var2).append("; description: ").append(var3).append(")");
            String var7 = var5.toString();
            Log.v("FacebookWebView", var7);
         }
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         boolean var3;
         if(super.shouldOverrideUrlLoading(var1, var2)) {
            var3 = true;
         } else if(!(var1 instanceof FacebookWebView)) {
            var3 = false;
         } else {
            FacebookWebView var4 = (FacebookWebView)var1;
            Uri var5 = Uri.parse(var2);
            String var6 = var5.getScheme();
            FacebookWebView.UrlHandler var7 = (FacebookWebView.UrlHandler)var4.mUrlHandlers.get(var6);
            if(var7 != null) {
               Context var8 = this.mContext;
               FacebookWebView var9 = (FacebookWebView)var1;
               var7.handle(var8, var9, var5);
               var3 = true;
            } else {
               var3 = false;
            }
         }

         return var3;
      }
   }

   public interface JsReturnHandler {

      void handle(FacebookWebView var1, String var2, boolean var3, String var4);
   }
}
