// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SRVRecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, Name, 
//            DNSOutput, Compression

public class SRVRecord extends Record
{

    SRVRecord()
    {
    }

    public SRVRecord(Name name, int i, long l, int j, int k, int i1, 
            Name name1)
    {
        Record(name, 33, i, l);
        priority = checkU16("priority", j);
        weight = checkU16("weight", k);
        port = checkU16("port", i1);
        target = checkName("target", name1);
    }

    public Name getAdditionalName()
    {
        return target;
    }

    Record getObject()
    {
        return new SRVRecord();
    }

    public int getPort()
    {
        return port;
    }

    public int getPriority()
    {
        return priority;
    }

    public Name getTarget()
    {
        return target;
    }

    public int getWeight()
    {
        return weight;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        priority = tokenizer.getUInt16();
        weight = tokenizer.getUInt16();
        port = tokenizer.getUInt16();
        target = tokenizer.getName(name);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        priority = dnsinput.readU16();
        weight = dnsinput.readU16();
        port = dnsinput.readU16();
        target = new Name(dnsinput);
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append((new StringBuilder()).append(priority).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(weight).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(port).append(" ").toString());
        stringbuffer.append(target);
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(priority);
        dnsoutput.writeU16(weight);
        dnsoutput.writeU16(port);
        target.toWire(dnsoutput, null, flag);
    }

    private static final long serialVersionUID = 0xd752f9fcL;
    private int port;
    private int priority;
    private Name target;
    private int weight;
}
