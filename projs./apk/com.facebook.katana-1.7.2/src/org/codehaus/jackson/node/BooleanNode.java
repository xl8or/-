// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            ValueNode

public final class BooleanNode extends ValueNode
{

    private BooleanNode()
    {
    }

    public static BooleanNode getFalse()
    {
        return FALSE;
    }

    public static BooleanNode getTrue()
    {
        return TRUE;
    }

    public static BooleanNode valueOf(boolean flag)
    {
        BooleanNode booleannode;
        if(flag)
            booleannode = TRUE;
        else
            booleannode = FALSE;
        return booleannode;
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

    public boolean getBooleanValue()
    {
        boolean flag;
        if(this == TRUE)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String getValueAsText()
    {
        String s;
        if(this == TRUE)
            s = "true";
        else
            s = "false";
        return s;
    }

    public boolean isBoolean()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        boolean flag;
        if(this == TRUE)
            flag = true;
        else
            flag = false;
        jsongenerator.writeBoolean(flag);
    }

    public static final BooleanNode FALSE = new BooleanNode();
    public static final BooleanNode TRUE = new BooleanNode();

}
