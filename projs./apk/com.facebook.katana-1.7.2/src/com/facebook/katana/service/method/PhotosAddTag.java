// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosAddTag.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhotoTag;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FBJsonFactory, ApiMethodListener

public class PhotosAddTag extends ApiMethod
{

    public PhotosAddTag(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "photos.addTag", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("pid", s1);
        mParams.put("tags", s2);
    }

    public List getTags()
    {
        return m_tags;
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        JsonParser jsonparser = mJsonFactory.createJsonParser((String)mParams.get("tags"));
        jsonparser.nextToken();
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_ARRAY)
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    m_tags.add(new FacebookPhotoTag(jsonparser));

        else
            throw new IOException("Malformed JSON");
    }

    private final List m_tags = new ArrayList();
}
