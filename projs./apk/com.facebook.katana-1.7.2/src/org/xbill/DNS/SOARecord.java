// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SOARecord.java

package org.xbill.DNS;

import java.io.IOException;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, Name, DNSInput, 
//            Options, DNSOutput, Compression

public class SOARecord extends Record
{

    SOARecord()
    {
    }

    public SOARecord(Name name, int i, long l, Name name1, Name name2, long l1, long l2, long l3, long l4, 
            long l5)
    {
        Record(name, 6, i, l);
        host = checkName("host", name1);
        admin = checkName("admin", name2);
        serial = checkU32("serial", l1);
        refresh = checkU32("refresh", l2);
        retry = checkU32("retry", l3);
        expire = checkU32("expire", l4);
        minimum = checkU32("minimum", l5);
    }

    public Name getAdmin()
    {
        return admin;
    }

    public long getExpire()
    {
        return expire;
    }

    public Name getHost()
    {
        return host;
    }

    public long getMinimum()
    {
        return minimum;
    }

    Record getObject()
    {
        return new SOARecord();
    }

    public long getRefresh()
    {
        return refresh;
    }

    public long getRetry()
    {
        return retry;
    }

    public long getSerial()
    {
        return serial;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        host = tokenizer.getName(name);
        admin = tokenizer.getName(name);
        serial = tokenizer.getUInt32();
        refresh = tokenizer.getTTLLike();
        retry = tokenizer.getTTLLike();
        expire = tokenizer.getTTLLike();
        minimum = tokenizer.getTTLLike();
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        host = new Name(dnsinput);
        admin = new Name(dnsinput);
        serial = dnsinput.readU32();
        refresh = dnsinput.readU32();
        retry = dnsinput.readU32();
        expire = dnsinput.readU32();
        minimum = dnsinput.readU32();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(host);
        stringbuffer.append(" ");
        stringbuffer.append(admin);
        if(Options.check("multiline"))
        {
            stringbuffer.append(" (\n\t\t\t\t\t");
            stringbuffer.append(serial);
            stringbuffer.append("\t; serial\n\t\t\t\t\t");
            stringbuffer.append(refresh);
            stringbuffer.append("\t; refresh\n\t\t\t\t\t");
            stringbuffer.append(retry);
            stringbuffer.append("\t; retry\n\t\t\t\t\t");
            stringbuffer.append(expire);
            stringbuffer.append("\t; expire\n\t\t\t\t\t");
            stringbuffer.append(minimum);
            stringbuffer.append(" )\t; minimum");
        } else
        {
            stringbuffer.append(" ");
            stringbuffer.append(serial);
            stringbuffer.append(" ");
            stringbuffer.append(refresh);
            stringbuffer.append(" ");
            stringbuffer.append(retry);
            stringbuffer.append(" ");
            stringbuffer.append(expire);
            stringbuffer.append(" ");
            stringbuffer.append(minimum);
        }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        host.toWire(dnsoutput, compression, flag);
        admin.toWire(dnsoutput, compression, flag);
        dnsoutput.writeU32(serial);
        dnsoutput.writeU32(refresh);
        dnsoutput.writeU32(retry);
        dnsoutput.writeU32(expire);
        dnsoutput.writeU32(minimum);
    }

    private static final long serialVersionUID = 0xbd70fa7bL;
    private Name admin;
    private long expire;
    private Name host;
    private long minimum;
    private long refresh;
    private long retry;
    private long serial;
}
