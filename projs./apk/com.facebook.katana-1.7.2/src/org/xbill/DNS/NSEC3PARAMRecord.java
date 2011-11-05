package org.xbill.DNS;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.NSEC3Record;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base16;

public class NSEC3PARAMRecord extends Record {

   private static final long serialVersionUID = -8689038598776316533L;
   private int flags;
   private int hashAlg;
   private int iterations;
   private byte[] salt;


   NSEC3PARAMRecord() {}

   public NSEC3PARAMRecord(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
      super(var1, 51, var2, var3);
      int var14 = checkU8("hashAlg", var5);
      this.hashAlg = var14;
      int var15 = checkU8("flags", var6);
      this.flags = var15;
      int var16 = checkU16("iterations", var7);
      this.iterations = var16;
      if(var8 != false) {
         if(var8.length > 255) {
            throw new IllegalArgumentException("Invalid salt length");
         } else if(var8.length > 0) {
            byte[] var17 = new byte[var8.length];
            this.salt = var17;
            byte[] var18 = this.salt;
            int var19 = var8.length;
            System.arraycopy(var8, 0, var18, 0, var19);
         }
      }
   }

   public int getFlags() {
      return this.flags;
   }

   public int getHashAlgorithm() {
      return this.hashAlg;
   }

   public int getIterations() {
      return this.iterations;
   }

   Record getObject() {
      return new NSEC3PARAMRecord();
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public byte[] hashName(Name var1) throws NoSuchAlgorithmException {
      int var2 = this.hashAlg;
      int var3 = this.iterations;
      byte[] var4 = this.salt;
      return NSEC3Record.hashName(var1, var2, var3, var4);
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt8();
      this.hashAlg = var3;
      int var4 = var1.getUInt8();
      this.flags = var4;
      int var5 = var1.getUInt16();
      this.iterations = var5;
      if(var1.getString().equals("-")) {
         this.salt = null;
      } else {
         var1.unget();
         byte[] var6 = var1.getHexString();
         this.salt = var6;
         if(this.salt.length > 255) {
            throw var1.exception("salt value too long");
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU8();
      this.hashAlg = var2;
      int var3 = var1.readU8();
      this.flags = var3;
      int var4 = var1.readU16();
      this.iterations = var4;
      int var5 = var1.readU8();
      if(var5 > 0) {
         byte[] var6 = var1.readByteArray(var5);
         this.salt = var6;
      } else {
         this.salt = null;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.hashAlg;
      var1.append(var2);
      StringBuffer var4 = var1.append(' ');
      int var5 = this.flags;
      var1.append(var5);
      StringBuffer var7 = var1.append(' ');
      int var8 = this.iterations;
      var1.append(var8);
      StringBuffer var10 = var1.append(' ');
      if(this.salt == null) {
         StringBuffer var11 = var1.append('-');
      } else {
         String var12 = base16.toString(this.salt);
         var1.append(var12);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.hashAlg;
      var1.writeU8(var4);
      int var5 = this.flags;
      var1.writeU8(var5);
      int var6 = this.iterations;
      var1.writeU16(var6);
      if(this.salt != null) {
         int var7 = this.salt.length;
         var1.writeU8(var7);
         byte[] var8 = this.salt;
         var1.writeByteArray(var8);
      } else {
         var1.writeU8(0);
      }
   }
}
