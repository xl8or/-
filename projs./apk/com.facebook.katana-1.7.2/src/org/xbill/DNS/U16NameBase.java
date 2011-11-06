// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   U16NameBase.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, Name, 
//            DNSOutput, Compression

abstract class U16NameBase extends Record
{

    protected U16NameBase()
    {
    }

    protected U16NameBase(Name name, int i, int j, long l)
    {
        super(name, i, j, l);
    }

    protected U16NameBase(Name name, int i, int j, long l, int k, String s, 
            Name name1, String s1)
    {
        super(name, i, j, l);
        u16Field = checkU16(s, k);
        nameField = checkName(s1, name1);
    }

    protected Name getNameField()
    {
        return nameField;
    }

    protected int getU16Field()
    {
        return u16Field;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        u16Field = tokenizer.getUInt16();
        nameField = tokenizer.getName(name);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        u16Field = dnsinput.readU16();
        nameField = new Name(dnsinput);
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(u16Field);
        stringbuffer.append(" ");
        stringbuffer.append(nameField);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(u16Field);
        nameField.toWire(dnsoutput, null, flag);
    }

    private static final long serialVersionUID = 0x184a312dL;
    protected Name nameField;
    protected int u16Field;
}
