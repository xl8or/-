// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookAccountReceiver.java

package com.facebook.katana;

import android.content.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.Log;

public class FacebookAccountReceiver extends BroadcastReceiver
{

    public FacebookAccountReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        String s;
        if(Log.isLoggable("FacebookAccountReceiver", 3))
            Log.d("FacebookAccountReceiver", "----> onReceive");
        s = UserValuesManager.getCurrentAccount(context);
        if(s != null) goto _L2; else goto _L1
_L1:
        if(Log.isLoggable("FacebookAccountReceiver", 3))
            Log.d("FacebookAccountReceiver", "Account does not exist.");
_L4:
        return;
_L2:
        if(FacebookAuthenticationService.getAccount(context, s) == null)
            break; /* Loop/switch isn't completed */
        if(Log.isLoggable("FacebookAccountReceiver", 3))
            Log.d("FacebookAccountReceiver", (new StringBuilder()).append("Account still exists: ").append(s).toString());
        if(true) goto _L4; else goto _L3
_L3:
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession == null) goto _L4; else goto _L5
_L5:
        if(Log.isLoggable("FacebookAccountReceiver", 3))
            Log.d("FacebookAccountReceiver", (new StringBuilder()).append("Session status: ").append(appsession.getStatus()).toString());
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[];

            static 
            {
                $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus = new int[com.facebook.katana.binding.AppSession.LoginStatus.values().length];
                NoSuchFieldError nosuchfielderror3;
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGING_IN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_IN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_OUT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGING_OUT.ordinal()] = 4;
_L2:
                return;
                nosuchfielderror3;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        switch(_cls1..SwitchMap.com.facebook.katana.binding.AppSession.LoginStatus[appsession.getStatus().ordinal()])
        {
        case 2: // '\002'
            if(Log.isLoggable("FacebookAccountReceiver", 3))
                Log.d("FacebookAccountReceiver", (new StringBuilder()).append("Logging out: ").append(s).toString());
            appsession.authLogout(context);
            break;
        }
        if(true) goto _L4; else goto _L6
_L6:
    }

    private static final String TAG = "FacebookAccountReceiver";
}
