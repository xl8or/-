// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LOCRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, WireParseException, DNSInput, 
//            DNSOutput, Name, Compression

public class LOCRecord extends Record
{

    LOCRecord()
    {
    }

    public LOCRecord(Name name, int i, long l, double d, double d1, double d2, double d3, double d4, 
            double d5)
    {
        Record(name, 29, i, l);
        latitude = (long)(2147483648D + 1000D * (3600D * d));
        longitude = (long)(2147483648D + 1000D * (3600D * d1));
        altitude = (long)(100D * (100000D + d2));
        size = (long)(100D * d3);
        hPrecision = (long)(100D * d4);
        vPrecision = (long)(100D * d5);
    }

    private long parseDouble(Tokenizer tokenizer, String s, boolean flag, long l, long l1, 
            long l2)
        throws IOException
    {
        Tokenizer.Token token = tokenizer.get();
        long l3;
        if(token.isEOL())
        {
            if(flag)
                throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).toString());
            tokenizer.unget();
            l3 = l2;
        } else
        {
            String s1 = token.value;
            if(s1.length() > 1 && s1.charAt(s1.length() - 1) == 'm')
                s1 = s1.substring(0, s1.length() - 1);
            try
            {
                l3 = (long)(100D * parseFixedPoint(s1));
                if(l3 < l || l3 > l1)
                    throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).toString());
            }
            catch(NumberFormatException numberformatexception)
            {
                throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).toString());
            }
        }
        return l3;
    }

    private double parseFixedPoint(String s)
    {
        double d;
        if(s.matches("^\\d+$"))
            d = Integer.parseInt(s);
        else
        if(s.matches("^\\d+\\.\\d*$"))
        {
            String as[] = s.split("\\.");
            d = (double)Integer.parseInt(as[0]) + (double)Integer.parseInt(as[1]) / Math.pow(10D, as[1].length());
        } else
        {
            throw new NumberFormatException();
        }
        return d;
    }

    private static long parseLOCformat(int i)
        throws WireParseException
    {
_L2:
        long l1;
        int k;
        do
        {
            int i1 = k + -1;
            if(k > 0)
            {
                l1 *= 10L;
                k = i1;
            } else
            {
                return l1;
            }
        } while(true);
        long l = i >> 4;
        int j = i & 0xf;
        if(l > 9L || j > 9)
            throw new WireParseException("Invalid LOC Encoding");
        l1 = l;
        k = j;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private long parsePosition(Tokenizer tokenizer, String s)
        throws IOException
    {
        boolean flag;
        int i;
        double d;
        int j;
        String s1;
        flag = s.equals("latitude");
        i = 0;
        d = 0D;
        j = tokenizer.getUInt16();
        if(j > 180 || j > 90 && flag)
            throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).append(" degrees").toString());
        s1 = tokenizer.getString();
        i = Integer.parseInt(s1);
        if(i < 0 || i > 59)
            throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).append(" minutes").toString());
          goto _L1
        NumberFormatException numberformatexception;
        numberformatexception;
        int k;
        String s4;
        String s3 = s1;
        k = i;
        s4 = s3;
_L3:
        if(s4.length() != 1)
            throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).toString());
        break; /* Loop/switch isn't completed */
_L1:
        String s5;
        String s2 = tokenizer.getString();
        d = parseFixedPoint(s2);
        if(d < 0D || d >= 60D)
            throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).append(" seconds").toString());
        s5 = tokenizer.getString();
        k = i;
        s4 = s5;
        if(true) goto _L3; else goto _L2
_L2:
        long l = (long)(1000D * (d + (double)(60L * ((long)k + 60L * (long)j))));
        char c = Character.toUpperCase(s4.charAt(0));
        long l1;
        if(flag && c == 'S' || !flag && c == 'W')
        {
            l1 = -l;
        } else
        {
            if(flag && c != 'N' || !flag && c != 'E')
                throw tokenizer.exception((new StringBuilder()).append("Invalid LOC ").append(s).toString());
            l1 = l;
        }
        return l1 + 0x80000000L;
    }

    private String positionToString(long l, char c, char c1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        long l1 = l - 0x80000000L;
        char c2;
        long l2;
        long l3;
        if(l1 < 0L)
        {
            l1 = -l1;
            c2 = c1;
        } else
        {
            c2 = c;
        }
        stringbuffer.append(l1 / 0x36ee80L);
        l2 = l1 % 0x36ee80L;
        stringbuffer.append(" ");
        stringbuffer.append(l2 / 60000L);
        l3 = l2 % 60000L;
        stringbuffer.append(" ");
        renderFixedPoint(stringbuffer, w3, l3, 1000L);
        stringbuffer.append(" ");
        stringbuffer.append(c2);
        return stringbuffer.toString();
    }

    private void renderFixedPoint(StringBuffer stringbuffer, NumberFormat numberformat, long l, long l1)
    {
        stringbuffer.append(l / l1);
        long l2 = l % l1;
        if(l2 != 0L)
        {
            stringbuffer.append(".");
            stringbuffer.append(numberformat.format(l2));
        }
    }

    private int toLOCformat(long l)
    {
        int i = 0;
        long l1;
        for(l1 = l; l1 > 9L; l1 /= 10L)
            i = (byte)(i + 1);

        return (int)((l1 << 4) + (long)i);
    }

    public double getAltitude()
    {
        return (double)(altitude - 0x989680L) / 100D;
    }

    public double getHPrecision()
    {
        return (double)hPrecision / 100D;
    }

    public double getLatitude()
    {
        return (double)(latitude - 0x80000000L) / 3600000D;
    }

    public double getLongitude()
    {
        return (double)(longitude - 0x80000000L) / 3600000D;
    }

    Record getObject()
    {
        return new LOCRecord();
    }

    public double getSize()
    {
        return (double)size / 100D;
    }

    public double getVPrecision()
    {
        return (double)vPrecision / 100D;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        latitude = parsePosition(tokenizer, "latitude");
        longitude = parsePosition(tokenizer, "longitude");
        altitude = 0x989680L + parseDouble(tokenizer, "altitude", true, 0xff676980L, 0xff67697fL, 0L);
        size = parseDouble(tokenizer, "size", false, 0L, 0x18711a00L, 100L);
        hPrecision = parseDouble(tokenizer, "horizontal precision", false, 0L, 0x18711a00L, 0xf4240L);
        vPrecision = parseDouble(tokenizer, "vertical precision", false, 0L, 0x18711a00L, 1000L);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        if(dnsinput.readU8() != 0)
        {
            throw new WireParseException("Invalid LOC version");
        } else
        {
            size = parseLOCformat(dnsinput.readU8());
            hPrecision = parseLOCformat(dnsinput.readU8());
            vPrecision = parseLOCformat(dnsinput.readU8());
            latitude = dnsinput.readU32();
            longitude = dnsinput.readU32();
            altitude = dnsinput.readU32();
            return;
        }
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(positionToString(latitude, 'N', 'S'));
        stringbuffer.append(" ");
        stringbuffer.append(positionToString(longitude, 'E', 'W'));
        stringbuffer.append(" ");
        renderFixedPoint(stringbuffer, w2, altitude - 0x989680L, 100L);
        stringbuffer.append("m ");
        renderFixedPoint(stringbuffer, w2, size, 100L);
        stringbuffer.append("m ");
        renderFixedPoint(stringbuffer, w2, hPrecision, 100L);
        stringbuffer.append("m ");
        renderFixedPoint(stringbuffer, w2, vPrecision, 100L);
        stringbuffer.append("m");
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU8(0);
        dnsoutput.writeU8(toLOCformat(size));
        dnsoutput.writeU8(toLOCformat(hPrecision));
        dnsoutput.writeU8(toLOCformat(vPrecision));
        dnsoutput.writeU32(latitude);
        dnsoutput.writeU32(longitude);
        dnsoutput.writeU32(altitude);
    }

    private static final long serialVersionUID = 0xc7eed6c9L;
    private static NumberFormat w2;
    private static NumberFormat w3;
    private long altitude;
    private long hPrecision;
    private long latitude;
    private long longitude;
    private long size;
    private long vPrecision;

    static 
    {
        w2 = new DecimalFormat();
        w2.setMinimumIntegerDigits(2);
        w3 = new DecimalFormat();
        w3.setMinimumIntegerDigits(3);
    }
}
