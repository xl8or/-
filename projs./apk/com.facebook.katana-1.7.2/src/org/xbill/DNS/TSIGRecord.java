package org.xbill.DNS;

import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base64;

public class TSIGRecord extends Record {

   private static final long serialVersionUID = -88820909016649306L;
   private Name alg;
   private int error;
   private int fudge;
   private int originalID;
   private byte[] other;
   private byte[] signature;
   private Date timeSigned;


   TSIGRecord() {}

   public TSIGRecord(Name var1, int var2, long var3, Name var5, Date var6, int var7, byte[] var8, int var9, int var10, byte[] var11) {
      super(var1, 250, var2, var3);
      Name var17 = checkName("alg", var5);
      this.alg = var17;
      this.timeSigned = var6;
      int var18 = checkU16("fudge", var7);
      this.fudge = var18;
      this.signature = var8;
      String var20 = "originalID";
      int var22 = checkU16(var20, var9);
      this.originalID = var22;
      String var23 = "error";
      int var25 = checkU16(var23, var10);
      this.error = var25;
      this.other = var11;
   }

   public Name getAlgorithm() {
      return this.alg;
   }

   public int getError() {
      return this.error;
   }

   public int getFudge() {
      return this.fudge;
   }

   Record getObject() {
      return new TSIGRecord();
   }

   public int getOriginalID() {
      return this.originalID;
   }

   public byte[] getOther() {
      return this.other;
   }

   public byte[] getSignature() {
      return this.signature;
   }

   public Date getTimeSigned() {
      return this.timeSigned;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      throw var1.exception("no text format defined for TSIG");
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.alg = var2;
      long var3 = (long)var1.readU16();
      long var5 = var1.readU32();
      long var7 = ((var3 << 32) + var5) * 1000L;
      Date var9 = new Date(var7);
      this.timeSigned = var9;
      int var10 = var1.readU16();
      this.fudge = var10;
      int var11 = var1.readU16();
      byte[] var12 = var1.readByteArray(var11);
      this.signature = var12;
      int var13 = var1.readU16();
      this.originalID = var13;
      int var14 = var1.readU16();
      this.error = var14;
      int var15 = var1.readU16();
      if(var15 > 0) {
         byte[] var16 = var1.readByteArray(var15);
         this.other = var16;
      } else {
         this.other = null;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.alg;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      if(Options.check("multiline")) {
         StringBuffer var5 = var1.append("(\n\t");
      }

      long var6 = this.timeSigned.getTime() / 1000L;
      var1.append(var6);
      StringBuffer var9 = var1.append(" ");
      int var10 = this.fudge;
      var1.append(var10);
      StringBuffer var12 = var1.append(" ");
      int var13 = this.signature.length;
      var1.append(var13);
      if(Options.check("multiline")) {
         StringBuffer var15 = var1.append("\n");
         String var16 = base64.formatString(this.signature, 64, "\t", (boolean)0);
         var1.append(var16);
      } else {
         StringBuffer var24 = var1.append(" ");
         String var25 = base64.toString(this.signature);
         var1.append(var25);
      }

      StringBuffer var18 = var1.append(" ");
      String var19 = Rcode.TSIGstring(this.error);
      var1.append(var19);
      StringBuffer var21 = var1.append(" ");
      if(this.other == null) {
         StringBuffer var22 = var1.append(0);
      } else {
         int var27 = this.other.length;
         var1.append(var27);
         if(Options.check("multiline")) {
            StringBuffer var29 = var1.append("\n\n\n\t");
         } else {
            StringBuffer var31 = var1.append(" ");
         }

         if(this.error == 18) {
            if(this.other.length != 6) {
               StringBuffer var30 = var1.append("<invalid BADTIME other data>");
            } else {
               long var32 = (long)(this.other[0] & 255) << 40;
               long var34 = (long)(this.other[1] & 255) << 32;
               long var36 = var32 + var34;
               long var38 = (long)((this.other[2] & 255) << 24);
               long var40 = var36 + var38;
               long var42 = (long)((this.other[3] & 255) << 16);
               long var44 = var40 + var42;
               long var46 = (long)((this.other[4] & 255) << 8);
               long var48 = var44 + var46;
               long var50 = (long)(this.other[5] & 255);
               long var52 = var48 + var50;
               StringBuffer var54 = var1.append("<server time: ");
               long var55 = var52 * 1000L;
               Date var57 = new Date(var55);
               var1.append(var57);
               StringBuffer var59 = var1.append(">");
            }
         } else {
            StringBuffer var60 = var1.append("<");
            String var61 = base64.toString(this.other);
            var1.append(var61);
            StringBuffer var63 = var1.append(">");
         }
      }

      if(Options.check("multiline")) {
         StringBuffer var23 = var1.append(" )");
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.alg.toWire(var1, (Compression)null, var3);
      long var4 = this.timeSigned.getTime() / 1000L;
      int var6 = (int)(var4 >> 32);
      long var7 = var4 & 4294967295L;
      var1.writeU16(var6);
      var1.writeU32(var7);
      int var9 = this.fudge;
      var1.writeU16(var9);
      int var10 = this.signature.length;
      var1.writeU16(var10);
      byte[] var11 = this.signature;
      var1.writeByteArray(var11);
      int var12 = this.originalID;
      var1.writeU16(var12);
      int var13 = this.error;
      var1.writeU16(var13);
      if(this.other != null) {
         int var14 = this.other.length;
         var1.writeU16(var14);
         byte[] var15 = this.other;
         var1.writeByteArray(var15);
      } else {
         var1.writeU16(0);
      }
   }
}
