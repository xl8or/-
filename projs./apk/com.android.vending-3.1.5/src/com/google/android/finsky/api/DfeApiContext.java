package com.google.android.finsky.api;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.android.volley.AuthFailureException;
import com.android.volley.Cache;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.UrlTools;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Utils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class DfeApiContext {

   public static final int API_VERSION_FINSKY = 2;
   public static final int API_VERSION_PHONESKY = 3;
   private static int sCachedSmallestScreenWidthDp = -1;
   private final AndroidAuthenticator mAuthenticator;
   private final Cache mCache;
   private final Context mContext;
   private boolean mHasPerformedInitialTokenInvalidation;
   private final Map<String, String> mHeaders;
   private String mLastAuthToken;


   public DfeApiContext(Context var1, AndroidAuthenticator var2, Cache var3, String var4, int var5, int var6, Locale var7, String var8, String var9, String var10) {
      HashMap var11 = Maps.newHashMap();
      this.mHeaders = var11;
      this.mContext = var1;
      this.mAuthenticator = var2;
      this.mCache = var3;
      Map var12 = this.mHeaders;
      String var13 = Long.toHexString(((Long)G.androidId.get()).longValue());
      var12.put("X-DFE-Device-Id", var13);
      Map var15 = this.mHeaders;
      StringBuilder var16 = new StringBuilder();
      String var17 = var7.getLanguage();
      StringBuilder var18 = var16.append(var17).append("-");
      String var19 = var7.getCountry();
      String var20 = var18.append(var19).toString();
      var15.put("Accept-Language", var20);
      if(!TextUtils.isEmpty(var8)) {
         this.mHeaders.put("X-DFE-MCCMNC", var8);
      }

      if(!TextUtils.isEmpty(var9)) {
         Map var23 = this.mHeaders;
         var23.put("X-DFE-Client-Id", var9);
      }

      if(!TextUtils.isEmpty(var9)) {
         Map var26 = this.mHeaders;
         var26.put("X-DFE-Logging-Id", var10);
      }

      Map var29 = this.mHeaders;
      String var30 = this.makeUserAgentString(var4, var5, var6);
      var29.put("User-Agent", var30);
      Map var32 = this.mHeaders;
      String var33 = this.getSmallestScreenWidthDp(var1);
      var32.put("X-DFE-SmallestScreenWidthDp", var33);
      if(this.mContext != null) {
         int var35 = ContentLevel.importFromSettings(this.mContext).getDfeValue();
         Map var36 = this.mHeaders;
         String var37 = String.valueOf(var35);
         var36.put("X-DFE-Filter-Level", var37);
         this.checkUrlRules();
      }
   }

   private void checkUrlRules() {
      String var1 = DfeApi.BASE_URI.toString();
      String var2 = UrlTools.rewrite(this.mContext, var1);
      if(var2 == null) {
         String var3 = "BASE_URI blocked by UrlRules: " + var1;
         throw new RuntimeException(var3);
      } else {
         Utils.checkUrlIsSecure(var2);
      }
   }

   public static DfeApiContext create(Account param0) {
      // $FF: Couldn't be decompiled
   }

   private String getSmallestScreenWidthDp(Context var1) {
      if(sCachedSmallestScreenWidthDp == -1) {
         Class var2 = var1.getResources().getConfiguration().getClass();

         try {
            Field var3 = var2.getDeclaredField("smallestScreenWidthDp");
            sCachedSmallestScreenWidthDp = var1.getResources().getConfiguration().smallestScreenWidthDp;
         } catch (Exception var13) {
            Object[] var12 = new Object[0];
            FinskyLog.d("smallestScreenWidthDp does not exist, using pre-ics hack.", var12);
         }

         if(sCachedSmallestScreenWidthDp == -1) {
            DisplayMetrics var4 = var1.getResources().getDisplayMetrics();
            float var5 = (float)var4.widthPixels;
            float var6 = var4.density;
            int var7 = (int)(var5 / var6);
            float var8 = (float)var4.heightPixels;
            float var9 = var4.density;
            int var10 = (int)(var8 / var9);
            sCachedSmallestScreenWidthDp = Math.min(var7, var10);
         }
      }

      return String.valueOf(sCachedSmallestScreenWidthDp);
   }

   private String makeUserAgentString(String var1, int var2, int var3) {
      Locale var4 = Locale.US;
      Object[] var5 = new Object[]{var1, null, null};
      Integer var6 = Integer.valueOf(var3);
      var5[1] = var6;
      Integer var7 = Integer.valueOf(var2);
      var5[2] = var7;
      return String.format(var4, "Android-Finsky/%s (api=%d,versionCode=%d)", var5);
   }

   public Account getAccount() {
      return this.mAuthenticator.getAccount();
   }

   public String getAccountName() {
      Account var1 = this.getAccount();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.name;
      }

      return var2;
   }

   public Cache getCache() {
      return this.mCache;
   }

   public Map<String, String> getHeaders() throws AuthFailureException {
      synchronized(this){}

      HashMap var2;
      try {
         if(!this.mHasPerformedInitialTokenInvalidation) {
            this.invalidateAuthToken();
            this.mHasPerformedInitialTokenInvalidation = (boolean)1;
         }

         String var1 = this.mAuthenticator.getAuthToken();
         this.mLastAuthToken = var1;
         var2 = Maps.newHashMap();
         Map var3 = this.mHeaders;
         var2.putAll(var3);
         StringBuilder var4 = (new StringBuilder()).append("GoogleLogin auth=");
         String var5 = this.mLastAuthToken;
         String var6 = var4.append(var5).toString();
         var2.put("Authorization", var6);
      } finally {
         ;
      }

      return var2;
   }

   public void invalidateAuthToken() {
      if(this.mLastAuthToken != null) {
         AndroidAuthenticator var1 = this.mAuthenticator;
         String var2 = this.mLastAuthToken;
         var1.invalidateAuthToken(var2);
         this.mLastAuthToken = null;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("[DfeApiContext headers={");
      boolean var3 = true;
      Iterator var4 = this.mHeaders.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(var3) {
            var3 = false;
         } else {
            StringBuilder var9 = var1.append(", ");
         }

         StringBuilder var6 = var1.append(var5).append(": ");
         String var7 = (String)this.mHeaders.get(var5);
         var6.append(var7);
      }

      StringBuilder var10 = var1.append("}]");
      return var1.toString();
   }
}
