package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

class FacebookJsBridge {

   protected static final StringUtils.StringProcessor jsonStringQuoter = new FacebookJsBridge.1();
   protected static final FBJsonFactory mJsonFactory = new FBJsonFactory();
   protected final String TAG;
   public final FacebookJsBridge.UriHandler handler;
   protected Map<String, Set<FacebookWebView.NativeCallHandler>> mNativeCallHandlers;
   protected final AtomicInteger mNextCallId;
   protected Map<String, Tuple<String, FacebookWebView.JsReturnHandler>> mPendingJsCalls;


   FacebookJsBridge(String var1) {
      this.TAG = var1;
      FacebookJsBridge.UriHandler var2 = new FacebookJsBridge.UriHandler();
      this.handler = var2;
      HashMap var3 = new HashMap();
      this.mNativeCallHandlers = var3;
      HashMap var4 = new HashMap();
      this.mPendingJsCalls = var4;
      AtomicInteger var5 = new AtomicInteger();
      this.mNextCallId = var5;
      FacebookJsBridge.NativeCallReturnHandler var6 = new FacebookJsBridge.NativeCallReturnHandler();
      this.registerNativeCallHandler("call_return", var6);
   }

   static String jsEncodeCall(String var0, List<?> var1) {
      StringBuilder var2 = new StringBuilder(var0);
      StringBuilder var3 = var2.append("(");
      StringUtils.StringProcessor var4 = jsonStringQuoter;
      Object[] var5 = new Object[]{var1};
      StringUtils.join(var2, ", ", var4, var5);
      StringBuilder var6 = var2.append(");");
      return var2.toString();
   }

   public void registerNativeCallHandler(String var1, FacebookWebView.NativeCallHandler var2) {
      Object var3 = (Set)this.mNativeCallHandlers.get(var1);
      if(var3 == null) {
         var3 = new HashSet();
         this.mNativeCallHandlers.put(var1, var3);
      }

      ((Set)var3).add(var2);
   }

   public String registerPendingJsCall(String var1, FacebookWebView.JsReturnHandler var2) {
      String var3 = Integer.toString(this.mNextCallId.getAndIncrement());
      synchronized(this) {
         Map var4 = this.mPendingJsCalls;
         Tuple var5 = new Tuple(var1, var2);
         var4.put(var3, var5);
         return var3;
      }
   }

   protected class UriHandler implements FacebookWebView.UrlHandler {

      protected UriHandler() {}

      public void handle(Context var1, FacebookWebView var2, Uri var3) {
         FacebookRpcCall var4 = new FacebookRpcCall(var3);
         if(!this.handleCall(var1, var2, var4)) {
            String var5 = FacebookJsBridge.this.TAG;
            StringBuilder var6 = (new StringBuilder()).append("RPC call ");
            String var7 = var4.method;
            String var8 = var6.append(var7).append(" not handled").toString();
            Log.e(var5, var8);
         }
      }

      protected boolean handleCall(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
         Map var4 = FacebookJsBridge.this.mNativeCallHandlers;
         String var5 = var3.method;
         Set var6 = (Set)var4.get(var5);
         boolean var7;
         if(var6 == null) {
            var7 = false;
         } else {
            Iterator var8 = var6.iterator();

            while(var8.hasNext()) {
               ((FacebookWebView.NativeCallHandler)var8.next()).handle(var1, var2, var3);
            }

            var7 = true;
         }

         return var7;
      }
   }

   protected static class NativeCallException extends Exception {

      private static final long serialVersionUID = -712604300151991483L;
      private final String mMessage;
      private final Exception mRootException;


      public NativeCallException(Exception var1) {
         this.mRootException = var1;
         this.mMessage = null;
      }

      public NativeCallException(String var1) {
         this.mRootException = null;
         this.mMessage = var1;
      }

      public String toString() {
         String var1;
         if(this.mRootException != null) {
            var1 = this.mRootException.toString();
         } else if(this.mMessage != null) {
            var1 = this.mMessage;
         } else {
            var1 = "Unknown cause";
         }

         return var1;
      }
   }

   static class 1 implements StringUtils.StringProcessor {

      1() {}

      public String formatString(Object var1) {
         String var2;
         if(!(var1 instanceof JSONArray) && !(var1 instanceof JSONObject) && !(var1 instanceof JSONStringer)) {
            var2 = JSONObject.quote(var1.toString());
         } else {
            var2 = var1.toString();
         }

         return var2;
      }
   }

   protected class NativeCallReturnHandler implements FacebookWebView.NativeCallHandler {

      protected NativeCallReturnHandler() {}

      public void handle(Context param1, FacebookWebView param2, FacebookRpcCall param3) {
         // $FF: Couldn't be decompiled
      }
   }
}
