package com.google.android.gsf;

import android.webkit.CookieManager;

public class SAMLUtils {

   private static final String DEFAULT_HOSTED_BASE_PATH = "https://www.google.com";
   private static final String HOSTED_PREFIX = "/a/";
   private static final String TEST_GAIA_HOSTED_BASE_PATH = "http://dasher-qa.corp.google.com";


   private SAMLUtils() {}

   private static String extractCookie(String var0, String var1) {
      String var2;
      if(var0 == null) {
         var2 = "";
      } else {
         String[] var3 = var0.split("; ");
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if(var5 >= var4) {
               var2 = "";
               break;
            }

            String[] var6 = var3[var5].split("=");
            if(var6.length == 2 && var6[0].equalsIgnoreCase(var1)) {
               var2 = var6[1];
               break;
            }

            ++var5;
         }
      }

      return var2;
   }

   public static String extractHID(CookieManager var0, String var1) {
      String var2 = makeHIDCookieExtractionPath((boolean)0, var1);
      String var3 = extractCookie(var0.getCookie(var2), "HID");
      if(var3.length() == 0) {
         String var4 = makeLSIDCookieExtractionPath((boolean)0, var1);
         var3 = extractCookie(var0.getCookie(var4), "LSID");
      }

      return var3;
   }

   private static final String getHostedBaseUrl(boolean var0) {
      StringBuilder var1 = new StringBuilder();
      String var2;
      if(var0) {
         var2 = "http://dasher-qa.corp.google.com";
      } else {
         var2 = "https://www.google.com";
      }

      return var1.append(var2).append("/a/").toString();
   }

   private static String makeHIDCookieExtractionPath(boolean var0, String var1) {
      return makeHostedGaiaBasePath(var0, var1);
   }

   private static String makeHostedGaiaBasePath(boolean var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = getHostedBaseUrl(var0);
      return var2.append(var3).append(var1).append("/").toString();
   }

   private static String makeLSIDCookieExtractionPath(boolean var0, String var1) {
      String var2;
      if(var0) {
         var2 = "http://dasher-qa.corp.google.com";
      } else {
         var2 = "https://www.google.com";
      }

      return var2 + "/accounts/";
   }

   public static String makeWebLoginStartUrl(boolean var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = makeHostedGaiaBasePath(var0, var1);
      return var2.append(var3).append("ServiceLogin").toString();
   }
}
