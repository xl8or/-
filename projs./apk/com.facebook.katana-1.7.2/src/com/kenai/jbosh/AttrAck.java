// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrAck.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractAttr, BOSHException

final class AttrAck extends AbstractAttr
{

    private AttrAck(String s)
        throws BOSHException
    {
        super(s);
    }

    static AttrAck createFromString(String s)
        throws BOSHException
    {
        AttrAck attrack;
        if(s == null)
            attrack = null;
        else
            attrack = new AttrAck(s);
        return attrack;
    }
}
