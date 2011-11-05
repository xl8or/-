package com.facebook.katana.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class FacebookWebViewActivity extends Activity {

   public static String URL = "url";
   private String mUrl;
   private WebView mWebView;


   public FacebookWebViewActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903183);
      Intent var2 = this.getIntent();
      String var3 = URL;
      String var4 = var2.getStringExtra(var3);
      this.mUrl = var4;
      WebView var5 = (WebView)this.findViewById(2131624288);
      this.mWebView = var5;
      this.mWebView.getSettings().setJavaScriptEnabled((boolean)1);
      WebView var6 = this.mWebView;
      String var7 = this.mUrl;
      var6.loadUrl(var7);
   }
}
