package org.xbill.DNS;

import java.io.IOException;
import java.security.PublicKey;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.KEYBase;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class DNSKEYRecord extends KEYBase {

   private static final long serialVersionUID = -8679800040426675002L;


   DNSKEYRecord() {}

   public DNSKEYRecord(Name var1, int var2, long var3, int var5, int var6, int var7, PublicKey var8) throws DNSSEC.DNSSECException {
      byte[] var11 = DNSSEC.fromPublicKey(var8, var7);
      super(var1, 48, var2, var3, var5, var6, var7, var11);
      this.publicKey = var8;
   }

   public DNSKEYRecord(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
      super(var1, 48, var2, var3, var5, var6, var7, var8);
   }

   Record getObject() {
      return new DNSKEYRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.flags = var3;
      int var4 = var1.getUInt8();
      this.proto = var4;
      String var5 = var1.getString();
      int var6 = DNSSEC.Algorithm.value(var5);
      this.alg = var6;
      if(this.alg < 0) {
         String var7 = "Invalid algorithm: " + var5;
         throw var1.exception(var7);
      } else {
         byte[] var8 = var1.getBase64();
         this.key = var8;
      }
   }

   public static class Protocol {

      public static final int DNSSEC = 3;


      private Protocol() {}
   }

   public static class Flags {

      public static final int REVOKE = 128;
      public static final int SEP_KEY = 1;
      public static final int ZONE_KEY = 256;


      private Flags() {}
   }
}
