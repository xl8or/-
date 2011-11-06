// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;


public final class NumberInput
{

    public NumberInput()
    {
    }

    public static final int parseInt(char ac[], int i, int j)
    {
        int k = ac[i] - 48;
        int l = j + i;
        int i1 = i + 1;
        if(i1 < l)
        {
            k = k * 10 + (ac[i1] - 48);
            int j1 = i1 + 1;
            if(j1 < l)
            {
                k = k * 10 + (ac[j1] - 48);
                int k1 = j1 + 1;
                if(k1 < l)
                {
                    k = k * 10 + (ac[k1] - 48);
                    int l1 = k1 + 1;
                    if(l1 < l)
                    {
                        k = k * 10 + (ac[l1] - 48);
                        int i2 = l1 + 1;
                        if(i2 < l)
                        {
                            k = k * 10 + (ac[i2] - 48);
                            int j2 = i2 + 1;
                            if(j2 < l)
                            {
                                k = k * 10 + (ac[j2] - 48);
                                int k2 = j2 + 1;
                                if(k2 < l)
                                {
                                    k = k * 10 + (ac[k2] - 48);
                                    int l2 = k2 + 1;
                                    if(l2 < l)
                                        k = k * 10 + (ac[l2] - 48);
                                }
                            }
                        }
                    }
                }
            }
        }
        return k;
    }

    public static final long parseLong(char ac[], int i, int j)
    {
        int k = j - 9;
        return 0x3b9aca00L * (long)parseInt(ac, i, k) + (long)parseInt(ac, k + i, 9);
    }

    static final long L_BILLION = 0x3b9aca00L;
}
