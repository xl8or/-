// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NAPTRRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, TextParseException, Tokenizer, DNSInput, 
//            Name, DNSOutput, Compression

public class NAPTRRecord extends Record
{

    NAPTRRecord()
    {
    }

    public NAPTRRecord(Name name, int i, long l, int j, int k, String s, 
            String s1, String s2, Name name1)
    {
        Record(name, 35, i, l);
        order = checkU16("order", j);
        preference = checkU16("preference", k);
        try
        {
            flags = byteArrayFromString(s);
            service = byteArrayFromString(s1);
            regexp = byteArrayFromString(s2);
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException(textparseexception.getMessage());
        }
        replacement = checkName("replacement", name1);
    }

    public Name getAdditionalName()
    {
        return replacement;
    }

    public String getFlags()
    {
        return byteArrayToString(flags, false);
    }

    Record getObject()
    {
        return new NAPTRRecord();
    }

    public int getOrder()
    {
        return order;
    }

    public int getPreference()
    {
        return preference;
    }

    public String getRegexp()
    {
        return byteArrayToString(regexp, false);
    }

    public Name getReplacement()
    {
        return replacement;
    }

    public String getService()
    {
        return byteArrayToString(service, false);
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        order = tokenizer.getUInt16();
        preference = tokenizer.getUInt16();
        try
        {
            flags = byteArrayFromString(tokenizer.getString());
            service = byteArrayFromString(tokenizer.getString());
            regexp = byteArrayFromString(tokenizer.getString());
        }
        catch(TextParseException textparseexception)
        {
            throw tokenizer.exception(textparseexception.getMessage());
        }
        replacement = tokenizer.getName(name);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        order = dnsinput.readU16();
        preference = dnsinput.readU16();
        flags = dnsinput.readCountedString();
        service = dnsinput.readCountedString();
        regexp = dnsinput.readCountedString();
        replacement = new Name(dnsinput);
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(order);
        stringbuffer.append(" ");
        stringbuffer.append(preference);
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(flags, true));
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(service, true));
        stringbuffer.append(" ");
        stringbuffer.append(byteArrayToString(regexp, true));
        stringbuffer.append(" ");
        stringbuffer.append(replacement);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(order);
        dnsoutput.writeU16(preference);
        dnsoutput.writeCountedString(flags);
        dnsoutput.writeCountedString(service);
        dnsoutput.writeCountedString(regexp);
        replacement.toWire(dnsoutput, null, flag);
    }

    private static final long serialVersionUID = 0x3257263aL;
    private byte flags[];
    private int order;
    private int preference;
    private byte regexp[];
    private Name replacement;
    private byte service[];
}
