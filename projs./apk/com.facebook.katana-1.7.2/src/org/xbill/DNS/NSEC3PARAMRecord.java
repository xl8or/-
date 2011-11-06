// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NSEC3PARAMRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.xbill.DNS.utils.base16;

// Referenced classes of package org.xbill.DNS:
//            Record, NSEC3Record, Tokenizer, DNSInput, 
//            DNSOutput, Name, Compression

public class NSEC3PARAMRecord extends Record
{

    NSEC3PARAMRecord()
    {
    }

    public NSEC3PARAMRecord(Name name, int i, long l, int j, int k, int i1, 
            byte abyte0[])
    {
        Record(name, 51, i, l);
        hashAlg = checkU8("hashAlg", j);
        flags = checkU8("flags", k);
        iterations = checkU16("iterations", i1);
        if(abyte0 != null)
        {
            if(abyte0.length > 255)
                throw new IllegalArgumentException("Invalid salt length");
            if(abyte0.length > 0)
            {
                salt = new byte[abyte0.length];
                System.arraycopy(abyte0, 0, salt, 0, abyte0.length);
            }
        }
    }

    public int getFlags()
    {
        return flags;
    }

    public int getHashAlgorithm()
    {
        return hashAlg;
    }

    public int getIterations()
    {
        return iterations;
    }

    Record getObject()
    {
        return new NSEC3PARAMRecord();
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public byte[] hashName(Name name)
        throws NoSuchAlgorithmException
    {
        return NSEC3Record.hashName(name, hashAlg, iterations, salt);
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        hashAlg = tokenizer.getUInt8();
        flags = tokenizer.getUInt8();
        iterations = tokenizer.getUInt16();
        if(tokenizer.getString().equals("-"))
        {
            salt = null;
        } else
        {
            tokenizer.unget();
            salt = tokenizer.getHexString();
            if(salt.length > 255)
                throw tokenizer.exception("salt value too long");
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        hashAlg = dnsinput.readU8();
        flags = dnsinput.readU8();
        iterations = dnsinput.readU16();
        int i = dnsinput.readU8();
        if(i > 0)
            salt = dnsinput.readByteArray(i);
        else
            salt = null;
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(hashAlg);
        stringbuffer.append(' ');
        stringbuffer.append(flags);
        stringbuffer.append(' ');
        stringbuffer.append(iterations);
        stringbuffer.append(' ');
        if(salt == null)
            stringbuffer.append('-');
        else
            stringbuffer.append(base16.toString(salt));
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU8(hashAlg);
        dnsoutput.writeU8(flags);
        dnsoutput.writeU16(iterations);
        if(salt != null)
        {
            dnsoutput.writeU8(salt.length);
            dnsoutput.writeByteArray(salt);
        } else
        {
            dnsoutput.writeU8(0);
        }
    }

    private static final long serialVersionUID = 0x50479d8bL;
    private int flags;
    private int hashAlg;
    private int iterations;
    private byte salt[];
}
