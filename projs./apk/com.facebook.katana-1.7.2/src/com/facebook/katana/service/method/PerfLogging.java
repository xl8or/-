// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PerfLogging.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.Constants;
import com.facebook.katana.UserAgent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiLogging

public class PerfLogging extends ApiLogging
{
    public static final class Step extends Enum
    {

        public static Step valueOf(String s)
        {
            return (Step)Enum.valueOf(com/facebook/katana/service/method/PerfLogging$Step, s);
        }

        public static Step[] values()
        {
            return (Step[])$VALUES.clone();
        }

        private static final Step $VALUES[];
        public static final Step DATA_RECEIVED;
        public static final Step DATA_REQUESTED;
        public static final Step ONCREATE;
        public static final Step ONRESUME;
        public static final Step UI_DRAWN_FRESH;
        public static final Step UI_DRAWN_STALE;

        static 
        {
            ONCREATE = new Step("ONCREATE", 0);
            ONRESUME = new Step("ONRESUME", 1);
            UI_DRAWN_STALE = new Step("UI_DRAWN_STALE", 2);
            DATA_REQUESTED = new Step("DATA_REQUESTED", 3);
            DATA_RECEIVED = new Step("DATA_RECEIVED", 4);
            UI_DRAWN_FRESH = new Step("UI_DRAWN_FRESH", 5);
            Step astep[] = new Step[6];
            astep[0] = ONCREATE;
            astep[1] = ONRESUME;
            astep[2] = UI_DRAWN_STALE;
            astep[3] = DATA_REQUESTED;
            astep[4] = DATA_RECEIVED;
            astep[5] = UI_DRAWN_FRESH;
            $VALUES = astep;
        }

        private Step(String s, int i)
        {
            super(s, i);
        }
    }


    public PerfLogging()
    {
    }

    public static JSONObject getCommonParams(Context context, long l)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            AppSession appsession = AppSession.getActiveSession(context, false);
            if(appsession != null)
            {
                FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
                if(facebooksessioninfo != null)
                    jsonobject.put("uid", facebooksessioninfo.userId);
            }
            jsonobject.put("time", l);
            jsonobject.put("device", UserAgent.getDevice());
            jsonobject.put("carrier", UserAgent.getCarrier(context));
            jsonobject.put("app_version", UserAgent.getAppVersion(context));
            jsonobject.put("os_version", UserAgent.getOSVersion());
        }
        catch(JSONException jsonexception) { }
        return jsonobject;
    }

    public static void logPageView(Context context, String s, long l)
    {
        logPageView(context, s, l, System.currentTimeMillis());
    }

    public static void logPageView(Context context, String s, long l, long l1)
    {
        if(l % 10000L == 0L || Constants.isBetaBuild())
        {
            JSONObject jsonobject = getCommonParams(context, l1);
            try
            {
                jsonobject.put("lid", 109);
                jsonobject.put("page", s);
                logAction(context, new StringBuilder(jsonobject.toString()));
            }
            catch(JSONException jsonexception) { }
        }
    }

    public static void logStep(Context context, Step step, String s, long l)
    {
        logStep(context, step, s, l, null);
    }

    public static void logStep(Context context, Step step, String s, long l, String s1)
    {
        long l1 = System.currentTimeMillis();
        if(l % 10000L == 0L || Constants.isBetaBuild())
        {
            JSONObject jsonobject = getCommonParams(context, l1);
            try
            {
                jsonobject.put("lid", 111);
                jsonobject.put("page", s);
                jsonobject.put("activity_id", l);
                jsonobject.put("step", step);
                if(s1 != null)
                    jsonobject.put("href", s1);
                logAction(context, new StringBuilder(jsonobject.toString()));
            }
            catch(JSONException jsonexception) { }
        }
    }

    private static final String ACTIVITY_ID_PARAM = "activity_id";
    private static final String APP_VERSION_PARAM = "app_version";
    private static final String CARRIER_PARAM = "carrier";
    private static final String DEVICE_PARAM = "device";
    private static final String HREF_PARAM = "href";
    protected static final int LOG_ID_PAGE_VIEW = 109;
    private static final String LOG_ID_PARAM = "lid";
    protected static final int LOG_ID_PERF_TTI = 111;
    private static final String OS_VERSION_PARAM = "os_version";
    private static final String PAGE_PARAM = "page";
    private static final int PERF_LOGGING_RATIO = 10000;
    private static final int PV_LOGGING_RATIO = 10000;
    private static final String STEP_PARAM = "step";
    private static final String TIMESTAMP_PARAM = "time";
    private static final String UID_PARAM = "uid";
}
