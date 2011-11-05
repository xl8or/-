package com.android.email;

import android.app.Application;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;
import com.android.email.Controller;
import com.android.email.Preferences;
import com.android.email.Utility;
import com.android.email.activity.AccountShortcutPicker;
import com.android.email.activity.Debug;
import com.android.email.activity.MessageCompose;
import com.android.email.provider.EmailContent;
import com.android.email.service.MailPushReceiver;
import com.android.email.service.MailService;
import com.sonyericsson.email.utils.customization.AccountData;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class Email extends Application {

   public static final String[] ACCEPTABLE_ATTACHMENT_DOWNLOAD_TYPES;
   public static final String[] ACCEPTABLE_ATTACHMENT_SEND_INTENT_TYPES;
   public static final String[] ACCEPTABLE_ATTACHMENT_SEND_UI_TYPES;
   public static final String[] ACCEPTABLE_ATTACHMENT_VIEW_TYPES;
   private static final int[] ACCOUNT_COLOR_CHIP_RES_IDS;
   private static final int[] ACCOUNT_COLOR_CHIP_RGBS;
   public static boolean DEBUG = 0;
   public static boolean DEBUG_SENSITIVE = 0;
   public static final String EXCHANGE_ACCOUNT_MANAGER_TYPE = "com.android.exchange";
   public static final boolean LOGD = false;
   public static final String LOG_TAG = "Email";
   public static final int MAX_ATTACHMENT_UPLOAD_SIZE = 5242880;
   public static final String[] UNACCEPTABLE_ATTACHMENT_DOWNLOAD_TYPES;
   public static final String[] UNACCEPTABLE_ATTACHMENT_VIEW_TYPES;
   private static final long UPDATE_INTERVAL = 300000L;
   public static final int VISIBLE_LIMIT_DEFAULT = 25;
   public static final int VISIBLE_LIMIT_INCREMENT = 25;
   private static boolean sAccountsChangedNotification;
   private static HashMap<Long, Long> sMailboxSyncTimes;
   private static File sTempDirectory;
   private Locale mCurrentLocale;


   static {
      String[] var0 = new String[]{"*/*"};
      ACCEPTABLE_ATTACHMENT_SEND_INTENT_TYPES = var0;
      String[] var1 = new String[]{"image/*", "video/*", "audio/*"};
      ACCEPTABLE_ATTACHMENT_SEND_UI_TYPES = var1;
      String[] var2 = new String[]{"*/*"};
      ACCEPTABLE_ATTACHMENT_VIEW_TYPES = var2;
      String[] var3 = new String[]{"application/vnd.android.package-archive", "application/x-zip-compressed", "application/rar", "application/zip"};
      UNACCEPTABLE_ATTACHMENT_VIEW_TYPES = var3;
      String[] var4 = new String[]{"*/*"};
      ACCEPTABLE_ATTACHMENT_DOWNLOAD_TYPES = var4;
      UNACCEPTABLE_ATTACHMENT_DOWNLOAD_TYPES = new String[0];
      sMailboxSyncTimes = new HashMap();
      sAccountsChangedNotification = (boolean)0;
      ACCOUNT_COLOR_CHIP_RES_IDS = new int[]{2130837504, 2130837505, 2130837506, 2130837507, 2130837508, 2130837509, 2130837510, 2130837511, 2130837512};
      ACCOUNT_COLOR_CHIP_RGBS = new int[]{7450279, 6428953, 1590831, 12553810, 8057, 11055042, 7038148, 7570265, 10309796};
   }

   public Email() {}

   public static int getAccountColor(long var0) {
      int[] var2 = ACCOUNT_COLOR_CHIP_RGBS;
      int var3 = getColorIndexFromAccountId(var0);
      return var2[var3];
   }

   public static int getAccountColorResourceId(long var0) {
      int[] var2 = ACCOUNT_COLOR_CHIP_RES_IDS;
      int var3 = getColorIndexFromAccountId(var0);
      return var2[var3];
   }

   static int getColorIndexFromAccountId(long var0) {
      int var2 = (int)(var0 - 1L);
      int var3 = ACCOUNT_COLOR_CHIP_RES_IDS.length;
      return Math.abs(var2 % var3);
   }

   public static boolean getNotifyUiAccountsChanged() {
      synchronized(Email.class){}

      boolean var0;
      try {
         var0 = sAccountsChangedNotification;
      } finally {
         ;
      }

      return var0;
   }

   public static File getTempDirectory() {
      if(sTempDirectory == null) {
         throw new RuntimeException("TempDirectory not set.  If in a unit test, call Email.setTempDirectory(context) in setUp().");
      } else {
         return sTempDirectory;
      }
   }

   public static void log(String var0) {
      int var1 = Log.d("Email", var0);
   }

   public static boolean mailboxRequiresRefresh(long var0) {
      HashMap var2 = sMailboxSyncTimes;
      synchronized(var2) {
         HashMap var3 = sMailboxSyncTimes;
         Long var4 = Long.valueOf(var0);
         boolean var11;
         if(var3.containsKey(var4)) {
            long var5 = System.currentTimeMillis();
            HashMap var7 = sMailboxSyncTimes;
            Long var8 = Long.valueOf(var0);
            long var9 = ((Long)var7.get(var8)).longValue();
            if(var5 - var9 <= 300000L) {
               var11 = false;
               return var11;
            }
         }

         var11 = true;
         return var11;
      }
   }

   public static void masterReset(Context param0) {
      // $FF: Couldn't be decompiled
   }

   static void masterResetAccountUpdate(Context var0, EmailContent.Account var1, AccountData var2) {
      int var3 = var1.getFlags() & -68;
      if(var2.hasEmailNotifications()) {
         var3 |= 1;
      }

      if(var2.hasNotificationVibrate()) {
         var3 |= 2;
      }

      ContentValues var4 = new ContentValues();
      Integer var5 = Integer.valueOf(var3);
      var4.put("flags", var5);
      String var6 = var2.getNotificationTone();
      var4.put("ringtoneUri", var6);
      Integer var7 = Integer.valueOf(var2.getCheckIntervalSeconds() / 60);
      var4.put("syncInterval", var7);
      var1.update(var0, var4);
   }

   public static void setNotifyUiAccountsChanged(boolean var0) {
      synchronized(Email.class){}

      try {
         sAccountsChangedNotification = var0;
      } finally {
         ;
      }

   }

   public static void setServicesEnabled(Context var0, boolean var1) {
      PackageManager var2 = var0.getPackageManager();
      if(!var1) {
         ComponentName var3 = new ComponentName(var0, MailService.class);
         if(var2.getComponentEnabledSetting(var3) == 1) {
            MailService.actionReschedule(var0);
         }
      }

      ComponentName var4 = new ComponentName(var0, MessageCompose.class);
      byte var5;
      if(var1) {
         var5 = 1;
      } else {
         var5 = 2;
      }

      var2.setComponentEnabledSetting(var4, var5, 1);
      ComponentName var6 = new ComponentName(var0, AccountShortcutPicker.class);
      byte var7;
      if(var1) {
         var7 = 1;
      } else {
         var7 = 2;
      }

      var2.setComponentEnabledSetting(var6, var7, 1);
      ComponentName var8 = new ComponentName(var0, MailService.class);
      byte var9;
      if(var1) {
         var9 = 1;
      } else {
         var9 = 2;
      }

      var2.setComponentEnabledSetting(var8, var9, 1);
      ComponentName var10 = new ComponentName(var0, MailPushReceiver.class);
      byte var11;
      if(var1) {
         var11 = 1;
      } else {
         var11 = 2;
      }

      var2.setComponentEnabledSetting(var10, var11, 1);
      if(var1) {
         ComponentName var12 = new ComponentName(var0, MailService.class);
         if(var2.getComponentEnabledSetting(var12) == 1) {
            MailService.actionReschedule(var0);
         }
      }
   }

   public static boolean setServicesEnabled(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static void setTempDirectory(Context var0) {
      sTempDirectory = var0.getCacheDir();
   }

   public static void updateMailboxRefreshTime(long var0) {
      HashMap var2 = sMailboxSyncTimes;
      synchronized(var2) {
         HashMap var3 = sMailboxSyncTimes;
         Long var4 = Long.valueOf(var0);
         Long var5 = Long.valueOf(System.currentTimeMillis());
         var3.put(var4, var5);
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      Locale var2 = var1.locale;
      Locale var3 = this.mCurrentLocale;
      if(!var2.equals(var3)) {
         Utility.FolderProperties.getInstance(this).refreshResource(this);
         Locale var4 = var1.locale;
         this.mCurrentLocale = var4;
      }
   }

   public void onCreate() {
      super.onCreate();
      Preferences var1 = Preferences.getPreferences(this);
      DEBUG = var1.getEnableDebugLogging();
      DEBUG_SENSITIVE = var1.getEnableSensitiveLogging();
      setTempDirectory(this);
      Controller.getInstance(this).resetVisibleLimits();
      Debug.updateLoggingFlags(this);
      Locale var2 = this.getResources().getConfiguration().locale;
      this.mCurrentLocale = var2;
   }
}
