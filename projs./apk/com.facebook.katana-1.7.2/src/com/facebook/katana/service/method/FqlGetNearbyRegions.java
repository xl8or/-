// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetNearbyRegions.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodCallback, ApiMethodListener

public class FqlGetNearbyRegions extends FqlGeneratedQuery
    implements ApiMethodCallback
{

    public FqlGetNearbyRegions(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, "geo_region", s1, com/facebook/katana/model/GeoRegion);
    }

    public static String GetRegions(Context context, String s)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetNearbyRegions(context, null, appsession.getSessionInfo().sessionKey, null, s), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onGetNearbyRegionsComplete(appsession, s, i, s1, exception, regions));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        regions = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/GeoRegion);
    }

    protected static final String TAG = Utils.getClassName(com/facebook/katana/service/method/FqlGetNearbyRegions);
    public List regions;

}
