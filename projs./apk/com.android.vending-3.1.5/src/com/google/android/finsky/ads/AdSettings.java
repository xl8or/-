package com.google.android.finsky.ads;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Base64;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerException;
import com.google.android.finsky.ads.Crypto;
import com.google.android.finsky.utils.FinskyLog;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AdSettings {

   private static final int APP_VERSION = 2;
   private static final String BASE_URL = "http://www.google.com/ads/preferences/";
   private static final String OPT_IN = "mobile_optin";
   private static final String OPT_OUT = "mobile_optout";
   private static final String RESULT_HEADER = "X-Mobile-PrefMgr";
   private static final String RESULT_OPTED_IN = "OPTED_IN";
   private static final String RESULT_OPTED_OUT = "OPTED_OUT";
   private final Context mContext;
   private final RequestQueue mRequestQueue;


   public AdSettings(Context var1, RequestQueue var2) {
      this.mContext = var1;
      this.mRequestQueue = var2;
   }

   private String getSettingsUrl(boolean var1) {
      StringBuilder var2 = (new StringBuilder()).append("http://www.google.com/ads/preferences/");
      String var3;
      if(var1) {
         var3 = "mobile_optin";
      } else {
         var3 = "mobile_optout";
      }

      String var4 = var2.append(var3).toString();
      String var5 = getSigString(this.mContext);
      String var6;
      if(var5 == null) {
         var6 = null;
      } else {
         var6 = var4 + "?sig=" + var5 + "&vv=" + 2;
      }

      return var6;
   }

   public static String getSigString(Context var0) {
      String var3;
      String var4;
      label23: {
         try {
            String var1 = Secure.getString(var0.getContentResolver(), "android_id");
            int var2 = (int)(System.currentTimeMillis() / 1000L);
            var3 = Base64.encodeToString(Crypto.encryptMobileId(2, var2, var1), 11);
            break label23;
         } catch (UnsupportedEncodingException var7) {
            ;
         } catch (NoSuchAlgorithmException var8) {
            ;
         }

         var4 = null;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public void enableInterestBasedAds(boolean var1, Response.Listener<Boolean> var2, Response.ErrorListener var3) {
      String var4 = this.getSettingsUrl(var1);
      if(var4 == null) {
         Response.ErrorCode var5 = Response.ErrorCode.SERVER;
         ServerException var6 = new ServerException();
         var3.onErrorResponse(var5, (String)null, var6);
      } else {
         AdSettings.AdPrefsRequest var7 = new AdSettings.AdPrefsRequest(var4, var2, var3);
         this.mRequestQueue.add(var7);
      }
   }

   private class AdPrefsRequest extends Request<Boolean> {

      private final Response.Listener<Boolean> mListener;


      public AdPrefsRequest(String var2, Response.Listener var3, Response.ErrorListener var4) {
         super(var2, var4);
         this.mListener = var3;
      }

      protected void deliverResponse(Boolean var1) {
         this.mListener.onResponse(var1);
      }

      protected Response<Boolean> parseNetworkResponse(NetworkResponse var1) {
         String var2 = (String)var1.headers.get("X-Mobile-PrefMgr");
         Response var3;
         if("OPTED_IN".equals(var2)) {
            var3 = Response.success(Boolean.valueOf((boolean)1), (Cache.Entry)null);
         } else if("OPTED_OUT".equals(var2)) {
            var3 = Response.success(Boolean.valueOf((boolean)0), (Cache.Entry)null);
         } else {
            Object[] var4 = new Object[]{var2, null};
            String var5 = this.getUrl();
            var4[1] = var5;
            FinskyLog.d("result header %s for %s", var4);
            Response.ErrorCode var6 = Response.ErrorCode.SERVER;
            ServerException var7 = new ServerException();
            var3 = Response.error(var6, (String)null, var7);
         }

         return var3;
      }
   }
}
