// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


// Referenced classes of package org.codehaus.jackson:
//            JsonProcessingException, JsonLocation

public class JsonParseException extends JsonProcessingException
{

    public JsonParseException(String s, JsonLocation jsonlocation)
    {
        super(s, jsonlocation);
    }

    public JsonParseException(String s, JsonLocation jsonlocation, Throwable throwable)
    {
        super(s, jsonlocation, throwable);
    }

    static final long serialVersionUID = 123L;
}
