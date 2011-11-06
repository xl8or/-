// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DNAMERecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class DNAMERecord extends SingleNameBase
{

    DNAMERecord()
    {
    }

    public DNAMERecord(Name name, int i, long l, Name name1)
    {
        super(name, 39, i, l, name1, "alias");
    }

    public Name getAlias()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new DNAMERecord();
    }

    public Name getTarget()
    {
        return getSingleName();
    }

    private static final long serialVersionUID = 0x5526c57aL;
}
