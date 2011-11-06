// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ACRA.java

package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.GoogleFormSender;
import org.acra.sender.HttpPostSender;

// Referenced classes of package org.acra:
//            ReportField, ACRAConfigurationException, ReportingInteractionMode, ErrorReporter

public class ACRA
{

    public ACRA()
    {
    }

    static void checkCrashResources()
        throws ACRAConfigurationException
    {
        class _cls2
        {

            static final int $SwitchMap$org$acra$ReportingInteractionMode[];

            static 
            {
                $SwitchMap$org$acra$ReportingInteractionMode = new int[ReportingInteractionMode.values().length];
                NoSuchFieldError nosuchfielderror1;
                try
                {
                    $SwitchMap$org$acra$ReportingInteractionMode[ReportingInteractionMode.TOAST.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                $SwitchMap$org$acra$ReportingInteractionMode[ReportingInteractionMode.NOTIFICATION.ordinal()] = 2;
_L2:
                return;
                nosuchfielderror1;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls2..SwitchMap.org.acra.ReportingInteractionMode[mReportsCrashes.mode().ordinal()];
        JVM INSTR tableswitch 1 2: default 36
    //                   1 37
    //                   2 58;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        if(mReportsCrashes.resToastText() == 0)
            throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
        continue; /* Loop/switch isn't completed */
_L3:
        if(mReportsCrashes.resNotifTickerText() == 0 || mReportsCrashes.resNotifTitle() == 0 || mReportsCrashes.resNotifText() == 0 || mReportsCrashes.resDialogText() == 0)
            throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText, resDialogText parameters in your application @ReportsCrashes() annotation.");
        if(true) goto _L1; else goto _L4
_L4:
    }

    public static SharedPreferences getACRASharedPreferences()
    {
        SharedPreferences sharedpreferences;
        if(!"".equals(mReportsCrashes.sharedPreferencesName()))
        {
            Log.d(LOG_TAG, (new StringBuilder()).append("Retrieve SharedPreferences ").append(mReportsCrashes.sharedPreferencesName()).toString());
            sharedpreferences = mApplication.getSharedPreferences(mReportsCrashes.sharedPreferencesName(), mReportsCrashes.sharedPreferencesMode());
        } else
        {
            Log.d(LOG_TAG, "Retrieve application default SharedPreferences.");
            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mApplication);
        }
        return sharedpreferences;
    }

    public static ReportsCrashes getConfig()
    {
        return mReportsCrashes;
    }

    public static void init(Application application)
    {
        mAppStartDate = new Time();
        mAppStartDate.setToNow();
        mApplication = application;
        mReportsCrashes = (ReportsCrashes)mApplication.getClass().getAnnotation(org/acra/annotation/ReportsCrashes);
        if(mReportsCrashes == null) goto _L2; else goto _L1
_L1:
        SharedPreferences sharedpreferences;
        boolean flag;
        sharedpreferences = getACRASharedPreferences();
        Log.d(LOG_TAG, "Set OnSharedPreferenceChangeListener.");
        mPrefListener = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {

            public void onSharedPreferenceChanged(SharedPreferences sharedpreferences1, String s)
            {
                if(!"acra.disable".equals(s) && !"acra.enable".equals(s)) goto _L2; else goto _L1
_L1:
                Boolean boolean1 = Boolean.valueOf(false);
                if(sharedpreferences1.getBoolean("acra.enable", true)) goto _L4; else goto _L3
_L3:
                boolean flag3 = true;
_L5:
                Boolean boolean2 = Boolean.valueOf(sharedpreferences1.getBoolean("acra.disable", flag3));
                boolean1 = boolean2;
_L6:
                if(boolean1.booleanValue())
                    ErrorReporter.getInstance().disable();
                else
                    try
                    {
                        ACRA.initAcra();
                    }
                    catch(ACRAConfigurationException acraconfigurationexception1)
                    {
                        Log.w(ACRA.LOG_TAG, "Error : ", acraconfigurationexception1);
                    }
_L2:
                return;
_L4:
                flag3 = false;
                  goto _L5
                Exception exception1;
                exception1;
                  goto _L6
            }

        }
;
        flag = false;
        if(sharedpreferences.getBoolean("acra.enable", true)) goto _L4; else goto _L3
_L3:
        boolean flag1 = true;
_L5:
        boolean flag2 = sharedpreferences.getBoolean("acra.disable", flag1);
        flag = flag2;
_L6:
        if(flag)
            Log.d(LOG_TAG, (new StringBuilder()).append("ACRA is disabled for ").append(mApplication.getPackageName()).append(".").toString());
        else
            try
            {
                initAcra();
            }
            catch(ACRAConfigurationException acraconfigurationexception)
            {
                Log.w(LOG_TAG, "Error : ", acraconfigurationexception);
            }
        sharedpreferences.registerOnSharedPreferenceChangeListener(mPrefListener);
_L2:
        return;
_L4:
        flag1 = false;
          goto _L5
        Exception exception;
        exception;
          goto _L6
    }

    private static void initAcra()
        throws ACRAConfigurationException
    {
        ErrorReporter errorreporter;
        checkCrashResources();
        Log.d(LOG_TAG, (new StringBuilder()).append("ACRA is enabled for ").append(mApplication.getPackageName()).append(", intializing...").toString());
        errorreporter = ErrorReporter.getInstance();
        errorreporter.setReportingInteractionMode(mReportsCrashes.mode());
        errorreporter.setAppStartDate(mAppStartDate);
        if("".equals(mReportsCrashes.mailTo())) goto _L2; else goto _L1
_L1:
        Log.w(LOG_TAG, (new StringBuilder()).append(mApplication.getPackageName()).append(" reports will be sent by email (if accepted by user).").toString());
        errorreporter.addReportSender(new EmailIntentSender(mApplication));
_L4:
        errorreporter.init(mApplication.getApplicationContext());
        errorreporter.checkReportsOnApplicationStart();
        return;
_L2:
        PackageManager packagemanager = mApplication.getPackageManager();
        if(packagemanager != null)
            if(packagemanager.checkPermission("android.permission.INTERNET", mApplication.getPackageName()) == 0)
            {
                if(mReportsCrashes.formUri() != null && !"".equals(mReportsCrashes.formUri()))
                    errorreporter.addReportSender(new HttpPostSender(mReportsCrashes.formUri(), null));
                else
                if(mReportsCrashes.formKey() != null && !"".equals(mReportsCrashes.formKey().trim()))
                    errorreporter.addReportSender(new GoogleFormSender(mReportsCrashes.formKey()));
            } else
            {
                Log.e(LOG_TAG, (new StringBuilder()).append(mApplication.getPackageName()).append(" should be granted permission ").append("android.permission.INTERNET").append(" if you want your crash reports to be sent. If you don't want to add this permission to your application you can also enable sending reports by email. If this is your will then provide your email address in @ReportsCrashes(mailTo=\"your.account@domain.com\"").toString());
            }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static final ReportField DEFAULT_MAIL_REPORT_FIELDS[];
    public static final ReportField DEFAULT_REPORT_FIELDS[];
    public static final boolean DEV_LOGGING = false;
    public static final String LOG_TAG = org/acra/ACRA.getSimpleName();
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
    private static android.content.SharedPreferences.OnSharedPreferenceChangeListener mPrefListener;
    private static ReportsCrashes mReportsCrashes;

    static 
    {
        ReportField areportfield[] = new ReportField[7];
        areportfield[0] = ReportField.USER_COMMENT;
        areportfield[1] = ReportField.ANDROID_VERSION;
        areportfield[2] = ReportField.APP_VERSION_NAME;
        areportfield[3] = ReportField.BRAND;
        areportfield[4] = ReportField.PHONE_MODEL;
        areportfield[5] = ReportField.CUSTOM_DATA;
        areportfield[6] = ReportField.STACK_TRACE;
        DEFAULT_MAIL_REPORT_FIELDS = areportfield;
        ReportField areportfield1[] = new ReportField[34];
        areportfield1[0] = ReportField.REPORT_ID;
        areportfield1[1] = ReportField.APP_VERSION_CODE;
        areportfield1[2] = ReportField.APP_VERSION_NAME;
        areportfield1[3] = ReportField.PACKAGE_NAME;
        areportfield1[4] = ReportField.FILE_PATH;
        areportfield1[5] = ReportField.PHONE_MODEL;
        areportfield1[6] = ReportField.BRAND;
        areportfield1[7] = ReportField.PRODUCT;
        areportfield1[8] = ReportField.ANDROID_VERSION;
        areportfield1[9] = ReportField.BUILD;
        areportfield1[10] = ReportField.TOTAL_MEM_SIZE;
        areportfield1[11] = ReportField.AVAILABLE_MEM_SIZE;
        areportfield1[12] = ReportField.CUSTOM_DATA;
        areportfield1[13] = ReportField.IS_SILENT;
        areportfield1[14] = ReportField.STACK_TRACE;
        areportfield1[15] = ReportField.INITIAL_CONFIGURATION;
        areportfield1[16] = ReportField.CRASH_CONFIGURATION;
        areportfield1[17] = ReportField.DISPLAY;
        areportfield1[18] = ReportField.USER_COMMENT;
        areportfield1[19] = ReportField.USER_EMAIL;
        areportfield1[20] = ReportField.USER_APP_START_DATE;
        areportfield1[21] = ReportField.USER_CRASH_DATE;
        areportfield1[22] = ReportField.DUMPSYS_MEMINFO;
        areportfield1[23] = ReportField.DROPBOX;
        areportfield1[24] = ReportField.LOGCAT;
        areportfield1[25] = ReportField.EVENTSLOG;
        areportfield1[26] = ReportField.RADIOLOG;
        areportfield1[27] = ReportField.DEVICE_ID;
        areportfield1[28] = ReportField.INSTALLATION_ID;
        areportfield1[29] = ReportField.DEVICE_FEATURES;
        areportfield1[30] = ReportField.ENVIRONMENT;
        areportfield1[31] = ReportField.SHARED_PREFERENCES;
        areportfield1[32] = ReportField.SETTINGS_SYSTEM;
        areportfield1[33] = ReportField.SETTINGS_SECURE;
        DEFAULT_REPORT_FIELDS = areportfield1;
    }

}
