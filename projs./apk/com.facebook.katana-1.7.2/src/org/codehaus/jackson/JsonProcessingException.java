// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.IOException;

// Referenced classes of package org.codehaus.jackson:
//            JsonLocation

public class JsonProcessingException extends IOException
{

    protected JsonProcessingException(String s)
    {
        super(s);
    }

    protected JsonProcessingException(String s, Throwable throwable)
    {
        this(s, null, throwable);
    }

    protected JsonProcessingException(String s, JsonLocation jsonlocation)
    {
        this(s, jsonlocation, null);
    }

    protected JsonProcessingException(String s, JsonLocation jsonlocation, Throwable throwable)
    {
        super(s);
        if(throwable != null)
            initCause(throwable);
        mLocation = jsonlocation;
    }

    protected JsonProcessingException(Throwable throwable)
    {
        this(null, null, throwable);
    }

    public JsonLocation getLocation()
    {
        return mLocation;
    }

    public String getMessage()
    {
        String s = super.getMessage();
        if(s == null)
            s = "N/A";
        JsonLocation jsonlocation = getLocation();
        if(jsonlocation != null)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(s);
            stringbuilder.append('\n');
            stringbuilder.append(" at ");
            stringbuilder.append(jsonlocation.toString());
            s = stringbuilder.toString();
        }
        return s;
    }

    public String toString()
    {
        return (new StringBuilder()).append(getClass().getName()).append(": ").append(getMessage()).toString();
    }

    static final long serialVersionUID = 123L;
    protected JsonLocation mLocation;
}
