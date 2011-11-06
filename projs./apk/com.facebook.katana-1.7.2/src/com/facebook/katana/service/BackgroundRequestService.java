// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BackgroundRequestService.java

package com.facebook.katana.service;

import android.app.IntentService;
import android.content.*;
import android.os.PowerManager;
import com.facebook.katana.provider.LoggingProvider;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Log;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class BackgroundRequestService extends IntentService
{
    public static final class Operation extends Enum
    {

        public static Operation valueOf(String s)
        {
            return (Operation)Enum.valueOf(com/facebook/katana/service/BackgroundRequestService$Operation, s);
        }

        public static Operation[] values()
        {
            return (Operation[])$VALUES.clone();
        }

        private static final Operation $VALUES[];
        public static final Operation HTTP_REQUEST;
        public static final Operation LOG;

        static 
        {
            LOG = new Operation("LOG", 0);
            HTTP_REQUEST = new Operation("HTTP_REQUEST", 1);
            Operation aoperation[] = new Operation[2];
            aoperation[0] = LOG;
            aoperation[1] = HTTP_REQUEST;
            $VALUES = aoperation;
        }

        private Operation(String s, int i)
        {
            super(s, i);
        }
    }


    public BackgroundRequestService()
    {
        super("BackgroundRequestService");
    }

    private void acquireWakeLock(Context context)
    {
        if(mWakeLock == null)
        {
            mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "FacebookService");
            mWakeLock.acquire();
        }
    }

    private HttpRequestBase getLoggingMethod(Intent intent)
    {
        HttpPost httppost;
        HttpPost httppost1;
        httppost = null;
        String s = intent.getStringExtra("com.facebook.katana.service.BackgroundRequestService.data");
        String s1 = intent.getStringExtra("com.facebook.katana.service.BackgroundRequestService.uri");
        try
        {
            httppost1 = new HttpPost(s1);
        }
        catch(Exception exception1)
        {
            continue; /* Loop/switch isn't completed */
        }
        httppost1.setEntity(new StringEntity(s));
        httppost1.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httppost = httppost1;
_L2:
        return httppost;
        Exception exception;
        exception;
        httppost = httppost1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private HttpRequestBase getRequestMethod(Intent intent)
    {
        HttpGet httpget;
        String s;
        httpget = null;
        s = intent.getStringExtra("com.facebook.katana.service.BackgroundRequestService.uri");
        HttpGet httpget1 = new HttpGet(s);
        httpget = httpget1;
_L2:
        return httpget;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void releaseWakeLock()
    {
        if(mWakeLock != null)
        {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    protected void onHandleIntent(Intent intent)
    {
        Operation operation;
        acquireWakeLock(this);
        operation = (Operation)intent.getSerializableExtra("com.facebook.katana.service.BackgroundRequestService.operation");
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation[];

            static 
            {
                $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation = new int[Operation.values().length];
                NoSuchFieldError nosuchfielderror1;
                try
                {
                    $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation[Operation.LOG.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation[Operation.HTTP_REQUEST.ordinal()] = 2;
_L2:
                return;
                nosuchfielderror1;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.service.BackgroundRequestService.Operation[operation.ordinal()];
        JVM INSTR tableswitch 1 2: default 44
    //                   1 49
    //                   2 66;
           goto _L1 _L2 _L3
_L1:
        releaseWakeLock();
_L4:
        return;
_L2:
        HttpRequestBase httprequestbase = getLoggingMethod(intent);
_L5:
        if(httprequestbase == null)
        {
            releaseWakeLock();
        } else
        {
            try
            {
                BasicHttpParams basichttpparams = new BasicHttpParams();
                basichttpparams.setParameter("http.socket.timeout", new Integer(0x19a28));
                basichttpparams.setParameter("http.connection.timeout", new Integer(20000));
                HttpOperation.initSchemeRegistry(this);
                DefaultHttpClient defaulthttpclient = new DefaultHttpClient(new SingleClientConnManager(basichttpparams, HttpOperation.supportedSchemes()), basichttpparams);
                defaulthttpclient.removeRequestInterceptorByClass(org/apache/http/client/protocol/RequestAddCookies);
                defaulthttpclient.removeResponseInterceptorByClass(org/apache/http/client/protocol/ResponseProcessCookies);
                HttpResponse httpresponse = defaulthttpclient.execute(httprequestbase);
                int i = httpresponse.getStatusLine().getStatusCode();
                httpresponse.getEntity().consumeContent();
                if(i == 200 && operation == Operation.LOG)
                    getContentResolver().delete(LoggingProvider.SESSIONS_CONTENT_URI, null, null);
            }
            catch(Exception exception)
            {
                Log.d("BackgroundRequestService", exception.toString());
            }
            releaseWakeLock();
        }
        if(true) goto _L4; else goto _L3
_L3:
        httprequestbase = getRequestMethod(intent);
          goto _L5
    }

    private static final int CONNECT_TIMEOUT = 20000;
    public static final String DATA_PARAM = "com.facebook.katana.service.BackgroundRequestService.data";
    public static final String OPERATION_PARAM = "com.facebook.katana.service.BackgroundRequestService.operation";
    private static final int READ_TIMEOUT = 0x19a28;
    private static final String TAG = "BackgroundRequestService";
    public static final String URI_PARAM = "com.facebook.katana.service.BackgroundRequestService.uri";
    private android.os.PowerManager.WakeLock mWakeLock;
}
