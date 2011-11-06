// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TSIGRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, Name, DNSInput, 
//            Options, Rcode, DNSOutput, Compression

public class TSIGRecord extends Record
{

    TSIGRecord()
    {
    }

    public TSIGRecord(Name name, int i, long l, Name name1, Date date, int j, 
            byte abyte0[], int k, int i1, byte abyte1[])
    {
        super(name, 250, i, l);
        alg = checkName("alg", name1);
        timeSigned = date;
        fudge = checkU16("fudge", j);
        signature = abyte0;
        originalID = checkU16("originalID", k);
        error = checkU16("error", i1);
        other = abyte1;
    }

    public Name getAlgorithm()
    {
        return alg;
    }

    public int getError()
    {
        return error;
    }

    public int getFudge()
    {
        return fudge;
    }

    Record getObject()
    {
        return new TSIGRecord();
    }

    public int getOriginalID()
    {
        return originalID;
    }

    public byte[] getOther()
    {
        return other;
    }

    public byte[] getSignature()
    {
        return signature;
    }

    public Date getTimeSigned()
    {
        return timeSigned;
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        throw tokenizer.exception("no text format defined for TSIG");
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        alg = new Name(dnsinput);
        long l = dnsinput.readU16();
        timeSigned = new Date(1000L * (dnsinput.readU32() + (l << 32)));
        fudge = dnsinput.readU16();
        signature = dnsinput.readByteArray(dnsinput.readU16());
        originalID = dnsinput.readU16();
        error = dnsinput.readU16();
        int i = dnsinput.readU16();
        if(i > 0)
            other = dnsinput.readByteArray(i);
        else
            other = null;
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(alg);
        stringbuffer.append(" ");
        if(Options.check("multiline"))
            stringbuffer.append("(\n\t");
        stringbuffer.append(timeSigned.getTime() / 1000L);
        stringbuffer.append(" ");
        stringbuffer.append(fudge);
        stringbuffer.append(" ");
        stringbuffer.append(signature.length);
        if(Options.check("multiline"))
        {
            stringbuffer.append("\n");
            stringbuffer.append(base64.formatString(signature, 64, "\t", false));
        } else
        {
            stringbuffer.append(" ");
            stringbuffer.append(base64.toString(signature));
        }
        stringbuffer.append(" ");
        stringbuffer.append(Rcode.TSIGstring(error));
        stringbuffer.append(" ");
        if(other == null)
        {
            stringbuffer.append(0);
        } else
        {
            stringbuffer.append(other.length);
            if(Options.check("multiline"))
                stringbuffer.append("\n\n\n\t");
            else
                stringbuffer.append(" ");
            if(error == 18)
            {
                if(other.length != 6)
                {
                    stringbuffer.append("<invalid BADTIME other data>");
                } else
                {
                    long l = ((long)(0xff & other[0]) << 40) + ((long)(0xff & other[1]) << 32) + (long)((0xff & other[2]) << 24) + (long)((0xff & other[3]) << 16) + (long)((0xff & other[4]) << 8) + (long)(0xff & other[5]);
                    stringbuffer.append("<server time: ");
                    stringbuffer.append(new Date(l * 1000L));
                    stringbuffer.append(">");
                }
            } else
            {
                stringbuffer.append("<");
                stringbuffer.append(base64.toString(other));
                stringbuffer.append(">");
            }
        }
        if(Options.check("multiline"))
            stringbuffer.append(" )");
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        alg.toWire(dnsoutput, null, flag);
        long l = timeSigned.getTime() / 1000L;
        int i = (int)(l >> 32);
        long l1 = l & 0xffffffffL;
        dnsoutput.writeU16(i);
        dnsoutput.writeU32(l1);
        dnsoutput.writeU16(fudge);
        dnsoutput.writeU16(signature.length);
        dnsoutput.writeByteArray(signature);
        dnsoutput.writeU16(originalID);
        dnsoutput.writeU16(error);
        if(other != null)
        {
            dnsoutput.writeU16(other.length);
            dnsoutput.writeByteArray(other);
        } else
        {
            dnsoutput.writeU16(0);
        }
    }

    private static final long serialVersionUID = 0x956f71a6L;
    private Name alg;
    private int error;
    private int fudge;
    private int originalID;
    private byte other[];
    private byte signature[];
    private Date timeSigned;
}
