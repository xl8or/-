// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            NumericNode

public final class BigIntegerNode extends NumericNode
{

    public BigIntegerNode(BigInteger biginteger)
    {
        _value = biginteger;
    }

    public static BigIntegerNode valueOf(BigInteger biginteger)
    {
        return new BigIntegerNode(biginteger);
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
        if(((BigIntegerNode)obj)._value == _value)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public BigInteger getBigIntegerValue()
    {
        return _value;
    }

    public BigDecimal getDecimalValue()
    {
        return new BigDecimal(_value);
    }

    public double getDoubleValue()
    {
        return _value.doubleValue();
    }

    public int getIntValue()
    {
        return _value.intValue();
    }

    public long getLongValue()
    {
        return _value.longValue();
    }

    public Number getNumberValue()
    {
        return _value;
    }

    public String getValueAsText()
    {
        return _value.toString();
    }

    public int hashCode()
    {
        return _value.hashCode();
    }

    public boolean isBigInteger()
    {
        return true;
    }

    public boolean isIntegralNumber()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeNumber(_value);
    }

    final BigInteger _value;
}
