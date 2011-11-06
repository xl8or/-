// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;


public final class NumberOutput
{

    public NumberOutput()
    {
    }

    private static int calcLongStrLength(long l)
    {
        long l1 = TEN_BILLION_L;
        int i = 10;
        long l2 = l1;
        do
        {
            if(l < l2 || i == 19)
                return i;
            i++;
            l2 = (l2 << 3) + (l2 << 1);
        } while(true);
    }

    private static int outputFullTriplet(int i, char ac[], int j)
    {
        int k = i << 2;
        int l = j + 1;
        char ac1[] = FULL_TRIPLETS;
        int i1 = k + 1;
        ac[j] = ac1[k];
        int j1 = l + 1;
        char ac2[] = FULL_TRIPLETS;
        int k1 = i1 + 1;
        ac[l] = ac2[i1];
        int l1 = j1 + 1;
        ac[j1] = FULL_TRIPLETS[k1];
        return l1;
    }

    public static int outputInt(int i, char ac[], int j)
    {
        if(i >= 0) goto _L2; else goto _L1
_L1:
        if(i != 0x80000000) goto _L4; else goto _L3
_L3:
        int j2 = outputLong(i, ac, j);
_L5:
        return j2;
_L4:
        int k;
        int l;
        k = j + 1;
        ac[j] = '-';
        l = -i;
_L6:
        if(l < MILLION)
        {
            if(l < 1000)
            {
                if(l < 10)
                {
                    int j3 = k + 1;
                    ac[k] = (char)(l + 48);
                    j2 = j3;
                } else
                {
                    j2 = outputLeadingTriplet(l, ac, k);
                }
            } else
            {
                int i3 = l / 1000;
                j2 = outputFullTriplet(l - i3 * 1000, ac, outputLeadingTriplet(i3, ac, k));
            }
        } else
        {
            boolean flag;
            int k1;
            int i2;
            if(l >= BILLION)
                flag = true;
            else
                flag = false;
            if(flag)
            {
                l -= BILLION;
                int i1;
                int j1;
                int l1;
                if(l >= BILLION)
                {
                    l -= BILLION;
                    int l2 = k + 1;
                    ac[k] = '2';
                    k = l2;
                } else
                {
                    int k2 = k + 1;
                    ac[k] = '1';
                    k = k2;
                }
            }
            i1 = l / 1000;
            j1 = l - i1 * 1000;
            k1 = i1 / 1000;
            l1 = i1 - k1 * 1000;
            if(flag)
                i2 = outputFullTriplet(k1, ac, k);
            else
                i2 = outputLeadingTriplet(k1, ac, k);
            j2 = outputFullTriplet(j1, ac, outputFullTriplet(l1, ac, i2));
        }
        if(true) goto _L5; else goto _L2
_L2:
        k = j;
        l = i;
          goto _L6
    }

    private static int outputLeadingTriplet(int i, char ac[], int j)
    {
        int k = i << 2;
        char ac1[] = LEADING_TRIPLETS;
        int l = k + 1;
        char c = ac1[k];
        int i1;
        char ac2[];
        int j1;
        char c1;
        int k1;
        if(c != 0)
        {
            int i2 = j + 1;
            ac[j] = c;
            i1 = i2;
        } else
        {
            i1 = j;
        }
        ac2 = LEADING_TRIPLETS;
        j1 = l + 1;
        c1 = ac2[l];
        if(c1 != 0)
        {
            int l1 = i1 + 1;
            ac[i1] = c1;
            i1 = l1;
        }
        k1 = i1 + 1;
        ac[i1] = LEADING_TRIPLETS[j1];
        return k1;
    }

    public static int outputLong(long l, char ac[], int i)
    {
        int j;
        long l1;
        int j2;
        if(l < 0L)
        {
            if(l > MIN_INT_AS_LONG)
            {
                j2 = outputInt((int)l, ac, i);
            } else
            {
label0:
                {
                    if(l != 0x0L)
                        break label0;
                    int i3 = SMALLEST_LONG.length();
                    SMALLEST_LONG.getChars(0, i3, ac, i);
                    j2 = i3 + i;
                }
            }
        } else
        {
            if(l > MAX_INT_AS_LONG)
                break MISSING_BLOCK_LABEL_231;
            j2 = outputInt((int)l, ac, i);
        }
_L4:
        return j2;
        j = i + 1;
        ac[i] = '-';
        l1 = -l;
_L2:
        int k = j + calcLongStrLength(l1);
        long l2 = l1;
        int i1 = k;
        long l3;
        for(; l2 > MAX_INT_AS_LONG; l2 = l3)
        {
            i1 -= 3;
            l3 = l2 / THOUSAND_L;
            outputFullTriplet((int)(l2 - l3 * THOUSAND_L), ac, i1);
        }

        int j1 = (int)l2;
        int k1 = i1;
        int i2;
        int k2;
        for(i2 = j1; i2 >= 1000; i2 = k2)
        {
            k1 -= 3;
            k2 = i2 / 1000;
            outputFullTriplet(i2 - k2 * 1000, ac, k1);
        }

        outputLeadingTriplet(i2, ac, j);
        j2 = k;
        if(true)
            continue; /* Loop/switch isn't completed */
        j = i;
        l1 = l;
        if(true) goto _L2; else goto _L1
_L1:
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String toString(double d)
    {
        return Double.toString(d);
    }

    public static String toString(int i)
    {
        if(i >= sSmallIntStrs.length) goto _L2; else goto _L1
_L1:
        if(i < 0) goto _L4; else goto _L3
_L3:
        String s = sSmallIntStrs[i];
_L6:
        return s;
_L4:
        int j = -i - 1;
        if(j < sSmallIntStrs2.length)
        {
            s = sSmallIntStrs2[j];
            continue; /* Loop/switch isn't completed */
        }
_L2:
        s = Integer.toString(i);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static String toString(long l)
    {
        String s;
        if(l <= 0x7fffffffL && l >= 0x80000000L)
            s = toString((int)l);
        else
            s = Long.toString(l);
        return s;
    }

    private static int BILLION = 0x3b9aca00;
    static final char FULL_TRIPLETS[];
    static final char LEADING_TRIPLETS[];
    private static long MAX_INT_AS_LONG = 0x7fffffffL;
    private static int MILLION = 0xf4240;
    private static long MIN_INT_AS_LONG = 0x80000000L;
    private static final char NULL_CHAR;
    static final String SMALLEST_LONG = String.valueOf(0x0L);
    private static long TEN_BILLION_L = 0x540be400L;
    private static long THOUSAND_L = 1000L;
    static final String sSmallIntStrs[];
    static final String sSmallIntStrs2[];

    static 
    {
        LEADING_TRIPLETS = new char[4000];
        FULL_TRIPLETS = new char[4000];
        int i = 0;
        int k;
        for(int j = 0; i < 10; j = k)
        {
            char c = (char)(i + 48);
            char c1;
            if(i == 0)
                c1 = '\0';
            else
                c1 = c;
            k = j;
            for(int l = 0; l < 10;)
            {
                char c2 = (char)(l + 48);
                char c3;
                int i1;
                if(i == 0 && l == 0)
                    c3 = '\0';
                else
                    c3 = c2;
                i1 = k;
                for(int j1 = 0; j1 < 10; j1++)
                {
                    char c4 = (char)(j1 + 48);
                    LEADING_TRIPLETS[i1] = c1;
                    LEADING_TRIPLETS[i1 + 1] = c3;
                    LEADING_TRIPLETS[i1 + 2] = c4;
                    FULL_TRIPLETS[i1] = c;
                    FULL_TRIPLETS[i1 + 1] = c2;
                    FULL_TRIPLETS[i1 + 2] = c4;
                    i1 += 4;
                }

                l++;
                k = i1;
            }

            i++;
        }

        String as[] = new String[11];
        as[0] = "0";
        as[1] = "1";
        as[2] = "2";
        as[3] = "3";
        as[4] = "4";
        as[5] = "5";
        as[6] = "6";
        as[7] = "7";
        as[8] = "8";
        as[9] = "9";
        as[10] = "10";
        sSmallIntStrs = as;
        String as1[] = new String[10];
        as1[0] = "-1";
        as1[1] = "-2";
        as1[2] = "-3";
        as1[3] = "-4";
        as1[4] = "-5";
        as1[5] = "-6";
        as1[6] = "-7";
        as1[7] = "-8";
        as1[8] = "-9";
        as1[9] = "-10";
        sSmallIntStrs2 = as1;
    }
}
