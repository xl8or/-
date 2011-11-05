package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import org.xbill.DNS.Address;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class WKSRecord extends Record {

   private static final long serialVersionUID = -9104259763909119805L;
   private byte[] address;
   private int protocol;
   private int[] services;


   WKSRecord() {}

   public WKSRecord(Name var1, int var2, long var3, InetAddress var5, int var6, int[] var7) {
      super(var1, 11, var2, var3);
      if(Address.familyOf(var5) != 1) {
         throw new IllegalArgumentException("invalid IPv4 address");
      } else {
         byte[] var13 = var5.getAddress();
         this.address = var13;
         int var14 = checkU8("protocol", var6);
         this.protocol = var14;
         int var15 = 0;

         while(true) {
            int var16 = var7.length;
            if(var15 >= var16) {
               int[] var19 = new int[var7.length];
               this.services = var19;
               int[] var20 = this.services;
               int var21 = var7.length;
               System.arraycopy(var7, 0, var20, 0, var21);
               Arrays.sort(this.services);
               return;
            }

            int var17 = var7[var15];
            int var18 = checkU16("service", var17);
            ++var15;
         }
      }
   }

   public InetAddress getAddress() {
      InetAddress var1;
      InetAddress var2;
      try {
         var1 = InetAddress.getByAddress(this.address);
      } catch (UnknownHostException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   Record getObject() {
      return new WKSRecord();
   }

   public int getProtocol() {
      return this.protocol;
   }

   public int[] getServices() {
      return this.services;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      byte[] var3 = Address.toByteArray(var1.getString(), 1);
      this.address = var3;
      if(this.address == null) {
         throw var1.exception("invalid address");
      } else {
         String var4 = var1.getString();
         int var5 = WKSRecord.Protocol.value(var4);
         this.protocol = var5;
         if(this.protocol < 0) {
            String var6 = "Invalid IP protocol: " + var4;
            throw var1.exception(var6);
         } else {
            ArrayList var7 = new ArrayList();

            while(true) {
               Tokenizer.Token var8 = var1.get();
               if(!var8.isString()) {
                  var1.unget();
                  int[] var9 = new int[var7.size()];
                  this.services = var9;
                  int var10 = 0;

                  while(true) {
                     int var11 = var7.size();
                     if(var10 >= var11) {
                        return;
                     }

                     int[] var12 = this.services;
                     int var13 = ((Integer)var7.get(var10)).intValue();
                     var12[var10] = var13;
                     ++var10;
                  }
               }

               int var14 = WKSRecord.Service.value(var8.value);
               if(var14 < 0) {
                  StringBuilder var15 = (new StringBuilder()).append("Invalid TCP/UDP service: ");
                  String var16 = var8.value;
                  String var17 = var15.append(var16).toString();
                  throw var1.exception(var17);
               }

               Integer var18 = new Integer(var14);
               var7.add(var18);
            }
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readByteArray(4);
      this.address = var2;
      int var3 = var1.readU8();
      this.protocol = var3;
      byte[] var4 = var1.readByteArray();
      ArrayList var5 = new ArrayList();
      int var6 = 0;

      while(true) {
         int var7 = var4.length;
         if(var6 >= var7) {
            int[] var15 = new int[var5.size()];
            this.services = var15;
            int var16 = 0;

            while(true) {
               int var17 = var5.size();
               if(var16 >= var17) {
                  return;
               }

               int[] var18 = this.services;
               int var19 = ((Integer)var5.get(var16)).intValue();
               var18[var16] = var19;
               ++var16;
            }
         }

         for(int var8 = 0; var8 < 8; ++var8) {
            int var9 = var4[var6] & 255;
            int var10 = 7 - var8;
            int var11 = 1 << var10;
            if((var9 & var11) != 0) {
               int var12 = var6 * 8 + var8;
               Integer var13 = new Integer(var12);
               var5.add(var13);
            }
         }

         ++var6;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Address.toDottedQuad(this.address);
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.protocol;
      var1.append(var5);
      int var7 = 0;

      while(true) {
         int var8 = this.services.length;
         if(var7 >= var8) {
            return var1.toString();
         }

         StringBuilder var9 = (new StringBuilder()).append(" ");
         int var10 = this.services[var7];
         String var11 = var9.append(var10).toString();
         var1.append(var11);
         ++var7;
      }
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.address;
      var1.writeByteArray(var4);
      int var5 = this.protocol;
      var1.writeU8(var5);
      int[] var6 = this.services;
      int var7 = this.services.length - 1;
      byte[] var8 = new byte[var6[var7] / 8 + 1];
      int var9 = 0;

      while(true) {
         int var10 = this.services.length;
         if(var9 >= var10) {
            var1.writeByteArray(var8);
            return;
         }

         int var11 = this.services[var9];
         int var12 = var11 / 8;
         byte var13 = var8[var12];
         int var14 = var11 % 8;
         int var15 = 7 - var14;
         byte var16 = (byte)(1 << var15 | var13);
         var8[var12] = var16;
         ++var9;
      }
   }

   public static class Service {

      public static final int AUTH = 113;
      public static final int BL_IDM = 142;
      public static final int BOOTPC = 68;
      public static final int BOOTPS = 67;
      public static final int CHARGEN = 19;
      public static final int CISCO_FNA = 130;
      public static final int CISCO_SYS = 132;
      public static final int CISCO_TNA = 131;
      public static final int CSNET_NS = 105;
      public static final int DAYTIME = 13;
      public static final int DCP = 93;
      public static final int DISCARD = 9;
      public static final int DOMAIN = 53;
      public static final int DSP = 33;
      public static final int ECHO = 7;
      public static final int EMFIS_CNTL = 141;
      public static final int EMFIS_DATA = 140;
      public static final int ERPC = 121;
      public static final int FINGER = 79;
      public static final int FTP = 21;
      public static final int FTP_DATA = 20;
      public static final int GRAPHICS = 41;
      public static final int HOSTNAME = 101;
      public static final int HOSTS2_NS = 81;
      public static final int INGRES_NET = 134;
      public static final int ISI_GL = 55;
      public static final int ISO_TSAP = 102;
      public static final int LA_MAINT = 51;
      public static final int LINK = 245;
      public static final int LOCUS_CON = 127;
      public static final int LOCUS_MAP = 125;
      public static final int LOC_SRV = 135;
      public static final int LOGIN = 49;
      public static final int METAGRAM = 99;
      public static final int MIT_DOV = 91;
      public static final int MPM = 45;
      public static final int MPM_FLAGS = 44;
      public static final int MPM_SND = 46;
      public static final int MSG_AUTH = 31;
      public static final int MSG_ICP = 29;
      public static final int NAMESERVER = 42;
      public static final int NETBIOS_DGM = 138;
      public static final int NETBIOS_NS = 137;
      public static final int NETBIOS_SSN = 139;
      public static final int NETRJS_1 = 71;
      public static final int NETRJS_2 = 72;
      public static final int NETRJS_3 = 73;
      public static final int NETRJS_4 = 74;
      public static final int NICNAME = 43;
      public static final int NI_FTP = 47;
      public static final int NI_MAIL = 61;
      public static final int NNTP = 119;
      public static final int NSW_FE = 27;
      public static final int NTP = 123;
      public static final int POP_2 = 109;
      public static final int PROFILE = 136;
      public static final int PWDGEN = 129;
      public static final int QUOTE = 17;
      public static final int RJE = 5;
      public static final int RLP = 39;
      public static final int RTELNET = 107;
      public static final int SFTP = 115;
      public static final int SMTP = 25;
      public static final int STATSRV = 133;
      public static final int SUNRPC = 111;
      public static final int SUPDUP = 95;
      public static final int SUR_MEAS = 243;
      public static final int SU_MIT_TG = 89;
      public static final int SWIFT_RVF = 97;
      public static final int TACACS_DS = 65;
      public static final int TACNEWS = 98;
      public static final int TELNET = 23;
      public static final int TFTP = 69;
      public static final int TIME = 37;
      public static final int USERS = 11;
      public static final int UUCP_PATH = 117;
      public static final int VIA_FTP = 63;
      public static final int X400 = 103;
      public static final int X400_SND = 104;
      private static Mnemonic services = new Mnemonic("TCP/UDP service", 3);


      static {
         services.setMaximum('\uffff');
         services.setNumericAllowed((boolean)1);
         services.add(5, "rje");
         services.add(7, "echo");
         services.add(9, "discard");
         services.add(11, "users");
         services.add(13, "daytime");
         services.add(17, "quote");
         services.add(19, "chargen");
         services.add(20, "ftp-data");
         services.add(21, "ftp");
         services.add(23, "telnet");
         services.add(25, "smtp");
         services.add(27, "nsw-fe");
         services.add(29, "msg-icp");
         services.add(31, "msg-auth");
         services.add(33, "dsp");
         services.add(37, "time");
         services.add(39, "rlp");
         services.add(41, "graphics");
         services.add(42, "nameserver");
         services.add(43, "nicname");
         services.add(44, "mpm-flags");
         services.add(45, "mpm");
         services.add(46, "mpm-snd");
         services.add(47, "ni-ftp");
         services.add(49, "login");
         services.add(51, "la-maint");
         services.add(53, "domain");
         services.add(55, "isi-gl");
         services.add(61, "ni-mail");
         services.add(63, "via-ftp");
         services.add(65, "tacacs-ds");
         services.add(67, "bootps");
         services.add(68, "bootpc");
         services.add(69, "tftp");
         services.add(71, "netrjs-1");
         services.add(72, "netrjs-2");
         services.add(73, "netrjs-3");
         services.add(74, "netrjs-4");
         services.add(79, "finger");
         services.add(81, "hosts2-ns");
         services.add(89, "su-mit-tg");
         services.add(91, "mit-dov");
         services.add(93, "dcp");
         services.add(95, "supdup");
         services.add(97, "swift-rvf");
         services.add(98, "tacnews");
         services.add(99, "metagram");
         services.add(101, "hostname");
         services.add(102, "iso-tsap");
         services.add(103, "x400");
         services.add(104, "x400-snd");
         services.add(105, "csnet-ns");
         services.add(107, "rtelnet");
         services.add(109, "pop-2");
         services.add(111, "sunrpc");
         services.add(113, "auth");
         services.add(115, "sftp");
         services.add(117, "uucp-path");
         services.add(119, "nntp");
         services.add(121, "erpc");
         services.add(123, "ntp");
         services.add(125, "locus-map");
         services.add(127, "locus-con");
         services.add(129, "pwdgen");
         services.add(130, "cisco-fna");
         services.add(131, "cisco-tna");
         services.add(132, "cisco-sys");
         services.add(133, "statsrv");
         services.add(134, "ingres-net");
         services.add(135, "loc-srv");
         services.add(136, "profile");
         services.add(137, "netbios-ns");
         services.add(138, "netbios-dgm");
         services.add(139, "netbios-ssn");
         services.add(140, "emfis-data");
         services.add(141, "emfis-cntl");
         services.add(142, "bl-idm");
         services.add(243, "sur-meas");
         services.add(245, "link");
      }

      private Service() {}

      public static String string(int var0) {
         return services.getText(var0);
      }

      public static int value(String var0) {
         return services.getValue(var0);
      }
   }

   public static class Protocol {

      public static final int ARGUS = 13;
      public static final int BBN_RCC_MON = 10;
      public static final int BR_SAT_MON = 76;
      public static final int CFTP = 62;
      public static final int CHAOS = 16;
      public static final int DCN_MEAS = 19;
      public static final int EGP = 8;
      public static final int EMCON = 14;
      public static final int GGP = 3;
      public static final int HMP = 20;
      public static final int ICMP = 1;
      public static final int IGMP = 2;
      public static final int IGP = 9;
      public static final int IPCV = 71;
      public static final int IPPC = 67;
      public static final int IRTP = 28;
      public static final int ISO_TP4 = 29;
      public static final int LEAF_1 = 25;
      public static final int LEAF_2 = 26;
      public static final int MERIT_INP = 32;
      public static final int MFE_NSP = 31;
      public static final int MIT_SUBNET = 65;
      public static final int MUX = 18;
      public static final int NETBLT = 30;
      public static final int NVP_II = 11;
      public static final int PRM = 21;
      public static final int PUP = 12;
      public static final int RDP = 27;
      public static final int RVD = 66;
      public static final int SAT_EXPAK = 64;
      public static final int SAT_MON = 69;
      public static final int SEP = 33;
      public static final int ST = 5;
      public static final int TCP = 6;
      public static final int TRUNK_1 = 23;
      public static final int TRUNK_2 = 24;
      public static final int UCL = 7;
      public static final int UDP = 17;
      public static final int WB_EXPAK = 79;
      public static final int WB_MON = 78;
      public static final int XNET = 15;
      public static final int XNS_IDP = 22;
      private static Mnemonic protocols = new Mnemonic("IP protocol", 3);


      static {
         protocols.setMaximum(255);
         protocols.setNumericAllowed((boolean)1);
         protocols.add(1, "icmp");
         protocols.add(2, "igmp");
         protocols.add(3, "ggp");
         protocols.add(5, "st");
         protocols.add(6, "tcp");
         protocols.add(7, "ucl");
         protocols.add(8, "egp");
         protocols.add(9, "igp");
         protocols.add(10, "bbn-rcc-mon");
         protocols.add(11, "nvp-ii");
         protocols.add(12, "pup");
         protocols.add(13, "argus");
         protocols.add(14, "emcon");
         protocols.add(15, "xnet");
         protocols.add(16, "chaos");
         protocols.add(17, "udp");
         protocols.add(18, "mux");
         protocols.add(19, "dcn-meas");
         protocols.add(20, "hmp");
         protocols.add(21, "prm");
         protocols.add(22, "xns-idp");
         protocols.add(23, "trunk-1");
         protocols.add(24, "trunk-2");
         protocols.add(25, "leaf-1");
         protocols.add(26, "leaf-2");
         protocols.add(27, "rdp");
         protocols.add(28, "irtp");
         protocols.add(29, "iso-tp4");
         protocols.add(30, "netblt");
         protocols.add(31, "mfe-nsp");
         protocols.add(32, "merit-inp");
         protocols.add(33, "sep");
         protocols.add(62, "cftp");
         protocols.add(64, "sat-expak");
         protocols.add(65, "mit-subnet");
         protocols.add(66, "rvd");
         protocols.add(67, "ippc");
         protocols.add(69, "sat-mon");
         protocols.add(71, "ipcv");
         protocols.add(76, "br-sat-mon");
         protocols.add(78, "wb-mon");
         protocols.add(79, "wb-expak");
      }

      private Protocol() {}

      public static String string(int var0) {
         return protocols.getText(var0);
      }

      public static int value(String var0) {
         return protocols.getValue(var0);
      }
   }
}
