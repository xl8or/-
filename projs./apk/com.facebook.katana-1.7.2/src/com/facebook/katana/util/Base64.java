// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64.java

package com.facebook.katana.util;


public class Base64
{

    public Base64()
    {
    }

    private static String encodeInt(int i, int j, int k)
    {
        StringBuilder stringbuilder = new StringBuilder(4);
        int l = 0;
        while(l < 4) 
        {
            if(l > k)
            {
                if(!isNoPadding(j))
                    stringbuilder.append('=');
            } else
            {
                int i1 = 6 * (3 - l);
                int j1 = (i & 63 << i1) >> i1;
                if(j1 == 62 && isUrlSafe(j))
                    stringbuilder.append('-');
                else
                if(j1 == 63 && isUrlSafe(j))
                    stringbuilder.append('_');
                else
                    stringbuilder.append(INDEX[j1]);
            }
            l++;
        }
        return stringbuilder.toString();
    }

    public static String encodeToString(byte abyte0[], int i)
    {
        String s;
        if(abyte0 == null)
        {
            s = "";
        } else
        {
            int j = 0;
            StringBuilder stringbuilder = new StringBuilder();
            for(int k = 0; k < abyte0.length; k++)
            {
                j += (0xff & abyte0[k]) << 8 * (2 - k % 3);
                if(k % 3 == 2)
                {
                    stringbuilder.append(encodeInt(j, i, 3));
                    j = 0;
                }
            }

            if(abyte0.length % 3 != 0)
                stringbuilder.append(encodeInt(j, i, abyte0.length % 3));
            s = stringbuilder.toString();
        }
        return s;
    }

    private static boolean isNoPadding(int i)
    {
        boolean flag;
        if((i & 1) == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isUrlSafe(int i)
    {
        boolean flag;
        if((i & 2) == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static final int DEFAULT = 0;
    private static final char INDEX[];
    public static final int NO_PADDING = 1;
    private static final char PAD = 61;
    private static final char URL62 = 45;
    private static final char URL63 = 95;
    public static final int URL_SAFE = 2;

    static 
    {
        char ac[] = new char[64];
        ac[0] = 'A';
        ac[1] = 'B';
        ac[2] = 'C';
        ac[3] = 'D';
        ac[4] = 'E';
        ac[5] = 'F';
        ac[6] = 'G';
        ac[7] = 'H';
        ac[8] = 'I';
        ac[9] = 'J';
        ac[10] = 'K';
        ac[11] = 'L';
        ac[12] = 'M';
        ac[13] = 'N';
        ac[14] = 'O';
        ac[15] = 'P';
        ac[16] = 'Q';
        ac[17] = 'R';
        ac[18] = 'S';
        ac[19] = 'T';
        ac[20] = 'U';
        ac[21] = 'V';
        ac[22] = 'W';
        ac[23] = 'X';
        ac[24] = 'Y';
        ac[25] = 'Z';
        ac[26] = 'a';
        ac[27] = 'b';
        ac[28] = 'c';
        ac[29] = 'd';
        ac[30] = 'e';
        ac[31] = 'f';
        ac[32] = 'g';
        ac[33] = 'h';
        ac[34] = 'i';
        ac[35] = 'j';
        ac[36] = 'k';
        ac[37] = 'l';
        ac[38] = 'm';
        ac[39] = 'n';
        ac[40] = 'o';
        ac[41] = 'p';
        ac[42] = 'q';
        ac[43] = 'r';
        ac[44] = 's';
        ac[45] = 't';
        ac[46] = 'u';
        ac[47] = 'v';
        ac[48] = 'w';
        ac[49] = 'x';
        ac[50] = 'y';
        ac[51] = 'z';
        ac[52] = '0';
        ac[53] = '1';
        ac[54] = '2';
        ac[55] = '3';
        ac[56] = '4';
        ac[57] = '5';
        ac[58] = '6';
        ac[59] = '7';
        ac[60] = '8';
        ac[61] = '9';
        ac[62] = '+';
        ac[63] = '/';
        INDEX = ac;
    }
}
