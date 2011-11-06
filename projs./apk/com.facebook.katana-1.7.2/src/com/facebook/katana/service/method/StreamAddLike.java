// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamAddLike.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FBJsonFactory, ApiMethodListener

public class StreamAddLike extends ApiMethod
{

    public StreamAddLike(Context context, Intent intent, String s, long l, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "stream.addLike", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("uid", (new StringBuilder()).append("").append(l).toString());
        mParams.put("post_id", s1);
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        else
            return;
    }
}
