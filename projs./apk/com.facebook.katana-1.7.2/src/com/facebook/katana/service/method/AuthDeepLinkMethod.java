package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Map;

public class AuthDeepLinkMethod extends ApiMethod {

   private String mSessionSecret;
   private String mUrl;


   public AuthDeepLinkMethod(Context var1, long var2, long var4, String var6, ApiMethodListener var7, String var8, String var9) {
      String var10 = Constants.URL.getMAuthUrl(var1);
      super(var1, (Intent)null, (String)null, (String)null, var10, var7);
      Object var14 = this.mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
      Map var15 = this.mParams;
      String var16 = "" + var2;
      var15.put("t", var16);
      Map var18 = this.mParams;
      String var19 = "" + var4;
      var18.put("uid", var19);
      Map var21 = this.mParams;
      String var22 = "url";
      var21.put(var22, var6);
      Map var26 = this.mParams;
      String var27 = "session_key";
      var26.put(var27, var8);
      this.mSessionSecret = var9;
   }

   public static String getDeepLinkUrl(Context var0, String var1) {
      AppSession var2 = AppSession.getActiveSession(var0, (boolean)0);
      long var3 = System.currentTimeMillis() / 1000L;
      long var5 = var2.getSessionInfo().userId;
      String var7 = var2.getSessionInfo().sessionKey;
      String var8 = var2.getSessionInfo().sessionSecret;
      AuthDeepLinkMethod var11 = new AuthDeepLinkMethod(var0, var3, var5, var1, (ApiMethodListener)null, var7, var8);
      var11.start();
      return var11.getUrl();
   }

   public String getUrl() {
      return this.mUrl;
   }

   protected String signatureKey() {
      return this.mSessionSecret;
   }

   public void start() {
      try {
         this.addSignature();
         String var1 = this.mBaseUrl;
         String var2 = this.buildGETUrl(var1);
         this.mUrl = var2;
         if(this.mListener != null) {
            this.mListener.onOperationComplete(this, 200, "OK", (Exception)null);
         }
      } catch (Exception var4) {
         if(this.mListener != null) {
            this.mListener.onOperationComplete(this, 0, (String)null, var4);
         }
      }
   }
}
