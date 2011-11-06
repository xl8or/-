// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.math.BigDecimal;
import java.math.BigInteger;

// Referenced classes of package org.codehaus.jackson.node:
//            ValueNode

public abstract class NumericNode extends ValueNode
{

    protected NumericNode()
    {
    }

    public abstract BigInteger getBigIntegerValue();

    public abstract BigDecimal getDecimalValue();

    public abstract double getDoubleValue();

    public abstract int getIntValue();

    public abstract long getLongValue();

    public abstract Number getNumberValue();

    public abstract String getValueAsText();

    public final boolean isNumber()
    {
        return true;
    }
}
