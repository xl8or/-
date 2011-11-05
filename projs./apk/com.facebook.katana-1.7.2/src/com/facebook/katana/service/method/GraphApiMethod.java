package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GraphApiMethod extends ApiMethod {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final String OAUTH_TOKEN_PARAM = "access_token";
   private static final String TAG = "GraphApiMethod";


   static {
      byte var0;
      if(!GraphApiMethod.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public GraphApiMethod(Context var1, String var2, String var3, String var4) {
      Object var10 = null;
      super(var1, (Intent)null, var2, var3, var4, (ApiMethodListener)var10);
   }

   public GraphApiMethod(Context var1, String var2, String var3, String var4, String var5) {
      Object var11 = null;
      super(var1, (Intent)null, var3, var4, var5, (ApiMethodListener)var11);
      this.mParams.put("access_token", var2);
   }

   public void addAuthenticationData(FacebookSessionInfo var1) {
      if(!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && var1.oAuthToken == null) {
         throw new AssertionError();
      } else {
         Map var2 = this.mParams;
         String var3 = var1.oAuthToken;
         var2.put("access_token", var3);
      }
   }

   protected StringBuilder buildQueryString() {
      return URLQueryBuilder.buildQueryString(this.mParams);
   }

   protected String buildUrl(boolean var1, boolean var2) throws UnsupportedEncodingException {
      StringBuilder var4;
      if(var1) {
         String var3 = this.mBaseUrl;
         var4 = new StringBuilder(var3);
      } else {
         var4 = new StringBuilder("/");
      }

      String var5 = this.mFacebookMethod;
      var4.append(var5);
      String var14;
      if(var2) {
         StringBuilder var7 = this.buildQueryString();
         if(var7.length() == 0) {
            String var8 = this.getClass().getName();
            String var13;
            if("GraphApiMethod" != var8) {
               StringBuffer var9 = new StringBuffer("GraphApiMethod");
               StringBuffer var10 = var9.append("(");
               var9.append(var8);
               StringBuffer var12 = var9.append(")");
               var13 = var9.toString();
            } else {
               var13 = "GraphApiMethod";
            }

            Log.e(var13, "We always should have something in the query (e.g., the signature)");
            var14 = this.mBaseUrl;
            return var14;
         }

         StringBuilder var15 = var4.append("?");
         var4.append(var7);
      }

      var14 = var4.toString();
      return var14;
   }

   public void start() {
      // $FF: Couldn't be decompiled
   }
}
