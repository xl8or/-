package com.facebook.katana.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.UserValuesProvider;

public class UserValuesManager {

   private static final String ACTIVE_SESSION_INFO_KEY = "active_session_info";
   private static final String CONTACTS_SYNC_DISPLAYED_KEY = "sync";
   private static final String CURRENT_ACCOUNT_KEY = "current_account";
   private static final String FALSE_VALUE = "false";
   public static final String GATEKEEPER_PREFIX = "gk:";
   private static final String[] ID_PROJECTION;
   private static final String LAST_CONTACTS_SYNC_ID = "last_contacts_sync";
   private static final String LAST_SEEN_ID = "last_seen_id";
   public static final String PLACES_HAS_CREATED_PLACE_BEFORE = "places:has_created_place_before";
   public static final String PLACES_LAST_CHECKIN_KEY = "places:last_checkin";
   public static final String PLACES_LAST_CHECKIN_PAGEID_KEY = "places:last_checkin_pageid";
   public static final String PLACES_LAST_CHECKIN_TIME_KEY = "places:last_checkin_time";
   private static final String REGISTER_RINGTONEKEY = "ringtone";
   public static final String USER_SERVER_SETTING_PREFIX = "uss:";
   private static final String[] VALUE_PROJECTION;


   static {
      String[] var0 = new String[]{"value"};
      VALUE_PROJECTION = var0;
      String[] var1 = new String[]{"_id"};
      ID_PROJECTION = var1;
   }

   private UserValuesManager() {}

   public static void clearUserValues(Context var0) {
      delete(var0, (String)null, (String[])null);
      setRegisterRingtone(var0, (boolean)1);
   }

   public static void delete(Context var0, String var1, String[] var2) {
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = UserValuesProvider.CONTENT_URI;
      var3.delete(var4, var1, var2);
   }

   public static boolean getBooleanValue(Context var0, String var1) {
      return Boolean.parseBoolean(getValue(var0, var1, "false"));
   }

   public static boolean getContactsSyncSetupDisplayed(Context var0) {
      return getBooleanValue(var0, "sync");
   }

   public static String getCurrentAccount(Context var0) {
      return getValue(var0, "current_account", (String)null);
   }

   public static long getLastContactsSync(Context var0) {
      return Long.parseLong(getValue(var0, "last_contacts_sync", "0"));
   }

   public static long getLastSeenId(Context var0, String var1) {
      String var2 = "last_seen_id_" + var1;
      return Long.parseLong(getValue(var0, var2, "0"));
   }

   public static long getLongValue(Context var0, String var1, long var2) {
      String var4 = getValue(var0, var1, (String)null);
      long var5;
      if(var4 != null) {
         var5 = Long.parseLong(var4);
      } else {
         var5 = var2;
      }

      return var5;
   }

   public static boolean getRegisterRingtone(Context var0) {
      return Boolean.parseBoolean(getValue(var0, "ringtone", "false"));
   }

   public static Boolean getTristateValue(Context var0, String var1) {
      String var2 = getValue(var0, var1, "");
      Boolean var3;
      if(var2.length() == 0) {
         var3 = null;
      } else {
         var3 = Boolean.valueOf(var2);
      }

      return var3;
   }

   public static String getValue(Context var0, String var1, String var2) {
      Uri var3 = Uri.withAppendedPath(UserValuesProvider.NAME_CONTENT_URI, var1);
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

   public static String loadActiveSessionInfo(Context var0) {
      return getValue(var0, "active_session_info", (String)null);
   }

   public static void saveActiveSessionInfo(Context var0, String var1) {
      setValue(var0, "active_session_info", var1);
   }

   public static void setContactsSyncSetupDisplayed(Context var0, boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      setValue(var0, "sync", var2);
   }

   public static void setCurrentAccount(Context var0, String var1) {
      setValue(var0, "current_account", var1);
   }

   public static void setLastContactsSync(Context var0) {
      Long var1 = Long.valueOf(System.currentTimeMillis());
      setValue(var0, "last_contacts_sync", var1);
   }

   public static void setLastSeenId(Context var0, String var1, Long var2) {
      String var3 = "last_seen_id_" + var1;
      setValue(var0, var3, var2);
   }

   public static void setRegisterRingtone(Context var0, boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      setValue(var0, "ringtone", var2);
   }

   public static void setValue(Context var0, String var1, Object var2) {
      Uri var3 = Uri.withAppendedPath(UserValuesProvider.NAME_CONTENT_URI, var1);
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
            var10.put("name", var1);
            Uri var14 = UserValuesProvider.CONTENT_URI;
            var4.insert(var14, var10);
         }
      }
   }
}
