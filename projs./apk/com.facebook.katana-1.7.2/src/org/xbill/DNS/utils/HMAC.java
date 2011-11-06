// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HMAC.java

package org.xbill.DNS.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HMAC
{

    public HMAC(String s, byte abyte0[])
    {
        try
        {
            digest = MessageDigest.getInstance(s);
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("unknown digest algorithm ").append(s).toString());
        }
        init(abyte0);
    }

    public HMAC(MessageDigest messagedigest, byte abyte0[])
    {
        messagedigest.reset();
        digest = messagedigest;
        init(abyte0);
    }

    private void init(byte abyte0[])
    {
_L2:
        int j;
        for(; j < 64; j++)
        {
            ipad[j] = 54;
            opad[j] = 92;
        }

        digest.update(ipad);
        return;
        byte abyte1[];
        int i;
        if(abyte0.length > 64)
        {
            abyte1 = digest.digest(abyte0);
            digest.reset();
        } else
        {
            abyte1 = abyte0;
        }
        ipad = new byte[64];
        opad = new byte[64];
        for(i = 0; i < abyte1.length; i++)
        {
            ipad[i] = (byte)(0x36 ^ abyte1[i]);
            opad[i] = (byte)(0x5c ^ abyte1[i]);
        }

        j = i;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void clear()
    {
        digest.reset();
        digest.update(ipad);
    }

    public byte[] sign()
    {
        byte abyte0[] = digest.digest();
        digest.reset();
        digest.update(opad);
        return digest.digest(abyte0);
    }

    public void update(byte abyte0[])
    {
        digest.update(abyte0);
    }

    public void update(byte abyte0[], int i, int j)
    {
        digest.update(abyte0, i, j);
    }

    public boolean verify(byte abyte0[])
    {
        return Arrays.equals(abyte0, sign());
    }

    private static final byte IPAD = 54;
    private static final byte OPAD = 92;
    private static final byte PADLEN = 64;
    MessageDigest digest;
    private byte ipad[];
    private byte opad[];
}
