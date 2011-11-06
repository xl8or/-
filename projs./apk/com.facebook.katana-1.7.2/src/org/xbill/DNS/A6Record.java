// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   A6Record.java

package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

// Referenced classes of package org.xbill.DNS:
//            Record, Address, Tokenizer, DNSInput, 
//            Name, DNSOutput, Compression

public class A6Record extends Record
{

    A6Record()
    {
    }

    public A6Record(Name name, int i, long l, int j, InetAddress inetaddress, Name name1)
    {
        super(name, 38, i, l);
        prefixBits = checkU8("prefixBits", j);
        if(inetaddress != null && Address.familyOf(inetaddress) != 2)
            throw new IllegalArgumentException("invalid IPv6 address");
        suffix = inetaddress;
        if(name1 != null)
            prefix = checkName("prefix", name1);
    }

    Record getObject()
    {
        return new A6Record();
    }

    public Name getPrefix()
    {
        return prefix;
    }

    public int getPrefixBits()
    {
        return prefixBits;
    }

    public InetAddress getSuffix()
    {
        return suffix;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        prefixBits = tokenizer.getUInt8();
        if(prefixBits > 128)
            throw tokenizer.exception("prefix bits must be [0..128]");
        if(prefixBits < 128)
        {
            String s = tokenizer.getString();
            try
            {
                suffix = Address.getByAddress(s, 2);
            }
            catch(UnknownHostException unknownhostexception)
            {
                throw tokenizer.exception((new StringBuilder()).append("invalid IPv6 address: ").append(s).toString());
            }
        }
        if(prefixBits > 0)
            prefix = tokenizer.getName(name);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        prefixBits = dnsinput.readU8();
        int i = (7 + (128 - prefixBits)) / 8;
        if(prefixBits < 128)
        {
            byte abyte0[] = new byte[16];
            dnsinput.readByteArray(abyte0, 16 - i, i);
            suffix = InetAddress.getByAddress(abyte0);
        }
        if(prefixBits > 0)
            prefix = new Name(dnsinput);
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(prefixBits);
        if(suffix != null)
        {
            stringbuffer.append(" ");
            stringbuffer.append(suffix.getHostAddress());
        }
        if(prefix != null)
        {
            stringbuffer.append(" ");
            stringbuffer.append(prefix);
        }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU8(prefixBits);
        if(suffix != null)
        {
            int i = (7 + (128 - prefixBits)) / 8;
            dnsoutput.writeByteArray(suffix.getAddress(), 16 - i, i);
        }
        if(prefix != null)
            prefix.toWire(dnsoutput, null, flag);
    }

    private static final long serialVersionUID = 0xfebd791bL;
    private Name prefix;
    private int prefixBits;
    private InetAddress suffix;
}
