// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Serial.java

package org.xbill.DNS;


public final class Serial
{

    private Serial()
    {
    }

    public static int compare(long l, long l1)
    {
        long l2;
        if(l < 0L || l > 0xffffffffL)
            throw new IllegalArgumentException((new StringBuilder()).append(l).append(" out of range").toString());
        if(l1 < 0L || l1 > 0xffffffffL)
            throw new IllegalArgumentException((new StringBuilder()).append(l1).append(" out of range").toString());
        l2 = l - l1;
        if(l2 < 0xffffffffL) goto _L2; else goto _L1
_L1:
        l2 -= 0x0L;
_L4:
        return (int)l2;
_L2:
        if(l2 < 0x1L)
            l2 += 0x0L;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static long increment(long l)
    {
        if(l < 0L || l > 0xffffffffL)
            throw new IllegalArgumentException((new StringBuilder()).append(l).append(" out of range").toString());
        long l1;
        if(l == 0xffffffffL)
            l1 = 0L;
        else
            l1 = 1L + l;
        return l1;
    }

    private static final long MAX32 = 0xffffffffL;
}
