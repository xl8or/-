// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AAAARecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;

// Referenced classes of package org.xbill.DNS:
//            Record, Address, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

public class AAAARecord extends Record
{

    AAAARecord()
    {
    }

    public AAAARecord(Name name, int i, long l, InetAddress inetaddress)
    {
        super(name, 28, i, l);
        if(Address.familyOf(inetaddress) != 2)
        {
            throw new IllegalArgumentException("invalid IPv6 address");
        } else
        {
            address = inetaddress;
            return;
        }
    }

    public InetAddress getAddress()
    {
        return address;
    }

    Record getObject()
    {
        return new AAAARecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        address = tokenizer.getAddress(2);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        address = InetAddress.getByAddress(dnsinput.readByteArray(16));
    }

    String rrToString()
    {
        return address.getHostAddress();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeByteArray(address.getAddress());
    }

    private static final long serialVersionUID = 0x79432aaeL;
    private InetAddress address;
}
