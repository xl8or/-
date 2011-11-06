// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.util.CharTypes;

// Referenced classes of package org.codehaus.jackson.node:
//            ValueNode

public final class TextNode extends ValueNode
{

    public TextNode(String s)
    {
        _value = s;
    }

    protected static void appendQuoted(StringBuilder stringbuilder, String s)
    {
        stringbuilder.append('"');
        CharTypes.appendQuoted(stringbuilder, s);
        stringbuilder.append('"');
    }

    public static TextNode valueOf(String s)
    {
        TextNode textnode;
        if(s == null)
            textnode = null;
        else
        if(s.length() == 0)
            textnode = EMPTY_STRING_NODE;
        else
            textnode = new TextNode(s);
        return textnode;
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
            flag = false;
        else
            flag = ((TextNode)obj)._value.equals(_value);
        return flag;
    }

    public String getTextValue()
    {
        return _value;
    }

    public String getValueAsText()
    {
        return _value;
    }

    public int hashCode()
    {
        return _value.hashCode();
    }

    public boolean isTextual()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        if(_value == null)
            jsongenerator.writeNull();
        else
            jsongenerator.writeString(_value);
    }

    public String toString()
    {
        int i = _value.length();
        StringBuilder stringbuilder = new StringBuilder(i + 2 + (i >> 4));
        appendQuoted(stringbuilder, _value);
        return stringbuilder.toString();
    }

    static final TextNode EMPTY_STRING_NODE = new TextNode("");
    final String _value;

}
