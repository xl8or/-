// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ErrorReporter.java

package org.acra;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.Resources;
import android.os.*;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.*;
import java.util.*;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.Installation;

// Referenced classes of package org.acra:
//            CrashReportData, ReportField, ReportingInteractionMode, ACRA, 
//            DumpSysCollector, LogCatCollector, DropBoxCollector, ConfigurationInspector, 
//            ReflectionCollector, DeviceFeaturesCollector, SettingsCollector, SharedPreferencesCollector, 
//            CrashReportDialog

public class ErrorReporter
    implements Thread.UncaughtExceptionHandler
{
    final class ReportsSenderWorker extends Thread
    {

        private android.os.PowerManager.WakeLock acquireWakeLock()
        {
            PackageManager packagemanager = ErrorReporter.mContext.getPackageManager();
            boolean flag;
            android.os.PowerManager.WakeLock wakelock1;
            if(packagemanager != null)
            {
                if(packagemanager.checkPermission("android.permission.WAKE_LOCK", ErrorReporter.mContext.getPackageName()) == 0)
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = false;
            }
            if(!flag)
            {
                wakelock1 = null;
            } else
            {
                android.os.PowerManager.WakeLock wakelock = ((PowerManager)ErrorReporter.mContext.getSystemService("power")).newWakeLock(1, "ACRA wakelock");
                wakelock.acquire();
                wakelock1 = wakelock;
            }
            return wakelock1;
        }

        public void run()
        {
            android.os.PowerManager.WakeLock wakelock = acquireWakeLock();
            if(mApprovePendingReports)
            {
                approvePendingReports();
                mCommentedReportFileName = mCommentedReportFileName.replace(".stacktrace", "-approved.stacktrace");
            }
            ErrorReporter.addUserDataToReport(ErrorReporter.mContext, mCommentedReportFileName, mUserComment, mUserEmail);
            checkAndSendReports(ErrorReporter.mContext, mSendOnlySilentReports);
            if(wakelock != null)
                wakelock.release();
            return;
            Exception exception;
            exception;
            if(wakelock != null)
                wakelock.release();
            throw exception;
        }

        public void setApprovePendingReports()
        {
            mApprovePendingReports = true;
        }

        void setUserComment(String s, String s1)
        {
            mCommentedReportFileName = s;
            mUserComment = s1;
        }

        void setUserEmail(String s, String s1)
        {
            mCommentedReportFileName = s;
            mUserEmail = s1;
        }

        private boolean mApprovePendingReports;
        private String mCommentedReportFileName;
        private boolean mSendOnlySilentReports;
        private String mUserComment;
        private String mUserEmail;
        final ErrorReporter this$0;

        public ReportsSenderWorker()
        {
            this$0 = ErrorReporter.this;
            super();
            mCommentedReportFileName = null;
            mUserComment = null;
            mUserEmail = null;
            mSendOnlySilentReports = false;
            mApprovePendingReports = false;
        }

        public ReportsSenderWorker(boolean flag)
        {
            this$0 = ErrorReporter.this;
            super();
            mCommentedReportFileName = null;
            mUserComment = null;
            mUserEmail = null;
            mSendOnlySilentReports = false;
            mApprovePendingReports = false;
            mSendOnlySilentReports = flag;
        }
    }


    public ErrorReporter()
    {
        mCustomParameters = new HashMap();
        mReportingInteractionMode = ReportingInteractionMode.SILENT;
    }

    private static void addUserDataToReport(Context context, String s, String s1, String s2)
    {
        Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Add user comment to ").append(s).toString());
        if(s == null || s1 == null)
            break MISSING_BLOCK_LABEL_175;
        FileInputStream fileinputstream;
        CrashReportData crashreportdata;
        fileinputstream = context.openFileInput(s);
        crashreportdata = new CrashReportData();
        Log.d(ACRA.LOG_TAG, "Loading Properties report to insert user comment.");
        crashreportdata.load(fileinputstream);
        ReportField reportfield;
        String s3;
        fileinputstream.close();
        crashreportdata.put(ReportField.USER_COMMENT, s1);
        reportfield = ReportField.USER_EMAIL;
        if(s2 != null)
            break MISSING_BLOCK_LABEL_140;
        s3 = "";
_L1:
        crashreportdata.put(reportfield, s3);
        saveCrashReportFile(s, crashreportdata);
        break MISSING_BLOCK_LABEL_175;
        Exception exception;
        exception;
        fileinputstream.close();
        throw exception;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        Log.w(ACRA.LOG_TAG, "User comment not added: ", filenotfoundexception);
        break MISSING_BLOCK_LABEL_175;
        s3 = s2;
          goto _L1
        InvalidPropertiesFormatException invalidpropertiesformatexception;
        invalidpropertiesformatexception;
        Log.w(ACRA.LOG_TAG, "User comment not added: ", invalidpropertiesformatexception);
        break MISSING_BLOCK_LABEL_175;
        IOException ioexception;
        ioexception;
        Log.w(ACRA.LOG_TAG, "User comment not added: ", ioexception);
    }

    private boolean containsOnlySilentOrApprovedReports(String as[])
    {
        int i;
        int j;
        i = as.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_32;
        if(isApproved(as[j])) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = true;
          goto _L4
    }

    private String createCustomInfoString()
    {
        String s = "";
        for(Iterator iterator = mCustomParameters.keySet().iterator(); iterator.hasNext();)
        {
            String s1 = (String)iterator.next();
            String s2 = (String)mCustomParameters.get(s1);
            s = (new StringBuilder()).append(s).append(s1).append(" = ").append(s2).append("\n").toString();
        }

        return s;
    }

    private void deleteFile(Context context, String s)
    {
        if(!context.deleteFile(s))
            Log.w(ACRA.LOG_TAG, (new StringBuilder()).append("Could not deleted error report : ").append(s).toString());
    }

    private void deletePendingReports(boolean flag, boolean flag1, int i)
    {
        String as[] = getCrashReportFilesList();
        Arrays.sort(as);
        if(as != null)
        {
            for(int j = 0; j < as.length - i; j++)
            {
                String s = as[j];
                boolean flag2 = isApproved(s);
                if(flag2 && flag || !flag2 && flag1)
                    (new File(mContext.getFilesDir(), s)).delete();
            }

        }
    }

    private static long getAvailableInternalMemorySize()
    {
        StatFs statfs = new StatFs(Environment.getDataDirectory().getPath());
        return (long)statfs.getBlockSize() * (long)statfs.getAvailableBlocks();
    }

    public static ErrorReporter getInstance()
    {
        if(mInstanceSingleton == null)
            mInstanceSingleton = new ErrorReporter();
        return mInstanceSingleton;
    }

    private String getLatestNonSilentReport(String as[])
    {
        int i;
        if(as == null || as.length <= 0)
            break MISSING_BLOCK_LABEL_50;
        i = as.length - 1;
_L5:
        if(i < 0) goto _L2; else goto _L1
_L1:
        if(isSilent(as[i])) goto _L4; else goto _L3
_L3:
        String s = as[i];
_L6:
        return s;
_L4:
        i--;
          goto _L5
_L2:
        s = as[as.length - 1];
          goto _L6
        s = null;
          goto _L6
    }

    private static long getTotalInternalMemorySize()
    {
        StatFs statfs = new StatFs(Environment.getDataDirectory().getPath());
        return (long)statfs.getBlockSize() * (long)statfs.getBlockCount();
    }

    private boolean isApproved(String s)
    {
        boolean flag;
        if(isSilent(s) || s.contains("-approved"))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isSilent(String s)
    {
        return s.contains(SILENT_SUFFIX);
    }

    private CrashReportData loadCrashReport(Context context, String s)
        throws IOException
    {
        CrashReportData crashreportdata;
        FileInputStream fileinputstream;
        crashreportdata = new CrashReportData();
        fileinputstream = context.openFileInput(s);
        crashreportdata.load(fileinputstream);
        fileinputstream.close();
        return crashreportdata;
        Exception exception;
        exception;
        fileinputstream.close();
        throw exception;
    }

    private void retrieveCrashData(Context context)
    {
        String s;
        ReportsCrashes reportscrashes = ACRA.getConfig();
        ReportField areportfield[] = reportscrashes.customReportContent();
        List list;
        SharedPreferences sharedpreferences;
        PackageManager packagemanager;
        if(areportfield.length == 0)
            if(reportscrashes.mailTo() == null || "".equals(reportscrashes.mailTo()))
                areportfield = ACRA.DEFAULT_REPORT_FIELDS;
            else
            if(!"".equals(reportscrashes.mailTo()))
                areportfield = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
        list = Arrays.asList(areportfield);
        sharedpreferences = ACRA.getACRASharedPreferences();
        if(list.contains(ReportField.REPORT_ID))
            mCrashProperties.put(ReportField.REPORT_ID, UUID.randomUUID().toString());
        if(list.contains(ReportField.DUMPSYS_MEMINFO))
            mCrashProperties.put(ReportField.DUMPSYS_MEMINFO, DumpSysCollector.collectMemInfo());
        packagemanager = context.getPackageManager();
        if(packagemanager != null)
        {
            PackageInfo packageinfo;
            Time time;
            Display display;
            CrashReportData crashreportdata;
            ReportField reportfield;
            android.content.res.Configuration configuration;
            if(sharedpreferences.getBoolean("acra.syslog.enable", true) && packagemanager.checkPermission("android.permission.READ_LOGS", context.getPackageName()) == 0)
            {
                Log.i(ACRA.LOG_TAG, "READ_LOGS granted! ACRA can include LogCat and DropBox data.");
                if(list.contains(ReportField.LOGCAT))
                    mCrashProperties.put(ReportField.LOGCAT, LogCatCollector.collectLogCat(null).toString());
                if(list.contains(ReportField.EVENTSLOG))
                    mCrashProperties.put(ReportField.EVENTSLOG, LogCatCollector.collectLogCat("events").toString());
                if(list.contains(ReportField.RADIOLOG))
                    mCrashProperties.put(ReportField.RADIOLOG, LogCatCollector.collectLogCat("radio").toString());
                if(list.contains(ReportField.DROPBOX))
                    mCrashProperties.put(ReportField.DROPBOX, DropBoxCollector.read(mContext, ACRA.getConfig().additionalDropBoxTags()));
            } else
            {
                Log.i(ACRA.LOG_TAG, "READ_LOGS not allowed. ACRA will not include LogCat and DropBox data.");
            }
            if(list.contains(ReportField.DEVICE_ID) && sharedpreferences.getBoolean("acra.deviceid.enable", true) && packagemanager.checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0)
            {
                String s1 = ((TelephonyManager)context.getSystemService("phone")).getDeviceId();
                if(s1 != null)
                    mCrashProperties.put(ReportField.DEVICE_ID, s1);
            }
        }
        if(list.contains(ReportField.INSTALLATION_ID))
            mCrashProperties.put(ReportField.INSTALLATION_ID, Installation.id(mContext));
        if(list.contains(ReportField.INITIAL_CONFIGURATION))
            mCrashProperties.put(ReportField.INITIAL_CONFIGURATION, mInitialConfiguration);
        if(list.contains(ReportField.CRASH_CONFIGURATION))
        {
            configuration = context.getResources().getConfiguration();
            mCrashProperties.put(ReportField.CRASH_CONFIGURATION, ConfigurationInspector.toString(configuration));
        }
        packageinfo = packagemanager.getPackageInfo(context.getPackageName(), 0);
        if(packageinfo == null) goto _L2; else goto _L1
_L1:
        if(list.contains(ReportField.APP_VERSION_CODE))
            mCrashProperties.put(ReportField.APP_VERSION_CODE, Integer.toString(packageinfo.versionCode));
        if(!list.contains(ReportField.APP_VERSION_NAME)) goto _L4; else goto _L3
_L3:
        crashreportdata = mCrashProperties;
        reportfield = ReportField.APP_VERSION_NAME;
        if(packageinfo.versionName == null) goto _L6; else goto _L5
_L5:
        s = packageinfo.versionName;
_L9:
        crashreportdata.put(reportfield, s);
_L4:
        if(list.contains(ReportField.PACKAGE_NAME))
            mCrashProperties.put(ReportField.PACKAGE_NAME, context.getPackageName());
        if(list.contains(ReportField.BUILD))
            mCrashProperties.put(ReportField.BUILD, ReflectionCollector.collectConstants(android/os/Build));
        if(list.contains(ReportField.PHONE_MODEL))
            mCrashProperties.put(ReportField.PHONE_MODEL, Build.MODEL);
        if(list.contains(ReportField.ANDROID_VERSION))
            mCrashProperties.put(ReportField.ANDROID_VERSION, android.os.Build.VERSION.RELEASE);
        if(list.contains(ReportField.BRAND))
            mCrashProperties.put(ReportField.BRAND, Build.BRAND);
        if(list.contains(ReportField.PRODUCT))
            mCrashProperties.put(ReportField.PRODUCT, Build.PRODUCT);
        if(list.contains(ReportField.TOTAL_MEM_SIZE))
            mCrashProperties.put(ReportField.TOTAL_MEM_SIZE, Long.toString(getTotalInternalMemorySize()));
        if(list.contains(ReportField.AVAILABLE_MEM_SIZE))
            mCrashProperties.put(ReportField.AVAILABLE_MEM_SIZE, Long.toString(getAvailableInternalMemorySize()));
        if(list.contains(ReportField.FILE_PATH))
            mCrashProperties.put(ReportField.FILE_PATH, context.getFilesDir().getAbsolutePath());
        if(list.contains(ReportField.DISPLAY))
        {
            display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
            mCrashProperties.put(ReportField.DISPLAY, toString(display));
        }
        if(list.contains(ReportField.USER_CRASH_DATE))
        {
            time = new Time();
            time.setToNow();
            mCrashProperties.put(ReportField.USER_CRASH_DATE, time.format3339(false));
        }
        if(list.contains(ReportField.CUSTOM_DATA))
            mCrashProperties.put(ReportField.CUSTOM_DATA, createCustomInfoString());
        if(list.contains(ReportField.USER_EMAIL))
            mCrashProperties.put(ReportField.USER_EMAIL, sharedpreferences.getString("acra.user.email", "N/A"));
        if(list.contains(ReportField.DEVICE_FEATURES))
            mCrashProperties.put(ReportField.DEVICE_FEATURES, DeviceFeaturesCollector.getFeatures(context));
        if(list.contains(ReportField.ENVIRONMENT))
            mCrashProperties.put(ReportField.ENVIRONMENT, ReflectionCollector.collectStaticGettersResults(android/os/Environment));
        if(list.contains(ReportField.SETTINGS_SYSTEM))
            mCrashProperties.put(ReportField.SETTINGS_SYSTEM, SettingsCollector.collectSystemSettings(mContext));
        if(list.contains(ReportField.SETTINGS_SECURE))
            mCrashProperties.put(ReportField.SETTINGS_SECURE, SettingsCollector.collectSecureSettings(mContext));
        if(list.contains(ReportField.SHARED_PREFERENCES))
            mCrashProperties.put(ReportField.SHARED_PREFERENCES, SharedPreferencesCollector.collect(mContext));
          goto _L7
        Exception exception;
        exception;
        Log.e(ACRA.LOG_TAG, "Error while retrieving crash data", exception);
          goto _L7
_L6:
        s = "not set";
        continue; /* Loop/switch isn't completed */
_L2:
        mCrashProperties.put(ReportField.APP_VERSION_NAME, "Package info unavailable");
          goto _L4
_L7:
        return;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private static String saveCrashReportFile(String s, CrashReportData crashreportdata)
    {
        Log.d(ACRA.LOG_TAG, "Writing crash report file.");
        if(crashreportdata == null)
            crashreportdata = mCrashProperties;
        if(s != null) goto _L2; else goto _L1
_L1:
        StringBuilder stringbuilder;
        String s3;
        Time time = new Time();
        time.setToNow();
        long l = time.toMillis(false);
        String s2 = crashreportdata.getProperty(ReportField.IS_SILENT);
        stringbuilder = (new StringBuilder()).append("").append(l);
        if(s2 == null)
            break MISSING_BLOCK_LABEL_155;
        s3 = SILENT_SUFFIX;
_L3:
        s = stringbuilder.append(s3).append(".stacktrace").toString();
_L2:
        FileOutputStream fileoutputstream = mContext.openFileOutput(s, 0);
        crashreportdata.store(fileoutputstream, "");
        String s1;
        fileoutputstream.close();
        s1 = s;
        break MISSING_BLOCK_LABEL_152;
        Exception exception1;
        exception1;
        fileoutputstream.close();
        throw exception1;
        Exception exception;
        exception;
        Log.e(ACRA.LOG_TAG, "An error occured while writing the report file...", exception);
        s1 = null;
        return s1;
        s3 = "";
          goto _L3
    }

    private static void sendCrashReport(Context context, CrashReportData crashreportdata)
        throws ReportSenderException
    {
        boolean flag;
        Iterator iterator;
        flag = false;
        iterator = mReportSenders.iterator();
_L2:
        ReportSender reportsender;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        reportsender = (ReportSender)iterator.next();
        reportsender.send(crashreportdata);
        flag = true;
        continue; /* Loop/switch isn't completed */
        ReportSenderException reportsenderexception;
        reportsenderexception;
        if(!flag)
            throw reportsenderexception;
        Log.w(ACRA.LOG_TAG, (new StringBuilder()).append("ReportSender of class ").append(reportsender.getClass().getName()).append(" failed but other senders completed their task. ACRA will not send this report again.").toString());
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static String toString(Display display)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("width=").append(display.getWidth()).append('\n').append("height=").append(display.getHeight()).append('\n').append("pixelFormat=").append(display.getPixelFormat()).append('\n').append("refreshRate=").append(display.getRefreshRate()).append("fps").append('\n').append("metrics.density=x").append(displaymetrics.density).append('\n').append("metrics.scaledDensity=x").append(displaymetrics.scaledDensity).append('\n').append("metrics.widthPixels=").append(displaymetrics.widthPixels).append('\n').append("metrics.heightPixels=").append(displaymetrics.heightPixels).append('\n').append("metrics.xdpi=").append(displaymetrics.xdpi).append('\n').append("metrics.ydpi=").append(displaymetrics.ydpi);
        return stringbuilder.toString();
    }

    public void addCustomData(String s, String s1)
    {
        mCustomParameters.put(s, s1);
    }

    public void addReportSender(ReportSender reportsender)
    {
        mReportSenders.add(reportsender);
    }

    public void approvePendingReports()
    {
        Log.d(ACRA.LOG_TAG, "Mark all pending reports as approved.");
        String as[] = getCrashReportFilesList();
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            String s = as[j];
            if(!isApproved(s))
            {
                File file = new File(mContext.getFilesDir(), s);
                String s1 = s.replace(".stacktrace", "-approved.stacktrace");
                file.renameTo(new File(mContext.getFilesDir(), s1));
            }
        }

    }

    /**
     * @deprecated Method checkAndSendReports is deprecated
     */

    void checkAndSendReports(Context context, boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        String as[];
        int i;
        int j;
        int k;
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - start");
        as = getCrashReportFilesList();
        Arrays.sort(as);
        i = 0;
        j = as.length;
        k = 0;
_L5:
        if(k >= j) goto _L2; else goto _L1
_L1:
        String s = as[k];
        if(!flag || isSilent(s)) goto _L4; else goto _L3
_L2:
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
        this;
        JVM INSTR monitorexit ;
        return;
_L6:
        Log.i(ACRA.LOG_TAG, (new StringBuilder()).append("Sending file ").append(s).toString());
        sendCrashReport(context, loadCrashReport(context, s));
        deleteFile(context, s);
        i++;
          goto _L3
        RuntimeException runtimeexception;
        runtimeexception;
        Log.e(ACRA.LOG_TAG, "Failed to send crash reports", runtimeexception);
        deleteFile(context, s);
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        IOException ioexception;
        ioexception;
        Log.e(ACRA.LOG_TAG, (new StringBuilder()).append("Failed to load crash report for ").append(s).toString(), ioexception);
        deleteFile(context, s);
        break; /* Loop/switch isn't completed */
        ReportSenderException reportsenderexception;
        reportsenderexception;
        Log.e(ACRA.LOG_TAG, (new StringBuilder()).append("Failed to send crash report for ").append(s).toString(), reportsenderexception);
        break; /* Loop/switch isn't completed */
_L3:
        k++;
          goto _L5
_L4:
        if(i < 5) goto _L6; else goto _L2
    }

    public void checkReportsOnApplicationStart()
    {
        String as[] = getCrashReportFilesList();
        if(as != null && as.length > 0)
        {
            boolean flag = containsOnlySilentOrApprovedReports(as);
            if(mReportingInteractionMode == ReportingInteractionMode.SILENT || mReportingInteractionMode == ReportingInteractionMode.TOAST || mReportingInteractionMode == ReportingInteractionMode.NOTIFICATION && flag)
            {
                if(mReportingInteractionMode == ReportingInteractionMode.TOAST && !flag)
                    Toast.makeText(mContext, ACRA.getConfig().resToastText(), 1).show();
                Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from #checkReportOnApplicationStart");
                (new ReportsSenderWorker()).start();
            } else
            if(ACRA.getConfig().deleteUnapprovedReportsOnApplicationStart())
                getInstance().deletePendingNonApprovedReports();
            else
                getInstance().notifySendReport(getLatestNonSilentReport(as));
        }
    }

    public void deletePendingNonApprovedReports()
    {
        int i;
        if(mReportingInteractionMode == ReportingInteractionMode.NOTIFICATION)
            i = 1;
        else
            i = 0;
        deletePendingReports(false, true, i);
    }

    public void deletePendingReports()
    {
        deletePendingReports(true, true, 0);
    }

    public void deletePendingSilentReports()
    {
        deletePendingReports(true, false, 0);
    }

    public void disable()
    {
        if(mContext != null)
            Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("ACRA is disabled for ").append(mContext.getPackageName()).toString());
        else
            Log.d(ACRA.LOG_TAG, "ACRA is disabled.");
        if(mDfltExceptionHandler != null)
        {
            Thread.setDefaultUncaughtExceptionHandler(mDfltExceptionHandler);
            enabled = false;
        }
    }

    String[] getCrashReportFilesList()
    {
        String as[];
        if(mContext == null)
        {
            Log.e(ACRA.LOG_TAG, "Trying to get ACRA reports but ACRA is not initialized.");
            as = new String[0];
        } else
        {
            File file = mContext.getFilesDir();
            if(file != null)
            {
                Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Looking for error files in ").append(file.getAbsolutePath()).toString());
                String as1[] = file.list(new FilenameFilter() {

                    public boolean accept(File file1, String s)
                    {
                        return s.endsWith(".stacktrace");
                    }

                    final ErrorReporter this$0;

            
            {
                this$0 = ErrorReporter.this;
                super();
            }
                }
);
                if(as1 == null)
                    as = new String[0];
                else
                    as = as1;
            } else
            {
                Log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
                as = new String[0];
            }
        }
        return as;
    }

    public String getCustomData(String s)
    {
        return (String)mCustomParameters.get(s);
    }

    public ReportsSenderWorker handleException(Throwable throwable)
    {
        return handleException(throwable, mReportingInteractionMode);
    }

    ReportsSenderWorker handleException(Throwable throwable, ReportingInteractionMode reportinginteractionmode)
    {
        boolean flag = false;
        if(reportinginteractionmode != null) goto _L2; else goto _L1
_L1:
        reportinginteractionmode = mReportingInteractionMode;
_L4:
        StringWriter stringwriter;
        PrintWriter printwriter;
        if(throwable == null)
            throwable = new Exception("Report requested by developer");
        if(reportinginteractionmode == ReportingInteractionMode.TOAST || reportinginteractionmode == ReportingInteractionMode.NOTIFICATION && ACRA.getConfig().resToastText() != 0)
            (new Thread() {

                public void run()
                {
                    Looper.prepare();
                    Toast.makeText(ErrorReporter.mContext, ACRA.getConfig().resToastText(), 1).show();
                    Looper.loop();
                }

                final ErrorReporter this$0;

            
            {
                this$0 = ErrorReporter.this;
                super();
            }
            }
).start();
        retrieveCrashData(mContext);
        stringwriter = new StringWriter();
        printwriter = new PrintWriter(stringwriter);
        throwable.printStackTrace(printwriter);
        Log.getStackTraceString(throwable);
        for(Throwable throwable1 = throwable.getCause(); throwable1 != null; throwable1 = throwable1.getCause())
            throwable1.printStackTrace(printwriter);

        break; /* Loop/switch isn't completed */
_L2:
        if(reportinginteractionmode == ReportingInteractionMode.SILENT && mReportingInteractionMode != ReportingInteractionMode.SILENT)
            flag = true;
        if(true) goto _L4; else goto _L3
_L3:
        mCrashProperties.put(ReportField.STACK_TRACE, stringwriter.toString());
        printwriter.close();
        String s = saveCrashReportFile(null, null);
        mCrashProperties.remove(ReportField.IS_SILENT);
        mCrashProperties.remove(ReportField.USER_COMMENT);
        ReportsSenderWorker reportssenderworker1;
        if(reportinginteractionmode == ReportingInteractionMode.SILENT || reportinginteractionmode == ReportingInteractionMode.TOAST || ACRA.getACRASharedPreferences().getBoolean("acra.alwaysaccept", false))
        {
            approvePendingReports();
            ReportsSenderWorker reportssenderworker = new ReportsSenderWorker(flag);
            Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from #handleException");
            reportssenderworker.start();
            reportssenderworker1 = reportssenderworker;
        } else
        {
            if(reportinginteractionmode == ReportingInteractionMode.NOTIFICATION)
                notifySendReport(s);
            reportssenderworker1 = null;
        }
        return reportssenderworker1;
    }

    public Thread handleSilentException(Throwable throwable)
    {
        ReportsSenderWorker reportssenderworker;
        if(enabled)
        {
            mCrashProperties.put(ReportField.IS_SILENT, "true");
            reportssenderworker = handleException(throwable, ReportingInteractionMode.SILENT);
        } else
        {
            Log.d(ACRA.LOG_TAG, "ACRA is disabled. Silent report not sent.");
            reportssenderworker = null;
        }
        return reportssenderworker;
    }

    public void init(Context context)
    {
        if(mDfltExceptionHandler == null)
        {
            mDfltExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            enabled = true;
            Thread.setDefaultUncaughtExceptionHandler(this);
            mContext = context;
            mInitialConfiguration = ConfigurationInspector.toString(mContext.getResources().getConfiguration());
        }
    }

    void notifySendReport(String s)
    {
        NotificationManager notificationmanager = (NotificationManager)mContext.getSystemService("notification");
        ReportsCrashes reportscrashes = ACRA.getConfig();
        Notification notification = new Notification(reportscrashes.resNotifIcon(), mContext.getText(reportscrashes.resNotifTickerText()), System.currentTimeMillis());
        CharSequence charsequence = mContext.getText(reportscrashes.resNotifTitle());
        CharSequence charsequence1 = mContext.getText(reportscrashes.resNotifText());
        Intent intent = new Intent(mContext, org/acra/CrashReportDialog);
        Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Creating Notification for ").append(s).toString());
        intent.putExtra("REPORT_FILE_NAME", s);
        PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0x8000000);
        notification.setLatestEventInfo(mContext, charsequence, charsequence1, pendingintent);
        notificationmanager.cancelAll();
        notificationmanager.notify(666, notification);
    }

    public String putCustomData(String s, String s1)
    {
        return (String)mCustomParameters.put(s, s1);
    }

    public void removeAllReportSenders()
    {
        mReportSenders.clear();
    }

    public String removeCustomData(String s)
    {
        return (String)mCustomParameters.remove(s);
    }

    public void removeReportSender(ReportSender reportsender)
    {
        mReportSenders.remove(reportsender);
    }

    public void removeReportSenders(Class class1)
    {
        if(org/acra/sender/ReportSender.isAssignableFrom(class1))
        {
            Iterator iterator = mReportSenders.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                ReportSender reportsender = (ReportSender)iterator.next();
                if(class1.isInstance(reportsender))
                    mReportSenders.remove(reportsender);
            } while(true);
        }
    }

    public void setAppStartDate(Time time)
    {
        mCrashProperties.put(ReportField.USER_APP_START_DATE, time.format3339(false));
    }

    public void setReportSender(ReportSender reportsender)
    {
        removeAllReportSenders();
        addReportSender(reportsender);
    }

    void setReportingInteractionMode(ReportingInteractionMode reportinginteractionmode)
    {
        mReportingInteractionMode = reportinginteractionmode;
    }

    public void uncaughtException(Thread thread, Throwable throwable)
    {
        Log.e(ACRA.LOG_TAG, (new StringBuilder()).append("ACRA caught a ").append(throwable.getClass().getSimpleName()).append(" exception for ").append(mContext.getPackageName()).append(". Building report.").toString());
        mCrashProperties.remove(ReportField.IS_SILENT);
        ReportsSenderWorker reportssenderworker = handleException(throwable);
        if(mReportingInteractionMode == ReportingInteractionMode.TOAST)
            try
            {
                Thread.sleep(4000L);
            }
            catch(InterruptedException interruptedexception1)
            {
                Log.e(ACRA.LOG_TAG, "Error : ", interruptedexception1);
            }
        if(reportssenderworker != null)
            while(reportssenderworker.isAlive()) 
                try
                {
                    Thread.sleep(100L);
                }
                catch(InterruptedException interruptedexception)
                {
                    Log.e(ACRA.LOG_TAG, "Error : ", interruptedexception);
                }
        if(mReportingInteractionMode != ReportingInteractionMode.SILENT && (mReportingInteractionMode != ReportingInteractionMode.TOAST || !ACRA.getConfig().forceCloseDialogAfterToast())) goto _L2; else goto _L1
_L1:
        mDfltExceptionHandler.uncaughtException(thread, throwable);
_L3:
        return;
_L2:
        CharSequence charsequence = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), 0).loadLabel(mContext.getPackageManager());
        Log.e(ACRA.LOG_TAG, (new StringBuilder()).append(charsequence).append(" fatal error : ").append(throwable.getMessage()).toString(), throwable);
        Process.killProcess(Process.myPid());
        System.exit(10);
          goto _L3
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        Log.e(ACRA.LOG_TAG, "Error : ", namenotfoundexception);
        Process.killProcess(Process.myPid());
        System.exit(10);
          goto _L3
        Exception exception;
        exception;
        Process.killProcess(Process.myPid());
        System.exit(10);
        throw exception;
    }

    static final String APPROVED_SUFFIX = "-approved";
    static final String EXTRA_REPORT_FILE_NAME = "REPORT_FILE_NAME";
    private static final int MAX_SEND_REPORTS = 5;
    public static final String REPORTFILE_EXTENSION = ".stacktrace";
    static final String SILENT_SUFFIX;
    private static boolean enabled = false;
    private static Context mContext;
    private static CrashReportData mCrashProperties = new CrashReportData();
    private static ErrorReporter mInstanceSingleton;
    private static ArrayList mReportSenders = new ArrayList();
    Map mCustomParameters;
    private Thread.UncaughtExceptionHandler mDfltExceptionHandler;
    private String mInitialConfiguration;
    private ReportingInteractionMode mReportingInteractionMode;

    static 
    {
        SILENT_SUFFIX = (new StringBuilder()).append("-").append(ReportField.IS_SILENT).toString();
    }


}
