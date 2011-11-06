// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Address.java

package org.xbill.DNS;

import java.net.*;

// Referenced classes of package org.xbill.DNS:
//            ARecord, Lookup, ReverseMap, PTRRecord, 
//            Name, TextParseException, Record

public final class Address
{

    private Address()
    {
    }

    private static InetAddress addrFromRecord(String s, Record record)
        throws UnknownHostException
    {
        return InetAddress.getByAddress(s, ((ARecord)record).getAddress().getAddress());
    }

    public static int addressLength(int i)
    {
        byte byte0;
        if(i == 1)
            byte0 = 4;
        else
        if(i == 2)
            byte0 = 16;
        else
            throw new IllegalArgumentException("unknown address family");
        return byte0;
    }

    public static int familyOf(InetAddress inetaddress)
    {
        int i;
        if(inetaddress instanceof Inet4Address)
            i = 1;
        else
        if(inetaddress instanceof Inet6Address)
            i = 2;
        else
            throw new IllegalArgumentException("unknown address family");
        return i;
    }

    public static InetAddress[] getAllByName(String s)
        throws UnknownHostException
    {
        InetAddress ainetaddress2[];
        InetAddress inetaddress = getByAddress(s);
        ainetaddress2 = new InetAddress[1];
        ainetaddress2[0] = inetaddress;
        InetAddress ainetaddress1[] = ainetaddress2;
_L2:
        return ainetaddress1;
        UnknownHostException unknownhostexception;
        unknownhostexception;
        Record arecord[] = lookupHostName(s);
        InetAddress ainetaddress[] = new InetAddress[arecord.length];
        for(int i = 0; i < arecord.length; i++)
            ainetaddress[i] = addrFromRecord(s, arecord[i]);

        ainetaddress1 = ainetaddress;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static InetAddress getByAddress(String s)
        throws UnknownHostException
    {
        byte abyte0[] = toByteArray(s, 1);
        InetAddress inetaddress;
        if(abyte0 != null)
        {
            inetaddress = InetAddress.getByAddress(abyte0);
        } else
        {
            byte abyte1[] = toByteArray(s, 2);
            if(abyte1 != null)
                inetaddress = InetAddress.getByAddress(abyte1);
            else
                throw new UnknownHostException((new StringBuilder()).append("Invalid address: ").append(s).toString());
        }
        return inetaddress;
    }

    public static InetAddress getByAddress(String s, int i)
        throws UnknownHostException
    {
        if(i != 1 && i != 2)
            throw new IllegalArgumentException("unknown address family");
        byte abyte0[] = toByteArray(s, i);
        if(abyte0 != null)
            return InetAddress.getByAddress(abyte0);
        else
            throw new UnknownHostException((new StringBuilder()).append("Invalid address: ").append(s).toString());
    }

    public static InetAddress getByName(String s)
        throws UnknownHostException
    {
        InetAddress inetaddress1 = getByAddress(s);
        InetAddress inetaddress = inetaddress1;
_L2:
        return inetaddress;
        UnknownHostException unknownhostexception;
        unknownhostexception;
        inetaddress = addrFromRecord(s, lookupHostName(s)[0]);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static String getHostName(InetAddress inetaddress)
        throws UnknownHostException
    {
        Record arecord[] = (new Lookup(ReverseMap.fromAddress(inetaddress), 12)).run();
        if(arecord == null)
            throw new UnknownHostException("unknown address");
        else
            return ((PTRRecord)arecord[0]).getTarget().toString();
    }

    public static boolean isDottedQuad(String s)
    {
        boolean flag;
        if(toByteArray(s, 1) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static Record[] lookupHostName(String s)
        throws UnknownHostException
    {
        Record arecord[];
        try
        {
            arecord = (new Lookup(s)).run();
            if(arecord == null)
                throw new UnknownHostException("unknown host");
        }
        catch(TextParseException textparseexception)
        {
            throw new UnknownHostException("invalid name");
        }
        return arecord;
    }

    private static byte[] parseV4(String s)
    {
        byte abyte0[];
        int i;
        int j;
        int k;
        int l;
        int i1;
        abyte0 = new byte[4];
        i = s.length();
        j = 0;
        k = 0;
        l = 0;
        i1 = 0;
_L2:
label0:
        {
            if(j >= i)
                break label0;
            char c = s.charAt(j);
            int j1;
            if(c >= '0' && c <= '9')
            {
                if(i1 == 3)
                {
                    abyte0 = null;
                } else
                {
label1:
                    {
                        if(i1 <= 0 || k != 0)
                            break label1;
                        abyte0 = null;
                    }
                }
            } else
            if(c == '.')
            {
                if(l == 3)
                {
                    abyte0 = null;
                } else
                {
label2:
                    {
                        if(i1 != 0)
                            break label2;
                        abyte0 = null;
                    }
                }
            } else
            {
                abyte0 = null;
            }
        }
_L1:
        return abyte0;
        i1++;
        k = k * 10 + (c - 48);
        if(k <= 255)
            break MISSING_BLOCK_LABEL_151;
        abyte0 = null;
          goto _L1
        j1 = l + 1;
        abyte0[l] = (byte)k;
        k = 0;
        l = j1;
        i1 = 0;
        j++;
          goto _L2
        if(l != 3)
            abyte0 = null;
        else
        if(i1 == 0)
            abyte0 = null;
        else
            abyte0[l] = (byte)k;
          goto _L1
    }

    private static byte[] parseV6(String s)
    {
        byte abyte0[];
        String as[];
        int i;
        abyte0 = new byte[16];
        as = s.split(":", -1);
        i = as.length - 1;
        if(as[0].length() != 0) goto _L2; else goto _L1
_L1:
        if(i - 0 <= 0 || as[1].length() != 0) goto _L4; else goto _L3
_L3:
        int j = 0 + 1;
_L25:
        if(as[i].length() != 0) goto _L6; else goto _L5
_L5:
        if(i - j <= 0 || as[i - 1].length() != 0) goto _L8; else goto _L7
_L7:
        i--;
_L6:
        if(1 + (i - j) <= 8) goto _L10; else goto _L9
_L9:
        abyte0 = null;
_L11:
        return abyte0;
_L4:
        abyte0 = null;
          goto _L11
_L8:
        abyte0 = null;
          goto _L11
_L10:
        int k;
        int l;
        int i1;
        k = j;
        l = -1;
        i1 = 0;
_L16:
        if(k > i) goto _L13; else goto _L12
_L12:
        if(as[k].length() != 0) goto _L15; else goto _L14
_L14:
label0:
        {
            if(l < 0)
                break label0;
            abyte0 = null;
        }
          goto _L11
        l = i1;
_L23:
        k++;
          goto _L16
_L15:
        if(as[k].indexOf('.') < 0) goto _L18; else goto _L17
_L17:
        byte abyte1[];
        if(k < i)
            abyte0 = null;
        else
        if(k > 6)
        {
            abyte0 = null;
        } else
        {
label1:
            {
                abyte1 = toByteArray(as[k], 1);
                if(abyte1 != null)
                    break label1;
                abyte0 = null;
            }
        }
          goto _L11
        int j1;
        for(int l2 = 0; l2 < 4;)
        {
            int i3 = i1 + 1;
            abyte0[i1] = abyte1[l2];
            l2++;
            i1 = i3;
        }

        j1 = i1;
_L24:
        int i2;
        NumberFormatException numberformatexception;
        if(j1 < 16 && l < 0)
            abyte0 = null;
        else
        if(l >= 0)
        {
            int k1 = 16 - j1;
            System.arraycopy(abyte0, l, abyte0, l + k1, j1 - l);
            int l1 = l;
            while(l1 < l + k1) 
            {
                abyte0[l1] = 0;
                l1++;
            }
        }
          goto _L11
_L18:
        i2 = 0;
_L26:
        if(i2 >= as[k].length()) goto _L20; else goto _L19
_L19:
        if(Character.digit(as[k].charAt(i2), 16) >= 0) goto _L22; else goto _L21
_L21:
        abyte0 = null;
          goto _L11
_L20:
        int j2 = Integer.parseInt(as[k], 16);
        if(j2 > 65535 || j2 < 0)
            break MISSING_BLOCK_LABEL_439;
        int k2 = i1 + 1;
        abyte0[i1] = (byte)(j2 >>> 8);
        i1 = k2 + 1;
        abyte0[k2] = (byte)(j2 & 0xff);
          goto _L23
        numberformatexception;
        abyte0 = null;
          goto _L11
_L13:
        j1 = i1;
          goto _L24
_L2:
        j = 0;
          goto _L25
_L22:
        i2++;
          goto _L26
        abyte0 = null;
          goto _L11
    }

    public static int[] toArray(String s)
    {
        return toArray(s, 1);
    }

    public static int[] toArray(String s, int i)
    {
        byte abyte0[] = toByteArray(s, i);
        int ai1[];
        if(abyte0 == null)
        {
            ai1 = null;
        } else
        {
            int ai[] = new int[abyte0.length];
            for(int j = 0; j < abyte0.length; j++)
                ai[j] = 0xff & abyte0[j];

            ai1 = ai;
        }
        return ai1;
    }

    public static byte[] toByteArray(String s, int i)
    {
        byte abyte0[];
        if(i == 1)
            abyte0 = parseV4(s);
        else
        if(i == 2)
            abyte0 = parseV6(s);
        else
            throw new IllegalArgumentException("unknown address family");
        return abyte0;
    }

    public static String toDottedQuad(byte abyte0[])
    {
        return (new StringBuilder()).append(0xff & abyte0[0]).append(".").append(0xff & abyte0[1]).append(".").append(0xff & abyte0[2]).append(".").append(0xff & abyte0[3]).toString();
    }

    public static String toDottedQuad(int ai[])
    {
        return (new StringBuilder()).append(ai[0]).append(".").append(ai[1]).append(".").append(ai[2]).append(".").append(ai[3]).toString();
    }

    public static final int IPv4 = 1;
    public static final int IPv6 = 2;
}
