// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NSAPRecord.java

package org.xbill.DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class NSAPRecord extends Record
{

    NSAPRecord()
    {
    }

    public NSAPRecord(Name name, int i, long l, String s)
    {
        Record(name, 22, i, l);
        address = checkAndConvertAddress(s);
        if(address == null)
            throw new IllegalArgumentException((new StringBuilder()).append("invalid NSAP address ").append(s).toString());
        else
            return;
    }

    private static final byte[] checkAndConvertAddress(String s)
    {
        if(s.substring(0, 2).equalsIgnoreCase("0x")) goto _L2; else goto _L1
_L1:
        byte abyte0[] = null;
_L4:
        return abyte0;
_L2:
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        int i = 2;
        boolean flag = false;
        int j = 0;
        while(i < s.length()) 
        {
            char c = s.charAt(i);
            if(c != '.')
            {
                int k = Character.digit(c, 16);
                if(k == -1)
                {
                    abyte0 = null;
                    continue; /* Loop/switch isn't completed */
                }
                if(flag)
                {
                    j += k;
                    bytearrayoutputstream.write(j);
                    flag = false;
                } else
                {
                    j = k << 4;
                    flag = true;
                }
            }
            i++;
        }
        if(flag)
            abyte0 = null;
        else
            abyte0 = bytearrayoutputstream.toByteArray();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String getAddress()
    {
        return byteArrayToString(address, false);
    }

    Record getObject()
    {
        return new NSAPRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        String s = tokenizer.getString();
        address = checkAndConvertAddress(s);
        if(address == null)
            throw tokenizer.exception((new StringBuilder()).append("invalid NSAP address ").append(s).toString());
        else
            return;
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        address = dnsinput.readByteArray();
    }

    String rrToString()
    {
        return (new StringBuilder()).append("0x").append(base16.toString(address)).toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeByteArray(address);
    }

    private static final long serialVersionUID = 0xd6c851fL;
    private byte address[];
}
