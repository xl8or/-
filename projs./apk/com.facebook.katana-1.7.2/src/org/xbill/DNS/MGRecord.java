// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MGRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class MGRecord extends SingleNameBase
{

    MGRecord()
    {
    }

    public MGRecord(Name name, int i, long l, Name name1)
    {
        SingleNameBase(name, 8, i, l, name1, "mailbox");
    }

    public Name getMailbox()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new MGRecord();
    }

    private static final long serialVersionUID = 0x5073685aL;
}
