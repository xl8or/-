// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, Name, DNSInput, 
//            DNSOutput, Compression

class EmptyRecord extends Record
{

    EmptyRecord()
    {
    }

    Record getObject()
    {
        return new EmptyRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
    }

    String rrToString()
    {
        return "";
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
    }

    private static final long serialVersionUID = 0x4c4aef8eL;
}
