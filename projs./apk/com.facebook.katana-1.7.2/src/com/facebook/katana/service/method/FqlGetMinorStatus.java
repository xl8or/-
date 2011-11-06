// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetMinorStatus.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodCallback, FBJsonFactory, ApiMethodListener

public class FqlGetMinorStatus extends FqlGeneratedQuery
    implements ApiMethodCallback
{

    protected FqlGetMinorStatus(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, NetworkRequestCallback networkrequestcallback, long l)
    {
        Object aobj[] = new Object[1];
        aobj[0] = Long.valueOf(l);
        super(context, intent, s, apimethodlistener, "user", String.format("uid=%d", aobj), com/facebook/katana/model/MinorStatusModel);
        mCb = networkrequestcallback;
    }

    public static String get(Context context, NetworkRequestCallback networkrequestcallback, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetMinorStatus(context, null, appsession.getSessionInfo().sessionKey, null, networkrequestcallback, l), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        NetworkRequestCallback networkrequestcallback = mCb;
        boolean flag;
        if(i == 200)
            flag = true;
        else
            flag = false;
        networkrequestcallback.callback(context, flag, null, mResponse, mIsMinor, null);
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonParser jsonparser = mJsonFactory.createJsonParser(s);
        jsonparser.nextToken();
        if(jsonparser.getCurrentToken() == JsonToken.START_OBJECT)
        {
            jsonparser.nextToken();
            if(jsonparser.getCurrentToken() != JsonToken.END_OBJECT)
            {
                JsonParser jsonparser2 = mJsonFactory.createJsonParser(s);
                jsonparser2.nextToken();
                throw new FacebookApiException(jsonparser2);
            }
        }
        JsonParser jsonparser1 = mJsonFactory.createJsonParser(s);
        jsonparser1.nextToken();
        List list = JMParser.parseObjectListJson(jsonparser1, com/facebook/katana/model/MinorStatusModel);
        if(list == null || list.size() != 1)
        {
            throw new IllegalArgumentException("unexpected number of results");
        } else
        {
            mResponse = s;
            mIsMinor = Boolean.valueOf(((MinorStatusModel)list.get(0)).isMinor);
            return;
        }
    }

    protected final NetworkRequestCallback mCb;
    protected Boolean mIsMinor;
    protected String mResponse;
}
