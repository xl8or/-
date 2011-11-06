// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrHold.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrHold extends AbstractIntegerAttr
{

    private AttrHold(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(0);
    }

    static AttrHold createFromString(String s)
        throws BOSHException
    {
        AttrHold attrhold;
        if(s == null)
            attrhold = null;
        else
            attrhold = new AttrHold(s);
        return attrhold;
    }
}
