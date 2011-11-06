// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

// Referenced classes of package org.codehaus.jackson:
//            JsonGenerationException, JsonGenerator

public abstract class JsonNode
    implements Iterable
{

    protected JsonNode()
    {
    }

    public abstract boolean equals(Object obj);

    public JsonNode get(int i)
    {
        return null;
    }

    public JsonNode get(String s)
    {
        return null;
    }

    public BigInteger getBigIntegerValue()
    {
        return BigInteger.ZERO;
    }

    public byte[] getBinaryValue()
    {
        return null;
    }

    public boolean getBooleanValue()
    {
        return false;
    }

    public BigDecimal getDecimalValue()
    {
        return BigDecimal.ZERO;
    }

    public double getDoubleValue()
    {
        return 0D;
    }

    public final JsonNode getElementValue(int i)
    {
        return get(i);
    }

    public Iterator getElements()
    {
        return NO_NODES.iterator();
    }

    public Iterator getFieldNames()
    {
        return NO_STRINGS.iterator();
    }

    public final JsonNode getFieldValue(String s)
    {
        return get(s);
    }

    public int getIntValue()
    {
        return 0;
    }

    public long getLongValue()
    {
        return 0L;
    }

    public Number getNumberValue()
    {
        return Integer.valueOf(getIntValue());
    }

    public final JsonNode getPath(int i)
    {
        return path(i);
    }

    public final JsonNode getPath(String s)
    {
        return path(s);
    }

    public String getTextValue()
    {
        return null;
    }

    public abstract String getValueAsText();

    public boolean isArray()
    {
        return false;
    }

    public boolean isBigDecimal()
    {
        return false;
    }

    public boolean isBigInteger()
    {
        return false;
    }

    public boolean isBinary()
    {
        return false;
    }

    public boolean isBoolean()
    {
        return false;
    }

    public boolean isContainerNode()
    {
        return false;
    }

    public boolean isDouble()
    {
        return false;
    }

    public boolean isFloatingPointNumber()
    {
        return false;
    }

    public boolean isInt()
    {
        return false;
    }

    public boolean isIntegralNumber()
    {
        return false;
    }

    public boolean isLong()
    {
        return false;
    }

    public boolean isMissingNode()
    {
        return false;
    }

    public boolean isNull()
    {
        return false;
    }

    public boolean isNumber()
    {
        return false;
    }

    public boolean isObject()
    {
        return false;
    }

    public boolean isPojo()
    {
        return false;
    }

    public boolean isTextual()
    {
        return false;
    }

    public boolean isValueNode()
    {
        return false;
    }

    public final Iterator iterator()
    {
        return getElements();
    }

    public abstract JsonNode path(int i);

    public abstract JsonNode path(String s);

    public int size()
    {
        return 0;
    }

    public abstract String toString();

    public abstract void writeTo(JsonGenerator jsongenerator)
        throws IOException, JsonGenerationException;

    static final List NO_NODES = Collections.emptyList();
    static final List NO_STRINGS = Collections.emptyList();

}
