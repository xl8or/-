package com.facebook.katana.activity.faceweb;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;

class SetNavBarTitleHandler extends FacebookWebView.NativeUICallHandler {

   protected Activity mActivity;


   public SetNavBarTitleHandler(Activity var1, Handler var2) {
      super(var2);
      this.mActivity = var1;
   }

   public void handleUI(FacebookWebView var1, FacebookRpcCall var2) {
      String var3 = var2.getParameterByName("title");
      if(var3 == null) {
         var3 = "";
      }

      ((TextView)this.mActivity.findViewById(2131624039)).setText(var3);
   }
}
