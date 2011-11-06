// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNAMERecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleCompressedNameBase, Name, Record

public class CNAMERecord extends SingleCompressedNameBase
{

    CNAMERecord()
    {
    }

    public CNAMERecord(Name name, int i, long l, Name name1)
    {
        super(name, 5, i, l, name1, "alias");
    }

    public Name getAlias()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new CNAMERecord();
    }

    public Name getTarget()
    {
        return getSingleName();
    }

    private static final long serialVersionUID = 0x2f83852cL;
}
