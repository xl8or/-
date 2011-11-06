// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DHCIDRecord.java

package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class DHCIDRecord extends Record
{

    DHCIDRecord()
    {
    }

    public DHCIDRecord(Name name, int i, long l, byte abyte0[])
    {
        super(name, 49, i, l);
        data = abyte0;
    }

    public byte[] getData()
    {
        return data;
    }

    Record getObject()
    {
        return new DHCIDRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        data = tokenizer.getBase64();
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        data = dnsinput.readByteArray();
    }

    String rrToString()
    {
        return base64.toString(data);
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeByteArray(data);
    }

    private static final long serialVersionUID = 0x25139cb5L;
    private byte data[];
}
