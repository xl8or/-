// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetGatekeeperSettings.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.*;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodCallback, ApiMethodListener

public class FqlGetGatekeeperSettings extends FqlGeneratedQuery
    implements ApiMethodCallback
{

    private FqlGetGatekeeperSettings(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Set set, NetworkRequestCallback networkrequestcallback)
    {
        super(context, intent, s, apimethodlistener, "project_gating", buildWhereClause(set), com/facebook/katana/model/GatekeeperSetting);
        mSettings = new HashMap();
        mCallback = networkrequestcallback;
    }

    public static String Get(Context context, String s, NetworkRequestCallback networkrequestcallback)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        String s1;
        if(appsession == null || appsession.isRequestPending(402))
        {
            s1 = null;
        } else
        {
            FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
            if(facebooksessioninfo == null)
            {
                s1 = null;
            } else
            {
                HashSet hashset = new HashSet();
                hashset.add(s);
                s1 = appsession.postToService(context, new FqlGetGatekeeperSettings(context, null, facebooksessioninfo.sessionKey, null, hashset, networkrequestcallback), 1001, 401, null);
            }
        }
        return s1;
    }

    public static String SyncAll(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        String s;
        if(appsession.isRequestPending(402))
        {
            s = null;
        } else
        {
            Set set = Gatekeeper.GATEKEEPER_PROJECTS.keySet();
            s = appsession.postToService(context, new FqlGetGatekeeperSettings(context, null, appsession.getSessionInfo().sessionKey, null, set, null), 1001, 402, null);
        }
        return s;
    }

    protected static String buildWhereClause(Set set)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("project_name IN(");
        com.facebook.katana.util.StringUtils.StringProcessor stringprocessor = StringUtils.FQLEscaper;
        Object aobj[] = new Object[1];
        aobj[0] = set;
        StringUtils.join(stringbuilder, ",", stringprocessor, aobj);
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        intent.getIntExtra("extended_type", -1);
        JVM INSTR tableswitch 401 402: default 32
    //                   401 160
    //                   402 33;
           goto _L1 _L2 _L3
_L1:
        return;
_L3:
        if(i == 200)
            Gatekeeper.cachePrefill(context, mSettings);
        Iterator iterator2 = appsession.getListeners().iterator();
        while(iterator2.hasNext()) 
        {
            AppSessionListener appsessionlistener = (AppSessionListener)iterator2.next();
            Iterator iterator3 = mSettings.entrySet().iterator();
            while(iterator3.hasNext()) 
            {
                java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator3.next();
                appsessionlistener.onGkSettingsGetComplete(appsession, s, i, s1, exception, (String)entry1.getKey(), ((Boolean)entry1.getValue()).booleanValue());
            }
        }
        continue; /* Loop/switch isn't completed */
_L2:
        String s2 = null;
        String s3 = null;
        Boolean boolean1 = null;
        Iterator iterator = mSettings.entrySet().iterator();
        if(iterator.hasNext())
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            s2 = (String)entry.getKey();
            boolean1 = (Boolean)entry.getValue();
        }
        boolean flag;
        boolean flag1;
        Iterator iterator1;
        if(i == 200 && s2 != null && boolean1 != null)
            flag = true;
        else
            flag = false;
        flag1 = false;
        if(boolean1 != null)
        {
            s3 = boolean1.toString();
            flag1 = boolean1.booleanValue();
        }
        mCallback.callback(context, flag, s2, s3, boolean1, null);
        iterator1 = appsession.getListeners().iterator();
        while(iterator1.hasNext()) 
            ((AppSessionListener)iterator1.next()).onGkSettingsGetComplete(appsession, s, i, s1, exception, s2, flag1);
        if(true) goto _L1; else goto _L4
_L4:
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        GatekeeperSetting gatekeepersetting;
        for(Iterator iterator = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/GatekeeperSetting).iterator(); iterator.hasNext(); mSettings.put(gatekeepersetting.mProjectName, Boolean.valueOf(gatekeepersetting.mEnabled)))
            gatekeepersetting = (GatekeeperSetting)iterator.next();

    }

    private static final String TAG = "FqlGetGatekeeperSettings";
    protected static Map mGatekeeperSettings;
    protected NetworkRequestCallback mCallback;
    protected Map mSettings;
}
