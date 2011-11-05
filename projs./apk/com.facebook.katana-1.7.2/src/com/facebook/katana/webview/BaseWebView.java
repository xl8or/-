package com.facebook.katana.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.facebook.katana.util.Log;

public class BaseWebView extends WebView {

   private static final String TAG = "BaseWebView";


   public BaseWebView(Context var1) {
      super(var1);
      this.customizeWebView(var1);
   }

   public BaseWebView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.customizeWebView(var1);
   }

   public BaseWebView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.customizeWebView(var1);
   }

   protected void customizeWebView(Context var1) {
      WebSettings var2 = this.getSettings();
      var2.setJavaScriptEnabled((boolean)1);
      var2.setCacheMode(0);
      this.setChromeClient(var1);
   }

   protected void setChromeClient(Context var1) {
      BaseWebView.BaseWebChromeClient var2 = new BaseWebView.BaseWebChromeClient();
      this.setWebChromeClient(var2);
   }

   protected static class BaseWebChromeClient extends WebChromeClient {

      protected String mCategory;


      public BaseWebChromeClient() {
         this("console");
      }

      public BaseWebChromeClient(String var1) {
         this.mCategory = var1;
      }

      public void onConsoleMessage(String var1, int var2, String var3) {
         String var4 = this.mCategory;
         String var5 = var3 + ":" + var2 + ": " + var1;
         Log.v(var4, var5);
      }
   }
}
