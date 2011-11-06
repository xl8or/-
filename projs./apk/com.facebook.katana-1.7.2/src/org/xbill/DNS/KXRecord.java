// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KXRecord.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            U16NameBase, Name, Record

public class KXRecord extends U16NameBase
{

    KXRecord()
    {
    }

    public KXRecord(Name name, int i, long l, int j, Name name1)
    {
        U16NameBase(name, 36, i, l, j, "preference", name1, "target");
    }

    public Name getAdditionalName()
    {
        return getNameField();
    }

    Record getObject()
    {
        return new KXRecord();
    }

    public int getPreference()
    {
        return getU16Field();
    }

    public Name getTarget()
    {
        return getNameField();
    }

    private static final long serialVersionUID = 0xa2499271L;
}
