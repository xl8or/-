// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GPOSRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, TextParseException, Tokenizer, WireParseException, 
//            DNSInput, DNSOutput, Name, Compression

public class GPOSRecord extends Record
{

    GPOSRecord()
    {
    }

    public GPOSRecord(Name name, int i, long l, double d, double d1, double d2)
    {
        Record(name, 27, i, l);
        validate(d, d1);
        longitude = Double.toString(d).getBytes();
        latitude = Double.toString(d1).getBytes();
        altitude = Double.toString(d2).getBytes();
    }

    public GPOSRecord(Name name, int i, long l, String s, String s1, String s2)
    {
        Record(name, 27, i, l);
        try
        {
            longitude = byteArrayFromString(s);
            latitude = byteArrayFromString(s1);
            validate(getLongitude(), getLatitude());
            altitude = byteArrayFromString(s2);
            return;
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException(textparseexception.getMessage());
        }
    }

    private void validate(double d, double d1)
        throws IllegalArgumentException
    {
        if(d < -90D || d > 90D)
            throw new IllegalArgumentException((new StringBuilder()).append("illegal longitude ").append(d).toString());
        if(d1 < -180D || d1 > 180D)
            throw new IllegalArgumentException((new StringBuilder()).append("illegal latitude ").append(d1).toString());
        else
            return;
    }

    public double getAltitude()
    {
        return Double.parseDouble(getAltitudeString());
    }

    public String getAltitudeString()
    {
        return byteArrayToString(altitude, false);
    }

    public double getLatitude()
    {
        return Double.parseDouble(getLatitudeString());
    }

    public String getLatitudeString()
    {
        return byteArrayToString(latitude, false);
    }

    public double getLongitude()
    {
        return Double.parseDouble(getLongitudeString());
    }

    public String getLongitudeString()
    {
        return byteArrayToString(longitude, false);
    }

    Record getObject()
    {
        return new GPOSRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        try
        {
            longitude = byteArrayFromString(tokenizer.getString());
            latitude = byteArrayFromString(tokenizer.getString());
            altitude = byteArrayFromString(tokenizer.getString());
        }
        catch(TextParseException textparseexception)
        {
            throw tokenizer.exception(textparseexception.getMessage());
        }
        try
        {
            validate(getLongitude(), getLatitude());
            return;
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new WireParseException(illegalargumentexception.getMessage());
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        longitude = dnsinput.readCountedString();
        latitude = dnsinput.readCountedString();
        altitude = dnsinput.readCountedString();
        try
        {
            validate(getLongitude(), getLatitude());
            return;
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new WireParseException(illegalargumentexception.getMessage());
        }
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(byteArrayToString(longitude, true));
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(latitude, true));
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(altitude, true));
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeCountedString(longitude);
        dnsoutput.writeCountedString(latitude);
        dnsoutput.writeCountedString(altitude);
    }

    private static final long serialVersionUID = 0x4a50804fL;
    private byte altitude[];
    private byte latitude[];
    private byte longitude[];
}
