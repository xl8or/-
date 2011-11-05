package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gsf.GoogleSettingsContract;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class UseLocationForServices {

   public static final String ACTION_SET_USE_LOCATION_FOR_SERVICES = "com.google.android.gsf.action.SET_USE_LOCATION_FOR_SERVICES";
   public static final String EXTRA_DISABLE_USE_LOCATION_FOR_SERVICES = "disable";
   private static final String[] GOOGLE_GEOLOCATION_ORIGINS;
   private static final String TAG = "UseLocationForServices";
   public static final int USE_LOCATION_FOR_SERVICES_NOT_SET = 2;
   public static final int USE_LOCATION_FOR_SERVICES_OFF = 0;
   public static final int USE_LOCATION_FOR_SERVICES_ON = 1;


   static {
      String[] var0 = new String[]{"http://www.google.com", "http://www.google.co.uk"};
      GOOGLE_GEOLOCATION_ORIGINS = var0;
   }

   public UseLocationForServices() {}

   private static String addGoogleOrigins(String var0) {
      HashSet var1 = parseAllowGeolocationOrigins(var0);
      String[] var2 = GOOGLE_GEOLOCATION_ORIGINS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         var1.add(var5);
      }

      return formatAllowGeolocationOrigins(var1);
   }

   private static String formatAllowGeolocationOrigins(Collection<String> var0) {
      StringBuilder var1 = new StringBuilder();

      String var3;
      for(Iterator var2 = var0.iterator(); var2.hasNext(); var1.append(var3)) {
         var3 = (String)var2.next();
         if(var1.length() > 0) {
            StringBuilder var4 = var1.append(' ');
         }
      }

      return var1.toString();
   }

   public static int getUseLocationForServices(Context var0) {
      return GoogleSettingsContract.Partner.getInt(var0.getContentResolver(), "use_location_for_services", 2);
   }

   private static HashSet<String> parseAllowGeolocationOrigins(String var0) {
      HashSet var1 = new HashSet();
      if(!TextUtils.isEmpty(var0)) {
         String[] var2 = var0.split("\\s+");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if(!TextUtils.isEmpty(var5)) {
               var1.add(var5);
            }
         }
      }

      return var1;
   }

   public static void registerUseLocationForServicesObserver(Context var0, ContentObserver var1) {
      Uri var2 = GoogleSettingsContract.Partner.getUriFor("use_location_for_services");
      var0.getContentResolver().registerContentObserver(var2, (boolean)0, var1);
   }

   private static String removeGoogleOrigins(String var0) {
      HashSet var1 = parseAllowGeolocationOrigins(var0);
      String[] var2 = GOOGLE_GEOLOCATION_ORIGINS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         var1.remove(var5);
      }

      return formatAllowGeolocationOrigins(var1);
   }

   private static void setGoogleBrowserGeolocation(Context param0, boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean setUseLocationForServices(Context var0, boolean var1) {
      setGoogleBrowserGeolocation(var0, var1);
      ContentResolver var2 = var0.getContentResolver();
      byte var3;
      if(var1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return GoogleSettingsContract.Partner.putInt(var2, "use_location_for_services", var3);
   }
}
