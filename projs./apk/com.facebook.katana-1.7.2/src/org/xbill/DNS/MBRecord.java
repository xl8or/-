// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MBRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class MBRecord extends SingleNameBase
{

    MBRecord()
    {
    }

    public MBRecord(Name name, int i, long l, Name name1)
    {
        SingleNameBase(name, 7, i, l, name1, "mailbox");
    }

    public Name getAdditionalName()
    {
        return getSingleName();
    }

    public Name getMailbox()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new MBRecord();
    }

    private static final long serialVersionUID = 0xdb6a8753L;
}
