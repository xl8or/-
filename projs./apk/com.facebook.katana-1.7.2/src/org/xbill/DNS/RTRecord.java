// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            U16NameBase, Name, Record

public class RTRecord extends U16NameBase
{

    RTRecord()
    {
    }

    public RTRecord(Name name, int i, long l, int j, Name name1)
    {
        U16NameBase(name, 21, i, l, j, "preference", name1, "intermediateHost");
    }

    public Name getIntermediateHost()
    {
        return getNameField();
    }

    Record getObject()
    {
        return new RTRecord();
    }

    public int getPreference()
    {
        return getU16Field();
    }

    private static final long serialVersionUID = 0x2234f1aeL;
}
