// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSetContactInfo.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, FBJsonFactory, ApiMethodListener

public class UserSetContactInfo extends ApiMethod
    implements ApiMethodCallback
{

    protected UserSetContactInfo(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, "POST", "userSetContactInfo", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("cell", s1);
    }

    public static String setCellNumber(AppSession appsession, Context context, String s)
    {
        return appsession.postToService(context, new UserSetContactInfo(context, null, appsession.getSessionInfo().sessionKey, null, s), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onUserSetContactInfoComplete(appsession, s, i, s1, exception));
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{") || !s.trim().equals("true"))
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        else
            return;
    }

    public static final String CELL_PARAM = "cell";
}
