// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrInactivity.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrInactivity extends AbstractIntegerAttr
{

    private AttrInactivity(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(0);
    }

    static AttrInactivity createFromString(String s)
        throws BOSHException
    {
        AttrInactivity attrinactivity;
        if(s == null)
            attrinactivity = null;
        else
            attrinactivity = new AttrInactivity(s);
        return attrinactivity;
    }
}
