package com.google.android.finsky.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.common.http.UrlRules;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Maps;
import java.util.Map;

public class FinskyDebug {

   public static final Map<String, FinskyDebug.Environment> ENVIRONMENTS = Maps.newLinkedHashMap();
   private static final String ORIGINAL_DFE_URL = DfeApi.BASE_URI.toString();
   private static final String ORIGINAL_VENDING_API_URL = "https://android.clients.google.com/vending/api/ApiRequest".replace("api/ApiRequest", "");
   private static final String OVERRIDE_ACTION = "com.google.gservices.intent.action.GSERVICES_OVERRIDE";


   static {
      Map var0 = ENVIRONMENTS;
      Object var1 = null;
      Object var2 = null;
      Object var3 = null;
      Object var4 = null;
      FinskyDebug.Environment var5 = new FinskyDebug.Environment((String)null, (String)var1, (String)var2, (String)var3, (FinskyDebug.1)var4);
      var0.put("Default", var5);
   }

   public FinskyDebug() {}

   public static boolean isEnvironmentSelected(Context var0, FinskyDebug.Environment var1) {
      String var2 = var1.dfeBaseUrl;
      UrlRules var3 = UrlRules.getRules(var0.getContentResolver());
      String var4 = ORIGINAL_DFE_URL;
      UrlRules.Rule var5 = var3.matchRule(var4);
      UrlRules.Rule var6 = UrlRules.Rule.DEFAULT;
      String var7;
      if(var5 == var6) {
         var7 = ORIGINAL_DFE_URL;
      } else {
         String var8 = ORIGINAL_DFE_URL;
         var7 = var5.apply(var8);
      }

      return TextUtils.equals(var2, var7);
   }

   public static void selectEnvironment(Context var0, FinskyDebug.Environment var1) {
      Intent var2 = new Intent("com.google.gservices.intent.action.GSERVICES_OVERRIDE");
      String var3 = null;
      String var4 = null;
      String var5 = null;
      String var6 = null;
      if(var1 != null) {
         if(var1.dfeBaseUrl == null) {
            var3 = null;
         } else {
            StringBuilder var14 = new StringBuilder();
            String var15 = ORIGINAL_DFE_URL;
            StringBuilder var16 = var14.append(var15).append(" rewrite ");
            String var17 = var1.dfeBaseUrl;
            var3 = var16.append(var17).toString();
         }

         if(var1.vendingBaseUrl == null) {
            var4 = null;
         } else {
            StringBuilder var18 = new StringBuilder();
            String var19 = ORIGINAL_VENDING_API_URL;
            StringBuilder var20 = var18.append(var19).append(" rewrite ");
            String var21 = var1.vendingBaseUrl;
            var4 = var20.append(var21).toString();
         }

         var5 = var1.checkoutTokenType;
         var6 = var1.escrowUrl;
      }

      var2.putExtra("url:finsky_dfe_url", var3);
      var2.putExtra("url:vending_machine_ssl_url", var4);
      var2.putExtra("url:licensing_url", var4);
      String var10 = G.checkoutAuthTokenType.getKey();
      var2.putExtra(var10, var5);
      String var12 = G.checkoutEscrowUrl.getKey();
      var2.putExtra(var12, var6);
      var0.sendBroadcast(var2);
   }

   public static class Environment {

      public final String checkoutTokenType;
      public final String dfeBaseUrl;
      public final String escrowUrl;
      public final String vendingBaseUrl;


      private Environment(String var1, String var2, String var3, String var4) {
         this.dfeBaseUrl = var1;
         this.vendingBaseUrl = var2;
         this.checkoutTokenType = var3;
         this.escrowUrl = var4;
      }

      // $FF: synthetic method
      Environment(String var1, String var2, String var3, String var4, FinskyDebug.1 var5) {
         this(var1, var2, var3, var4);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
