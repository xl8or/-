// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


// Referenced classes of package org.codehaus.jackson:
//            JsonProcessingException, JsonLocation

public class JsonGenerationException extends JsonProcessingException
{

    public JsonGenerationException(String s)
    {
        super(s, (JsonLocation)null);
    }

    public JsonGenerationException(String s, Throwable throwable)
    {
        super(s, (JsonLocation)null, throwable);
    }

    public JsonGenerationException(Throwable throwable)
    {
        super(throwable);
    }

    static final long serialVersionUID = 123L;
}
