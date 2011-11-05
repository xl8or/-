package org.xbill.DNS;

import java.io.IOException;
import java.security.PublicKey;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.utils.base64;

abstract class KEYBase extends Record {

   private static final long serialVersionUID = 3469321722693285454L;
   protected int alg;
   protected int flags;
   protected int footprint = -1;
   protected byte[] key;
   protected int proto;
   protected PublicKey publicKey = null;


   protected KEYBase() {}

   public KEYBase(Name var1, int var2, int var3, long var4, int var6, int var7, int var8, byte[] var9) {
      super(var1, var2, var3, var4);
      int var10 = checkU16("flags", var6);
      this.flags = var10;
      int var11 = checkU8("proto", var7);
      this.proto = var11;
      int var12 = checkU8("alg", var8);
      this.alg = var12;
      this.key = var9;
   }

   public int getAlgorithm() {
      return this.alg;
   }

   public int getFlags() {
      return this.flags;
   }

   public int getFootprint() {
      int var1 = 0;
      int var2;
      if(this.footprint >= 0) {
         var2 = this.footprint;
      } else {
         DNSOutput var3 = new DNSOutput();
         this.rrToWire(var3, (Compression)null, (boolean)0);
         byte[] var4 = var3.toByteArray();
         int var10;
         if(this.alg == 1) {
            int var5 = var4.length - 3;
            int var6 = var4[var5] & 255;
            int var7 = var4.length - 2;
            int var8 = var4[var7] & 255;
            int var9 = var6 << 8;
            var10 = var8 + var9;
         } else {
            int var12 = 0;

            while(true) {
               int var13 = var4.length - 1;
               if(var12 >= var13) {
                  int var18 = var4.length;
                  int var19;
                  if(var12 < var18) {
                     var19 = ((var4[var12] & 255) << 8) + var1;
                  } else {
                     var19 = var1;
                  }

                  int var20 = var19 >> 16 & '\uffff';
                  var10 = var19 + var20;
                  break;
               }

               int var14 = var4[var12] & 255;
               int var15 = var12 + 1;
               int var16 = var4[var15] & 255;
               int var17 = (var14 << 8) + var16;
               var1 += var17;
               var12 += 2;
            }
         }

         int var11 = var10 & '\uffff';
         this.footprint = var11;
         var2 = this.footprint;
      }

      return var2;
   }

   public byte[] getKey() {
      return this.key;
   }

   public int getProtocol() {
      return this.proto;
   }

   public PublicKey getPublicKey() throws DNSSEC.DNSSECException {
      PublicKey var1;
      if(this.publicKey != null) {
         var1 = this.publicKey;
      } else {
         PublicKey var2 = DNSSEC.toPublicKey(this);
         this.publicKey = var2;
         var1 = this.publicKey;
      }

      return var1;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.flags = var2;
      int var3 = var1.readU8();
      this.proto = var3;
      int var4 = var1.readU8();
      this.alg = var4;
      if(var1.remaining() > 0) {
         byte[] var5 = var1.readByteArray();
         this.key = var5;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.flags;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.proto;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      int var8 = this.alg;
      var1.append(var8);
      if(this.key != null) {
         if(Options.check("multiline")) {
            StringBuffer var10 = var1.append(" (\n");
            String var11 = base64.formatString(this.key, 64, "\t", (boolean)1);
            var1.append(var11);
            StringBuffer var13 = var1.append(" ; key_tag = ");
            int var14 = this.getFootprint();
            var1.append(var14);
         } else {
            StringBuffer var16 = var1.append(" ");
            String var17 = base64.toString(this.key);
            var1.append(var17);
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.flags;
      var1.writeU16(var4);
      int var5 = this.proto;
      var1.writeU8(var5);
      int var6 = this.alg;
      var1.writeU8(var6);
      if(this.key != null) {
         byte[] var7 = this.key;
         var1.writeByteArray(var7);
      }
   }
}
