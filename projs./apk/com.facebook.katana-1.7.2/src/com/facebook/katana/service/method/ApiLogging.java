// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApiLogging.java

package com.facebook.katana.service.method;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.provider.LoggingProvider;
import com.facebook.katana.service.BackgroundRequestService;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Utils;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            HttpOperation

public class ApiLogging extends BroadcastReceiver
{
    public static interface LoggingQuery
    {

        public static final int LOG_INDEX;
        public static final String LOG_PROJECTION[] = as;

        
        {
            String as[] = new String[1];
            as[0] = "api_log";
        }
    }


    public ApiLogging()
    {
        errorCode = 0;
        ex = null;
    }

    public static void flush(Context context)
    {
        if(needsFlush)
        {
            long l = -1L;
            AppSession appsession = AppSession.getActiveSession(context, false);
            if(appsession != null && appsession.getSessionInfo() != null)
                l = appsession.getSessionInfo().userId;
            endTime = SystemClock.elapsedRealtime();
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("{\"lid\":");
            stringbuilder.append(108);
            stringbuilder.append(",\"trx_io_ec\":");
            stringbuilder.append(trxErrorCount.getAndSet(0));
            stringbuilder.append(",\"api_io_ec\":");
            stringbuilder.append(apiNetworkErrorCount.getAndSet(0));
            stringbuilder.append(",\"api_data_ec\":");
            stringbuilder.append(apiPayloadErrorCount.getAndSet(0));
            stringbuilder.append(",\"trx_count\":");
            stringbuilder.append(trxCount.getAndSet(0));
            stringbuilder.append(",\"api_count\":");
            stringbuilder.append(apiCount.getAndSet(0));
            if(l != -1L)
            {
                stringbuilder.append(USER_ID_PARAM);
                stringbuilder.append(l);
            }
            stringbuilder.append(",\"resume_count\":");
            stringbuilder.append(resumeCount.getAndSet(0));
            stringbuilder.append(",\"kill_count\":");
            stringbuilder.append(killCount.getAndSet(0));
            stringbuilder.append(",\"start_time\":");
            stringbuilder.append(startTime);
            stringbuilder.append(",\"end_time\":");
            stringbuilder.append(endTime);
            stringbuilder.append("}");
            ContentResolver contentresolver = context.getContentResolver();
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("session_id", Integer.valueOf(0));
            contentvalues.put("start_time", Long.valueOf(startTime));
            contentvalues.put("end_time", Long.valueOf(endTime));
            contentvalues.put("api_log", stringbuilder.toString());
            contentresolver.insert(LoggingProvider.SESSIONS_CONTENT_URI, contentvalues);
            needsFlush = false;
        }
    }

    public static void incrementKillCount()
    {
        needsFlush = true;
        killCount.incrementAndGet();
    }

    public static void incrementResumeCount()
    {
        needsFlush = true;
        resumeCount.incrementAndGet();
    }

    public static void load(Context context)
    {
        AlarmManager alarmmanager;
        PendingIntent pendingintent;
        if(versionName == null)
            try
            {
                versionName = URLEncoder.encode(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
            }
            catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
            {
                versionName = "";
            }
        if(deviceName == null)
            deviceName = URLEncoder.encode((new StringBuilder()).append(android.os.Build.VERSION.RELEASE).append(":").append(Build.DEVICE).append(":").append(Build.HOST).append(":").append(Build.MODEL).append(":").append(Build.PRODUCT).toString());
        if(operatorName == null)
            operatorName = URLEncoder.encode(((TelephonyManager)context.getSystemService("phone")).getNetworkOperatorName());
        alarmmanager = (AlarmManager)context.getSystemService("alarm");
        pendingintent = PendingIntent.getBroadcast(context, 0, new Intent(context, com/facebook/katana/service/method/ApiLogging), 0);
        alarmmanager.setInexactRepeating(2, 10000L + SystemClock.elapsedRealtime(), 0x2932e00L, pendingintent);
        apiCount.set(0);
        apiNetworkErrorCount.set(0);
        apiPayloadErrorCount.set(0);
        trxCount.set(0);
        trxErrorCount.set(0);
        resumeCount.set(0);
        killCount.set(0);
        startTime = SystemClock.elapsedRealtime();
    }

    public static void logAction(Context context, StringBuilder stringbuilder)
    {
        StringBuilder stringbuilder1 = new StringBuilder(2048);
        stringbuilder1.append("payload");
        stringbuilder1.append('=');
        stringbuilder1.append('[');
        stringbuilder1.append(URLEncoder.encode(stringbuilder.toString()));
        stringbuilder1.append(']');
        stringbuilder1.append('\n');
        stringbuilder1.append('\n');
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(stringbuilder1.toString().getBytes());
        String s = (new StringBuilder()).append(com.facebook.katana.Constants.URL.getLoggingUrl(context)).append("?id=").append(0xa67c8e50L).append("&lid=").append(105).toString();
        (new HttpOperation(context, (new StringBuilder()).append(s).append("&device=").append(deviceName).append("&carrier=").append(operatorName).append("&version=").append(versionName).toString(), bytearrayinputstream, new ByteArrayOutputStream(8192), "application/x-www-form-urlencoded;", null, false)).start();
_L1:
        return;
        Exception exception;
        exception;
        Log.e("LogClass", "Logging failed", exception);
          goto _L1
    }

    public static void logApiResponse(Context context, String s, long l, int i, Exception exception)
    {
        long l1;
        long l2;
        l1 = 0L;
        l2 = 0L;
        if(!(exception instanceof FacebookApiException)) goto _L2; else goto _L1
_L1:
        l1 = ((FacebookApiException)exception).getErrorCode();
_L4:
        StringBuilder stringbuilder = new StringBuilder(600);
        stringbuilder.append("{\"lid\":");
        stringbuilder.append(106);
        stringbuilder.append(s);
        stringbuilder.append("\",\"elapsed\":");
        stringbuilder.append(l);
        stringbuilder.append(",\"data\":");
        stringbuilder.append(i);
        if(l1 > 0L)
        {
            stringbuilder.append(",\"api_error\":");
            stringbuilder.append(l1);
        }
        if(l2 > 0L)
        {
            stringbuilder.append(",\"error\":");
            stringbuilder.append(l2);
        }
        stringbuilder.append(",\"frequency\":");
        stringbuilder.append(API_LOG_RATIO);
        stringbuilder.append("}");
        logAction(context, stringbuilder);
        return;
_L2:
        if((exception instanceof SocketTimeoutException) || (exception instanceof IOException))
            l2 = 121L;
        else
        if((exception instanceof JsonParseException) || (exception instanceof IllegalArgumentException))
            l2 = 120L;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void logTransferResponse(Context context, String s, long l, long l1, int i)
    {
        StringBuilder stringbuilder = new StringBuilder(600);
        stringbuilder.append("{\"lid\":");
        stringbuilder.append(107);
        stringbuilder.append(",\"url\":\"");
        stringbuilder.append(URLEncoder.encode(s));
        stringbuilder.append("\",\"elapsed\":");
        stringbuilder.append(l);
        stringbuilder.append(",\"data\":");
        stringbuilder.append(l1);
        if(i != 200)
        {
            stringbuilder.append(",\"error\":");
            stringbuilder.append(i);
        }
        stringbuilder.append(",\"frequency\":");
        stringbuilder.append(TRX_LOG_RATIO);
        stringbuilder.append("}");
        logAction(context, stringbuilder);
    }

    public static boolean reportAndCheckApi(Exception exception)
    {
        needsFlush = true;
        apiCount.incrementAndGet();
        boolean flag;
        if((exception instanceof SocketTimeoutException) || (exception instanceof IOException))
            apiNetworkErrorCount.incrementAndGet();
        else
        if((exception instanceof JsonParseException) || (exception instanceof IllegalArgumentException))
            apiPayloadErrorCount.incrementAndGet();
        if(Utils.RNG.nextInt() % API_LOG_RATIO == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static boolean reportAndCheckTrx(int i)
    {
        needsFlush = true;
        trxCount.incrementAndGet();
        if(i != 200)
            trxErrorCount.incrementAndGet();
        boolean flag;
        if(Utils.RNG.nextInt() % TRX_LOG_RATIO == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void updateLogRatios(int i, int j)
    {
        API_LOG_RATIO = i;
        TRX_LOG_RATIO = j;
    }

    public void onReceive(Context context, Intent intent)
    {
        Cursor cursor = context.getContentResolver().query(LoggingProvider.SESSIONS_CONTENT_URI, LoggingQuery.LOG_PROJECTION, null, null, null);
        if(cursor.getCount() > 0)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("payload");
            stringbuilder.append('=');
            stringbuilder.append('[');
            if(cursor.moveToFirst())
                do
                {
                    stringbuilder.append(cursor.getString(0));
                    stringbuilder.append(',');
                } while(cursor.moveToNext());
            stringbuilder.append(']');
            stringbuilder.append('\n');
            stringbuilder.append('\n');
            Intent intent1 = new Intent(context, com/facebook/katana/service/BackgroundRequestService);
            String s = (new StringBuilder()).append(com.facebook.katana.Constants.URL.getLoggingUrl(context)).append("?id=").append(0xa67c8e50L).append("&lid=").append(105).toString();
            intent1.putExtra("com.facebook.katana.service.BackgroundRequestService.operation", com.facebook.katana.service.BackgroundRequestService.Operation.LOG);
            intent1.putExtra("com.facebook.katana.service.BackgroundRequestService.data", stringbuilder.toString());
            intent1.putExtra("com.facebook.katana.service.BackgroundRequestService.uri", (new StringBuilder()).append(s).append("&device=").append(deviceName).append("&carrier=").append(operatorName).append("&version=").append(versionName).toString());
            context.startService(intent1);
        }
        cursor.close();
    }

    private static final String API_DATA_ERROR_PARAM = ",\"api_data_ec\":";
    private static final int API_EC_IO_EXCEPTION = 121;
    private static final int API_EC_MALFORMED_JSON = 120;
    private static final String API_ERROR_LOGGING_PARAM = ",\"api_error\":";
    private static final String API_IO_ERROR_PARAM = ",\"api_io_ec\":";
    public static volatile int API_LOG_RATIO = 0;
    public static final String ARGS_LOGGING_PARAM = "\",\"args\":\"";
    private static final String DATA_LOGGING_PARAM = ",\"data\":";
    private static final String DEVICE_HTTP_PARAM = "&device=";
    private static final String ELAPSED_LOGGING_PARAM = "\",\"elapsed\":";
    private static final String END_TIME_PARAM = ",\"end_time\":";
    private static final String ERROR_LOGGING_END = "}";
    private static final String ERROR_LOGGING_START = "{\"lid\":";
    private static final String FREQUENCY_PARAM = ",\"frequency\":";
    private static final String GENERIC_ERROR_LOGGING_PARAM = ",\"error\":";
    private static final int LOG_ID_API_HIT = 106;
    private static final int LOG_ID_BATCH = 105;
    private static final int LOG_ID_SESSION = 108;
    private static final int LOG_ID_TRX_HIT = 107;
    public static final String METHOD_LOGGING_PARAM = ",\"method\":\"";
    private static final String OPERATOR_HTTP_PARAM = "&carrier=";
    private static final String PAYLOAD_PARAM = "payload";
    private static final String START_TIME_PARAM = ",\"start_time\":";
    private static final String TAG = "LogClass";
    private static final String TOTAL_API_COUNT_PARAM = ",\"api_count\":";
    private static final String TOTAL_KILL_COUNT_PARAM = ",\"kill_count\":";
    private static final String TOTAL_RESUME_COUNT_PARAM = ",\"resume_count\":";
    private static final String TOTAL_TRX_COUNT_PARAM = ",\"trx_count\":";
    private static final String TRX_IO_ERROR_PARAM = ",\"trx_io_ec\":";
    public static volatile int TRX_LOG_RATIO = 0;
    private static final String URL_LOGGING_PARAM = ",\"url\":\"";
    private static final Object USER_ID_PARAM = ",\"uid\":";
    private static final String VERSION_HTTP_PARAM = "&version=";
    private static AtomicInteger apiCount = new AtomicInteger(0);
    private static AtomicInteger apiNetworkErrorCount = new AtomicInteger(0);
    private static AtomicInteger apiPayloadErrorCount = new AtomicInteger(0);
    public static String deviceName = null;
    private static long endTime;
    private static AtomicInteger killCount = new AtomicInteger(0);
    public static boolean needsFlush = false;
    public static String operatorName = null;
    private static AtomicInteger resumeCount = new AtomicInteger(0);
    private static long startTime;
    private static AtomicInteger trxCount = new AtomicInteger(0);
    private static AtomicInteger trxErrorCount = new AtomicInteger(0);
    public static String versionName = null;
    int errorCode;
    Exception ex;

    static 
    {
        int i;
        int j;
        if(Constants.isBetaBuild())
            i = 30;
        else
            i = 1000;
        API_LOG_RATIO = i;
        if(Constants.isBetaBuild())
            j = 100;
        else
            j = 3000;
        TRX_LOG_RATIO = j;
    }
}
