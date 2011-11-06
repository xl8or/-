// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PXRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, Name, 
//            DNSOutput, Compression

public class PXRecord extends Record
{

    PXRecord()
    {
    }

    public PXRecord(Name name, int i, long l, int j, Name name1, Name name2)
    {
        Record(name, 26, i, l);
        preference = checkU16("preference", j);
        map822 = checkName("map822", name1);
        mapX400 = checkName("mapX400", name2);
    }

    public Name getMap822()
    {
        return map822;
    }

    public Name getMapX400()
    {
        return mapX400;
    }

    Record getObject()
    {
        return new PXRecord();
    }

    public int getPreference()
    {
        return preference;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        preference = tokenizer.getUInt16();
        map822 = tokenizer.getName(name);
        mapX400 = tokenizer.getName(name);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        preference = dnsinput.readU16();
        map822 = new Name(dnsinput);
        mapX400 = new Name(dnsinput);
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(preference);
        stringbuffer.append(" ");
        stringbuffer.append(map822);
        stringbuffer.append(" ");
        stringbuffer.append(mapX400);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(preference);
        map822.toWire(dnsoutput, null, flag);
        mapX400.toWire(dnsoutput, null, flag);
    }

    private static final long serialVersionUID = 0x41543a3bL;
    private Name map822;
    private Name mapX400;
    private int preference;
}
