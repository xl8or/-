// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrPause.java

package com.kenai.jbosh;

import java.util.concurrent.TimeUnit;

// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrPause extends AbstractIntegerAttr
{

    private AttrPause(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(1);
    }

    static AttrPause createFromString(String s)
        throws BOSHException
    {
        AttrPause attrpause;
        if(s == null)
            attrpause = null;
        else
            attrpause = new AttrPause(s);
        return attrpause;
    }

    public int getInMilliseconds()
    {
        return (int)TimeUnit.MILLISECONDS.convert(intValue(), TimeUnit.SECONDS);
    }
}
