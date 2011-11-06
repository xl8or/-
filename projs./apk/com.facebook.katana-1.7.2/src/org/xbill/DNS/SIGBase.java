// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SIGBase.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, Type, TTL, Name, 
//            Tokenizer, FormattedTime, DNSInput, Options, 
//            DNSOutput, Compression

abstract class SIGBase extends Record
{

    protected SIGBase()
    {
    }

    public SIGBase(Name name, int i, int j, long l, int k, int i1, 
            long l1, Date date, Date date1, int j1, Name name1, byte abyte0[])
    {
        Record(name, i, j, l);
        Type.check(k);
        TTL.check(l1);
        covered = k;
        alg = checkU8("alg", i1);
        labels = name.labels() - 1;
        if(name.isWild())
            labels = labels - 1;
        origttl = l1;
        expire = date;
        timeSigned = date1;
        footprint = checkU16("footprint", j1);
        signer = checkName("signer", name1);
        signature = abyte0;
    }

    public int getAlgorithm()
    {
        return alg;
    }

    public Date getExpire()
    {
        return expire;
    }

    public int getFootprint()
    {
        return footprint;
    }

    public int getLabels()
    {
        return labels;
    }

    public long getOrigTTL()
    {
        return origttl;
    }

    public byte[] getSignature()
    {
        return signature;
    }

    public Name getSigner()
    {
        return signer;
    }

    public Date getTimeSigned()
    {
        return timeSigned;
    }

    public int getTypeCovered()
    {
        return covered;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        String s = tokenizer.getString();
        covered = Type.value(s);
        if(covered < 0)
            throw tokenizer.exception((new StringBuilder()).append("Invalid type: ").append(s).toString());
        String s1 = tokenizer.getString();
        alg = DNSSEC.Algorithm.value(s1);
        if(alg < 0)
        {
            throw tokenizer.exception((new StringBuilder()).append("Invalid algorithm: ").append(s1).toString());
        } else
        {
            labels = tokenizer.getUInt8();
            origttl = tokenizer.getTTL();
            expire = FormattedTime.parse(tokenizer.getString());
            timeSigned = FormattedTime.parse(tokenizer.getString());
            footprint = tokenizer.getUInt16();
            signer = tokenizer.getName(name);
            signature = tokenizer.getBase64();
            return;
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        covered = dnsinput.readU16();
        alg = dnsinput.readU8();
        labels = dnsinput.readU8();
        origttl = dnsinput.readU32();
        expire = new Date(1000L * dnsinput.readU32());
        timeSigned = new Date(1000L * dnsinput.readU32());
        footprint = dnsinput.readU16();
        signer = new Name(dnsinput);
        signature = dnsinput.readByteArray();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(Type.string(covered));
        stringbuffer.append(" ");
        stringbuffer.append(alg);
        stringbuffer.append(" ");
        stringbuffer.append(labels);
        stringbuffer.append(" ");
        stringbuffer.append(origttl);
        stringbuffer.append(" ");
        if(Options.check("multiline"))
            stringbuffer.append("(\n\t");
        stringbuffer.append(FormattedTime.format(expire));
        stringbuffer.append(" ");
        stringbuffer.append(FormattedTime.format(timeSigned));
        stringbuffer.append(" ");
        stringbuffer.append(footprint);
        stringbuffer.append(" ");
        stringbuffer.append(signer);
        if(Options.check("multiline"))
        {
            stringbuffer.append("\n");
            stringbuffer.append(base64.formatString(signature, 64, "\t", true));
        } else
        {
            stringbuffer.append(" ");
            stringbuffer.append(base64.toString(signature));
        }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(covered);
        dnsoutput.writeU8(alg);
        dnsoutput.writeU8(labels);
        dnsoutput.writeU32(origttl);
        dnsoutput.writeU32(expire.getTime() / 1000L);
        dnsoutput.writeU32(timeSigned.getTime() / 1000L);
        dnsoutput.writeU16(footprint);
        signer.toWire(dnsoutput, null, flag);
        dnsoutput.writeByteArray(signature);
    }

    void setSignature(byte abyte0[])
    {
        signature = abyte0;
    }

    private static final long serialVersionUID = 0xe13656fL;
    protected int alg;
    protected int covered;
    protected Date expire;
    protected int footprint;
    protected int labels;
    protected long origttl;
    protected byte signature[];
    protected Name signer;
    protected Date timeSigned;
}
