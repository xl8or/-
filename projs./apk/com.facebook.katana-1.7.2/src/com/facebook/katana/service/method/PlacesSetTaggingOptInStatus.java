// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesSetTaggingOptInStatus.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.features.places.PlacesUserSettings;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PlacesSetTaggingOptInStatus extends ApiMethod
    implements ApiMethodCallback
{

    public PlacesSetTaggingOptInStatus(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, int i)
    {
        super(context, intent, "POST", "places.setTaggingOptInStatus", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("value", Integer.toString(i));
    }

    public static String SetStatus(Context context, int i)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new PlacesSetTaggingOptInStatus(context, null, appsession.getSessionInfo().sessionKey, null, i), 1001, 1001, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(i == 200)
            PlacesUserSettings.setSetting(context, "places_opt_in", "enabled");
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onSetTaggingOptInStatusComplete(appsession, s, i, s1, exception));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() != JsonToken.VALUE_NULL)
            throw new FacebookApiException(jsonparser);
        else
            return;
    }

    public static final long INVALID_ID = -1L;
}
