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

public final class POJONode extends ValueNode
{

    public POJONode(Object obj)
    {
        _value = obj;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(obj == null)
            flag = false;
        else
        if(obj.getClass() != getClass())
        {
            flag = false;
        } else
        {
            POJONode pojonode = (POJONode)obj;
            if(_value == null)
            {
                if(pojonode._value == null)
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = _value.equals(pojonode._value);
            }
        }
        return flag;
    }

    public Object getPojo()
    {
        return _value;
    }

    public String getValueAsText()
    {
        return null;
    }

    public int hashCode()
    {
        return _value.hashCode();
    }

    public boolean isPojo()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        if(_value == null)
            jsongenerator.writeNull();
        else
            jsongenerator.writeObject(_value);
    }

    public String toString()
    {
        return String.valueOf(_value);
    }

    final Object _value;
}
