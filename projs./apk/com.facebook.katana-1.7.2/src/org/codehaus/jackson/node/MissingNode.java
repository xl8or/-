// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            BaseJsonNode

public final class MissingNode extends BaseJsonNode
{

    private MissingNode()
    {
    }

    public static MissingNode getInstance()
    {
        return instance;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String getValueAsText()
    {
        return null;
    }

    public boolean isMissingNode()
    {
        return true;
    }

    public JsonNode path(int i)
    {
        return this;
    }

    public JsonNode path(String s)
    {
        return this;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
    }

    public String toString()
    {
        return "";
    }

    private static final MissingNode instance = new MissingNode();

}
