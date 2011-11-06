// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   C2DMReceiver.java

package com.facebook.katana;

import android.app.PendingIntent;
import android.content.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookPushNotification;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMParser;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;

public class C2DMReceiver extends BroadcastReceiver
{

    public C2DMReceiver()
    {
    }

    public static void getClientLogin(Context context)
    {
        if(KeyValueManager.getValue(context, "C2DMKey", null) == null)
        {
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
            intent.putExtra("sender", "facebook.android@gmail.com");
            context.startService(intent);
        }
    }

    private void handleMessage(Context context, Intent intent)
    {
        if(AppSession.getActiveSession(context, false) != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s = intent.getStringExtra("notification");
        JsonFactory jsonfactory = new JsonFactory();
        if(s != null)
            try
            {
                JsonParser jsonparser = jsonfactory.createJsonParser(s);
                jsonparser.nextToken();
                ((FacebookPushNotification)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookPushNotification)).showNotification(context);
            }
            catch(Exception exception)
            {
                Log.e("C2DMReceiver", (new StringBuilder()).append("Exception parsing push notification: ").append(exception.getMessage()).toString(), exception);
            }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void handleRegistration(Context context, Intent intent)
    {
        String s = intent.getStringExtra("registration_id");
        if(intent.getStringExtra("error") == null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(intent.getStringExtra("unregistered") != null)
        {
            AppSession appsession1 = AppSession.getActiveSession(context, false);
            if(appsession1 != null)
                appsession1.unregisterForPush(context);
        } else
        if(s != null)
        {
            AppSession appsession = AppSession.getActiveSession(context, false);
            if(appsession != null)
                appsession.registerForPush(context, s);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static void logout(Context context)
    {
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
        context.startService(intent);
    }

    public void onReceive(Context context, Intent intent)
    {
        if(!intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) goto _L2; else goto _L1
_L1:
        handleRegistration(context, intent);
_L4:
        return;
_L2:
        if(intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE"))
            handleMessage(context, intent);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final String MSG_EXTRA_PAYLOAD = "notification";
    private static final String MSG_RCV = "com.google.android.c2dm.intent.RECEIVE";
    private static final String NOTIFICATION_SENDER = "facebook.android@gmail.com";
    private static final String REG_EXTRA_ERROR = "error";
    private static final String REG_EXTRA_ID = "registration_id";
    private static final String REG_EXTRA_UNREG = "unregistered";
    private static final String REG_INTENT = "com.google.android.c2dm.intent.REGISTER";
    private static final String REG_PARAM_APP = "app";
    private static final String REG_PARAM_SENDER = "sender";
    private static final String REG_RCV = "com.google.android.c2dm.intent.REGISTRATION";
    private static final String TAG = "C2DMReceiver";
    private static final String UNREG_INTENT = "com.google.android.c2dm.intent.UNREGISTER";
}
