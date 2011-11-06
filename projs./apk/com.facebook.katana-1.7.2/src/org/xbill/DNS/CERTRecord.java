// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CERTRecord.java

package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Record, Tokenizer, DNSInput, Options, 
//            DNSOutput, Name, Compression, Mnemonic

public class CERTRecord extends Record
{
    public static class CertificateType
    {

        public static String string(int i)
        {
            return types.getText(i);
        }

        public static int value(String s)
        {
            return types.getValue(s);
        }

        public static final int ACPKIX = 7;
        public static final int IACPKIX = 8;
        public static final int IPGP = 6;
        public static final int IPKIX = 4;
        public static final int ISPKI = 5;
        public static final int OID = 254;
        public static final int PGP = 3;
        public static final int PKIX = 1;
        public static final int SPKI = 2;
        public static final int URI = 253;
        private static Mnemonic types;

        static 
        {
            types = new Mnemonic("Certificate type", 2);
            types.setMaximum(65535);
            types.setNumericAllowed(true);
            types.add(1, "PKIX");
            types.add(2, "SPKI");
            types.add(3, "PGP");
            types.add(1, "IPKIX");
            types.add(2, "ISPKI");
            types.add(3, "IPGP");
            types.add(3, "ACPKIX");
            types.add(3, "IACPKIX");
            types.add(253, "URI");
            types.add(254, "OID");
        }

        private CertificateType()
        {
        }
    }


    CERTRecord()
    {
    }

    public CERTRecord(Name name, int i, long l, int j, int k, int i1, 
            byte abyte0[])
    {
        super(name, 37, i, l);
        certType = checkU16("certType", j);
        keyTag = checkU16("keyTag", k);
        alg = checkU8("alg", i1);
        cert = abyte0;
    }

    public int getAlgorithm()
    {
        return alg;
    }

    public byte[] getCert()
    {
        return cert;
    }

    public int getCertType()
    {
        return certType;
    }

    public int getKeyTag()
    {
        return keyTag;
    }

    Record getObject()
    {
        return new CERTRecord();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        String s = tokenizer.getString();
        certType = CertificateType.value(s);
        if(certType < 0)
            throw tokenizer.exception((new StringBuilder()).append("Invalid certificate type: ").append(s).toString());
        keyTag = tokenizer.getUInt16();
        String s1 = tokenizer.getString();
        alg = DNSSEC.Algorithm.value(s1);
        if(alg < 0)
        {
            throw tokenizer.exception((new StringBuilder()).append("Invalid algorithm: ").append(s1).toString());
        } else
        {
            cert = tokenizer.getBase64();
            return;
        }
    }

    void rrFromWire(DNSInput dnsinput)
        throws IOException
    {
        certType = dnsinput.readU16();
        keyTag = dnsinput.readU16();
        alg = dnsinput.readU8();
        cert = dnsinput.readByteArray();
    }

    String rrToString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(certType);
        stringbuffer.append(" ");
        stringbuffer.append(keyTag);
        stringbuffer.append(" ");
        stringbuffer.append(alg);
        if(cert != null)
            if(Options.check("multiline"))
            {
                stringbuffer.append(" (\n");
                stringbuffer.append(base64.formatString(cert, 64, "\t", true));
            } else
            {
                stringbuffer.append(" ");
                stringbuffer.append(base64.toString(cert));
            }
        return stringbuffer.toString();
    }

    void rrToWire(DNSOutput dnsoutput, Compression compression, boolean flag)
    {
        dnsoutput.writeU16(certType);
        dnsoutput.writeU16(keyTag);
        dnsoutput.writeU8(alg);
        dnsoutput.writeByteArray(cert);
    }

    public static final int OID = 254;
    public static final int PGP = 3;
    public static final int PKIX = 1;
    public static final int SPKI = 2;
    public static final int URI = 253;
    private static final long serialVersionUID = 0xe1a12903L;
    private int alg;
    private byte cert[];
    private int certType;
    private int keyTag;
}
