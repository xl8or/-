// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RRSIGRecord.java

package org.xbill.DNS;

import java.util.Date;

// Referenced classes of package org.xbill.DNS:
//            SIGBase, Name, Record

public class RRSIGRecord extends SIGBase
{

    RRSIGRecord()
    {
    }

    public RRSIGRecord(Name name, int i, long l, int j, int k, long l1, Date date, Date date1, int i1, Name name1, byte abyte0[])
    {
        SIGBase(name, 46, i, l, j, k, l1, date, date1, i1, name1, abyte0);
    }

    public volatile int getAlgorithm()
    {
        return getAlgorithm();
    }

    public volatile Date getExpire()
    {
        return getExpire();
    }

    public volatile int getFootprint()
    {
        return getFootprint();
    }

    public volatile int getLabels()
    {
        return getLabels();
    }

    Record getObject()
    {
        return new RRSIGRecord();
    }

    public volatile long getOrigTTL()
    {
        return getOrigTTL();
    }

    public volatile byte[] getSignature()
    {
        return getSignature();
    }

    public volatile Name getSigner()
    {
        return getSigner();
    }

    public volatile Date getTimeSigned()
    {
        return getTimeSigned();
    }

    public volatile int getTypeCovered()
    {
        return getTypeCovered();
    }

    private static final long serialVersionUID = 0x4a57a9b3L;
}
