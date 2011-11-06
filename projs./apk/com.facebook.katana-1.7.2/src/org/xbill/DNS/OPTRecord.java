// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OPTRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.*;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, Name, Tokenizer, DNSInput, 
//            DNSOutput, Compression

public class OPTRecord extends Record
{
    public static class Option
    {

        public String toString()
        {
            return (new StringBuilder()).append("{").append(code).append(" <").append(base16.toString(data)).append(">}").toString();
        }

        public final int code;
        public final byte data[];

        public Option(int i, byte abyte0[])
        {
            code = Record.checkU8("option code", i);
            data = abyte0;
        }
    }


    OPTRecord()
    {
    }

    public OPTRecord(int i, int j, int k)
    {
        OPTRecord(i, j, k, 0, null);
    }

    public OPTRecord(int i, int j, int k, int l)
    {
        OPTRecord(i, j, k, l, null);
    }

    public OPTRecord(int i, int j, int k, int l, List list)
    {
        Record(Name.root, 41, i, 0L);
        checkU16("payloadSize", i);
        checkU8("xrcode", j);
        checkU8("version", k);
        checkU16("flags", l);
        ttl = ((long)j << 24) + ((long)k << 16) + (long)l;
        if(list != null)
            options = new ArrayList(list);
    }

    public int getExtendedRcode()
    {
        return (int)(ttl >>> 24);
    }

    public int getFlags()
    {
        return (int)(65535L & ttl);
    }

    Record getObject()
    {
        return new OPTRecord();
    }

    public List getOptions()
    {
        List list;
        if(options == null)
            list = Collections.EMPTY_LIST;
        else
            list = Collections.unmodifiableList(options);
        return list;
    }

    public List getOptions(int i)
    {
        if(options != null) goto _L2; else goto _L1
_L1:
        Object obj = Collections.EMPTY_LIST;
_L4:
        return ((List) (obj));
_L2:
        obj = null;
        Iterator iterator = options.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Option option = (Option)iterator.next();
            if(option.code == i)
            {
                if(obj == null)
                    obj = new ArrayList();
                ((List) (obj)).add(option.data);
            }
        } while(true);
        if(obj == null)
            obj = Collections.EMPTY_LIST;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getPayloadSize()
    {
        return dclass;
    }

    public int getVersion()
    {
        return (int)(255L & ttl >>> 16);
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        throw tokenizer.exception("no text format defined for OPT");
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        if(dnsinput.remaining() > 0)
            options = new ArrayList();
        int i;
        byte abyte0[];
        for(; dnsinput.remaining() > 0; options.add(new Option(i, abyte0)))
        {
            i = dnsinput.readU16();
            abyte0 = dnsinput.readByteArray(dnsinput.readU16());
        }

    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        if(options != null)
        {
            stringbuffer.append(options);
            stringbuffer.append(" ");
        }
        stringbuffer.append(" ; payload ");
        stringbuffer.append(getPayloadSize());
        stringbuffer.append(", xrcode ");
        stringbuffer.append(getExtendedRcode());
        stringbuffer.append(", version ");
        stringbuffer.append(getVersion());
        stringbuffer.append(", flags ");
        stringbuffer.append(getFlags());
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        if(options != null)
        {
            Iterator iterator = options.iterator();
            while(iterator.hasNext()) 
            {
                Option option = (Option)iterator.next();
                dnsoutput.writeU16(option.code);
                dnsoutput.writeU16(option.data.length);
                dnsoutput.writeByteArray(option.data);
            }
        }
    }

    private static final long serialVersionUID = 0x7bfc0a7eL;
    private List options;
}
