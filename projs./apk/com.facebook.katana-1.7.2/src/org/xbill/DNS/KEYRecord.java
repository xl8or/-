package org.xbill.DNS;

import java.io.IOException;
import java.security.PublicKey;
import java.util.StringTokenizer;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.KEYBase;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class KEYRecord extends KEYBase {

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
   private static final long serialVersionUID = 6385613447571488906L;


   KEYRecord() {}

   public KEYRecord(Name var1, int var2, long var3, int var5, int var6, int var7, PublicKey var8) throws DNSSEC.DNSSECException {
      byte[] var11 = DNSSEC.fromPublicKey(var8, var7);
      super(var1, 25, var2, var3, var5, var6, var7, var11);
      this.publicKey = var8;
   }

   public KEYRecord(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
      super(var1, 25, var2, var3, var5, var6, var7, var8);
   }

   Record getObject() {
      return new KEYRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      String var3 = var1.getIdentifier();
      int var4 = KEYRecord.Flags.value(var3);
      this.flags = var4;
      if(this.flags < 0) {
         String var5 = "Invalid flags: " + var3;
         throw var1.exception(var5);
      } else {
         String var6 = var1.getIdentifier();
         int var7 = KEYRecord.Protocol.value(var6);
         this.proto = var7;
         if(this.proto < 0) {
            String var8 = "Invalid protocol: " + var6;
            throw var1.exception(var8);
         } else {
            String var9 = var1.getIdentifier();
            int var10 = DNSSEC.Algorithm.value(var9);
            this.alg = var10;
            if(this.alg < 0) {
               String var11 = "Invalid algorithm: " + var9;
               throw var1.exception(var11);
            } else if((this.flags & '\uc000') == '\uc000') {
               this.key = null;
            } else {
               byte[] var12 = var1.getBase64();
               this.key = var12;
            }
         }
      }
   }

   public static class Flags {

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
      private static Mnemonic flags = new Mnemonic("KEY flags", 2);


      static {
         flags.setMaximum('\uffff');
         flags.setNumericAllowed((boolean)0);
         flags.add(16384, "NOCONF");
         flags.add('\u8000', "NOAUTH");
         flags.add('\uc000', "NOKEY");
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

      private Flags() {}

      public static int value(String var0) {
         int var1;
         int var2;
         try {
            var1 = Integer.parseInt(var0);
         } catch (NumberFormatException var9) {
            StringTokenizer var4 = new StringTokenizer(var0, "|");

            int var5;
            int var8;
            for(var5 = 0; var4.hasMoreTokens(); var5 |= var8) {
               Mnemonic var6 = flags;
               String var7 = var4.nextToken();
               var8 = var6.getValue(var7);
               if(var8 < 0) {
                  var2 = -1;
                  return var2;
               }
            }

            var2 = var5;
            return var2;
         }

         var2 = var1;
         if(var1 < 0 || var1 > '\uffff') {
            var2 = -1;
         }

         return var2;
      }
   }

   public static class Protocol {

      public static final int ANY = 255;
      public static final int DNSSEC = 3;
      public static final int EMAIL = 2;
      public static final int IPSEC = 4;
      public static final int NONE = 0;
      public static final int TLS = 1;
      private static Mnemonic protocols = new Mnemonic("KEY protocol", 2);


      static {
         protocols.setMaximum(255);
         protocols.setNumericAllowed((boolean)1);
         protocols.add(0, "NONE");
         protocols.add(1, "TLS");
         protocols.add(2, "EMAIL");
         protocols.add(3, "DNSSEC");
         protocols.add(4, "IPSEC");
         protocols.add(255, "ANY");
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
