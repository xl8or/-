// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrMaxPause.java

package com.kenai.jbosh;

import java.util.concurrent.TimeUnit;

// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrMaxPause extends AbstractIntegerAttr
{

    private AttrMaxPause(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(1);
    }

    static AttrMaxPause createFromString(String s)
        throws BOSHException
    {
        AttrMaxPause attrmaxpause;
        if(s == null)
            attrmaxpause = null;
        else
            attrmaxpause = new AttrMaxPause(s);
        return attrmaxpause;
    }

    public int getInMilliseconds()
    {
        return (int)TimeUnit.MILLISECONDS.convert(intValue(), TimeUnit.SECONDS);
    }
}
