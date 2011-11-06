// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DSRecord.java

package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, DNSKEYRecord, DNSSEC, Tokenizer, 
//            DNSInput, DNSOutput, Name, Compression

public class DSRecord extends Record
{
    public static class Digest
    {

        public static final int SHA1 = 1;
        public static final int SHA256 = 2;

        private Digest()
        {
        }
    }


    DSRecord()
    {
    }

    public DSRecord(Name name, int i, long l, int j, int k, int i1, 
            byte abyte0[])
    {
        super(name, 43, i, l);
        footprint = checkU16("footprint", j);
        alg = checkU8("alg", k);
        digestid = checkU8("digestid", i1);
        digest = abyte0;
    }

    public DSRecord(Name name, int i, long l, int j, int k, DNSKEYRecord dnskeyrecord)
    {
        this(name, i, l, j, dnskeyrecord.getAlgorithm(), k, DNSSEC.generateDS(dnskeyrecord, k));
    }

    public int getAlgorithm()
    {
        return alg;
    }

    public byte[] getDigest()
    {
        return digest;
    }

    public int getDigestID()
    {
        return digestid;
    }

    public int getFootprint()
    {
        return footprint;
    }

    Record getObject()
    {
        return new DSRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        footprint = tokenizer.getUInt16();
        alg = tokenizer.getUInt8();
        digestid = tokenizer.getUInt8();
        digest = tokenizer.getHex();
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        footprint = dnsinput.readU16();
        alg = dnsinput.readU8();
        digestid = dnsinput.readU8();
        digest = dnsinput.readByteArray();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(footprint);
        stringbuffer.append(" ");
        stringbuffer.append(alg);
        stringbuffer.append(" ");
        stringbuffer.append(digestid);
        if(digest != null)
        {
            stringbuffer.append(" ");
            stringbuffer.append(base16.toString(digest));
        }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(footprint);
        dnsoutput.writeU8(alg);
        dnsoutput.writeU8(digestid);
        if(digest != null)
            dnsoutput.writeByteArray(digest);
    }

    public static final int SHA1_DIGEST_ID = 1;
    public static final int SHA256_DIGEST_ID = 2;
    private static final long serialVersionUID = 0x68fb50abL;
    private int alg;
    private byte digest[];
    private int digestid;
    private int footprint;
}
