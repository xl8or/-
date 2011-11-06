// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarkGroupRead.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            GraphApiMethod, ApiMethodCallback

public class MarkGroupRead extends GraphApiMethod
    implements ApiMethodCallback
{

    private MarkGroupRead(Context context, String s, long l)
    {
        super(context, s, "POST", Long.toString(l), com.facebook.katana.Constants.URL.getGraphUrl(context));
        mParams.put("viewed", "true");
        mGroupId = l;
    }

    public static String Request(Context context, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new MarkGroupRead(context, appsession.getSessionInfo().oAuthToken, l), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(!mSuccess)
            i = 0;
        AppSessionListener appsessionlistener;
        long l;
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); appsessionlistener.onMarkGroupReadComplete(appsession, s, i, s1, exception, l))
        {
            appsessionlistener = (AppSessionListener)iterator.next();
            l = mGroupId;
        }

    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        Object obj = JMParser.parseJsonResponse(jsonparser, JMBase.BOOLEAN);
        if(obj != null && (obj instanceof Boolean))
            mSuccess = ((Boolean)obj).booleanValue();
    }

    public final long mGroupId;
    protected boolean mSuccess;
}
