package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Map;

public class FqlQuery extends ApiMethod {

   public FqlQuery(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiReadUrl(var1);
      super(var1, var2, "GET", "fql.query", var6, var5);
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      this.mParams.put("query", var4);
      if(var3 != null) {
         this.mParams.put("session_key", var3);
      }
   }

   public String generateLogParams() {
      StringBuilder var1 = new StringBuilder(500);
      StringBuilder var2 = var1.append(",\"method\":\"");
      String var3 = this.mFacebookMethod;
      var1.append(var3);
      StringBuilder var5 = var1.append("\",\"args\":\"");
      String var6 = this.getSanitizedQueryString();
      var1.append(var6);
      return var1.toString();
   }

   public String getQueryString() {
      return (String)this.mParams.get("query");
   }

   public String getSanitizedQueryString() {
      return this.getQueryString().replaceAll("([=\'(, ])[0-9]+_?[0-9]*", "$1NULL");
   }
}
