// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PagesAddFan.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PagesAddFan extends ApiMethod
    implements ApiMethodCallback
{

    public PagesAddFan(Context context, Intent intent, String s, long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "pages.addFan", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mListener = apimethodlistener;
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("page_id", String.valueOf(l));
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPagesAddFanComplete(appsession, s, i, s1, exception, mSuccess));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        Object obj = JMParser.parseJsonResponse(jsonparser, JMBase.BOOLEAN);
        if(obj != null && (obj instanceof Boolean))
            mSuccess = ((Boolean)obj).booleanValue();
    }

    protected boolean mSuccess;
}
