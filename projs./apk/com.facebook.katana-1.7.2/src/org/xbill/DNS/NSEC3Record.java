package org.xbill.DNS;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.TypeBitmap;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base32;

public class NSEC3Record extends Record {

   public static final int SHA1_DIGEST_ID = 1;
   private static final base32 b32 = new base32("0123456789ABCDEFGHIJKLMNOPQRSTUV=", (boolean)0, (boolean)0);
   private static final long serialVersionUID = -7123504635968932855L;
   private int flags;
   private int hashAlg;
   private int iterations;
   private byte[] next;
   private byte[] salt;
   private TypeBitmap types;


   NSEC3Record() {}

   public NSEC3Record(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8, byte[] var9, int[] var10) {
      super(var1, 50, var2, var3);
      String var16 = "hashAlg";
      int var18 = checkU8(var16, var5);
      this.hashAlg = var18;
      String var19 = "flags";
      int var21 = checkU8(var19, var6);
      this.flags = var21;
      String var22 = "iterations";
      int var24 = checkU16(var22, var7);
      this.iterations = var24;
      if(var8 != false) {
         if(var8.length > 255) {
            throw new IllegalArgumentException("Invalid salt");
         }

         if(var8.length > 0) {
            byte[] var25 = new byte[var8.length];
            this.salt = var25;
            byte[] var26 = this.salt;
            int var27 = var8.length;
            byte var29 = 0;
            byte var31 = 0;
            System.arraycopy(var8, var29, var26, var31, var27);
         }
      }

      if(var9.length > 255) {
         throw new IllegalArgumentException("Invalid next hash");
      } else {
         byte[] var33 = new byte[var9.length];
         this.next = var33;
         byte[] var34 = this.next;
         int var35 = var9.length;
         byte var37 = 0;
         byte var39 = 0;
         System.arraycopy(var9, var37, var34, var39, var35);
         TypeBitmap var41 = new TypeBitmap(var10);
         this.types = var41;
      }
   }

   static byte[] hashName(Name var0, int var1, int var2, byte[] var3) throws NoSuchAlgorithmException {
      switch(var1) {
      case 1:
         MessageDigest var5 = MessageDigest.getInstance("sha-1");
         byte var6 = 0;
         byte[] var7 = null;

         for(int var8 = var6; var8 <= var2; ++var8) {
            var5.reset();
            if(var8 == 0) {
               var7 = var0.toWireCanonical();
               var5.update(var7);
            } else {
               var5.update(var7);
            }

            if(var3 != null) {
               var5.update(var3);
            }

            byte[] var9 = var5.digest();
         }

         return var7;
      default:
         String var4 = "Unknown NSEC3 algorithmidentifier: " + var1;
         throw new NoSuchAlgorithmException(var4);
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

   public byte[] getNext() {
      return this.next;
   }

   Record getObject() {
      return new NSEC3Record();
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public int[] getTypes() {
      return this.types.toArray();
   }

   public boolean hasType(int var1) {
      return this.types.contains(var1);
   }

   public byte[] hashName(Name var1) throws NoSuchAlgorithmException {
      int var2 = this.hashAlg;
      int var3 = this.iterations;
      byte[] var4 = this.salt;
      return hashName(var1, var2, var3, var4);
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
         byte[] var9 = var1.getHexString();
         this.salt = var9;
         if(this.salt.length > 255) {
            throw var1.exception("salt value too long");
         }
      }

      base32 var6 = b32;
      byte[] var7 = var1.getBase32String(var6);
      this.next = var7;
      TypeBitmap var8 = new TypeBitmap(var1);
      this.types = var8;
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

      int var7 = var1.readU8();
      byte[] var8 = var1.readByteArray(var7);
      this.next = var8;
      TypeBitmap var9 = new TypeBitmap(var1);
      this.types = var9;
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
         String var20 = base16.toString(this.salt);
         var1.append(var20);
      }

      StringBuffer var12 = var1.append(' ');
      base32 var13 = b32;
      byte[] var14 = this.next;
      String var15 = var13.toString(var14);
      var1.append(var15);
      if(!this.types.empty()) {
         StringBuffer var17 = var1.append(' ');
         String var18 = this.types.toString();
         var1.append(var18);
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

      int var9 = this.next.length;
      var1.writeU8(var9);
      byte[] var10 = this.next;
      var1.writeByteArray(var10);
      this.types.toWire(var1);
   }

   public static class Flags {

      public static final int OPT_OUT = 1;


      private Flags() {}
   }

   public static class Digest {

      public static final int SHA1 = 1;


      private Digest() {}
   }
}
