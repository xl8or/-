// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReverseMap.java

package org.xbill.DNS;

import java.net.InetAddress;
import java.net.UnknownHostException;

// Referenced classes of package org.xbill.DNS:
//            Name, Address, TextParseException

public final class ReverseMap
{

    private ReverseMap()
    {
    }

    public static Name fromAddress(String s)
        throws UnknownHostException
    {
        byte abyte0[] = Address.toByteArray(s, 1);
        if(abyte0 == null)
            abyte0 = Address.toByteArray(s, 2);
        if(abyte0 == null)
            throw new UnknownHostException("Invalid IP address");
        else
            return fromAddress(abyte0);
    }

    public static Name fromAddress(String s, int i)
        throws UnknownHostException
    {
        byte abyte0[] = Address.toByteArray(s, i);
        if(abyte0 == null)
            throw new UnknownHostException("Invalid IP address");
        else
            return fromAddress(abyte0);
    }

    public static Name fromAddress(InetAddress inetaddress)
    {
        return fromAddress(inetaddress.getAddress());
    }

    public static Name fromAddress(byte abyte0[])
    {
        Name name1;
label0:
        {
            if(abyte0.length != 4 && abyte0.length != 16)
                throw new IllegalArgumentException("array must contain 4 or 16 elements");
            StringBuffer stringbuffer = new StringBuffer();
            if(abyte0.length == 4)
            {
                for(int k = abyte0.length - 1; k >= 0; k--)
                {
                    stringbuffer.append(0xff & abyte0[k]);
                    if(k > 0)
                        stringbuffer.append(".");
                }

            } else
            {
                int ai[] = new int[2];
                for(int i = abyte0.length - 1; i >= 0; i--)
                {
                    ai[0] = (0xff & abyte0[i]) >> 4;
                    ai[1] = 0xf & (0xff & abyte0[i]);
                    for(int j = ai.length - 1; j >= 0; j--)
                    {
                        stringbuffer.append(Integer.toHexString(ai[j]));
                        if(i > 0 || j > 0)
                            stringbuffer.append(".");
                    }

                }

            }
            Name name;
            try
            {
                if(abyte0.length == 4)
                {
                    name1 = Name.fromString(stringbuffer.toString(), inaddr4);
                    break label0;
                }
                name = Name.fromString(stringbuffer.toString(), inaddr6);
            }
            catch(TextParseException textparseexception)
            {
                throw new IllegalStateException("name cannot be invalid");
            }
            name1 = name;
        }
        return name1;
    }

    public static Name fromAddress(int ai[])
    {
        byte abyte0[] = new byte[ai.length];
        for(int i = 0; i < ai.length; i++)
        {
            if(ai[i] < 0 || ai[i] > 255)
                throw new IllegalArgumentException("array must contain values between 0 and 255");
            abyte0[i] = (byte)ai[i];
        }

        return fromAddress(abyte0);
    }

    private static Name inaddr4 = Name.fromConstantString("in-addr.arpa.");
    private static Name inaddr6 = Name.fromConstantString("ip6.arpa.");

}
