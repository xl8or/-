// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PTRRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleCompressedNameBase, Name, Record

public class PTRRecord extends SingleCompressedNameBase
{

    PTRRecord()
    {
    }

    public PTRRecord(Name name, int i, long l, Name name1)
    {
        SingleCompressedNameBase(name, 12, i, l, name1, "target");
    }

    Record getObject()
    {
        return new PTRRecord();
    }

    public Name getTarget()
    {
        return getSingleName();
    }

    private static final long serialVersionUID = 0xbdb4cfb0L;
}
