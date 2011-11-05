package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.SharedPreferencesUtils;
import java.util.Iterator;
import java.util.Map;

public class VendingPreferences {

   public static final String[] ACCOUNT_SPECIFIC_PREFIXES;
   public static PreferenceFile.SharedPreference<Boolean> BACKED_UP;
   public static PreferenceFile.SharedPreference<String> CACHED_GL_EXTENSIONS;
   public static PreferenceFile.SharedPreference<Integer> CONTENT_RATING;
   public static PreferenceFile.SharedPreference<String> DIRECT_DOWNLOAD_KEY;
   public static PreferenceFile.SharedPreference<Boolean> GL_DRIVER_CRASHED;
   public static PreferenceFile.SharedPreference<Boolean> INTEREST_BASED_ADS_ENABLED;
   public static PreferenceFile.SharedPreference<String> LAST_BUILD_FINGERPRINT;
   private static final String LAST_CHECKIN_HASH_PREFIX = "last_checkin_hash_";
   public static PreferenceFile.SharedPreference<Long> LAST_METADATA_WARNING_TIMESTAMP;
   public static PreferenceFile.SharedPreference<Long> LAST_RECONSTRUCT_TIMESTAMP;
   private static final String LAST_SYSTEMS_APPS_HASH_PREFIX = "last_systems_apps_hash_";
   public static PreferenceFile.SharedPreference<Long> LAST_UPDATE_CHECK_TIME;
   private static final String MARKET_ALARM_START_TIME_PREFIX = "last_market_alarm_start_time_";
   private static final String MARKET_ALARM_TIMEOUT_PREFIX = "last_market_alarm_timeout_";
   public static PreferenceFile.SharedPreference<Boolean> NOTIFY_UPDATES;
   public static PreferenceFile.SharedPreference<String> PENDING_RESTORE_AID;
   public static PreferenceFile.SharedPreference<String> PIN_CODE;
   public static PreferenceFile.SharedPreference<Integer> RECONCILED_VERSION;
   public static PreferenceFile.SharedPreference<Boolean> SEND_CONTENT_RATING;
   private static PreferenceFile sPrefs;


   static {
      String[] var0 = new String[]{"last_checkin_hash_", "last_systems_apps_hash_", "last_market_alarm_timeout_", "last_market_alarm_start_time_"};
      ACCOUNT_SPECIFIC_PREFIXES = var0;
      sPrefs = new PreferenceFile("vending_preferences", 0);
      PreferenceFile var1 = sPrefs;
      String var2 = (String)false;
      DIRECT_DOWNLOAD_KEY = var1.value("ddl_key", var2);
      PreferenceFile var3 = sPrefs;
      String var4 = (String)false;
      CACHED_GL_EXTENSIONS = var3.value("cached_gl_extensions", var4);
      PreferenceFile var5 = sPrefs;
      Boolean var6 = Boolean.valueOf((boolean)0);
      GL_DRIVER_CRASHED = var5.value("gl_driver_crashed", var6);
      PreferenceFile var7 = sPrefs;
      String var8 = (String)false;
      LAST_BUILD_FINGERPRINT = var7.value("last_build_fingerprint", var8);
      PreferenceFile var9 = sPrefs;
      Integer var10 = Integer.valueOf(0);
      RECONCILED_VERSION = var9.value("reconciled_version", var10);
      PreferenceFile var11 = sPrefs;
      Boolean var12 = Boolean.valueOf((boolean)0);
      BACKED_UP = var11.value("finsky_backed_up", var12);
      PreferenceFile var13 = sPrefs;
      Long var14 = Long.valueOf(0L);
      LAST_RECONSTRUCT_TIMESTAMP = var13.value("last_reconstruct_timestamp", var14);
      PreferenceFile var15 = sPrefs;
      Long var16 = Long.valueOf(0L);
      LAST_UPDATE_CHECK_TIME = var15.value("last_update_check_timestamp", var16);
      PreferenceFile var17 = sPrefs;
      String var18 = (String)false;
      PENDING_RESTORE_AID = var17.value("pending_restore_aid", var18);
      PreferenceFile var19 = sPrefs;
      String var20 = (String)false;
      PIN_CODE = var19.value("pin_code", var20);
      PreferenceFile var21 = sPrefs;
      Integer var22 = Integer.valueOf(-1);
      CONTENT_RATING = var21.value("content_rating", var22);
      PreferenceFile var23 = sPrefs;
      Boolean var24 = Boolean.valueOf((boolean)0);
      SEND_CONTENT_RATING = var23.value("send_content_rating", var24);
      PreferenceFile var25 = sPrefs;
      Boolean var26 = Boolean.valueOf((boolean)1);
      INTEREST_BASED_ADS_ENABLED = var25.value("ads_interest_based", var26);
      PreferenceFile var27 = sPrefs;
      Long var28 = Long.valueOf(0L);
      LAST_METADATA_WARNING_TIMESTAMP = var27.value("last_metadata_warning_timestamp", var28);
      PreferenceFile var29 = sPrefs;
      Boolean var30 = Boolean.valueOf((boolean)1);
      NOTIFY_UPDATES = var29.value("notify_updates", var30);
   }

   public VendingPreferences() {}

   public static void cleanupAccountSpecificPreferences(Context var0) {
      Account[] var1 = AccountHandler.getAccounts(var0);
      SharedPreferences var2 = getMainPrefs().open();
      cleanupAccountSpecificPreferences(var1, var2);
   }

   public static void cleanupAccountSpecificPreferences(Account[] var0, SharedPreferences var1) {
      Map var2 = var1.getAll();
      Editor var3 = var1.edit();
      boolean var4 = false;
      Iterator var5 = var2.keySet().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         String[] var7 = ACCOUNT_SPECIFIC_PREFIXES;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String var10 = var7[var9];
            if(var6.startsWith(var10)) {
               int var11 = var10.length();
               String var12 = var6.substring(var11);
               if(!contains(var0, var12)) {
                  var3.remove(var6);
                  var4 = true;
               }
               break;
            }
         }
      }

      if(var4) {
         boolean var14 = SharedPreferencesUtils.commit(var3);
      }
   }

   public static boolean contains(Account[] var0, String var1) {
      Account[] var2 = var0;
      int var3 = var0.length;
      int var4 = 0;

      boolean var5;
      while(true) {
         if(var4 >= var3) {
            var5 = false;
            break;
         }

         if(var2[var4].name.equals(var1)) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }

   public static PreferenceFile.SharedPreference<Integer> getLastCheckinHashProperty(String var0) {
      PreferenceFile var1 = sPrefs;
      String var2 = "last_checkin_hash_" + var0;
      Integer var3 = Integer.valueOf(0);
      return var1.value(var2, var3);
   }

   public static PreferenceFile.SharedPreference<Integer> getLastSystemAppsHashProperty(String var0) {
      PreferenceFile var1 = sPrefs;
      String var2 = "last_systems_apps_hash_" + var0;
      Integer var3 = Integer.valueOf(0);
      return var1.value(var2, var3);
   }

   public static PreferenceFile getMainPrefs() {
      return sPrefs;
   }

   public static PreferenceFile.SharedPreference<Long> getMarketAlarmStartTime(String var0) {
      PreferenceFile var1 = sPrefs;
      String var2 = "last_market_alarm_start_time_" + var0;
      Long var3 = Long.valueOf(0L);
      return var1.value(var2, var3);
   }

   public static PreferenceFile.SharedPreference<Long> getMarketAlarmTimeout(String var0) {
      PreferenceFile var1 = sPrefs;
      String var2 = "last_market_alarm_timeout_" + var0;
      Long var3 = Long.valueOf(0L);
      return var1.value(var2, var3);
   }
}
