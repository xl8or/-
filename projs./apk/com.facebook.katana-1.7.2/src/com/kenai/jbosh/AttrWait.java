// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrWait.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrWait extends AbstractIntegerAttr
{

    private AttrWait(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(1);
    }

    static AttrWait createFromString(String s)
        throws BOSHException
    {
        AttrWait attrwait;
        if(s == null)
            attrwait = null;
        else
            attrwait = new AttrWait(s);
        return attrwait;
    }
}
