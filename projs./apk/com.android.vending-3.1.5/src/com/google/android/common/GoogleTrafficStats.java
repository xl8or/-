package com.google.android.common;


public class GoogleTrafficStats {

   public static final int ACCOUNT_BITS = 4;
   private static final int DASHER_ACCOUNT = 536870912;
   private static final int GMAIL_ACCOUNT = 268435456;
   private static final String GMAIL_DOMAIN = "gmail.com";
   private static final String GOOGLEMAIL_DOMAIN = "googlemail.com";
   private static final int GOOGLE_ACCOUNT = 805306368;
   private static final String GOOGLE_DOMAIN = "google.com";
   public static final int SERVICE_BITS = 6;
   public static final int SERVICE_CONTACTS = 4194304;


   public GoogleTrafficStats() {}

   public static int getDomainType(String var0) {
      int var1;
      if(var0.contains("google.com")) {
         var1 = 805306368;
      } else if(!var0.contains("gmail.com") && !var0.contains("googlemail.com")) {
         var1 = 536870912;
      } else {
         var1 = 268435456;
      }

      return var1;
   }
}
