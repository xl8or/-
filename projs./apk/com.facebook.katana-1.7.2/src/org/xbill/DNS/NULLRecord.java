// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NULLRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class NULLRecord extends Record
{

    NULLRecord()
    {
    }

    public NULLRecord(Name name, int i, long l, byte abyte0[])
    {
        Record(name, 10, i, l);
        if(abyte0.length > 65535)
        {
            throw new IllegalArgumentException("data must be <65536 bytes");
        } else
        {
            data = abyte0;
            return;
        }
    }

    public byte[] getData()
    {
        return data;
    }

    Record getObject()
    {
        return new NULLRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        throw tokenizer.exception("no defined text format for NULL records");
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

    private static final long serialVersionUID = 0x364aab66L;
    private byte data[];
}
