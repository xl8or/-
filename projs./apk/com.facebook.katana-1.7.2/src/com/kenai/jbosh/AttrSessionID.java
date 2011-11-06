// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrSessionID.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractAttr

final class AttrSessionID extends AbstractAttr
{

    private AttrSessionID(String s)
    {
        super(s);
    }

    static AttrSessionID createFromString(String s)
    {
        return new AttrSessionID(s);
    }
}
