// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MFRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class MFRecord extends SingleNameBase
{

    MFRecord()
    {
    }

    public MFRecord(Name name, int i, long l, Name name1)
    {
        SingleNameBase(name, 4, i, l, name1, "mail agent");
    }

    public Name getAdditionalName()
    {
        return getSingleName();
    }

    public Name getMailAgent()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new MFRecord();
    }

    private static final long serialVersionUID = 0x4a9ff137L;
}
