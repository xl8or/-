// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrPolling.java

package com.kenai.jbosh;

import java.util.concurrent.TimeUnit;

// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrPolling extends AbstractIntegerAttr
{

    private AttrPolling(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(0);
    }

    static AttrPolling createFromString(String s)
        throws BOSHException
    {
        AttrPolling attrpolling;
        if(s == null)
            attrpolling = null;
        else
            attrpolling = new AttrPolling(s);
        return attrpolling;
    }

    public int getInMilliseconds()
    {
        return (int)TimeUnit.MILLISECONDS.convert(intValue(), TimeUnit.SECONDS);
    }
}
