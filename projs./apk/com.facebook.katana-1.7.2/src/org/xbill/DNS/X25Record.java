// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   X25Record.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class X25Record extends Record
{

    X25Record()
    {
    }

    public X25Record(Name name, int i, long l, String s)
    {
        super(name, 19, i, l);
        address = checkAndConvertAddress(s);
        if(address == null)
            throw new IllegalArgumentException((new StringBuilder()).append("invalid PSDN address ").append(s).toString());
        else
            return;
    }

    private static final byte[] checkAndConvertAddress(String s)
    {
        int i;
        byte abyte0[];
        int j;
        i = s.length();
        abyte0 = new byte[i];
        j = 0;
_L3:
        char c;
        if(j >= i)
            break MISSING_BLOCK_LABEL_49;
        c = s.charAt(j);
        if(Character.isDigit(c)) goto _L2; else goto _L1
_L1:
        byte abyte1[] = null;
_L4:
        return abyte1;
_L2:
        abyte0[j] = (byte)c;
        j++;
          goto _L3
        abyte1 = abyte0;
          goto _L4
    }

    public String getAddress()
    {
        return byteArrayToString(address, false);
    }

    Record getObject()
    {
        return new X25Record();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        String s = tokenizer.getString();
        address = checkAndConvertAddress(s);
        if(address == null)
            throw tokenizer.exception((new StringBuilder()).append("invalid PSDN address ").append(s).toString());
        else
            return;
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        address = dnsinput.readCountedString();
    }

    String rrToString()
    {
        return byteArrayToString(address, true);
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeCountedString(address);
    }

    private static final long serialVersionUID = 0xac5ae274L;
    private byte address[];
}
