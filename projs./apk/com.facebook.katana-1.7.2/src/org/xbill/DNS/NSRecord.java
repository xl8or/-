// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NSRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleCompressedNameBase, Name, Record

public class NSRecord extends SingleCompressedNameBase
{

    NSRecord()
    {
    }

    public NSRecord(Name name, int i, long l, Name name1)
    {
        SingleCompressedNameBase(name, 2, i, l, name1, "target");
    }

    public Name getAdditionalName()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new NSRecord();
    }

    public Name getTarget()
    {
        return getSingleName();
    }

    private static final long serialVersionUID = 0x2fca0ca6L;
}
