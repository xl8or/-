// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingleCompressedNameBase.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            SingleNameBase, Name, DNSOutput, Compression

abstract class SingleCompressedNameBase extends SingleNameBase
{

    protected SingleCompressedNameBase()
    {
    }

    protected SingleCompressedNameBase(Name name, int i, int j, long l, Name name1, String s)
    {
        SingleNameBase(name, i, j, l, name1, s);
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        singleName.toWire(dnsoutput, compression, flag);
    }

    private static final long serialVersionUID = 0x607c66bbL;
}
