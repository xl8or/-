package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.preference.PreferenceManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Utils;
import java.util.HashSet;
import java.util.Set;

public class FacebookAuthentication {

   public static final String TAG = Utils.getClassName(FacebookAuthentication.class);
   protected static boolean mAuthenticated = 1;
   protected static boolean mAuthenticationInProgress = 0;
   protected static Set<FacebookAuthentication.Callback> mFWAuthenticationListeners = new HashSet();


   public FacebookAuthentication() {}

   public static boolean isAuthenticated() {
      return mAuthenticated;
   }

   static boolean matchMSitePathIgnoreSSL(String var0, String var1) {
      Uri var2 = Uri.parse(var0);
      String var3 = var2.getScheme();
      boolean var4;
      if(!StringUtils.saneStringEquals(var3, "http") && !StringUtils.saneStringEquals(var3, "https")) {
         var4 = false;
      } else {
         String var5 = var2.getHost();
         if(var5.startsWith("m.") && var5.endsWith(".facebook.com")) {
            var4 = StringUtils.saneStringEquals(var2.getPath(), var1);
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   static boolean matchUrlLiberally(String var0, String var1) {
      byte var2;
      if(var0.equals(var1)) {
         var2 = 1;
      } else {
         Uri var3 = Uri.parse(var0);
         Uri var4 = Uri.parse(var1);
         String var5 = var3.getScheme();
         String var6 = var4.getScheme();
         boolean var7;
         if(!StringUtils.saneStringEquals(var5, "http") && !StringUtils.saneStringEquals(var5, "https")) {
            var7 = false;
         } else {
            var7 = true;
         }

         boolean var8;
         if(!StringUtils.saneStringEquals(var6, "http") && !StringUtils.saneStringEquals(var6, "https")) {
            var8 = false;
         } else {
            var8 = true;
         }

         if((!var7 || !var8) && !StringUtils.saneStringEquals(var5, var6)) {
            var2 = 0;
         } else {
            Uri var9 = rebuildUri(var3);
            Uri var10 = rebuildUri(var4);
            var2 = var9.equals(var10);
         }
      }

      return (boolean)var2;
   }

   protected static Uri rebuildUri(Uri var0) {
      return var0.buildUpon().scheme("http").query((String)null).fragment((String)null).build();
   }

   public static void startAuthentication(Context param0, FacebookAuthentication.Callback param1) {
      // $FF: Couldn't be decompiled
   }

   protected static class FacewebAuthenticationWebViewClient extends WebViewClient {

      protected final Context mContext;
      protected final String mExpectedUrlPath;


      public FacewebAuthenticationWebViewClient(Context var1, String var2) {
         this.mContext = var1;
         String var3 = Uri.parse(var2).getPath();
         this.mExpectedUrlPath = var3;
      }

      public void onPageFinished(WebView param1, String param2) {
         // $FF: Couldn't be decompiled
      }

      public void onReceivedError(WebView param1, int param2, String param3, String param4) {
         // $FF: Couldn't be decompiled
      }
   }

   public interface Callback {

      void authenticationFailed();

      void authenticationNetworkFailed();

      void authenticationSuccessful();
   }

   public static class AuthWebViewClient extends WebViewClient {

      protected FacebookAuthentication.Callback mCallback;
      protected Context mContext;


      public AuthWebViewClient(Context var1, FacebookAuthentication.Callback var2) {
         this.mContext = var1;
         this.mCallback = var2;
      }

      public void onReceivedSslError(WebView var1, SslErrorHandler var2, SslError var3) {
         if(!PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean("check_certs", (boolean)1)) {
            var2.proceed();
         } else if(!Constants.isBetaBuild() && !FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone()) {
            String var4 = FacebookAuthentication.TAG;
            String var5 = this.mContext.getString(2131361876);
            Log.d(var4, var5);
         } else {
            Toast.makeText(this.mContext, 2131361877, 1).show();
         }
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         Uri var3 = Uri.parse(var2);
         String var4 = var3.getScheme();
         String var5 = var3.getHost();
         String var6 = var3.getPath();
         boolean var9;
         if((StringUtils.saneStringEquals(var4, "http") || StringUtils.saneStringEquals(var4, "https")) && var5.endsWith(".facebook.com") && var6.equals("/login.php")) {
            Context var7 = this.mContext;
            FacebookAuthentication.Callback var8 = this.mCallback;
            FacebookAuthentication.startAuthentication(var7, var8);
            var9 = true;
         } else {
            var9 = false;
         }

         return var9;
      }
   }
}
