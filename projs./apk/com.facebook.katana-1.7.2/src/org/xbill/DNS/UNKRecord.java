// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UNKRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class UNKRecord extends Record
{

    UNKRecord()
    {
    }

    public byte[] getData()
    {
        return data;
    }

    Record getObject()
    {
        return new UNKRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        throw tokenizer.exception("invalid unknown RR encoding");
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        data = dnsinput.readByteArray();
    }

    String rrToString()
    {
        return unknownToString(data);
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeByteArray(data);
    }

    private static final long serialVersionUID = 0x5865609dL;
    private byte data[];
}
