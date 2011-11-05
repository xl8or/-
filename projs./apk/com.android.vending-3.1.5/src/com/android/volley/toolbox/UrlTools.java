package com.android.volley.toolbox;

import android.content.Context;
import com.google.android.common.http.UrlRules;

public class UrlTools {

   public UrlTools() {}

   public static String rewrite(Context var0, String var1) {
      return UrlRules.getRules(var0.getContentResolver()).matchRule(var1).apply(var1);
   }
}
