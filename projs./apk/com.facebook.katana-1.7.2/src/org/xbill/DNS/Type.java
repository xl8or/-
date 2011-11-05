package org.xbill.DNS;

import java.util.HashMap;
import org.xbill.DNS.A6Record;
import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.AFSDBRecord;
import org.xbill.DNS.APLRecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CERTRecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.DHCIDRecord;
import org.xbill.DNS.DLVRecord;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.DSRecord;
import org.xbill.DNS.GPOSRecord;
import org.xbill.DNS.HINFORecord;
import org.xbill.DNS.IPSECKEYRecord;
import org.xbill.DNS.ISDNRecord;
import org.xbill.DNS.InvalidTypeException;
import org.xbill.DNS.KEYRecord;
import org.xbill.DNS.KXRecord;
import org.xbill.DNS.LOCRecord;
import org.xbill.DNS.MBRecord;
import org.xbill.DNS.MDRecord;
import org.xbill.DNS.MFRecord;
import org.xbill.DNS.MGRecord;
import org.xbill.DNS.MINFORecord;
import org.xbill.DNS.MRRecord;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.NSAPRecord;
import org.xbill.DNS.NSAP_PTRRecord;
import org.xbill.DNS.NSEC3PARAMRecord;
import org.xbill.DNS.NSEC3Record;
import org.xbill.DNS.NSECRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.NULLRecord;
import org.xbill.DNS.NXTRecord;
import org.xbill.DNS.OPTRecord;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.PXRecord;
import org.xbill.DNS.RPRecord;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.RTRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SIGRecord;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.SPFRecord;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.SSHFPRecord;
import org.xbill.DNS.TKEYRecord;
import org.xbill.DNS.TSIGRecord;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.WKSRecord;
import org.xbill.DNS.X25Record;

public final class Type {

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
   private static Type.TypeMnemonic types = new Type.TypeMnemonic();


   static {
      Type.TypeMnemonic var0 = types;
      ARecord var1 = new ARecord();
      var0.add(1, "A", var1);
      Type.TypeMnemonic var2 = types;
      NSRecord var3 = new NSRecord();
      var2.add(2, "NS", var3);
      Type.TypeMnemonic var4 = types;
      MDRecord var5 = new MDRecord();
      var4.add(3, "MD", var5);
      Type.TypeMnemonic var6 = types;
      MFRecord var7 = new MFRecord();
      var6.add(4, "MF", var7);
      Type.TypeMnemonic var8 = types;
      CNAMERecord var9 = new CNAMERecord();
      var8.add(5, "CNAME", var9);
      Type.TypeMnemonic var10 = types;
      SOARecord var11 = new SOARecord();
      var10.add(6, "SOA", var11);
      Type.TypeMnemonic var12 = types;
      MBRecord var13 = new MBRecord();
      var12.add(7, "MB", var13);
      Type.TypeMnemonic var14 = types;
      MGRecord var15 = new MGRecord();
      var14.add(8, "MG", var15);
      Type.TypeMnemonic var16 = types;
      MRRecord var17 = new MRRecord();
      var16.add(9, "MR", var17);
      Type.TypeMnemonic var18 = types;
      NULLRecord var19 = new NULLRecord();
      var18.add(10, "NULL", var19);
      Type.TypeMnemonic var20 = types;
      WKSRecord var21 = new WKSRecord();
      var20.add(11, "WKS", var21);
      Type.TypeMnemonic var22 = types;
      PTRRecord var23 = new PTRRecord();
      var22.add(12, "PTR", var23);
      Type.TypeMnemonic var24 = types;
      HINFORecord var25 = new HINFORecord();
      var24.add(13, "HINFO", var25);
      Type.TypeMnemonic var26 = types;
      MINFORecord var27 = new MINFORecord();
      var26.add(14, "MINFO", var27);
      Type.TypeMnemonic var28 = types;
      MXRecord var29 = new MXRecord();
      var28.add(15, "MX", var29);
      Type.TypeMnemonic var30 = types;
      TXTRecord var31 = new TXTRecord();
      var30.add(16, "TXT", var31);
      Type.TypeMnemonic var32 = types;
      RPRecord var33 = new RPRecord();
      var32.add(17, "RP", var33);
      Type.TypeMnemonic var34 = types;
      AFSDBRecord var35 = new AFSDBRecord();
      var34.add(18, "AFSDB", var35);
      Type.TypeMnemonic var36 = types;
      X25Record var37 = new X25Record();
      var36.add(19, "X25", var37);
      Type.TypeMnemonic var38 = types;
      ISDNRecord var39 = new ISDNRecord();
      var38.add(20, "ISDN", var39);
      Type.TypeMnemonic var40 = types;
      RTRecord var41 = new RTRecord();
      var40.add(21, "RT", var41);
      Type.TypeMnemonic var42 = types;
      NSAPRecord var43 = new NSAPRecord();
      var42.add(22, "NSAP", var43);
      Type.TypeMnemonic var44 = types;
      NSAP_PTRRecord var45 = new NSAP_PTRRecord();
      var44.add(23, "NSAP-PTR", var45);
      Type.TypeMnemonic var46 = types;
      SIGRecord var47 = new SIGRecord();
      var46.add(24, "SIG", var47);
      Type.TypeMnemonic var48 = types;
      KEYRecord var49 = new KEYRecord();
      var48.add(25, "KEY", var49);
      Type.TypeMnemonic var50 = types;
      PXRecord var51 = new PXRecord();
      var50.add(26, "PX", var51);
      Type.TypeMnemonic var52 = types;
      GPOSRecord var53 = new GPOSRecord();
      var52.add(27, "GPOS", var53);
      Type.TypeMnemonic var54 = types;
      AAAARecord var55 = new AAAARecord();
      var54.add(28, "AAAA", var55);
      Type.TypeMnemonic var56 = types;
      LOCRecord var57 = new LOCRecord();
      var56.add(29, "LOC", var57);
      Type.TypeMnemonic var58 = types;
      NXTRecord var59 = new NXTRecord();
      var58.add(30, "NXT", var59);
      types.add(31, "EID");
      types.add(32, "NIMLOC");
      Type.TypeMnemonic var60 = types;
      SRVRecord var61 = new SRVRecord();
      var60.add(33, "SRV", var61);
      types.add(34, "ATMA");
      Type.TypeMnemonic var62 = types;
      NAPTRRecord var63 = new NAPTRRecord();
      var62.add(35, "NAPTR", var63);
      Type.TypeMnemonic var64 = types;
      KXRecord var65 = new KXRecord();
      var64.add(36, "KX", var65);
      Type.TypeMnemonic var66 = types;
      CERTRecord var67 = new CERTRecord();
      var66.add(37, "CERT", var67);
      Type.TypeMnemonic var68 = types;
      A6Record var69 = new A6Record();
      var68.add(38, "A6", var69);
      Type.TypeMnemonic var70 = types;
      DNAMERecord var71 = new DNAMERecord();
      var70.add(39, "DNAME", var71);
      Type.TypeMnemonic var72 = types;
      OPTRecord var73 = new OPTRecord();
      var72.add(41, "OPT", var73);
      Type.TypeMnemonic var74 = types;
      APLRecord var75 = new APLRecord();
      var74.add(42, "APL", var75);
      Type.TypeMnemonic var76 = types;
      DSRecord var77 = new DSRecord();
      var76.add(43, "DS", var77);
      Type.TypeMnemonic var78 = types;
      SSHFPRecord var79 = new SSHFPRecord();
      var78.add(44, "SSHFP", var79);
      Type.TypeMnemonic var80 = types;
      IPSECKEYRecord var81 = new IPSECKEYRecord();
      var80.add(45, "IPSECKEY", var81);
      Type.TypeMnemonic var82 = types;
      RRSIGRecord var83 = new RRSIGRecord();
      var82.add(46, "RRSIG", var83);
      Type.TypeMnemonic var84 = types;
      NSECRecord var85 = new NSECRecord();
      var84.add(47, "NSEC", var85);
      Type.TypeMnemonic var86 = types;
      DNSKEYRecord var87 = new DNSKEYRecord();
      var86.add(48, "DNSKEY", var87);
      Type.TypeMnemonic var88 = types;
      DHCIDRecord var89 = new DHCIDRecord();
      var88.add(49, "DHCID", var89);
      Type.TypeMnemonic var90 = types;
      NSEC3Record var91 = new NSEC3Record();
      var90.add(50, "NSEC3", var91);
      Type.TypeMnemonic var92 = types;
      NSEC3PARAMRecord var93 = new NSEC3PARAMRecord();
      var92.add(51, "NSEC3PARAM", var93);
      Type.TypeMnemonic var94 = types;
      SPFRecord var95 = new SPFRecord();
      var94.add(99, "SPF", var95);
      Type.TypeMnemonic var96 = types;
      TKEYRecord var97 = new TKEYRecord();
      var96.add(249, "TKEY", var97);
      Type.TypeMnemonic var98 = types;
      TSIGRecord var99 = new TSIGRecord();
      var98.add(250, "TSIG", var99);
      types.add(251, "IXFR");
      types.add(252, "AXFR");
      types.add(253, "MAILB");
      types.add(254, "MAILA");
      types.add(255, "ANY");
      Type.TypeMnemonic var100 = types;
      DLVRecord var101 = new DLVRecord();
      var100.add('\u8001', "DLV", var101);
   }

   private Type() {}

   public static void check(int var0) {
      if(var0 < 0 || var0 > '\uffff') {
         throw new InvalidTypeException(var0);
      }
   }

   static Record getProto(int var0) {
      return types.getProto(var0);
   }

   public static boolean isRR(int var0) {
      boolean var1;
      switch(var0) {
      case 41:
      case 249:
      case 250:
      case 251:
      case 252:
      case 253:
      case 254:
      case 255:
         var1 = false;
         break;
      default:
         var1 = true;
      }

      return var1;
   }

   public static String string(int var0) {
      return types.getText(var0);
   }

   public static int value(String var0) {
      return value(var0, (boolean)0);
   }

   public static int value(String var0, boolean var1) {
      int var2 = types.getValue(var0);
      if(var2 == -1 && var1) {
         Type.TypeMnemonic var3 = types;
         String var4 = "TYPE" + var0;
         var2 = var3.getValue(var4);
      }

      return var2;
   }

   private static class TypeMnemonic extends Mnemonic {

      private HashMap objects;


      public TypeMnemonic() {
         super("Type", 2);
         this.setPrefix("TYPE");
         HashMap var1 = new HashMap();
         this.objects = var1;
      }

      public void add(int var1, String var2, Record var3) {
         super.add(var1, var2);
         HashMap var4 = this.objects;
         Integer var5 = Mnemonic.toInteger(var1);
         var4.put(var5, var3);
      }

      public void check(int var1) {
         Type.check(var1);
      }

      public Record getProto(int var1) {
         this.check(var1);
         HashMap var2 = this.objects;
         Integer var3 = toInteger(var1);
         return (Record)var2.get(var3);
      }
   }
}
