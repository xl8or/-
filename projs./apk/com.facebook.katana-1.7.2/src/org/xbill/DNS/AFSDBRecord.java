// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AFSDBRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            U16NameBase, Name, Record

public class AFSDBRecord extends U16NameBase
{

    AFSDBRecord()
    {
    }

    public AFSDBRecord(Name name, int i, long l, int j, Name name1)
    {
        super(name, 18, i, l, j, "subtype", name1, "host");
    }

    public Name getHost()
    {
        return getNameField();
    }

    Record getObject()
    {
        return new AFSDBRecord();
    }

    public int getSubtype()
    {
        return getU16Field();
    }

    private static final long serialVersionUID = 0x5caebc65L;
}
