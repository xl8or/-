// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JsErrorLogging.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.Constants;
import com.facebook.katana.UserAgent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.Utils;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiLogging

public class JsErrorLogging extends ApiLogging
{

    public JsErrorLogging()
    {
    }

    public static void log(Context context, String s, String s1)
    {
        if(Utils.RNG.nextInt() % 10000 == 0 || Constants.isBetaBuild())
        {
            JSONObject jsonobject = new JSONObject();
            try
            {
                jsonobject.put("uid", AppSession.getActiveSession(context, false).getSessionInfo().userId);
                jsonobject.put("device", UserAgent.getDevice());
                jsonobject.put("carrier", UserAgent.getCarrier(context));
                jsonobject.put("app_version", UserAgent.getAppVersion(context));
                jsonobject.put("os_version", UserAgent.getOSVersion());
                jsonobject.put("lid", 112);
                jsonobject.put("script", s);
                jsonobject.put("exc_text", s1);
                logAction(context, new StringBuilder(jsonobject.toString()));
            }
            catch(JSONException jsonexception) { }
        }
    }

    private static final String APP_VERSION_PARAM = "app_version";
    private static final String CARRIER_PARAM = "carrier";
    private static final String DEVICE_PARAM = "device";
    private static final int ERR_LOGGING_RATIO = 10000;
    private static final String EXC_TEXT_PARAM = "exc_text";
    protected static final int LOG_ID_JS_ERROR = 112;
    private static final String LOG_ID_PARAM = "lid";
    private static final String OS_VERSION_PARAM = "os_version";
    private static final String SCRIPT_PARAM = "script";
    private static final String UID_PARAM = "uid";
}
