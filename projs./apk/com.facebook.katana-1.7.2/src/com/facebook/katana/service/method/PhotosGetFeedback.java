// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosGetFeedback.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PhotosGetFeedback extends ApiMethod
{

    public PhotosGetFeedback(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "getPhotoFeedback", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("pid", s1);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        else
            throw new IOException("Malformed JSON");
    }
}
