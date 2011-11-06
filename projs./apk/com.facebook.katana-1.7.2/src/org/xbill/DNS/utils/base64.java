// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   base64.java

package org.xbill.DNS.utils;

import java.io.*;

public class base64
{

    private base64()
    {
    }

    public static String formatString(byte abyte0[], int i, String s, boolean flag)
    {
        String s1 = toString(abyte0);
        StringBuffer stringbuffer = new StringBuffer();
        int j = 0;
        while(j < s1.length()) 
        {
            stringbuffer.append(s);
            if(j + i >= s1.length())
            {
                stringbuffer.append(s1.substring(j));
                if(flag)
                    stringbuffer.append(" )");
            } else
            {
                stringbuffer.append(s1.substring(j, j + i));
                stringbuffer.append("\n");
            }
            j += i;
        }
        return stringbuffer.toString();
    }

    public static byte[] fromString(String s)
    {
        ByteArrayOutputStream bytearrayoutputstream;
        byte abyte1[];
        bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = s.getBytes();
        for(int i = 0; i < abyte0.length; i++)
            if(!Character.isWhitespace((char)abyte0[i]))
                bytearrayoutputstream.write(abyte0[i]);

        abyte1 = bytearrayoutputstream.toByteArray();
        if(abyte1.length % 4 == 0) goto _L2; else goto _L1
_L1:
        byte abyte2[] = null;
_L9:
        return abyte2;
_L2:
        DataOutputStream dataoutputstream;
        int j;
        bytearrayoutputstream.reset();
        dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        j = 0;
_L7:
        if(j >= (3 + abyte1.length) / 4) goto _L4; else goto _L3
_L3:
        short aword1[];
        int l;
        short aword0[] = new short[4];
        aword1 = new short[3];
        for(int k = 0; k < 4; k++)
            aword0[k] = (short)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(abyte1[k + j * 4]);

        aword1[0] = (short)((aword0[0] << 2) + (aword0[1] >> 4));
        if(aword0[2] == 64)
        {
            aword1[2] = -1;
            aword1[1] = -1;
            if((0xf & aword0[1]) != 0)
            {
                abyte2 = null;
                continue; /* Loop/switch isn't completed */
            }
        } else
        if(aword0[3] == 64)
        {
            aword1[1] = (short)(0xff & (aword0[1] << 4) + (aword0[2] >> 2));
            aword1[2] = -1;
            if((3 & aword0[2]) != 0)
            {
                abyte2 = null;
                continue; /* Loop/switch isn't completed */
            }
        } else
        {
            aword1[1] = (short)(0xff & (aword0[1] << 4) + (aword0[2] >> 2));
            aword1[2] = (short)(0xff & (aword0[2] << 6) + aword0[3]);
        }
        l = 0;
_L6:
        if(l >= 3)
            continue; /* Loop/switch isn't completed */
        if(aword1[l] >= 0)
            dataoutputstream.writeByte(aword1[l]);
        l++;
        if(true) goto _L6; else goto _L5
_L5:
        IOException ioexception;
        ioexception;
        j++;
          goto _L7
_L4:
        abyte2 = bytearrayoutputstream.toByteArray();
        if(true) goto _L9; else goto _L8
_L8:
    }

    public static String toString(byte abyte0[])
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        short aword0[];
        short aword1[];
        for(int i = 0; i < (2 + abyte0.length) / 3; i++)
        {
            aword0 = new short[3];
            aword1 = new short[4];
            int j = 0;
            while(j < 3) 
            {
                if(j + i * 3 < abyte0.length)
                    aword0[j] = (short)(0xff & abyte0[j + i * 3]);
                else
                    aword0[j] = -1;
                j++;
            }
            aword1[0] = (short)(aword0[0] >> 2);
            int k;
            if(aword0[1] == -1)
                aword1[1] = (short)((3 & aword0[0]) << 4);
            else
                aword1[1] = (short)(((3 & aword0[0]) << 4) + (aword0[1] >> 4));
            if(aword0[1] == -1)
            {
                aword1[3] = 64;
                aword1[2] = 64;
            } else
            if(aword0[2] == -1)
            {
                aword1[2] = (short)((0xf & aword0[1]) << 2);
                aword1[3] = 64;
            } else
            {
                aword1[2] = (short)(((0xf & aword0[1]) << 2) + (aword0[2] >> 6));
                aword1[3] = (short)(0x3f & aword0[2]);
            }
            for(k = 0; k < 4; k++)
                bytearrayoutputstream.write("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(aword1[k]));

        }

        return new String(bytearrayoutputstream.toByteArray());
    }

    private static final String Base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
}
