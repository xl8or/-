// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HINFORecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, TextParseException, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

public class HINFORecord extends Record
{

    HINFORecord()
    {
    }

    public HINFORecord(Name name, int i, long l, String s, String s1)
    {
        Record(name, 13, i, l);
        try
        {
            cpu = byteArrayFromString(s);
            os = byteArrayFromString(s1);
            return;
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException(textparseexception.getMessage());
        }
    }

    public String getCPU()
    {
        return byteArrayToString(cpu, false);
    }

    public String getOS()
    {
        return byteArrayToString(os, false);
    }

    Record getObject()
    {
        return new HINFORecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        try
        {
            cpu = byteArrayFromString(tokenizer.getString());
            os = byteArrayFromString(tokenizer.getString());
            return;
        }
        catch(TextParseException textparseexception)
        {
            throw tokenizer.exception(textparseexception.getMessage());
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        cpu = dnsinput.readCountedString();
        os = dnsinput.readCountedString();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(byteArrayToString(cpu, true));
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(os, true));
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeCountedString(cpu);
        dnsoutput.writeCountedString(os);
    }

    private static final long serialVersionUID = 0xaa7f8b30L;
    private byte cpu[];
    private byte os[];
}
