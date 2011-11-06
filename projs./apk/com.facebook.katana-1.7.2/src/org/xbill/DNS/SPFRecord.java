// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SPFRecord.java

package org.xbill.DNS;

import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            TXTBase, Name, Record

public class SPFRecord extends TXTBase
{

    SPFRecord()
    {
    }

    public SPFRecord(Name name, int i, long l, String s)
    {
        TXTBase(name, 99, i, l, s);
    }

    public SPFRecord(Name name, int i, long l, List list)
    {
        TXTBase(name, 99, i, l, list);
    }

    Record getObject()
    {
        return new SPFRecord();
    }

    public volatile List getStrings()
    {
        return getStrings();
    }

    public volatile List getStringsAsByteArrays()
    {
        return getStringsAsByteArrays();
    }

    private static final long serialVersionUID = 0xe0e6c89eL;
}
