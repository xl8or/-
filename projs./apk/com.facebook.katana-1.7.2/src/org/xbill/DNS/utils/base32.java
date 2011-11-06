// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   base32.java

package org.xbill.DNS.utils;

import java.io.*;

public class base32
{
    public static class Alphabet
    {

        public static final String BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=";
        public static final String BASE32HEX = "0123456789ABCDEFGHIJKLMNOPQRSTUV=";

        private Alphabet()
        {
        }
    }


    public base32(String s, boolean flag, boolean flag1)
    {
        alphabet = s;
        padding = flag;
        lowercase = flag1;
    }

    private static int blockLenToPadding(int i)
    {
        i;
        JVM INSTR tableswitch 1 5: default 36
    //                   1 41
    //                   2 47
    //                   3 52
    //                   4 57
    //                   5 62;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        byte byte0 = -1;
_L8:
        return byte0;
_L2:
        byte0 = 6;
        continue; /* Loop/switch isn't completed */
_L3:
        byte0 = 4;
        continue; /* Loop/switch isn't completed */
_L4:
        byte0 = 3;
        continue; /* Loop/switch isn't completed */
_L5:
        byte0 = 1;
        continue; /* Loop/switch isn't completed */
_L6:
        byte0 = 0;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private static int paddingToBlockLen(int i)
    {
        i;
        JVM INSTR tableswitch 0 6: default 44
    //                   0 69
    //                   1 64
    //                   2 44
    //                   3 59
    //                   4 54
    //                   5 44
    //                   6 49;
           goto _L1 _L2 _L3 _L1 _L4 _L5 _L1 _L6
_L1:
        byte byte0 = -1;
_L8:
        return byte0;
_L6:
        byte0 = 1;
        continue; /* Loop/switch isn't completed */
_L5:
        byte0 = 2;
        continue; /* Loop/switch isn't completed */
_L4:
        byte0 = 3;
        continue; /* Loop/switch isn't completed */
_L3:
        byte0 = 4;
        continue; /* Loop/switch isn't completed */
_L2:
        byte0 = 5;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public byte[] fromString(String s)
    {
        ByteArrayOutputStream bytearrayoutputstream;
        bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = s.getBytes();
        for(int i = 0; i < abyte0.length; i++)
        {
            char c = (char)abyte0[i];
            if(!Character.isWhitespace(c))
                bytearrayoutputstream.write((byte)Character.toUpperCase(c));
        }

        if(!padding) goto _L2; else goto _L1
_L1:
        if(bytearrayoutputstream.size() % 8 == 0) goto _L4; else goto _L3
_L3:
        byte abyte2[] = null;
_L11:
        return abyte2;
_L2:
        for(; bytearrayoutputstream.size() % 8 != 0; bytearrayoutputstream.write(61));
_L4:
        byte abyte1[];
        DataOutputStream dataoutputstream;
        int j;
        abyte1 = bytearrayoutputstream.toByteArray();
        bytearrayoutputstream.reset();
        dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        j = 0;
_L9:
        if(j >= abyte1.length / 8) goto _L6; else goto _L5
_L5:
        int ai[];
        int i1;
        int j1;
        short aword0[] = new short[8];
        ai = new int[5];
        int k = 8;
        int l = 0;
        do
        {
            if(l >= 8 || (char)abyte1[l + j * 8] == '=')
            {
                i1 = paddingToBlockLen(k);
                if(i1 >= 0)
                    break;
                abyte2 = null;
                continue; /* Loop/switch isn't completed */
            }
            aword0[l] = (short)alphabet.indexOf(abyte1[l + j * 8]);
            if(aword0[l] < 0)
            {
                abyte2 = null;
                continue; /* Loop/switch isn't completed */
            }
            k--;
            l++;
        } while(true);
        ai[0] = aword0[0] << 3 | aword0[1] >> 2;
        ai[1] = (3 & aword0[1]) << 6 | aword0[2] << 1 | aword0[3] >> 4;
        ai[2] = (0xf & aword0[3]) << 4 | 0xf & aword0[4] >> 1;
        ai[3] = aword0[4] << 7 | aword0[5] << 2 | aword0[6] >> 3;
        ai[4] = (7 & aword0[6]) << 5 | aword0[7];
        j1 = 0;
_L8:
        if(j1 >= i1)
            continue; /* Loop/switch isn't completed */
        dataoutputstream.writeByte((byte)(0xff & ai[j1]));
        j1++;
        if(true) goto _L8; else goto _L7
_L7:
        IOException ioexception;
        ioexception;
        j++;
          goto _L9
_L6:
        abyte2 = bytearrayoutputstream.toByteArray();
        if(true) goto _L11; else goto _L10
_L10:
    }

    public String toString(byte abyte0[])
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        for(int i = 0; i < (4 + abyte0.length) / 5; i++)
        {
            short aword0[] = new short[5];
            int ai[] = new int[8];
            int j = 0;
            int k = 5;
            while(j < 5) 
            {
                if(j + i * 5 < abyte0.length)
                {
                    aword0[j] = (short)(0xff & abyte0[j + i * 5]);
                } else
                {
                    aword0[j] = 0;
                    k--;
                }
                j++;
            }
            int l = blockLenToPadding(k);
            ai[0] = (byte)(0x1f & aword0[0] >> 3);
            ai[1] = (byte)((7 & aword0[0]) << 2 | 3 & aword0[1] >> 6);
            ai[2] = (byte)(0x1f & aword0[1] >> 1);
            ai[3] = (byte)((1 & aword0[1]) << 4 | 0xf & aword0[2] >> 4);
            ai[4] = (byte)((0xf & aword0[2]) << 1 | 1 & aword0[3] >> 7);
            ai[5] = (byte)(0x1f & aword0[3] >> 2);
            ai[6] = (byte)((3 & aword0[3]) << 3 | 7 & aword0[4] >> 5);
            ai[7] = (byte)(0x1f & aword0[4]);
            for(int i1 = 0; i1 < ai.length - l; i1++)
            {
                char c = alphabet.charAt(ai[i1]);
                if(lowercase)
                    c = Character.toLowerCase(c);
                bytearrayoutputstream.write(c);
            }

            if(!padding)
                continue;
            for(int j1 = ai.length - l; j1 < ai.length; j1++)
                bytearrayoutputstream.write(61);

        }

        return new String(bytearrayoutputstream.toByteArray());
    }

    private String alphabet;
    private boolean lowercase;
    private boolean padding;
}
