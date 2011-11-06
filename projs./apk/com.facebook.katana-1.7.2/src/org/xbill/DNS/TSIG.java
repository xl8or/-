// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TSIG.java

package org.xbill.DNS;

import java.io.PrintStream;
import java.util.Date;
import org.xbill.DNS.utils.HMAC;
import org.xbill.DNS.utils.base64;

// Referenced classes of package org.xbill.DNS:
//            Name, TextParseException, Message, Options, 
//            DNSOutput, TSIGRecord, Header

public class TSIG
{
    public static class StreamVerifier
    {

        public int verify(Message message, byte abyte0[])
        {
            TSIGRecord tsigrecord = message.getTSIG();
            nresponses = 1 + nresponses;
            int j;
            if(nresponses == 1)
            {
                int i1 = key.verify(message, abyte0, lastTSIG);
                if(i1 == 0)
                {
                    byte abyte2[] = tsigrecord.getSignature();
                    DNSOutput dnsoutput2 = new DNSOutput();
                    dnsoutput2.writeU16(abyte2.length);
                    verifier.update(dnsoutput2.toByteArray());
                    verifier.update(abyte2);
                }
                lastTSIG = tsigrecord;
                j = i1;
            } else
            {
                if(tsigrecord != null)
                    message.getHeader().decCount(3);
                byte abyte1[] = message.getHeader().toWire();
                if(tsigrecord != null)
                    message.getHeader().incCount(3);
                verifier.update(abyte1);
                int i;
                if(tsigrecord == null)
                    i = abyte0.length - abyte1.length;
                else
                    i = message.tsigstart - abyte1.length;
                verifier.update(abyte0, abyte1.length, i);
                if(tsigrecord != null)
                {
                    lastsigned = nresponses;
                    lastTSIG = tsigrecord;
                    boolean flag;
                    if(!tsigrecord.getName().equals(key.name) || !tsigrecord.getAlgorithm().equals(key.alg))
                    {
                        if(Options.check("verbose"))
                            System.err.println("BADKEY failure");
                        message.tsigState = 4;
                        j = 17;
                    } else
                    {
                        DNSOutput dnsoutput = new DNSOutput();
                        long l = tsigrecord.getTimeSigned().getTime() / 1000L;
                        int k = (int)(l >> 32);
                        long l1 = l & 0xffffffffL;
                        dnsoutput.writeU16(k);
                        dnsoutput.writeU32(l1);
                        dnsoutput.writeU16(tsigrecord.getFudge());
                        verifier.update(dnsoutput.toByteArray());
                        if(!verifier.verify(tsigrecord.getSignature()))
                        {
                            if(Options.check("verbose"))
                                System.err.println("BADSIG failure");
                            j = 16;
                        } else
                        {
                            verifier.clear();
                            DNSOutput dnsoutput1 = new DNSOutput();
                            dnsoutput1.writeU16(tsigrecord.getSignature().length);
                            verifier.update(dnsoutput1.toByteArray());
                            verifier.update(tsigrecord.getSignature());
                            j = 0;
                        }
                    }
                } else
                {
                    if(nresponses - lastsigned >= 100)
                        flag = true;
                    else
                        flag = false;
                    if(flag)
                    {
                        message.tsigState = 4;
                        j = 1;
                    } else
                    {
                        message.tsigState = 2;
                        j = 0;
                    }
                }
            }
            return j;
        }

        private TSIG key;
        private TSIGRecord lastTSIG;
        private int lastsigned;
        private int nresponses;
        private HMAC verifier;

        public StreamVerifier(TSIG tsig, TSIGRecord tsigrecord)
        {
            key = tsig;
            verifier = new HMAC(key.digest, key.key);
            nresponses = 0;
            lastTSIG = tsigrecord;
        }
    }


    public TSIG(String s, String s1)
    {
        this(HMAC_MD5, s, s1);
    }

    public TSIG(String s, String s1, String s2)
    {
        this(HMAC_MD5, s1, s2);
        if(s.equalsIgnoreCase("hmac-md5"))
            alg = HMAC_MD5;
        else
        if(s.equalsIgnoreCase("hmac-sha1"))
            alg = HMAC_SHA1;
        else
        if(s.equalsIgnoreCase("hmac-sha256"))
            alg = HMAC_SHA256;
        else
            throw new IllegalArgumentException("Invalid TSIG algorithm");
        getDigest();
    }

    public TSIG(Name name1, String s, String s1)
    {
        key = base64.fromString(s1);
        if(key == null)
            throw new IllegalArgumentException("Invalid TSIG key string");
        try
        {
            name = Name.fromString(s, Name.root);
        }
        catch(TextParseException textparseexception)
        {
            throw new IllegalArgumentException("Invalid TSIG key name");
        }
        alg = name1;
        getDigest();
    }

    public TSIG(Name name1, Name name2, byte abyte0[])
    {
        name = name2;
        alg = name1;
        key = abyte0;
        getDigest();
    }

    public TSIG(Name name1, byte abyte0[])
    {
        this(HMAC_MD5, name1, abyte0);
    }

    public static TSIG fromString(String s)
    {
        String as[] = s.split("[:/]");
        if(as.length < 2 || as.length > 3)
            throw new IllegalArgumentException("Invalid TSIG key specification");
        TSIG tsig;
        if(as.length == 3)
            tsig = new TSIG(as[0], as[1], as[2]);
        else
            tsig = new TSIG(HMAC_MD5, as[0], as[1]);
        return tsig;
    }

    private void getDigest()
    {
        if(alg.equals(HMAC_MD5))
            digest = "md5";
        else
        if(alg.equals(HMAC_SHA1))
            digest = "sha-1";
        else
        if(alg.equals(HMAC_SHA256))
            digest = "sha-256";
        else
            throw new IllegalArgumentException("Invalid algorithm");
    }

    public void apply(Message message, int i, TSIGRecord tsigrecord)
    {
        message.addRecord(generate(message, message.toWire(), i, tsigrecord), 3);
        message.tsigState = 3;
    }

    public void apply(Message message, TSIGRecord tsigrecord)
    {
        apply(message, 0, tsigrecord);
    }

    public void applyStream(Message message, TSIGRecord tsigrecord, boolean flag)
    {
        if(flag)
        {
            apply(message, tsigrecord);
        } else
        {
            Date date = new Date();
            HMAC hmac = new HMAC(digest, key);
            int i = Options.intValue("tsigfudge");
            int j;
            DNSOutput dnsoutput;
            DNSOutput dnsoutput1;
            long l;
            int k;
            long l1;
            byte abyte0[];
            if(i < 0 || i > 32767)
                j = 300;
            else
                j = i;
            dnsoutput = new DNSOutput();
            dnsoutput.writeU16(tsigrecord.getSignature().length);
            hmac.update(dnsoutput.toByteArray());
            hmac.update(tsigrecord.getSignature());
            hmac.update(message.toWire());
            dnsoutput1 = new DNSOutput();
            l = date.getTime() / 1000L;
            k = (int)(l >> 32);
            l1 = l & 0xffffffffL;
            dnsoutput1.writeU16(k);
            dnsoutput1.writeU32(l1);
            dnsoutput1.writeU16(j);
            hmac.update(dnsoutput1.toByteArray());
            abyte0 = hmac.sign();
            message.addRecord(new TSIGRecord(name, 255, 0L, alg, date, j, abyte0, message.getHeader().getID(), 0, null), 3);
            message.tsigState = 3;
        }
    }

    public TSIGRecord generate(Message message, byte abyte0[], int i, TSIGRecord tsigrecord)
    {
        Date date;
        HMAC hmac;
        int j;
        int k;
        DNSOutput dnsoutput1;
        long l;
        int i1;
        long l1;
        byte abyte1[];
        byte abyte2[];
        if(i != 18)
            date = new Date();
        else
            date = tsigrecord.getTimeSigned();
        hmac = null;
        if(i == 0 || i == 18)
            hmac = new HMAC(digest, key);
        j = Options.intValue("tsigfudge");
        if(j < 0 || j > 32767)
            k = 300;
        else
            k = j;
        if(tsigrecord != null)
        {
            DNSOutput dnsoutput = new DNSOutput();
            dnsoutput.writeU16(tsigrecord.getSignature().length);
            if(hmac != null)
            {
                hmac.update(dnsoutput.toByteArray());
                hmac.update(tsigrecord.getSignature());
            }
        }
        if(hmac != null)
            hmac.update(abyte0);
        dnsoutput1 = new DNSOutput();
        name.toWireCanonical(dnsoutput1);
        dnsoutput1.writeU16(255);
        dnsoutput1.writeU32(0L);
        alg.toWireCanonical(dnsoutput1);
        l = date.getTime() / 1000L;
        i1 = (int)(l >> 32);
        l1 = l & 0xffffffffL;
        dnsoutput1.writeU16(i1);
        dnsoutput1.writeU32(l1);
        dnsoutput1.writeU16(k);
        dnsoutput1.writeU16(i);
        dnsoutput1.writeU16(0);
        if(hmac != null)
            hmac.update(dnsoutput1.toByteArray());
        if(hmac != null)
            abyte1 = hmac.sign();
        else
            abyte1 = new byte[0];
        if(i == 18)
        {
            DNSOutput dnsoutput2 = new DNSOutput();
            long l2 = (new Date()).getTime() / 1000L;
            int j1 = (int)(l2 >> 32);
            long l3 = l2 & 0xffffffffL;
            dnsoutput2.writeU16(j1);
            dnsoutput2.writeU32(l3);
            abyte2 = dnsoutput2.toByteArray();
        } else
        {
            abyte2 = null;
        }
        return new TSIGRecord(name, 255, 0L, alg, date, k, abyte1, message.getHeader().getID(), i, abyte2);
    }

    public int recordLength()
    {
        return 8 + (4 + (18 + (8 + (10 + name.length() + alg.length()))));
    }

    public byte verify(Message message, byte abyte0[], int i, TSIGRecord tsigrecord)
    {
        message.tsigState = 4;
        TSIGRecord tsigrecord1 = message.getTSIG();
        HMAC hmac = new HMAC(digest, key);
        byte byte0;
        if(tsigrecord1 == null)
            byte0 = 1;
        else
        if(!tsigrecord1.getName().equals(name) || !tsigrecord1.getAlgorithm().equals(alg))
        {
            if(Options.check("verbose"))
                System.err.println("BADKEY failure");
            byte0 = 17;
        } else
        {
            long l = System.currentTimeMillis();
            long l1 = tsigrecord1.getTimeSigned().getTime();
            long l2 = tsigrecord1.getFudge();
            if(Math.abs(l - l1) > 1000L * l2)
            {
                if(Options.check("verbose"))
                    System.err.println("BADTIME failure");
                byte0 = 18;
            } else
            {
                if(tsigrecord != null && tsigrecord1.getError() != 17 && tsigrecord1.getError() != 16)
                {
                    DNSOutput dnsoutput1 = new DNSOutput();
                    dnsoutput1.writeU16(tsigrecord.getSignature().length);
                    hmac.update(dnsoutput1.toByteArray());
                    hmac.update(tsigrecord.getSignature());
                }
                message.getHeader().decCount(3);
                byte abyte1[] = message.getHeader().toWire();
                message.getHeader().incCount(3);
                hmac.update(abyte1);
                int j = message.tsigstart - abyte1.length;
                hmac.update(abyte0, abyte1.length, j);
                DNSOutput dnsoutput = new DNSOutput();
                tsigrecord1.getName().toWireCanonical(dnsoutput);
                dnsoutput.writeU16(tsigrecord1.dclass);
                dnsoutput.writeU32(tsigrecord1.ttl);
                tsigrecord1.getAlgorithm().toWireCanonical(dnsoutput);
                long l3 = tsigrecord1.getTimeSigned().getTime() / 1000L;
                int k = (int)(l3 >> 32);
                long l4 = l3 & 0xffffffffL;
                dnsoutput.writeU16(k);
                dnsoutput.writeU32(l4);
                dnsoutput.writeU16(tsigrecord1.getFudge());
                dnsoutput.writeU16(tsigrecord1.getError());
                if(tsigrecord1.getOther() != null)
                {
                    dnsoutput.writeU16(tsigrecord1.getOther().length);
                    dnsoutput.writeByteArray(tsigrecord1.getOther());
                } else
                {
                    dnsoutput.writeU16(0);
                }
                hmac.update(dnsoutput.toByteArray());
                if(hmac.verify(tsigrecord1.getSignature()))
                {
                    message.tsigState = 1;
                    byte0 = 0;
                } else
                {
                    if(Options.check("verbose"))
                        System.err.println("BADSIG failure");
                    byte0 = 16;
                }
            }
        }
        return byte0;
    }

    public int verify(Message message, byte abyte0[], TSIGRecord tsigrecord)
    {
        return verify(message, abyte0, abyte0.length, tsigrecord);
    }

    public static final short FUDGE = 300;
    public static final Name HMAC;
    public static final Name HMAC_MD5;
    private static final String HMAC_MD5_STR = "HMAC-MD5.SIG-ALG.REG.INT.";
    public static final Name HMAC_SHA1 = Name.fromConstantString("hmac-sha1.");
    private static final String HMAC_SHA1_STR = "hmac-sha1.";
    public static final Name HMAC_SHA256 = Name.fromConstantString("hmac-sha256.");
    private static final String HMAC_SHA256_STR = "hmac-sha256.";
    private Name alg;
    private String digest;
    private byte key[];
    private Name name;

    static 
    {
        HMAC_MD5 = Name.fromConstantString("HMAC-MD5.SIG-ALG.REG.INT.");
        HMAC = HMAC_MD5;
    }




}
