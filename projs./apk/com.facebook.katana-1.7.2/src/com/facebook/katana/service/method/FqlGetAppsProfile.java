// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetAppsProfile.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookApp;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

public class FqlGetAppsProfile extends FqlGeneratedQuery
{

    public FqlGetAppsProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, "application", s1, com/facebook/katana/model/FacebookApp);
        mAppsMap = new HashMap();
    }

    public FqlGetAppsProfile(Context context, Intent intent, String s, Map map, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, apimethodlistener, "application", buildWhereClause(map), com/facebook/katana/model/FacebookApp);
        mAppsMap = map;
    }

    private static String buildWhereClause(Map map)
    {
        StringBuilder stringbuilder = new StringBuilder("(app_id IN(");
        boolean flag = true;
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()) 
        {
            Long long1 = (Long)iterator.next();
            if(!flag)
                stringbuilder.append(',');
            else
                flag = false;
            stringbuilder.append(long1);
        }
        stringbuilder.append("))");
        return stringbuilder.toString();
    }

    public Map getApps()
    {
        return Collections.unmodifiableMap(mAppsMap);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookApp);
        if(list != null)
        {
            FacebookApp facebookapp1;
            for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); mAppsMap.put(Long.valueOf(facebookapp1.mAppId), facebookapp1))
                facebookapp1 = (FacebookApp)iterator1.next();

        }
        FacebookApp facebookapp = null;
        Iterator iterator = mAppsMap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Long long1 = (Long)iterator.next();
            if(mAppsMap.get(long1) == null)
            {
                Log.w("FqlGetAppsProfile", (new StringBuilder()).append("App not found: ").append(long1).toString());
                if(facebookapp == null)
                    facebookapp = new FacebookApp(long1.longValue(), "", null);
                mAppsMap.put(long1, facebookapp);
            }
        } while(true);
    }

    private final Map mAppsMap;
}
