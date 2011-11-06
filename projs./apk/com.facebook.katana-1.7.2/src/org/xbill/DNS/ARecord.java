// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ARecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

// Referenced classes of package org.xbill.DNS:
//            Record, Address, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

public class ARecord extends Record
{

    ARecord()
    {
    }

    public ARecord(Name name, int i, long l, InetAddress inetaddress)
    {
        super(name, 1, i, l);
        if(Address.familyOf(inetaddress) != 1)
        {
            throw new IllegalArgumentException("invalid IPv4 address");
        } else
        {
            addr = fromArray(inetaddress.getAddress());
            return;
        }
    }

    private static final int fromArray(byte abyte0[])
    {
        return (0xff & abyte0[0]) << 24 | (0xff & abyte0[1]) << 16 | (0xff & abyte0[2]) << 8 | 0xff & abyte0[3];
    }

    private static final byte[] toArray(int i)
    {
        byte abyte0[] = new byte[4];
        abyte0[0] = (byte)(0xff & i >>> 24);
        abyte0[1] = (byte)(0xff & i >>> 16);
        abyte0[2] = (byte)(0xff & i >>> 8);
        abyte0[3] = (byte)(i & 0xff);
        return abyte0;
    }

    public InetAddress getAddress()
    {
        InetAddress inetaddress1 = InetAddress.getByAddress(toArray(addr));
        InetAddress inetaddress = inetaddress1;
_L2:
        return inetaddress;
        UnknownHostException unknownhostexception;
        unknownhostexception;
        inetaddress = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    Record getObject()
    {
        return new ARecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        addr = fromArray(tokenizer.getAddress(1).getAddress());
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        addr = fromArray(dnsinput.readByteArray(4));
    }

    String rrToString()
    {
        return Address.toDottedQuad(toArray(addr));
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU32(0xffffffffL & (long)addr);
    }

    private static final long serialVersionUID = 0x5ba43dcdL;
    private int addr;
}
