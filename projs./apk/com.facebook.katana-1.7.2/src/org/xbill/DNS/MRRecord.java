// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, Record

public class MRRecord extends SingleNameBase
{

    MRRecord()
    {
    }

    public MRRecord(Name name, int i, long l, Name name1)
    {
        SingleNameBase(name, 9, i, l, name1, "new name");
    }

    public Name getNewName()
    {
        return getSingleName();
    }

    Record getObject()
    {
        return new MRRecord();
    }

    private static final long serialVersionUID = 0xafd66693L;
}
