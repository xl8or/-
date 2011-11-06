// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DNSSEC.java

package org.xbill.DNS;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            DNSInput, DNSOutput, Message, RRset, 
//            Record, RRSIGRecord, Name, SIGBase, 
//            DNSKEYRecord, KEYBase, KEYRecord, SIGRecord, 
//            Header, Mnemonic

public class DNSSEC
{
    public static class IncompatibleKeyException extends IllegalArgumentException
    {

        IncompatibleKeyException()
        {
            super("incompatible keys");
        }
    }

    public static class SignatureVerificationException extends DNSSECException
    {

        SignatureVerificationException()
        {
            super("signature verification failed");
        }
    }

    public static class SignatureNotYetValidException extends DNSSECException
    {

        public Date getExpiration()
        {
            return when;
        }

        public Date getVerifyTime()
        {
            return now;
        }

        private Date now;
        private Date when;

        SignatureNotYetValidException(Date date, Date date1)
        {
            super("signature is not yet valid");
            when = date;
            now = date1;
        }
    }

    public static class SignatureExpiredException extends DNSSECException
    {

        public Date getExpiration()
        {
            return when;
        }

        public Date getVerifyTime()
        {
            return now;
        }

        private Date now;
        private Date when;

        SignatureExpiredException(Date date, Date date1)
        {
            super("signature expired");
            when = date;
            now = date1;
        }
    }

    public static class KeyMismatchException extends DNSSECException
    {

        private KEYBase key;
        private SIGBase sig;

        KeyMismatchException(KEYBase keybase, SIGBase sigbase)
        {
            super((new StringBuilder()).append("key ").append(keybase.getName()).append("/").append(Algorithm.string(keybase.getAlgorithm())).append("/").append(keybase.getFootprint()).append(" ").append("does not match signature ").append(sigbase.getSigner()).append("/").append(Algorithm.string(sigbase.getAlgorithm())).append("/").append(sigbase.getFootprint()).toString());
        }
    }

    public static class MalformedKeyException extends DNSSECException
    {

        MalformedKeyException(KEYBase keybase)
        {
            super((new StringBuilder()).append("Invalid key data: ").append(keybase.rdataToString()).toString());
        }
    }

    public static class UnsupportedAlgorithmException extends DNSSECException
    {

        UnsupportedAlgorithmException(int i)
        {
            super((new StringBuilder()).append("Unsupported algorithm: ").append(i).toString());
        }
    }

    public static class DNSSECException extends Exception
    {

        DNSSECException(String s)
        {
            super(s);
        }
    }

    public static class Algorithm
    {

        public static String string(int i)
        {
            return algs.getText(i);
        }

        public static int value(String s)
        {
            return algs.getValue(s);
        }

        public static final int DH = 2;
        public static final int DSA = 3;
        public static final int DSA_NSEC3_SHA1 = 6;
        public static final int ECC = 4;
        public static final int INDIRECT = 252;
        public static final int PRIVATEDNS = 253;
        public static final int PRIVATEOID = 254;
        public static final int RSAMD5 = 1;
        public static final int RSASHA1 = 5;
        public static final int RSASHA256 = 8;
        public static final int RSASHA512 = 10;
        public static final int RSA_NSEC3_SHA1 = 7;
        private static Mnemonic algs;

        static 
        {
            algs = new Mnemonic("DNSSEC algorithm", 2);
            algs.setMaximum(255);
            algs.setNumericAllowed(true);
            algs.add(1, "RSAMD5");
            algs.add(2, "DH");
            algs.add(3, "DSA");
            algs.add(4, "ECC");
            algs.add(5, "RSASHA1");
            algs.add(6, "DSA-NSEC3-SHA1");
            algs.add(7, "RSA-NSEC3-SHA1");
            algs.add(8, "RSASHA256");
            algs.add(10, "RSASHA512");
            algs.add(252, "INDIRECT");
            algs.add(253, "PRIVATEDNS");
            algs.add(254, "PRIVATEOID");
        }

        private Algorithm()
        {
        }
    }


    private DNSSEC()
    {
    }

    private static int BigIntegerLength(BigInteger biginteger)
    {
        return (7 + biginteger.bitLength()) / 8;
    }

    private static byte[] DSASignaturefromDNS(byte abyte0[])
        throws DNSSECException, IOException
    {
        if(abyte0.length != 41)
            throw new SignatureVerificationException();
        DNSInput dnsinput = new DNSInput(abyte0);
        DNSOutput dnsoutput = new DNSOutput();
        dnsinput.readU8();
        byte abyte1[] = dnsinput.readByteArray(20);
        int i;
        byte abyte2[];
        int j;
        if(abyte1[0] < 0)
            i = 20 + 1;
        else
            i = 20;
        abyte2 = dnsinput.readByteArray(20);
        if(abyte2[0] < 0)
            j = 20 + 1;
        else
            j = 20;
        dnsoutput.writeU8(48);
        dnsoutput.writeU8(4 + (i + j));
        dnsoutput.writeU8(2);
        dnsoutput.writeU8(i);
        if(i > 20)
            dnsoutput.writeU8(0);
        dnsoutput.writeByteArray(abyte1);
        dnsoutput.writeU8(2);
        dnsoutput.writeU8(j);
        if(j > 20)
            dnsoutput.writeU8(0);
        dnsoutput.writeByteArray(abyte2);
        return dnsoutput.toByteArray();
    }

    private static byte[] DSASignaturetoDNS(byte abyte0[], int i)
        throws IOException
    {
        DNSInput dnsinput = new DNSInput(abyte0);
        DNSOutput dnsoutput = new DNSOutput();
        dnsoutput.writeU8(i);
        if(dnsinput.readU8() != 48)
            throw new IOException();
        dnsinput.readU8();
        if(dnsinput.readU8() != 2)
            throw new IOException();
        int j = dnsinput.readU8();
        if(j == 21)
        {
            if(dnsinput.readU8() != 0)
                throw new IOException();
        } else
        if(j != 20)
            throw new IOException();
        dnsoutput.writeByteArray(dnsinput.readByteArray(20));
        if(dnsinput.readU8() != 2)
            throw new IOException();
        int k = dnsinput.readU8();
        if(k == 21)
        {
            if(dnsinput.readU8() != 0)
                throw new IOException();
        } else
        if(k != 20)
            throw new IOException();
        dnsoutput.writeByteArray(dnsinput.readByteArray(20));
        return dnsoutput.toByteArray();
    }

    private static String algString(int i)
        throws UnsupportedAlgorithmException
    {
        i;
        JVM INSTR tableswitch 1 10: default 56
    //                   1 65
    //                   2 56
    //                   3 70
    //                   4 56
    //                   5 76
    //                   6 70
    //                   7 76
    //                   8 82
    //                   9 56
    //                   10 88;
           goto _L1 _L2 _L1 _L3 _L1 _L4 _L3 _L4 _L5 _L1 _L6
_L1:
        throw new UnsupportedAlgorithmException(i);
_L2:
        String s = "MD5withRSA";
_L8:
        return s;
_L3:
        s = "SHA1withDSA";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "SHA1withRSA";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "SHA256withRSA";
        continue; /* Loop/switch isn't completed */
_L6:
        s = "SHA512withRSA";
        if(true) goto _L8; else goto _L7
_L7:
    }

    static void checkAlgorithm(PrivateKey privatekey, int i)
        throws UnsupportedAlgorithmException
    {
        switch(i)
        {
        case 2: // '\002'
        case 4: // '\004'
        case 9: // '\t'
        default:
            throw new UnsupportedAlgorithmException(i);

        case 1: // '\001'
        case 5: // '\005'
        case 7: // '\007'
        case 8: // '\b'
        case 10: // '\n'
            if(!(privatekey instanceof RSAPrivateKey))
                throw new IncompatibleKeyException();
            break;

        case 3: // '\003'
        case 6: // '\006'
            if(!(privatekey instanceof DSAPrivateKey))
                throw new IncompatibleKeyException();
            break;
        }
    }

    public static byte[] digestMessage(SIGRecord sigrecord, Message message, byte abyte0[])
    {
        DNSOutput dnsoutput = new DNSOutput();
        digestSIG(dnsoutput, sigrecord);
        if(abyte0 != null)
            dnsoutput.writeByteArray(abyte0);
        message.toWire(dnsoutput);
        return dnsoutput.toByteArray();
    }

    public static byte[] digestRRset(RRSIGRecord rrsigrecord, RRset rrset)
    {
        DNSOutput dnsoutput = new DNSOutput();
        digestSIG(dnsoutput, rrsigrecord);
        int i = rrset.size();
        Record arecord[] = new Record[i];
        Iterator iterator = rrset.rrs();
        Name name = rrset.getName();
        Name name1 = null;
        int j = 1 + rrsigrecord.getLabels();
        if(name.labels() > j)
            name1 = name.wild(name.labels() - j);
        while(iterator.hasNext()) 
        {
            int j1 = i + -1;
            arecord[j1] = (Record)iterator.next();
            i = j1;
        }
        Arrays.sort(arecord);
        DNSOutput dnsoutput1 = new DNSOutput();
        if(name1 != null)
            name1.toWireCanonical(dnsoutput1);
        else
            name.toWireCanonical(dnsoutput1);
        dnsoutput1.writeU16(rrset.getType());
        dnsoutput1.writeU16(rrset.getDClass());
        dnsoutput1.writeU32(rrsigrecord.getOrigTTL());
        for(int k = 0; k < arecord.length; k++)
        {
            dnsoutput.writeByteArray(dnsoutput1.toByteArray());
            int l = dnsoutput.current();
            dnsoutput.writeU16(0);
            dnsoutput.writeByteArray(arecord[k].rdataToWireCanonical());
            int i1 = dnsoutput.current() - l - 2;
            dnsoutput.save();
            dnsoutput.jump(l);
            dnsoutput.writeU16(i1);
            dnsoutput.restore();
        }

        return dnsoutput.toByteArray();
    }

    private static void digestSIG(DNSOutput dnsoutput, SIGBase sigbase)
    {
        dnsoutput.writeU16(sigbase.getTypeCovered());
        dnsoutput.writeU8(sigbase.getAlgorithm());
        dnsoutput.writeU8(sigbase.getLabels());
        dnsoutput.writeU32(sigbase.getOrigTTL());
        dnsoutput.writeU32(sigbase.getExpire().getTime() / 1000L);
        dnsoutput.writeU32(sigbase.getTimeSigned().getTime() / 1000L);
        dnsoutput.writeU16(sigbase.getFootprint());
        sigbase.getSigner().toWireCanonical(dnsoutput);
    }

    private static byte[] fromDSAPublicKey(DSAPublicKey dsapublickey)
    {
        DNSOutput dnsoutput = new DNSOutput();
        BigInteger biginteger = dsapublickey.getParams().getQ();
        BigInteger biginteger1 = dsapublickey.getParams().getP();
        BigInteger biginteger2 = dsapublickey.getParams().getG();
        BigInteger biginteger3 = dsapublickey.getY();
        dnsoutput.writeU8((biginteger1.toByteArray().length - 64) / 8);
        writeBigInteger(dnsoutput, biginteger);
        writeBigInteger(dnsoutput, biginteger1);
        writeBigInteger(dnsoutput, biginteger2);
        writeBigInteger(dnsoutput, biginteger3);
        return dnsoutput.toByteArray();
    }

    static byte[] fromPublicKey(PublicKey publickey, int i)
        throws DNSSECException
    {
        i;
        JVM INSTR tableswitch 1 10: default 56
    //                   1 65
    //                   2 56
    //                   3 90
    //                   4 56
    //                   5 65
    //                   6 90
    //                   7 65
    //                   8 65
    //                   9 56
    //                   10 65;
           goto _L1 _L2 _L1 _L3 _L1 _L2 _L3 _L2 _L2 _L1 _L2
_L1:
        throw new UnsupportedAlgorithmException(i);
_L2:
        byte abyte0[];
        if(!(publickey instanceof RSAPublicKey))
            throw new IncompatibleKeyException();
        abyte0 = fromRSAPublicKey((RSAPublicKey)publickey);
_L5:
        return abyte0;
_L3:
        if(!(publickey instanceof DSAPublicKey))
            throw new IncompatibleKeyException();
        abyte0 = fromDSAPublicKey((DSAPublicKey)publickey);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static byte[] fromRSAPublicKey(RSAPublicKey rsapublickey)
    {
        DNSOutput dnsoutput = new DNSOutput();
        BigInteger biginteger = rsapublickey.getPublicExponent();
        BigInteger biginteger1 = rsapublickey.getModulus();
        int i = BigIntegerLength(biginteger);
        if(i < 256)
        {
            dnsoutput.writeU8(i);
        } else
        {
            dnsoutput.writeU8(0);
            dnsoutput.writeU16(i);
        }
        writeBigInteger(dnsoutput, biginteger);
        writeBigInteger(dnsoutput, biginteger1);
        return dnsoutput.toByteArray();
    }

    static byte[] generateDS(DNSKEYRecord dnskeyrecord, int i)
    {
        DNSOutput dnsoutput;
        dnsoutput = new DNSOutput();
        dnsoutput.writeU16(dnskeyrecord.getFootprint());
        dnsoutput.writeU8(dnskeyrecord.getAlgorithm());
        dnsoutput.writeU8(i);
        i;
        JVM INSTR tableswitch 1 2: default 52
    //                   1 93
    //                   2 140;
           goto _L1 _L2 _L3
_L1:
        try
        {
            throw new IllegalArgumentException((new StringBuilder()).append("unknown DS digest type ").append(i).toString());
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new IllegalStateException("no message digest support");
        }
_L2:
        MessageDigest messagedigest2 = MessageDigest.getInstance("sha-1");
        MessageDigest messagedigest1 = messagedigest2;
_L5:
        messagedigest1.update(dnskeyrecord.getName().toWire());
        messagedigest1.update(dnskeyrecord.rdataToWireCanonical());
        dnsoutput.writeByteArray(messagedigest1.digest());
        return dnsoutput.toByteArray();
_L3:
        MessageDigest messagedigest = MessageDigest.getInstance("sha-256");
        messagedigest1 = messagedigest;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static boolean matches(SIGBase sigbase, KEYBase keybase)
    {
        boolean flag;
        if(keybase.getAlgorithm() == sigbase.getAlgorithm() && keybase.getFootprint() == sigbase.getFootprint() && keybase.getName().equals(sigbase.getSigner()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static BigInteger readBigInteger(DNSInput dnsinput)
    {
        return new BigInteger(1, dnsinput.readByteArray());
    }

    private static BigInteger readBigInteger(DNSInput dnsinput, int i)
        throws IOException
    {
        return new BigInteger(1, dnsinput.readByteArray(i));
    }

    public static RRSIGRecord sign(RRset rrset, DNSKEYRecord dnskeyrecord, PrivateKey privatekey, Date date, Date date1)
        throws DNSSECException
    {
        int i = dnskeyrecord.getAlgorithm();
        checkAlgorithm(privatekey, i);
        RRSIGRecord rrsigrecord = new RRSIGRecord(rrset.getName(), rrset.getDClass(), rrset.getTTL(), rrset.getType(), i, rrset.getTTL(), date1, date, dnskeyrecord.getFootprint(), dnskeyrecord.getName(), null);
        rrsigrecord.setSignature(sign(privatekey, dnskeyrecord.getPublicKey(), i, digestRRset(rrsigrecord, rrset)));
        return rrsigrecord;
    }

    private static byte[] sign(PrivateKey privatekey, PublicKey publickey, int i, byte abyte0[])
        throws DNSSECException
    {
        byte abyte1[];
        byte abyte2[];
        try
        {
            Signature signature = Signature.getInstance(algString(i));
            signature.initSign(privatekey);
            signature.update(abyte0);
            abyte1 = signature.sign();
        }
        catch(GeneralSecurityException generalsecurityexception)
        {
            throw new DNSSECException(generalsecurityexception.toString());
        }
        abyte2 = abyte1;
        if(publickey instanceof DSAPublicKey)
        {
            byte abyte3[];
            try
            {
                abyte3 = DSASignaturetoDNS(abyte2, (BigIntegerLength(((DSAPublicKey)publickey).getParams().getP()) - 64) / 8);
            }
            catch(IOException ioexception)
            {
                throw new IllegalStateException();
            }
            abyte2 = abyte3;
        }
        return abyte2;
    }

    static SIGRecord signMessage(Message message, SIGRecord sigrecord, KEYRecord keyrecord, PrivateKey privatekey, Date date, Date date1)
        throws DNSSECException
    {
        int i = keyrecord.getAlgorithm();
        checkAlgorithm(privatekey, i);
        SIGRecord sigrecord1 = new SIGRecord(Name.root, 255, 0L, 0, i, 0L, date1, date, keyrecord.getFootprint(), keyrecord.getName(), null);
        DNSOutput dnsoutput = new DNSOutput();
        digestSIG(dnsoutput, sigrecord1);
        if(sigrecord != null)
            dnsoutput.writeByteArray(sigrecord.getSignature());
        message.toWire(dnsoutput);
        sigrecord1.setSignature(sign(privatekey, keyrecord.getPublicKey(), i, dnsoutput.toByteArray()));
        return sigrecord1;
    }

    private static PublicKey toDSAPublicKey(KEYBase keybase)
        throws IOException, GeneralSecurityException, MalformedKeyException
    {
        DNSInput dnsinput = new DNSInput(keybase.getKey());
        int i = dnsinput.readU8();
        if(i > 8)
        {
            throw new MalformedKeyException(keybase);
        } else
        {
            BigInteger biginteger = readBigInteger(dnsinput, 20);
            BigInteger biginteger1 = readBigInteger(dnsinput, 64 + i * 8);
            BigInteger biginteger2 = readBigInteger(dnsinput, 64 + i * 8);
            BigInteger biginteger3 = readBigInteger(dnsinput, 64 + i * 8);
            return KeyFactory.getInstance("DSA").generatePublic(new DSAPublicKeySpec(biginteger3, biginteger1, biginteger, biginteger2));
        }
    }

    static PublicKey toPublicKey(KEYBase keybase)
        throws DNSSECException
    {
        int i = keybase.getAlgorithm();
        i;
        JVM INSTR tableswitch 1 10: default 60
    //                   1 80
    //                   2 60
    //                   3 89
    //                   4 60
    //                   5 80
    //                   6 89
    //                   7 80
    //                   8 80
    //                   9 60
    //                   10 80;
           goto _L1 _L2 _L1 _L3 _L1 _L2 _L3 _L2 _L2 _L1 _L2
_L1:
        PublicKey publickey;
        PublicKey publickey1;
        try
        {
            throw new UnsupportedAlgorithmException(i);
        }
        catch(IOException ioexception)
        {
            throw new MalformedKeyException(keybase);
        }
        catch(GeneralSecurityException generalsecurityexception)
        {
            throw new DNSSECException(generalsecurityexception.toString());
        }
_L2:
        publickey1 = toRSAPublicKey(keybase);
        break; /* Loop/switch isn't completed */
_L3:
        publickey = toDSAPublicKey(keybase);
        publickey1 = publickey;
        return publickey1;
    }

    private static PublicKey toRSAPublicKey(KEYBase keybase)
        throws IOException, GeneralSecurityException
    {
        DNSInput dnsinput = new DNSInput(keybase.getKey());
        int i = dnsinput.readU8();
        if(i == 0)
            i = dnsinput.readU16();
        BigInteger biginteger = readBigInteger(dnsinput, i);
        BigInteger biginteger1 = readBigInteger(dnsinput);
        return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(biginteger1, biginteger));
    }

    private static void verify(PublicKey publickey, int i, byte abyte0[], byte abyte1[])
        throws DNSSECException
    {
        byte abyte2[];
        if(publickey instanceof DSAPublicKey)
        {
            GeneralSecurityException generalsecurityexception;
            Signature signature;
            byte abyte3[];
            try
            {
                abyte3 = DSASignaturefromDNS(abyte1);
            }
            catch(IOException ioexception)
            {
                throw new IllegalStateException();
            }
            abyte2 = abyte3;
        } else
        {
            abyte2 = abyte1;
        }
        try
        {
            signature = Signature.getInstance(algString(i));
            signature.initVerify(publickey);
            signature.update(abyte0);
            if(!signature.verify(abyte2))
                throw new SignatureVerificationException();
        }
        // Misplaced declaration of an exception variable
        catch(GeneralSecurityException generalsecurityexception)
        {
            throw new DNSSECException(generalsecurityexception.toString());
        }
    }

    public static void verify(RRset rrset, RRSIGRecord rrsigrecord, DNSKEYRecord dnskeyrecord)
        throws DNSSECException
    {
        if(!matches(rrsigrecord, dnskeyrecord))
            throw new KeyMismatchException(dnskeyrecord, rrsigrecord);
        Date date = new Date();
        if(date.compareTo(rrsigrecord.getExpire()) > 0)
            throw new SignatureExpiredException(rrsigrecord.getExpire(), date);
        if(date.compareTo(rrsigrecord.getTimeSigned()) < 0)
        {
            throw new SignatureNotYetValidException(rrsigrecord.getTimeSigned(), date);
        } else
        {
            verify(dnskeyrecord.getPublicKey(), rrsigrecord.getAlgorithm(), digestRRset(rrsigrecord, rrset), rrsigrecord.getSignature());
            return;
        }
    }

    static void verifyMessage(Message message, byte abyte0[], SIGRecord sigrecord, SIGRecord sigrecord1, KEYRecord keyrecord)
        throws DNSSECException
    {
        if(!matches(sigrecord, keyrecord))
            throw new KeyMismatchException(keyrecord, sigrecord);
        Date date = new Date();
        if(date.compareTo(sigrecord.getExpire()) > 0)
            throw new SignatureExpiredException(sigrecord.getExpire(), date);
        if(date.compareTo(sigrecord.getTimeSigned()) < 0)
            throw new SignatureNotYetValidException(sigrecord.getTimeSigned(), date);
        DNSOutput dnsoutput = new DNSOutput();
        digestSIG(dnsoutput, sigrecord);
        if(sigrecord1 != null)
            dnsoutput.writeByteArray(sigrecord1.getSignature());
        Header header = (Header)message.getHeader().clone();
        header.decCount(3);
        dnsoutput.writeByteArray(header.toWire());
        dnsoutput.writeByteArray(abyte0, 12, message.sig0start - 12);
        verify(keyrecord.getPublicKey(), sigrecord.getAlgorithm(), dnsoutput.toByteArray(), sigrecord.getSignature());
    }

    private static void writeBigInteger(DNSOutput dnsoutput, BigInteger biginteger)
    {
        byte abyte0[] = biginteger.toByteArray();
        if(abyte0[0] == 0)
            dnsoutput.writeByteArray(abyte0, 1, abyte0.length - 1);
        else
            dnsoutput.writeByteArray(abyte0);
    }

    private static final int ASN1_INT = 2;
    private static final int ASN1_SEQ = 48;
    private static final int DSA_LEN = 20;
}
