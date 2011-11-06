// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KEYBase.java

package org.xbill.DNS;

import java.io.IOException;
import java.security.PublicKey;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, DNSOutput, DNSSEC, DNSInput, 
//            Options, Name, Compression

abstract class KEYBase extends Record
{

    protected KEYBase()
    {
        footprint = -1;
        publicKey = null;
    }

    public KEYBase(Name name, int i, int j, long l, int k, int i1, 
            int j1, byte abyte0[])
    {
        Record(name, i, j, l);
        footprint = -1;
        publicKey = null;
        flags = checkU16("flags", k);
        proto = checkU8("proto", i1);
        alg = checkU8("alg", j1);
        key = abyte0;
    }

    public int getAlgorithm()
    {
        return alg;
    }

    public int getFlags()
    {
        return flags;
    }

    public int getFootprint()
    {
        boolean flag = false;
        if(footprint < 0) goto _L2; else goto _L1
_L1:
        int l = footprint;
_L4:
        return l;
_L2:
        byte abyte0[];
        int k;
        DNSOutput dnsoutput = new DNSOutput();
        rrToWire(dnsoutput, null, flag);
        abyte0 = dnsoutput.toByteArray();
        if(alg != 1)
            break; /* Loop/switch isn't completed */
        int j1 = 0xff & abyte0[abyte0.length - 3];
        k = (0xff & abyte0[abyte0.length - 2]) + (j1 << 8);
_L5:
        footprint = k & 0xffff;
        l = footprint;
        if(true) goto _L4; else goto _L3
_L3:
        int i;
        for(i = ((flag) ? 1 : 0); i < abyte0.length - 1; i += 2)
        {
            int i1 = 0xff & abyte0[i];
            flag += (0xff & abyte0[i + 1]) + (i1 << 8);
        }

        int j;
        if(i < abyte0.length)
            j = flag + ((0xff & abyte0[i]) << 8);
        else
            j = ((flag) ? 1 : 0);
        k = j + (0xffff & j >> 16);
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    public byte[] getKey()
    {
        return key;
    }

    public int getProtocol()
    {
        return proto;
    }

    public PublicKey getPublicKey()
        throws DNSSEC.DNSSECException
    {
        PublicKey publickey;
        if(publicKey != null)
        {
            publickey = publicKey;
        } else
        {
            publicKey = DNSSEC.toPublicKey(this);
            publickey = publicKey;
        }
        return publickey;
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        flags = dnsinput.readU16();
        proto = dnsinput.readU8();
        alg = dnsinput.readU8();
        if(dnsinput.remaining() > 0)
            key = dnsinput.readByteArray();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(flags);
        stringbuffer.append(" ");
        stringbuffer.append(proto);
        stringbuffer.append(" ");
        stringbuffer.append(alg);
        if(key != null)
            if(Options.check("multiline"))
            {
                stringbuffer.append(" (\n");
                stringbuffer.append(base64.formatString(key, 64, "\t", true));
                stringbuffer.append(" ; key_tag = ");
                stringbuffer.append(getFootprint());
            } else
            {
                stringbuffer.append(" ");
                stringbuffer.append(base64.toString(key));
            }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(flags);
        dnsoutput.writeU8(proto);
        dnsoutput.writeU8(alg);
        if(key != null)
            dnsoutput.writeByteArray(key);
    }

    private static final long serialVersionUID = 0xbcadf64eL;
    protected int alg;
    protected int flags;
    protected int footprint;
    protected byte key[];
    protected int proto;
    protected PublicKey publicKey;
}
