package com.google.android.apps.analytics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AnalyticsParameterEncoder {

   private AnalyticsParameterEncoder() {}

   public static String encode(String var0) {
      return encode(var0, "UTF-8");
   }

   static String encode(String var0, String var1) {
      try {
         String var2 = URLEncoder.encode(var0, var1).replace("+", "%20");
         return var2;
      } catch (UnsupportedEncodingException var5) {
         String var4 = "URL encoding failed for: " + var0;
         throw new AssertionError(var4);
      }
   }
}
