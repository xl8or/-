// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SSHFPRecord.java

package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, DNSOutput, 
//            Name, Compression

public class SSHFPRecord extends Record
{
    public static class Digest
    {

        public static final int SHA1 = 1;

        private Digest()
        {
        }
    }

    public static class Algorithm
    {

        public static final int DSS = 2;
        public static final int RSA = 1;

        private Algorithm()
        {
        }
    }


    SSHFPRecord()
    {
    }

    public SSHFPRecord(Name name, int i, long l, int j, int k, byte abyte0[])
    {
        Record(name, 44, i, l);
        alg = checkU8("alg", j);
        digestType = checkU8("digestType", k);
        fingerprint = abyte0;
    }

    public int getAlgorithm()
    {
        return alg;
    }

    public int getDigestType()
    {
        return digestType;
    }

    public byte[] getFingerPrint()
    {
        return fingerprint;
    }

    Record getObject()
    {
        return new SSHFPRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        alg = tokenizer.getUInt8();
        digestType = tokenizer.getUInt8();
        fingerprint = tokenizer.getHex(true);
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        alg = dnsinput.readU8();
        digestType = dnsinput.readU8();
        fingerprint = dnsinput.readByteArray();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(alg);
        stringbuffer.append(" ");
        stringbuffer.append(digestType);
        stringbuffer.append(" ");
        stringbuffer.append(base16.toString(fingerprint));
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU8(alg);
        dnsoutput.writeU8(digestType);
        dnsoutput.writeByteArray(fingerprint);
    }

    private static final long serialVersionUID = 0xcc2840cfL;
    private int alg;
    private int digestType;
    private byte fingerprint[];
}
