package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base16;

public class DLVRecord extends Record {

   public static final int SHA1_DIGEST_ID = 1;
   public static final int SHA256_DIGEST_ID = 1;
   private static final long serialVersionUID = 1960742375677534148L;
   private int alg;
   private byte[] digest;
   private int digestid;
   private int footprint;


   DLVRecord() {}

   public DLVRecord(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
      super(var1, '\u8001', var2, var3);
      int var14 = checkU16("footprint", var5);
      this.footprint = var14;
      int var15 = checkU8("alg", var6);
      this.alg = var15;
      int var16 = checkU8("digestid", var7);
      this.digestid = var16;
      this.digest = var8;
   }

   public int getAlgorithm() {
      return this.alg;
   }

   public byte[] getDigest() {
      return this.digest;
   }

   public int getDigestID() {
      return this.digestid;
   }

   public int getFootprint() {
      return this.footprint;
   }

   Record getObject() {
      return new DLVRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.footprint = var3;
      int var4 = var1.getUInt8();
      this.alg = var4;
      int var5 = var1.getUInt8();
      this.digestid = var5;
      byte[] var6 = var1.getHex();
      this.digest = var6;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.footprint = var2;
      int var3 = var1.readU8();
      this.alg = var3;
      int var4 = var1.readU8();
      this.digestid = var4;
      byte[] var5 = var1.readByteArray();
      this.digest = var5;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.footprint;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.alg;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      int var8 = this.digestid;
      var1.append(var8);
      if(this.digest != null) {
         StringBuffer var10 = var1.append(" ");
         String var11 = base16.toString(this.digest);
         var1.append(var11);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.footprint;
      var1.writeU16(var4);
      int var5 = this.alg;
      var1.writeU8(var5);
      int var6 = this.digestid;
      var1.writeU8(var6);
      if(this.digest != null) {
         byte[] var7 = this.digest;
         var1.writeByteArray(var7);
      }
   }
}
