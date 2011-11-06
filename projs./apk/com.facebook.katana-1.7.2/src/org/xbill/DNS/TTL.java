// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TTL.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            InvalidTTLException

public final class TTL
{

    private TTL()
    {
    }

    static void check(long l)
    {
        if(l < 0L || l > 0x7fffffffL)
            throw new InvalidTTLException(l);
        else
            return;
    }

    public static String format(long l)
    {
        check(l);
        StringBuffer stringbuffer = new StringBuffer();
        long l1 = l % 60L;
        long l2 = l / 60L;
        long l3 = l2 % 60L;
        long l4 = l2 / 60L;
        long l5 = l4 % 24L;
        long l6 = l4 / 24L;
        long l7 = l6 % 7L;
        long l8 = l6 / 7L;
        if(l8 > 0L)
            stringbuffer.append((new StringBuilder()).append(l8).append("W").toString());
        if(l7 > 0L)
            stringbuffer.append((new StringBuilder()).append(l7).append("D").toString());
        if(l5 > 0L)
            stringbuffer.append((new StringBuilder()).append(l5).append("H").toString());
        if(l3 > 0L)
            stringbuffer.append((new StringBuilder()).append(l3).append("M").toString());
        if(l1 > 0L || l8 == 0L && l7 == 0L && l5 == 0L && l3 == 0L)
            stringbuffer.append((new StringBuilder()).append(l1).append("S").toString());
        return stringbuffer.toString();
    }

    public static long parse(String s, boolean flag)
    {
        if(s == null || s.length() == 0 || !Character.isDigit(s.charAt(0)))
            throw new NumberFormatException();
        long l = 0L;
        int i = 0;
        long l1 = 0L;
        for(; i < s.length(); i++)
        {
            char c = s.charAt(i);
            long l4;
            if(Character.isDigit(c))
            {
                l4 = 10L * l + (long)Character.getNumericValue(c);
                if(l4 < l)
                    throw new NumberFormatException();
            } else
            {
                switch(Character.toUpperCase(c))
                {
                default:
                    throw new NumberFormatException();

                case 87: // 'W'
                    l *= 7L;
                    // fall through

                case 68: // 'D'
                    l *= 24L;
                    // fall through

                case 72: // 'H'
                    l *= 60L;
                    // fall through

                case 77: // 'M'
                    l *= 60L;
                    // fall through

                case 83: // 'S'
                    l1 += l;
                    break;
                }
                l = 0L;
                if(l1 > 0xffffffffL)
                    throw new NumberFormatException();
                continue;
            }
            l = l4;
        }

        long l2;
        long l3;
        if(l1 == 0L)
            l2 = l;
        else
            l2 = l1;
        if(l2 > 0xffffffffL)
            throw new NumberFormatException();
        if(l2 > 0x7fffffffL && flag)
            l3 = 0x7fffffffL;
        else
            l3 = l2;
        return l3;
    }

    public static long parseTTL(String s)
    {
        return parse(s, true);
    }

    public static final long MAX_VALUE = 0x7fffffffL;
}
