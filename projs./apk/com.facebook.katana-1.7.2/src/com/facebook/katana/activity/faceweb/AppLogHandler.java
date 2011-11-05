package com.facebook.katana.activity.faceweb;

import android.content.Context;
import com.facebook.katana.util.Log;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;

class AppLogHandler implements FacebookWebView.NativeCallHandler {

   private final String TAG;


   public AppLogHandler(String var1) {
      this.TAG = var1;
   }

   public void handle(Context var1, FacebookWebView var2, FacebookRpcCall var3) {
      String var4 = "";
      String var5 = var3.getParameterByName("msg");
      if(var5 != null && var5 instanceof String) {
         var4 = (String)var5;
      }

      Log.v(this.TAG, var4);
   }
}
