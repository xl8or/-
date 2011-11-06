// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Type.java

package org.xbill.DNS;

import java.util.HashMap;

// Referenced classes of package org.xbill.DNS:
//            ARecord, NSRecord, MDRecord, MFRecord, 
//            CNAMERecord, SOARecord, MBRecord, MGRecord, 
//            MRRecord, NULLRecord, WKSRecord, PTRRecord, 
//            HINFORecord, MINFORecord, MXRecord, TXTRecord, 
//            RPRecord, AFSDBRecord, X25Record, ISDNRecord, 
//            RTRecord, NSAPRecord, NSAP_PTRRecord, SIGRecord, 
//            KEYRecord, PXRecord, GPOSRecord, AAAARecord, 
//            LOCRecord, NXTRecord, SRVRecord, NAPTRRecord, 
//            KXRecord, CERTRecord, A6Record, DNAMERecord, 
//            OPTRecord, APLRecord, DSRecord, SSHFPRecord, 
//            IPSECKEYRecord, RRSIGRecord, NSECRecord, DNSKEYRecord, 
//            DHCIDRecord, NSEC3Record, NSEC3PARAMRecord, SPFRecord, 
//            TKEYRecord, TSIGRecord, DLVRecord, InvalidTypeException, 
//            Record, Mnemonic

public final class Type
{
    private static class TypeMnemonic extends Mnemonic
    {

        public void add(int i, String s, Record record)
        {
            super.add(i, s);
            objects.put(Mnemonic.toInteger(i), record);
        }

        public void check(int i)
        {
            Type.check(i);
        }

        public Record getProto(int i)
        {
            check(i);
            return (Record)objects.get(toInteger(i));
        }

        private HashMap objects;

        public TypeMnemonic()
        {
            super("Type", 2);
            setPrefix("TYPE");
            objects = new HashMap();
        }
    }


    private Type()
    {
    }

    public static void check(int i)
    {
        if(i < 0 || i > 65535)
            throw new InvalidTypeException(i);
        else
            return;
    }

    static Record getProto(int i)
    {
        return types.getProto(i);
    }

    public static boolean isRR(int i)
    {
        i;
        JVM INSTR lookupswitch 8: default 76
    //                   41: 80
    //                   249: 80
    //                   250: 80
    //                   251: 80
    //                   252: 80
    //                   253: 80
    //                   254: 80
    //                   255: 80;
           goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String string(int i)
    {
        return types.getText(i);
    }

    public static int value(String s)
    {
        return value(s, false);
    }

    public static int value(String s, boolean flag)
    {
        int i = types.getValue(s);
        if(i == -1 && flag)
            i = types.getValue((new StringBuilder()).append("TYPE").append(s).toString());
        return i;
    }

    public static final int A = 1;
    public static final int A6 = 38;
    public static final int AAAA = 28;
    public static final int AFSDB = 18;
    public static final int ANY = 255;
    public static final int APL = 42;
    public static final int ATMA = 34;
    public static final int AXFR = 252;
    public static final int CERT = 37;
    public static final int CNAME = 5;
    public static final int DHCID = 49;
    public static final int DLV = 32769;
    public static final int DNAME = 39;
    public static final int DNSKEY = 48;
    public static final int DS = 43;
    public static final int EID = 31;
    public static final int GPOS = 27;
    public static final int HINFO = 13;
    public static final int IPSECKEY = 45;
    public static final int ISDN = 20;
    public static final int IXFR = 251;
    public static final int KEY = 25;
    public static final int KX = 36;
    public static final int LOC = 29;
    public static final int MAILA = 254;
    public static final int MAILB = 253;
    public static final int MB = 7;
    public static final int MD = 3;
    public static final int MF = 4;
    public static final int MG = 8;
    public static final int MINFO = 14;
    public static final int MR = 9;
    public static final int MX = 15;
    public static final int NAPTR = 35;
    public static final int NIMLOC = 32;
    public static final int NS = 2;
    public static final int NSAP = 22;
    public static final int NSAP_PTR = 23;
    public static final int NSEC = 47;
    public static final int NSEC3 = 50;
    public static final int NSEC3PARAM = 51;
    public static final int NULL = 10;
    public static final int NXT = 30;
    public static final int OPT = 41;
    public static final int PTR = 12;
    public static final int PX = 26;
    public static final int RP = 17;
    public static final int RRSIG = 46;
    public static final int RT = 21;
    public static final int SIG = 24;
    public static final int SOA = 6;
    public static final int SPF = 99;
    public static final int SRV = 33;
    public static final int SSHFP = 44;
    public static final int TKEY = 249;
    public static final int TSIG = 250;
    public static final int TXT = 16;
    public static final int WKS = 11;
    public static final int X25 = 19;
    private static TypeMnemonic types;

    static 
    {
        types = new TypeMnemonic();
        types.add(1, "A", new ARecord());
        types.add(2, "NS", new NSRecord());
        types.add(3, "MD", new MDRecord());
        types.add(4, "MF", new MFRecord());
        types.add(5, "CNAME", new CNAMERecord());
        types.add(6, "SOA", new SOARecord());
        types.add(7, "MB", new MBRecord());
        types.add(8, "MG", new MGRecord());
        types.add(9, "MR", new MRRecord());
        types.add(10, "NULL", new NULLRecord());
        types.add(11, "WKS", new WKSRecord());
        types.add(12, "PTR", new PTRRecord());
        types.add(13, "HINFO", new HINFORecord());
        types.add(14, "MINFO", new MINFORecord());
        types.add(15, "MX", new MXRecord());
        types.add(16, "TXT", new TXTRecord());
        types.add(17, "RP", new RPRecord());
        types.add(18, "AFSDB", new AFSDBRecord());
        types.add(19, "X25", new X25Record());
        types.add(20, "ISDN", new ISDNRecord());
        types.add(21, "RT", new RTRecord());
        types.add(22, "NSAP", new NSAPRecord());
        types.add(23, "NSAP-PTR", new NSAP_PTRRecord());
        types.add(24, "SIG", new SIGRecord());
        types.add(25, "KEY", new KEYRecord());
        types.add(26, "PX", new PXRecord());
        types.add(27, "GPOS", new GPOSRecord());
        types.add(28, "AAAA", new AAAARecord());
        types.add(29, "LOC", new LOCRecord());
        types.add(30, "NXT", new NXTRecord());
        types.add(31, "EID");
        types.add(32, "NIMLOC");
        types.add(33, "SRV", new SRVRecord());
        types.add(34, "ATMA");
        types.add(35, "NAPTR", new NAPTRRecord());
        types.add(36, "KX", new KXRecord());
        types.add(37, "CERT", new CERTRecord());
        types.add(38, "A6", new A6Record());
        types.add(39, "DNAME", new DNAMERecord());
        types.add(41, "OPT", new OPTRecord());
        types.add(42, "APL", new APLRecord());
        types.add(43, "DS", new DSRecord());
        types.add(44, "SSHFP", new SSHFPRecord());
        types.add(45, "IPSECKEY", new IPSECKEYRecord());
        types.add(46, "RRSIG", new RRSIGRecord());
        types.add(47, "NSEC", new NSECRecord());
        types.add(48, "DNSKEY", new DNSKEYRecord());
        types.add(49, "DHCID", new DHCIDRecord());
        types.add(50, "NSEC3", new NSEC3Record());
        types.add(51, "NSEC3PARAM", new NSEC3PARAMRecord());
        types.add(99, "SPF", new SPFRecord());
        types.add(249, "TKEY", new TKEYRecord());
        types.add(250, "TSIG", new TSIGRecord());
        types.add(251, "IXFR");
        types.add(252, "AXFR");
        types.add(253, "MAILB");
        types.add(254, "MAILA");
        types.add(255, "ANY");
        types.add(32769, "DLV", new DLVRecord());
    }
}
