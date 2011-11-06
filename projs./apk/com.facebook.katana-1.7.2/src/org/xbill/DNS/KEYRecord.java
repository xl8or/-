// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KEYRecord.java

package org.xbill.DNS;

import java.io.IOException;
import java.security.PublicKey;
import java.util.StringTokenizer;

// Referenced classes of package org.xbill.DNS:
//            KEYBase, DNSSEC, Tokenizer, Name, 
//            Record, Mnemonic

public class KEYRecord extends KEYBase
{
    public static class Flags
    {

        public static int value(String s)
        {
            int l = Integer.parseInt(s);
            int j;
            j = l;
            if(j < 0 || j > 65535)
                j = -1;
_L2:
            return j;
            NumberFormatException numberformatexception;
            numberformatexception;
            StringTokenizer stringtokenizer = new StringTokenizer(s, "|");
            int i = 0;
            do
            {
                if(!stringtokenizer.hasMoreTokens())
                    break;
                int k = flags.getValue(stringtokenizer.nextToken());
                if(k < 0)
                {
                    j = -1;
                    continue; /* Loop/switch isn't completed */
                }
                i |= k;
            } while(true);
            j = i;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public static final int EXTEND = 4096;
        public static final int FLAG10 = 32;
        public static final int FLAG11 = 16;
        public static final int FLAG2 = 8192;
        public static final int FLAG4 = 2048;
        public static final int FLAG5 = 1024;
        public static final int FLAG8 = 128;
        public static final int FLAG9 = 64;
        public static final int HOST = 512;
        public static final int NOAUTH = 32768;
        public static final int NOCONF = 16384;
        public static final int NOKEY = 49152;
        public static final int NTYP3 = 768;
        public static final int OWNER_MASK = 768;
        public static final int SIG0 = 0;
        public static final int SIG1 = 1;
        public static final int SIG10 = 10;
        public static final int SIG11 = 11;
        public static final int SIG12 = 12;
        public static final int SIG13 = 13;
        public static final int SIG14 = 14;
        public static final int SIG15 = 15;
        public static final int SIG2 = 2;
        public static final int SIG3 = 3;
        public static final int SIG4 = 4;
        public static final int SIG5 = 5;
        public static final int SIG6 = 6;
        public static final int SIG7 = 7;
        public static final int SIG8 = 8;
        public static final int SIG9 = 9;
        public static final int USER = 0;
        public static final int USE_MASK = 49152;
        public static final int ZONE = 256;
        private static Mnemonic flags;

        static 
        {
            flags = new Mnemonic("KEY flags", 2);
            flags.setMaximum(65535);
            flags.setNumericAllowed(false);
            flags.add(16384, "NOCONF");
            flags.add(32768, "NOAUTH");
            flags.add(49152, "NOKEY");
            flags.add(8192, "FLAG2");
            flags.add(4096, "EXTEND");
            flags.add(2048, "FLAG4");
            flags.add(1024, "FLAG5");
            flags.add(0, "USER");
            flags.add(256, "ZONE");
            flags.add(512, "HOST");
            flags.add(768, "NTYP3");
            flags.add(128, "FLAG8");
            flags.add(64, "FLAG9");
            flags.add(32, "FLAG10");
            flags.add(16, "FLAG11");
            flags.add(0, "SIG0");
            flags.add(1, "SIG1");
            flags.add(2, "SIG2");
            flags.add(3, "SIG3");
            flags.add(4, "SIG4");
            flags.add(5, "SIG5");
            flags.add(6, "SIG6");
            flags.add(7, "SIG7");
            flags.add(8, "SIG8");
            flags.add(9, "SIG9");
            flags.add(10, "SIG10");
            flags.add(11, "SIG11");
            flags.add(12, "SIG12");
            flags.add(13, "SIG13");
            flags.add(14, "SIG14");
            flags.add(15, "SIG15");
        }

        private Flags()
        {
        }
    }

    public static class Protocol
    {

        public static String string(int i)
        {
            return protocols.getText(i);
        }

        public static int value(String s)
        {
            return protocols.getValue(s);
        }

        public static final int ANY = 255;
        public static final int DNSSEC = 3;
        public static final int EMAIL = 2;
        public static final int IPSEC = 4;
        public static final int NONE = 0;
        public static final int TLS = 1;
        private static Mnemonic protocols;

        static 
        {
            protocols = new Mnemonic("KEY protocol", 2);
            protocols.setMaximum(255);
            protocols.setNumericAllowed(true);
            protocols.add(0, "NONE");
            protocols.add(1, "TLS");
            protocols.add(2, "EMAIL");
            protocols.add(3, "DNSSEC");
            protocols.add(4, "IPSEC");
            protocols.add(255, "ANY");
        }

        private Protocol()
        {
        }
    }


    KEYRecord()
    {
    }

    public KEYRecord(Name name, int i, long l, int j, int k, int i1, 
            PublicKey publickey)
        throws DNSSEC.DNSSECException
    {
        KEYBase(name, 25, i, l, j, k, i1, DNSSEC.fromPublicKey(publickey, i1));
        publicKey = publickey;
    }

    public KEYRecord(Name name, int i, long l, int j, int k, int i1, 
            byte abyte0[])
    {
        KEYBase(name, 25, i, l, j, k, i1, abyte0);
    }

    public volatile int getAlgorithm()
    {
        return getAlgorithm();
    }

    public volatile int getFlags()
    {
        return getFlags();
    }

    public volatile int getFootprint()
    {
        return getFootprint();
    }

    public volatile byte[] getKey()
    {
        return getKey();
    }

    Record getObject()
    {
        return new KEYRecord();
    }

    public volatile int getProtocol()
    {
        return getProtocol();
    }

    public volatile PublicKey getPublicKey()
        throws DNSSEC.DNSSECException
    {
        return getPublicKey();
    }

    void rdataFromString(Tokenizer tokenizer, Name name)
        throws IOException
    {
        String s = tokenizer.getIdentifier();
        flags = Flags.value(s);
        if(flags < 0)
            throw tokenizer.exception((new StringBuilder()).append("Invalid flags: ").append(s).toString());
        String s1 = tokenizer.getIdentifier();
        proto = Protocol.value(s1);
        if(proto < 0)
            throw tokenizer.exception((new StringBuilder()).append("Invalid protocol: ").append(s1).toString());
        String s2 = tokenizer.getIdentifier();
        alg = DNSSEC.Algorithm.value(s2);
        if(alg < 0)
            throw tokenizer.exception((new StringBuilder()).append("Invalid algorithm: ").append(s2).toString());
        if((0xc000 & flags) == 49152)
            key = null;
        else
            key = tokenizer.getBase64();
    }

    public static final int FLAG_NOAUTH = 32768;
    public static final int FLAG_NOCONF = 16384;
    public static final int FLAG_NOKEY = 49152;
    public static final int OWNER_HOST = 512;
    public static final int OWNER_USER = 0;
    public static final int OWNER_ZONE = 256;
    public static final int PROTOCOL_ANY = 255;
    public static final int PROTOCOL_DNSSEC = 3;
    public static final int PROTOCOL_EMAIL = 2;
    public static final int PROTOCOL_IPSEC = 4;
    public static final int PROTOCOL_TLS = 1;
    private static final long serialVersionUID = 0x1f74c08aL;
}
