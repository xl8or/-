// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NSAP_PTRRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class NSAP_PTRRecord extends SingleNameBase
{

    NSAP_PTRRecord()
    {
    }

    public NSAP_PTRRecord(Name name, int i, long l, Name name1)
    {
        SingleNameBase(name, 23, i, l, name1, "target");
    }

    Record getObject()
    {
        return new NSAP_PTRRecord();
    }

    public Name getTarget()
    {
        return getSingleName();
    }

    private static final long serialVersionUID = 0x71093d08L;
}
