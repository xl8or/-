package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public final class GoogleSettingsContract {

   public static final String AUTHORITY = "com.google.settings";
   private static final String TAG = "GoogleSettings";


   public GoogleSettingsContract() {}

   public static final class Partner extends GoogleSettingsContract.NameValueTable {

      public static final String CLIENT_ID = "client_id";
      public static final Uri CONTENT_URI = Uri.parse("content://com.google.settings/partner");
      public static final String DATA_STORE_VERSION = "data_store_version";
      public static final String LOGGING_ID2 = "logging_id2";
      public static final String MAPS_CLIENT_ID = "maps_client_id";
      public static final String MARKET_CHECKIN = "market_checkin";
      public static final String MARKET_CLIENT_ID = "market_client_id";
      public static final String NETWORK_LOCATION_OPT_IN = "network_location_opt_in";
      public static final String RLZ = "rlz";
      public static final String SEARCH_CLIENT_ID = "search_client_id";
      public static final String SHOPPER_CLIENT_ID = "shopper_client_id";
      public static final String USE_LOCATION_FOR_SERVICES = "use_location_for_services";
      public static final String VOICESEARCH_CLIENT_ID = "voicesearch_client_id";
      public static final String WALLET_CLIENT_ID = "wallet_client_id";
      public static final String YOUTUBE_CLIENT_ID = "youtube_client_id";


      public Partner() {}

      public static int getInt(ContentResolver var0, String var1, int var2) {
         String var3 = getString(var0, var1);
         int var5;
         if(var3 != null) {
            int var4;
            try {
               var4 = Integer.parseInt(var3);
            } catch (NumberFormatException var7) {
               var5 = var2;
               return var5;
            }

            var5 = var4;
         } else {
            var5 = var2;
         }

         return var5;
      }

      public static long getLong(ContentResolver var0, String var1, long var2) {
         String var4 = getString(var0, var1);
         long var7;
         if(var4 != null) {
            long var5;
            try {
               var5 = Long.parseLong(var4);
            } catch (NumberFormatException var10) {
               var7 = var2;
               return var7;
            }

            var7 = var5;
         } else {
            var7 = var2;
         }

         return var7;
      }

      public static String getString(ContentResolver param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      public static String getString(ContentResolver var0, String var1, String var2) {
         String var3 = getString(var0, var1);
         if(var3 == null) {
            var3 = var2;
         }

         return var3;
      }

      public static Uri getUriFor(String var0) {
         return getUriFor(CONTENT_URI, var0);
      }

      public static boolean putInt(ContentResolver var0, String var1, int var2) {
         String var3 = String.valueOf(var2);
         return putString(var0, var1, var3);
      }

      public static boolean putString(ContentResolver var0, String var1, String var2) {
         Uri var3 = CONTENT_URI;
         return putString(var0, var3, var1, var2);
      }
   }

   public static class NameValueTable implements BaseColumns {

      public static final String NAME = "name";
      public static final String VALUE = "value";


      public NameValueTable() {}

      public static Uri getUriFor(Uri var0, String var1) {
         return Uri.withAppendedPath(var0, var1);
      }

      protected static boolean putString(ContentResolver var0, Uri var1, String var2, String var3) {
         boolean var4 = false;

         try {
            ContentValues var5 = new ContentValues();
            var5.put("name", var2);
            var5.put("value", var3);
            var0.insert(var1, var5);
         } catch (SQLException var13) {
            String var8 = "Can\'t set key " + var2 + " in " + var1;
            int var9 = Log.e("GoogleSettings", var8, var13);
            return var4;
         } catch (IllegalArgumentException var14) {
            String var11 = "Can\'t set key " + var2 + " in " + var1;
            int var12 = Log.e("GoogleSettings", var11, var14);
            return var4;
         }

         var4 = true;
         return var4;
      }
   }
}
