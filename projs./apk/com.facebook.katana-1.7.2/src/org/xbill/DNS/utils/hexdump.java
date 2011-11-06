// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   hexdump.java

package org.xbill.DNS.utils;


public class hexdump
{

    public hexdump()
    {
    }

    public static String dump(String s, byte abyte0[])
    {
        return dump(s, abyte0, 0, abyte0.length);
    }

    public static String dump(String s, byte abyte0[], int i, int j)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append((new StringBuilder()).append(j).append("b").toString());
        if(s != null)
            stringbuffer.append((new StringBuilder()).append(" (").append(s).append(")").toString());
        stringbuffer.append(':');
        int k = -8 & 8 + stringbuffer.toString().length();
        stringbuffer.append('\t');
        int l = (80 - k) / 3;
        for(int i1 = 0; i1 < j; i1++)
        {
            if(i1 != 0 && i1 % l == 0)
            {
                stringbuffer.append('\n');
                for(int k1 = 0; k1 < k / 8; k1++)
                    stringbuffer.append('\t');

            }
            int j1 = 0xff & abyte0[i1 + i];
            stringbuffer.append(hex[j1 >> 4]);
            stringbuffer.append(hex[j1 & 0xf]);
            stringbuffer.append(' ');
        }

        stringbuffer.append('\n');
        return stringbuffer.toString();
    }

    private static final char hex[] = "0123456789ABCDEF".toCharArray();

}
