// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NSEC3Record.java

package org.xbill.DNS;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base32;

// Referenced classes of package org.xbill.DNS:
//            Record, TypeBitmap, Name, Tokenizer, 
//            DNSInput, DNSOutput, Compression

public class NSEC3Record extends Record
{
    public static class Digest
    {

        public static final int SHA1 = 1;

        private Digest()
        {
        }
    }

    public static class Flags
    {

        public static final int OPT_OUT = 1;

        private Flags()
        {
        }
    }


    NSEC3Record()
    {
    }

    public NSEC3Record(Name name, int i, long l, int j, int k, int i1, 
            byte abyte0[], byte abyte1[], int ai[])
    {
        Record(name, 50, i, l);
        hashAlg = checkU8("hashAlg", j);
        flags = checkU8("flags", k);
        iterations = checkU16("iterations", i1);
        if(abyte0 != null)
        {
            if(abyte0.length > 255)
                throw new IllegalArgumentException("Invalid salt");
            if(abyte0.length > 0)
            {
                salt = new byte[abyte0.length];
                System.arraycopy(abyte0, 0, salt, 0, abyte0.length);
            }
        }
        if(abyte1.length > 255)
        {
            throw new IllegalArgumentException("Invalid next hash");
        } else
        {
            next = new byte[abyte1.length];
            System.arraycopy(abyte1, 0, next, 0, abyte1.length);
            TypeBitmap typebitmap = new TypeBitmap(ai);
            types = typebitmap;
            return;
        }
    }

    static byte[] hashName(Name name, int i, int j, byte abyte0[])
        throws NoSuchAlgorithmException
    {
        MessageDigest messagedigest;
        switch(i)
        {
        default:
            throw new NoSuchAlgorithmException((new StringBuilder()).append("Unknown NSEC3 algorithmidentifier: ").append(i).toString());

        case 1: // '\001'
            messagedigest = MessageDigest.getInstance("sha-1");
            break;
        }
        byte abyte1[] = null;
        int k = 0;
        while(k <= j) 
        {
            messagedigest.reset();
            if(k == 0)
                messagedigest.update(name.toWireCanonical());
            else
                messagedigest.update(abyte1);
            if(abyte0 != null)
                messagedigest.update(abyte0);
            abyte1 = messagedigest.digest();
            k++;
        }
        return abyte1;
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

    public byte[] getNext()
    {
        return next;
    }

    Record getObject()
    {
        return new NSEC3Record();
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public int[] getTypes()
    {
        return types.toArray();
    }

    public boolean hasType(int i)
    {
        return types.contains(i);
    }

    public byte[] hashName(Name name)
        throws NoSuchAlgorithmException
    {
        return hashName(name, hashAlg, iterations, salt);
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
        next = tokenizer.getBase32String(b32);
        types = new TypeBitmap(tokenizer);
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
        next = dnsinput.readByteArray(dnsinput.readU8());
        types = new TypeBitmap(dnsinput);
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
        stringbuffer.append(' ');
        stringbuffer.append(b32.toString(next));
        if(!types.empty())
        {
            stringbuffer.append(' ');
            stringbuffer.append(types.toString());
        }
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
        dnsoutput.writeU8(next.length);
        dnsoutput.writeByteArray(next);
        types.toWire(dnsoutput);
    }

    public static final int SHA1_DIGEST_ID = 1;
    private static final base32 b32 = new base32("0123456789ABCDEFGHIJKLMNOPQRSTUV=", false, false);
    private static final long serialVersionUID = 0x89393409L;
    private int flags;
    private int hashAlg;
    private int iterations;
    private byte next[];
    private byte salt[];
    private TypeBitmap types;

}
