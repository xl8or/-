// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            NumericNode

public final class DoubleNode extends NumericNode
{

    public DoubleNode(double d)
    {
        _value = d;
    }

    public static DoubleNode valueOf(double d)
    {
        return new DoubleNode(d);
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
        if(((DoubleNode)obj)._value == _value)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public BigInteger getBigIntegerValue()
    {
        return getDecimalValue().toBigInteger();
    }

    public BigDecimal getDecimalValue()
    {
        return BigDecimal.valueOf(_value);
    }

    public double getDoubleValue()
    {
        return _value;
    }

    public int getIntValue()
    {
        return (int)_value;
    }

    public long getLongValue()
    {
        return (long)_value;
    }

    public Number getNumberValue()
    {
        return Double.valueOf(_value);
    }

    public String getValueAsText()
    {
        return NumberOutput.toString(_value);
    }

    public int hashCode()
    {
        long l = Double.doubleToLongBits(_value);
        return (int)l ^ (int)(l >> 32);
    }

    public boolean isDouble()
    {
        return true;
    }

    public boolean isFloatingPointNumber()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeNumber(_value);
    }

    final double _value;
}
