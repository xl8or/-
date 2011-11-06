// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookVideoUploadResponse.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException

public class FacebookVideoUploadResponse extends JMCachingDictDestination
{

    private FacebookVideoUploadResponse()
    {
    }

    public static FacebookVideoUploadResponse parseJson(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        return (FacebookVideoUploadResponse)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookVideoUploadResponse);
    }

    public final String link = null;
    public final String vid = null;
}
