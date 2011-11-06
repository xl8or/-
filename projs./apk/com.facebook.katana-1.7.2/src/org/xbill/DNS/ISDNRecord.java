// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISDNRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, TextParseException, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

public class ISDNRecord extends Record
{

    ISDNRecord()
    {
    }

    public ISDNRecord(Name name, int i, long l, String s, String s1)
    {
        Record(name, 20, i, l);
        try
        {
            address = byteArrayFromString(s);
            if(s1 != null)
                subAddress = byteArrayFromString(s1);
            return;
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException(textparseexception.getMessage());
        }
    }

    public String getAddress()
    {
        return byteArrayToString(address, false);
    }

    Record getObject()
    {
        return new ISDNRecord();
    }

    public String getSubAddress()
    {
        String s;
        if(subAddress == null)
            s = null;
        else
            s = byteArrayToString(subAddress, false);
        return s;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        try
        {
            address = byteArrayFromString(tokenizer.getString());
            Tokenizer.Token token = tokenizer.get();
            if(token.isString())
                subAddress = byteArrayFromString(token.value);
            else
                tokenizer.unget();
        }
        catch(TextParseException textparseexception)
        {
            throw tokenizer.exception(textparseexception.getMessage());
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        address = dnsinput.readCountedString();
        if(dnsinput.remaining() > 0)
            subAddress = dnsinput.readCountedString();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(byteArrayToString(address, true));
        if(subAddress != null)
        {
            stringbuffer.append(" ");
            stringbuffer.append(byteArrayToString(subAddress, true));
        }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeCountedString(address);
        if(subAddress != null)
            dnsoutput.writeCountedString(subAddress);
    }

    private static final long serialVersionUID = 0xdfeb4d22L;
    private byte address[];
    private byte subAddress[];
}
