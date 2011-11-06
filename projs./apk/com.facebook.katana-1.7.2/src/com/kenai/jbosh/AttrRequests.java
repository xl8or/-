// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrRequests.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractIntegerAttr, BOSHException

final class AttrRequests extends AbstractIntegerAttr
{

    private AttrRequests(String s)
        throws BOSHException
    {
        super(s);
        checkMinValue(1);
    }

    static AttrRequests createFromString(String s)
        throws BOSHException
    {
        AttrRequests attrrequests;
        if(s == null)
            attrrequests = null;
        else
            attrrequests = new AttrRequests(s);
        return attrrequests;
    }
}
