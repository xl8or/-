// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReportField.java

package org.acra;


public final class ReportField extends Enum
{

    private ReportField(String s, int i)
    {
        super(s, i);
    }

    public static ReportField valueOf(String s)
    {
        return (ReportField)Enum.valueOf(org/acra/ReportField, s);
    }

    public static ReportField[] values()
    {
        return (ReportField[])$VALUES.clone();
    }

    private static final ReportField $VALUES[];
    public static final ReportField ANDROID_VERSION;
    public static final ReportField APP_VERSION_CODE;
    public static final ReportField APP_VERSION_NAME;
    public static final ReportField AVAILABLE_MEM_SIZE;
    public static final ReportField BRAND;
    public static final ReportField BUILD;
    public static final ReportField CRASH_CONFIGURATION;
    public static final ReportField CUSTOM_DATA;
    public static final ReportField DEVICE_FEATURES;
    public static final ReportField DEVICE_ID;
    public static final ReportField DISPLAY;
    public static final ReportField DROPBOX;
    public static final ReportField DUMPSYS_MEMINFO;
    public static final ReportField ENVIRONMENT;
    public static final ReportField EVENTSLOG;
    public static final ReportField FILE_PATH;
    public static final ReportField INITIAL_CONFIGURATION;
    public static final ReportField INSTALLATION_ID;
    public static final ReportField IS_SILENT;
    public static final ReportField LOGCAT;
    public static final ReportField PACKAGE_NAME;
    public static final ReportField PHONE_MODEL;
    public static final ReportField PRODUCT;
    public static final ReportField RADIOLOG;
    public static final ReportField REPORT_ID;
    public static final ReportField SETTINGS_SECURE;
    public static final ReportField SETTINGS_SYSTEM;
    public static final ReportField SHARED_PREFERENCES;
    public static final ReportField STACK_TRACE;
    public static final ReportField TOTAL_MEM_SIZE;
    public static final ReportField USER_APP_START_DATE;
    public static final ReportField USER_COMMENT;
    public static final ReportField USER_CRASH_DATE;
    public static final ReportField USER_EMAIL;

    static 
    {
        REPORT_ID = new ReportField("REPORT_ID", 0);
        APP_VERSION_CODE = new ReportField("APP_VERSION_CODE", 1);
        APP_VERSION_NAME = new ReportField("APP_VERSION_NAME", 2);
        PACKAGE_NAME = new ReportField("PACKAGE_NAME", 3);
        FILE_PATH = new ReportField("FILE_PATH", 4);
        PHONE_MODEL = new ReportField("PHONE_MODEL", 5);
        ANDROID_VERSION = new ReportField("ANDROID_VERSION", 6);
        BUILD = new ReportField("BUILD", 7);
        BRAND = new ReportField("BRAND", 8);
        PRODUCT = new ReportField("PRODUCT", 9);
        TOTAL_MEM_SIZE = new ReportField("TOTAL_MEM_SIZE", 10);
        AVAILABLE_MEM_SIZE = new ReportField("AVAILABLE_MEM_SIZE", 11);
        CUSTOM_DATA = new ReportField("CUSTOM_DATA", 12);
        STACK_TRACE = new ReportField("STACK_TRACE", 13);
        INITIAL_CONFIGURATION = new ReportField("INITIAL_CONFIGURATION", 14);
        CRASH_CONFIGURATION = new ReportField("CRASH_CONFIGURATION", 15);
        DISPLAY = new ReportField("DISPLAY", 16);
        USER_COMMENT = new ReportField("USER_COMMENT", 17);
        USER_APP_START_DATE = new ReportField("USER_APP_START_DATE", 18);
        USER_CRASH_DATE = new ReportField("USER_CRASH_DATE", 19);
        DUMPSYS_MEMINFO = new ReportField("DUMPSYS_MEMINFO", 20);
        DROPBOX = new ReportField("DROPBOX", 21);
        LOGCAT = new ReportField("LOGCAT", 22);
        EVENTSLOG = new ReportField("EVENTSLOG", 23);
        RADIOLOG = new ReportField("RADIOLOG", 24);
        IS_SILENT = new ReportField("IS_SILENT", 25);
        DEVICE_ID = new ReportField("DEVICE_ID", 26);
        INSTALLATION_ID = new ReportField("INSTALLATION_ID", 27);
        USER_EMAIL = new ReportField("USER_EMAIL", 28);
        DEVICE_FEATURES = new ReportField("DEVICE_FEATURES", 29);
        ENVIRONMENT = new ReportField("ENVIRONMENT", 30);
        SETTINGS_SYSTEM = new ReportField("SETTINGS_SYSTEM", 31);
        SETTINGS_SECURE = new ReportField("SETTINGS_SECURE", 32);
        SHARED_PREFERENCES = new ReportField("SHARED_PREFERENCES", 33);
        ReportField areportfield[] = new ReportField[34];
        areportfield[0] = REPORT_ID;
        areportfield[1] = APP_VERSION_CODE;
        areportfield[2] = APP_VERSION_NAME;
        areportfield[3] = PACKAGE_NAME;
        areportfield[4] = FILE_PATH;
        areportfield[5] = PHONE_MODEL;
        areportfield[6] = ANDROID_VERSION;
        areportfield[7] = BUILD;
        areportfield[8] = BRAND;
        areportfield[9] = PRODUCT;
        areportfield[10] = TOTAL_MEM_SIZE;
        areportfield[11] = AVAILABLE_MEM_SIZE;
        areportfield[12] = CUSTOM_DATA;
        areportfield[13] = STACK_TRACE;
        areportfield[14] = INITIAL_CONFIGURATION;
        areportfield[15] = CRASH_CONFIGURATION;
        areportfield[16] = DISPLAY;
        areportfield[17] = USER_COMMENT;
        areportfield[18] = USER_APP_START_DATE;
        areportfield[19] = USER_CRASH_DATE;
        areportfield[20] = DUMPSYS_MEMINFO;
        areportfield[21] = DROPBOX;
        areportfield[22] = LOGCAT;
        areportfield[23] = EVENTSLOG;
        areportfield[24] = RADIOLOG;
        areportfield[25] = IS_SILENT;
        areportfield[26] = DEVICE_ID;
        areportfield[27] = INSTALLATION_ID;
        areportfield[28] = USER_EMAIL;
        areportfield[29] = DEVICE_FEATURES;
        areportfield[30] = ENVIRONMENT;
        areportfield[31] = SETTINGS_SYSTEM;
        areportfield[32] = SETTINGS_SECURE;
        areportfield[33] = SHARED_PREFERENCES;
        $VALUES = areportfield;
    }
}
