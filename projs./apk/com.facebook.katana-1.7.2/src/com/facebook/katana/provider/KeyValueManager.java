package com.facebook.katana.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.KeyValueProvider;

public class KeyValueManager {

   public static final String FACEBOOK_EMPLOYEE_EVER_KEY = "seen_employee";
   public static final String FACEBOOK_EMPLOYEE_KEY = "is_employee";
   private static final String FALSE_VALUE = "false";
   private static final String[] ID_PROJECTION;
   public static final int INDEX_PROP_KEY = 0;
   public static final int INDEX_PROP_VALUE = 1;
   public static final String LAST_RUN_BUILD = "last_run_build";
   public static final String LAST_USERNAME = "last_username";
   public static final String[] PROJECTION;
   private static final String[] VALUE_PROJECTION;


   static {
      String[] var0 = new String[]{"key", "value"};
      PROJECTION = var0;
      String[] var1 = new String[]{"value"};
      VALUE_PROJECTION = var1;
      String[] var2 = new String[]{"_id"};
      ID_PROJECTION = var2;
   }

   private KeyValueManager() {}

   public static void delete(Context var0, String var1, String[] var2) {
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = KeyValueProvider.CONTENT_URI;
      var3.delete(var4, var1, var2);
   }

   public static boolean getBooleanValue(Context var0, String var1) {
      return Boolean.parseBoolean(getValue(var0, var1, "false"));
   }

   public static long getLongValue(Context var0, String var1, long var2) {
      String var4 = String.valueOf(var2);
      return Long.parseLong(getValue(var0, var1, var4));
   }

   public static String getValue(Context var0, String var1, String var2) {
      Uri var3 = Uri.withAppendedPath(KeyValueProvider.KEY_CONTENT_URI, var1);
      ContentResolver var4 = var0.getContentResolver();
      String[] var5 = VALUE_PROJECTION;
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var4.query(var3, var5, (String)null, (String[])var6, (String)var7);
      String var9 = var2;
      if(var8 != null) {
         if(var8.moveToFirst()) {
            var9 = var8.getString(0);
         }

         var8.close();
      }

      return var9;
   }

   public static void setValue(Context var0, String var1, Object var2) {
      Uri var3 = Uri.withAppendedPath(KeyValueProvider.KEY_CONTENT_URI, var1);
      ContentResolver var4 = var0.getContentResolver();
      String[] var5 = ID_PROJECTION;
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var4.query(var3, var5, (String)null, (String[])var6, (String)var7);
      if(var8 != null) {
         boolean var9 = var8.moveToFirst();
         var8.close();
         ContentValues var10 = new ContentValues();
         String var11 = "value";
         String var12;
         if(var2 == null) {
            var12 = null;
         } else {
            var12 = var2.toString();
         }

         var10.put(var11, var12);
         if(var9) {
            var4.update(var3, var10, (String)null, (String[])null);
         } else {
            var10.put("key", var1);
            Uri var14 = KeyValueProvider.CONTENT_URI;
            var4.insert(var14, var10);
         }
      }
   }
}
