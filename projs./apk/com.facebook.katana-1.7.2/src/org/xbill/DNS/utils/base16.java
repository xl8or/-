// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   base16.java

package org.xbill.DNS.utils;

import java.io.*;

public class base16
{

    private base16()
    {
    }

    public static byte[] fromString(String s)
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = s.getBytes();
        for(int i = 0; i < abyte0.length; i++)
            if(!Character.isWhitespace((char)abyte0[i]))
                bytearrayoutputstream.write(abyte0[i]);

        byte abyte1[] = bytearrayoutputstream.toByteArray();
        byte abyte2[];
        if(abyte1.length % 2 != 0)
        {
            abyte2 = null;
        } else
        {
            bytearrayoutputstream.reset();
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
            int j = 0;
            while(j < abyte1.length) 
            {
                byte byte0 = (byte)"0123456789ABCDEF".indexOf(Character.toUpperCase((char)abyte1[j]));
                int k = (byte)"0123456789ABCDEF".indexOf(Character.toUpperCase((char)abyte1[j + 1])) + (byte0 << 4);
                try
                {
                    dataoutputstream.writeByte(k);
                }
                catch(IOException ioexception) { }
                j += 2;
            }
            abyte2 = bytearrayoutputstream.toByteArray();
        }
        return abyte2;
    }

    public static String toString(byte abyte0[])
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        for(int i = 0; i < abyte0.length; i++)
        {
            short word0 = (short)(0xff & abyte0[i]);
            byte byte0 = (byte)(word0 >> 4);
            byte byte1 = (byte)(word0 & 0xf);
            bytearrayoutputstream.write("0123456789ABCDEF".charAt(byte0));
            bytearrayoutputstream.write("0123456789ABCDEF".charAt(byte1));
        }

        return new String(bytearrayoutputstream.toByteArray());
    }

    private static final String Base16 = "0123456789ABCDEF";
}
