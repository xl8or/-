// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.util;

import java.util.Arrays;

public final class CharTypes
{

    private CharTypes()
    {
    }

    public static void appendQuoted(StringBuilder stringbuilder, String s)
    {
        int ai[] = sOutputEscapes;
        int i = ai.length;
        int j = 0;
        int k = s.length();
        while(j < k) 
        {
            char c = s.charAt(j);
            if(c >= i || ai[c] == 0)
            {
                stringbuilder.append(c);
            } else
            {
                stringbuilder.append('\\');
                int l = ai[c];
                if(l < 0)
                {
                    stringbuilder.append('u');
                    stringbuilder.append('0');
                    stringbuilder.append('0');
                    int i1 = -(l + 1);
                    stringbuilder.append(HEX_CHARS[i1 >> 4]);
                    stringbuilder.append(HEX_CHARS[i1 & 0xf]);
                } else
                {
                    stringbuilder.append((char)l);
                }
            }
            j++;
        }
    }

    public static int charToHex(int i)
    {
        int j;
        if(i > 127)
            j = -1;
        else
            j = sHexValues[i];
        return j;
    }

    public static final int[] getInputCodeComment()
    {
        return sInputCodesComment;
    }

    public static final int[] getInputCodeLatin1()
    {
        return sInputCodes;
    }

    public static final int[] getInputCodeUtf8()
    {
        return sInputCodesUtf8;
    }

    public static final int[] getOutputEscapes()
    {
        return sOutputEscapes;
    }

    static final char HEX_CHARS[] = "0123456789ABCDEF".toCharArray();
    static final int sHexValues[];
    static final int sInputCodes[];
    static final int sInputCodesComment[];
    static final int sInputCodesUtf8[];
    static final int sOutputEscapes[];

    static 
    {
        int ai[] = new int[256];
        for(int i = 0; i < 32; i++)
            ai[i] = -1;

        ai[34] = 1;
        ai[92] = 1;
        sInputCodes = ai;
        int ai1[] = new int[sInputCodes.length];
        System.arraycopy(sInputCodes, 0, ai1, 0, sInputCodes.length);
        int j = 128;
        while(j < 256) 
        {
            byte byte0;
            if((j & 0xe0) == 192)
                byte0 = 2;
            else
            if((j & 0xf0) == 224)
                byte0 = 3;
            else
            if((j & 0xf8) == 240)
                byte0 = 4;
            else
                byte0 = -1;
            ai1[j] = byte0;
            j++;
        }
        sInputCodesUtf8 = ai1;
        sInputCodesComment = new int[256];
        System.arraycopy(sInputCodesUtf8, 128, sInputCodesComment, 128, 128);
        Arrays.fill(sInputCodesComment, 0, 32, -1);
        sInputCodesComment[9] = 0;
        sInputCodesComment[10] = 10;
        sInputCodesComment[13] = 13;
        sInputCodesComment[42] = 42;
        int ai2[] = new int[256];
        for(int k = 0; k < 32; k++)
            ai2[k] = -(k + 1);

        ai2[34] = 34;
        ai2[92] = 92;
        ai2[8] = 98;
        ai2[9] = 116;
        ai2[12] = 102;
        ai2[10] = 110;
        ai2[13] = 114;
        sOutputEscapes = ai2;
        sHexValues = new int[128];
        Arrays.fill(sHexValues, -1);
        for(int l = 0; l < 10; l++)
            sHexValues[l + 48] = l;

        for(int i1 = 0; i1 < 6; i1++)
        {
            sHexValues[i1 + 97] = i1 + 10;
            sHexValues[i1 + 65] = i1 + 10;
        }

    }
}
