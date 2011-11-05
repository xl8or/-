package org.acra;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import java.util.Map;
import org.acra.ACRAConfigurationException;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.GoogleFormSender;
import org.acra.sender.HttpPostSender;

public class ACRA {

   public static final ReportField[] DEFAULT_MAIL_REPORT_FIELDS;
   public static final ReportField[] DEFAULT_REPORT_FIELDS;
   public static final boolean DEV_LOGGING = false;
   public static final String LOG_TAG = ACRA.class.getSimpleName();
   static final int NOTIF_CRASH_ID = 666;
   public static final String NULL_VALUE = "ACRA-NULL-STRING";
   public static final String PREF_ALWAYS_ACCEPT = "acra.alwaysaccept";
   public static final String PREF_DISABLE_ACRA = "acra.disable";
   public static final String PREF_ENABLE_ACRA = "acra.enable";
   public static final String PREF_ENABLE_DEVICE_ID = "acra.deviceid.enable";
   public static final String PREF_ENABLE_SYSTEM_LOGS = "acra.syslog.enable";
   public static final String PREF_USER_EMAIL_ADDRESS = "acra.user.email";
   static final String RES_DIALOG_COMMENT_PROMPT = "RES_DIALOG_COMMENT_PROMPT";
   static final String RES_DIALOG_ICON = "RES_DIALOG_ICON";
   static final String RES_DIALOG_OK_TOAST = "RES_DIALOG_OK_TOAST";
   static final String RES_DIALOG_TEXT = "RES_DIALOG_TEXT";
   static final String RES_DIALOG_TITLE = "RES_DIALOG_TITLE";
   static final String RES_NOTIF_ICON = "RES_NOTIF_ICON";
   static final String RES_NOTIF_TEXT = "RES_NOTIF_TEXT";
   static final String RES_NOTIF_TICKER_TEXT = "RES_NOTIF_TICKER_TEXT";
   static final String RES_NOTIF_TITLE = "RES_NOTIF_TITLE";
   static final String RES_TOAST_TEXT = "RES_TOAST_TEXT";
   private static Time mAppStartDate;
   private static Application mApplication;
   private static OnSharedPreferenceChangeListener mPrefListener;
   private static ReportsCrashes mReportsCrashes;


   static {
      ReportField[] var0 = new ReportField[7];
      ReportField var1 = ReportField.USER_COMMENT;
      var0[0] = var1;
      ReportField var2 = ReportField.ANDROID_VERSION;
      var0[1] = var2;
      ReportField var3 = ReportField.APP_VERSION_NAME;
      var0[2] = var3;
      ReportField var4 = ReportField.BRAND;
      var0[3] = var4;
      ReportField var5 = ReportField.PHONE_MODEL;
      var0[4] = var5;
      ReportField var6 = ReportField.CUSTOM_DATA;
      var0[5] = var6;
      ReportField var7 = ReportField.STACK_TRACE;
      var0[6] = var7;
      DEFAULT_MAIL_REPORT_FIELDS = var0;
      ReportField[] var8 = new ReportField[34];
      ReportField var9 = ReportField.REPORT_ID;
      var8[0] = var9;
      ReportField var10 = ReportField.APP_VERSION_CODE;
      var8[1] = var10;
      ReportField var11 = ReportField.APP_VERSION_NAME;
      var8[2] = var11;
      ReportField var12 = ReportField.PACKAGE_NAME;
      var8[3] = var12;
      ReportField var13 = ReportField.FILE_PATH;
      var8[4] = var13;
      ReportField var14 = ReportField.PHONE_MODEL;
      var8[5] = var14;
      ReportField var15 = ReportField.BRAND;
      var8[6] = var15;
      ReportField var16 = ReportField.PRODUCT;
      var8[7] = var16;
      ReportField var17 = ReportField.ANDROID_VERSION;
      var8[8] = var17;
      ReportField var18 = ReportField.BUILD;
      var8[9] = var18;
      ReportField var19 = ReportField.TOTAL_MEM_SIZE;
      var8[10] = var19;
      ReportField var20 = ReportField.AVAILABLE_MEM_SIZE;
      var8[11] = var20;
      ReportField var21 = ReportField.CUSTOM_DATA;
      var8[12] = var21;
      ReportField var22 = ReportField.IS_SILENT;
      var8[13] = var22;
      ReportField var23 = ReportField.STACK_TRACE;
      var8[14] = var23;
      ReportField var24 = ReportField.INITIAL_CONFIGURATION;
      var8[15] = var24;
      ReportField var25 = ReportField.CRASH_CONFIGURATION;
      var8[16] = var25;
      ReportField var26 = ReportField.DISPLAY;
      var8[17] = var26;
      ReportField var27 = ReportField.USER_COMMENT;
      var8[18] = var27;
      ReportField var28 = ReportField.USER_EMAIL;
      var8[19] = var28;
      ReportField var29 = ReportField.USER_APP_START_DATE;
      var8[20] = var29;
      ReportField var30 = ReportField.USER_CRASH_DATE;
      var8[21] = var30;
      ReportField var31 = ReportField.DUMPSYS_MEMINFO;
      var8[22] = var31;
      ReportField var32 = ReportField.DROPBOX;
      var8[23] = var32;
      ReportField var33 = ReportField.LOGCAT;
      var8[24] = var33;
      ReportField var34 = ReportField.EVENTSLOG;
      var8[25] = var34;
      ReportField var35 = ReportField.RADIOLOG;
      var8[26] = var35;
      ReportField var36 = ReportField.DEVICE_ID;
      var8[27] = var36;
      ReportField var37 = ReportField.INSTALLATION_ID;
      var8[28] = var37;
      ReportField var38 = ReportField.DEVICE_FEATURES;
      var8[29] = var38;
      ReportField var39 = ReportField.ENVIRONMENT;
      var8[30] = var39;
      ReportField var40 = ReportField.SHARED_PREFERENCES;
      var8[31] = var40;
      ReportField var41 = ReportField.SETTINGS_SYSTEM;
      var8[32] = var41;
      ReportField var42 = ReportField.SETTINGS_SECURE;
      var8[33] = var42;
      DEFAULT_REPORT_FIELDS = var8;
   }

   public ACRA() {}

   // $FF: synthetic method
   static void access$000() throws ACRAConfigurationException {
      initAcra();
   }

   static void checkCrashResources() throws ACRAConfigurationException {
      int[] var0 = ACRA.2.$SwitchMap$org$acra$ReportingInteractionMode;
      int var1 = mReportsCrashes.mode().ordinal();
      switch(var0[var1]) {
      case 1:
         if(mReportsCrashes.resToastText() != 0) {
            return;
         }

         throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
      case 2:
         if(mReportsCrashes.resNotifTickerText() != 0 && mReportsCrashes.resNotifTitle() != 0 && mReportsCrashes.resNotifText() != 0 && mReportsCrashes.resDialogText() != 0) {
            return;
         }

         throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText, resDialogText parameters in your application @ReportsCrashes() annotation.");
      default:
      }
   }

   public static SharedPreferences getACRASharedPreferences() {
      String var0 = mReportsCrashes.sharedPreferencesName();
      SharedPreferences var9;
      if(!"".equals(var0)) {
         String var1 = LOG_TAG;
         StringBuilder var2 = (new StringBuilder()).append("Retrieve SharedPreferences ");
         String var3 = mReportsCrashes.sharedPreferencesName();
         String var4 = var2.append(var3).toString();
         Log.d(var1, var4);
         Application var6 = mApplication;
         String var7 = mReportsCrashes.sharedPreferencesName();
         int var8 = mReportsCrashes.sharedPreferencesMode();
         var9 = var6.getSharedPreferences(var7, var8);
      } else {
         int var10 = Log.d(LOG_TAG, "Retrieve application default SharedPreferences.");
         var9 = PreferenceManager.getDefaultSharedPreferences(mApplication);
      }

      return var9;
   }

   public static ReportsCrashes getConfig() {
      return mReportsCrashes;
   }

   public static void init(Application var0) {
      mAppStartDate = new Time();
      mAppStartDate.setToNow();
      mApplication = var0;
      mReportsCrashes = (ReportsCrashes)mApplication.getClass().getAnnotation(ReportsCrashes.class);
      if(mReportsCrashes != null) {
         SharedPreferences var1 = getACRASharedPreferences();
         String var2 = LOG_TAG;
         String var3 = "Set OnSharedPreferenceChangeListener.";
         Log.d(var2, var3);
         mPrefListener = new ACRA.1();
         byte var5 = 0;

         label31: {
            byte var7;
            try {
               String var6 = "acra.disable";
               if(!var1.getBoolean("acra.enable", (boolean)1)) {
                  var3 = null;
               }

               var7 = var1.getBoolean(var6, (boolean)var3);
            } catch (Exception var18) {
               break label31;
            }

            var5 = var7;
         }

         if(var5 != 0) {
            String var8 = LOG_TAG;
            StringBuilder var9 = (new StringBuilder()).append("ACRA is disabled for ");
            String var10 = mApplication.getPackageName();
            String var11 = var9.append(var10).append(".").toString();
            Log.d(var8, var11);
         } else {
            try {
               initAcra();
            } catch (ACRAConfigurationException var17) {
               int var15 = Log.w(LOG_TAG, "Error : ", var17);
            }
         }

         OnSharedPreferenceChangeListener var13 = mPrefListener;
         var1.registerOnSharedPreferenceChangeListener(var13);
      }
   }

   private static void initAcra() throws ACRAConfigurationException {
      checkCrashResources();
      String var0 = LOG_TAG;
      StringBuilder var1 = (new StringBuilder()).append("ACRA is enabled for ");
      String var2 = mApplication.getPackageName();
      String var3 = var1.append(var2).append(", intializing...").toString();
      Log.d(var0, var3);
      ErrorReporter var5 = ErrorReporter.getInstance();
      ReportingInteractionMode var6 = mReportsCrashes.mode();
      var5.setReportingInteractionMode(var6);
      Time var7 = mAppStartDate;
      var5.setAppStartDate(var7);
      String var8 = mReportsCrashes.mailTo();
      if(!"".equals(var8)) {
         String var9 = LOG_TAG;
         StringBuilder var10 = new StringBuilder();
         String var11 = mApplication.getPackageName();
         String var12 = var10.append(var11).append(" reports will be sent by email (if accepted by user).").toString();
         Log.w(var9, var12);
         Application var14 = mApplication;
         EmailIntentSender var15 = new EmailIntentSender(var14);
         var5.addReportSender(var15);
      } else {
         PackageManager var17 = mApplication.getPackageManager();
         if(var17 != null) {
            String var18 = mApplication.getPackageName();
            if(var17.checkPermission("android.permission.INTERNET", var18) == 0) {
               label21: {
                  if(mReportsCrashes.formUri() != null) {
                     String var19 = mReportsCrashes.formUri();
                     if(!"".equals(var19)) {
                        String var20 = mReportsCrashes.formUri();
                        HttpPostSender var21 = new HttpPostSender(var20, (Map)null);
                        var5.addReportSender(var21);
                        break label21;
                     }
                  }

                  if(mReportsCrashes.formKey() != null) {
                     String var22 = mReportsCrashes.formKey().trim();
                     if(!"".equals(var22)) {
                        String var23 = mReportsCrashes.formKey();
                        GoogleFormSender var24 = new GoogleFormSender(var23);
                        var5.addReportSender(var24);
                     }
                  }
               }
            } else {
               String var25 = LOG_TAG;
               StringBuilder var26 = new StringBuilder();
               String var27 = mApplication.getPackageName();
               String var28 = var26.append(var27).append(" should be granted permission ").append("android.permission.INTERNET").append(" if you want your crash reports to be sent. If you don\'t want to add this permission to your application you can also enable sending reports by email. If this is your will then provide your email address in @ReportsCrashes(mailTo=\"your.account@domain.com\"").toString();
               Log.e(var25, var28);
            }
         }
      }

      Context var16 = mApplication.getApplicationContext();
      var5.init(var16);
      var5.checkReportsOnApplicationStart();
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$acra$ReportingInteractionMode = new int[ReportingInteractionMode.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$acra$ReportingInteractionMode;
            int var1 = ReportingInteractionMode.TOAST.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$acra$ReportingInteractionMode;
            int var3 = ReportingInteractionMode.NOTIFICATION.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   static class 1 implements OnSharedPreferenceChangeListener {

      1() {}

      public void onSharedPreferenceChanged(SharedPreferences param1, String param2) {
         // $FF: Couldn't be decompiled
      }
   }
}
