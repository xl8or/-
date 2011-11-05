package org.acra;


public enum ReportField {

   // $FF: synthetic field
   private static final ReportField[] $VALUES;
   ANDROID_VERSION("ANDROID_VERSION", 6),
   APP_VERSION_CODE("APP_VERSION_CODE", 1),
   APP_VERSION_NAME("APP_VERSION_NAME", 2),
   AVAILABLE_MEM_SIZE("AVAILABLE_MEM_SIZE", 11),
   BRAND("BRAND", 8),
   BUILD("BUILD", 7),
   CRASH_CONFIGURATION("CRASH_CONFIGURATION", 15),
   CUSTOM_DATA("CUSTOM_DATA", 12),
   DEVICE_FEATURES("DEVICE_FEATURES", 29),
   DEVICE_ID("DEVICE_ID", 26),
   DISPLAY("DISPLAY", 16),
   DROPBOX("DROPBOX", 21),
   DUMPSYS_MEMINFO("DUMPSYS_MEMINFO", 20),
   ENVIRONMENT("ENVIRONMENT", 30),
   EVENTSLOG("EVENTSLOG", 23),
   FILE_PATH("FILE_PATH", 4),
   INITIAL_CONFIGURATION("INITIAL_CONFIGURATION", 14),
   INSTALLATION_ID("INSTALLATION_ID", 27),
   IS_SILENT("IS_SILENT", 25),
   LOGCAT("LOGCAT", 22),
   PACKAGE_NAME("PACKAGE_NAME", 3),
   PHONE_MODEL("PHONE_MODEL", 5),
   PRODUCT("PRODUCT", 9),
   RADIOLOG("RADIOLOG", 24),
   REPORT_ID("REPORT_ID", 0),
   SETTINGS_SECURE("SETTINGS_SECURE", 32),
   SETTINGS_SYSTEM("SETTINGS_SYSTEM", 31),
   SHARED_PREFERENCES("SHARED_PREFERENCES", 33),
   STACK_TRACE("STACK_TRACE", 13),
   TOTAL_MEM_SIZE("TOTAL_MEM_SIZE", 10),
   USER_APP_START_DATE("USER_APP_START_DATE", 18),
   USER_COMMENT("USER_COMMENT", 17),
   USER_CRASH_DATE("USER_CRASH_DATE", 19),
   USER_EMAIL("USER_EMAIL", 28);


   static {
      ReportField[] var0 = new ReportField[34];
      ReportField var1 = REPORT_ID;
      var0[0] = var1;
      ReportField var2 = APP_VERSION_CODE;
      var0[1] = var2;
      ReportField var3 = APP_VERSION_NAME;
      var0[2] = var3;
      ReportField var4 = PACKAGE_NAME;
      var0[3] = var4;
      ReportField var5 = FILE_PATH;
      var0[4] = var5;
      ReportField var6 = PHONE_MODEL;
      var0[5] = var6;
      ReportField var7 = ANDROID_VERSION;
      var0[6] = var7;
      ReportField var8 = BUILD;
      var0[7] = var8;
      ReportField var9 = BRAND;
      var0[8] = var9;
      ReportField var10 = PRODUCT;
      var0[9] = var10;
      ReportField var11 = TOTAL_MEM_SIZE;
      var0[10] = var11;
      ReportField var12 = AVAILABLE_MEM_SIZE;
      var0[11] = var12;
      ReportField var13 = CUSTOM_DATA;
      var0[12] = var13;
      ReportField var14 = STACK_TRACE;
      var0[13] = var14;
      ReportField var15 = INITIAL_CONFIGURATION;
      var0[14] = var15;
      ReportField var16 = CRASH_CONFIGURATION;
      var0[15] = var16;
      ReportField var17 = DISPLAY;
      var0[16] = var17;
      ReportField var18 = USER_COMMENT;
      var0[17] = var18;
      ReportField var19 = USER_APP_START_DATE;
      var0[18] = var19;
      ReportField var20 = USER_CRASH_DATE;
      var0[19] = var20;
      ReportField var21 = DUMPSYS_MEMINFO;
      var0[20] = var21;
      ReportField var22 = DROPBOX;
      var0[21] = var22;
      ReportField var23 = LOGCAT;
      var0[22] = var23;
      ReportField var24 = EVENTSLOG;
      var0[23] = var24;
      ReportField var25 = RADIOLOG;
      var0[24] = var25;
      ReportField var26 = IS_SILENT;
      var0[25] = var26;
      ReportField var27 = DEVICE_ID;
      var0[26] = var27;
      ReportField var28 = INSTALLATION_ID;
      var0[27] = var28;
      ReportField var29 = USER_EMAIL;
      var0[28] = var29;
      ReportField var30 = DEVICE_FEATURES;
      var0[29] = var30;
      ReportField var31 = ENVIRONMENT;
      var0[30] = var31;
      ReportField var32 = SETTINGS_SYSTEM;
      var0[31] = var32;
      ReportField var33 = SETTINGS_SECURE;
      var0[32] = var33;
      ReportField var34 = SHARED_PREFERENCES;
      var0[33] = var34;
      $VALUES = var0;
   }

   private ReportField(String var1, int var2) {}
}
