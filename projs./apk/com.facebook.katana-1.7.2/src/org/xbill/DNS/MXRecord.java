// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MXRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            U16NameBase, DNSOutput, Name, Record, 
//            Compression

public class MXRecord extends U16NameBase
{

    MXRecord()
    {
    }

    public MXRecord(Name name, int i, long l, int j, Name name1)
    {
        U16NameBase(name, 15, i, l, j, "priority", name1, "target");
    }

    public Name getAdditionalName()
    {
        return getNameField();
    }

    Record getObject()
    {
        return new MXRecord();
    }

    public int getPriority()
    {
        return getU16Field();
    }

    public Name getTarget()
    {
        return getNameField();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(u16Field);
        nameField.toWire(dnsoutput, compression, flag);
    }

    private static final long serialVersionUID = 0x99e42ea2L;
}
