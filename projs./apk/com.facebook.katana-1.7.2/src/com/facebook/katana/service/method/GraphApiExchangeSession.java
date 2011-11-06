// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GraphApiExchangeSession.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            GraphApiMethod, ApiMethodCallback

public class GraphApiExchangeSession extends GraphApiMethod
    implements ApiMethodCallback
{

    private GraphApiExchangeSession(Context context, String s)
    {
        super(context, "POST", "oauth/exchange_sessions", com.facebook.katana.Constants.URL.getGraphUrl(context));
        mParams.put("sessions", s);
        mParams.put("client_id", Long.toString(0xa67c8e50L));
        mParams.put("client_secret", "62f8ce9f74b12f84c123cc23437a4a32");
    }

    public static String RequestOAuthToken(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new GraphApiExchangeSession(context, appsession.getSessionInfo().sessionKey), 1001, 1020, null);
    }

    public void addAuthenticationData(FacebookSessionInfo facebooksessioninfo)
    {
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(i == 200)
            appsession.updateSessionInfo(context, new FacebookSessionInfo(appsession.getSessionInfo(), mOAuthToken));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookSessionInfo);
        if(list.size() == 1)
            mOAuthToken = ((FacebookSessionInfo)list.get(0)).oAuthToken;
    }

    protected String mOAuthToken;
    protected FacebookSessionInfo mSessionInfo;
}
