package org.xbill.DNS;

import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.FormattedTime;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;
import org.xbill.DNS.utils.base64;

abstract class SIGBase extends Record {

   private static final long serialVersionUID = -3738444391533812369L;
   protected int alg;
   protected int covered;
   protected Date expire;
   protected int footprint;
   protected int labels;
   protected long origttl;
   protected byte[] signature;
   protected Name signer;
   protected Date timeSigned;


   protected SIGBase() {}

   public SIGBase(Name param1, int param2, int param3, long param4, int param6, int param7, long param8, Date param10, Date param11, int param12, Name param13, byte[] param14) {
      // $FF: Couldn't be decompiled
   }

   public int getAlgorithm() {
      return this.alg;
   }

   public Date getExpire() {
      return this.expire;
   }

   public int getFootprint() {
      return this.footprint;
   }

   public int getLabels() {
      return this.labels;
   }

   public long getOrigTTL() {
      return this.origttl;
   }

   public byte[] getSignature() {
      return this.signature;
   }

   public Name getSigner() {
      return this.signer;
   }

   public Date getTimeSigned() {
      return this.timeSigned;
   }

   public int getTypeCovered() {
      return this.covered;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      String var3 = var1.getString();
      int var4 = Type.value(var3);
      this.covered = var4;
      if(this.covered < 0) {
         String var5 = "Invalid type: " + var3;
         throw var1.exception(var5);
      } else {
         String var6 = var1.getString();
         int var7 = DNSSEC.Algorithm.value(var6);
         this.alg = var7;
         if(this.alg < 0) {
            String var8 = "Invalid algorithm: " + var6;
            throw var1.exception(var8);
         } else {
            int var9 = var1.getUInt8();
            this.labels = var9;
            long var10 = var1.getTTL();
            this.origttl = var10;
            Date var12 = FormattedTime.parse(var1.getString());
            this.expire = var12;
            Date var13 = FormattedTime.parse(var1.getString());
            this.timeSigned = var13;
            int var14 = var1.getUInt16();
            this.footprint = var14;
            Name var15 = var1.getName(var2);
            this.signer = var15;
            byte[] var16 = var1.getBase64();
            this.signature = var16;
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.covered = var2;
      int var3 = var1.readU8();
      this.alg = var3;
      int var4 = var1.readU8();
      this.labels = var4;
      long var5 = var1.readU32();
      this.origttl = var5;
      long var7 = var1.readU32() * 1000L;
      Date var9 = new Date(var7);
      this.expire = var9;
      long var10 = var1.readU32() * 1000L;
      Date var12 = new Date(var10);
      this.timeSigned = var12;
      int var13 = var1.readU16();
      this.footprint = var13;
      Name var14 = new Name(var1);
      this.signer = var14;
      byte[] var15 = var1.readByteArray();
      this.signature = var15;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Type.string(this.covered);
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.alg;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      int var8 = this.labels;
      var1.append(var8);
      StringBuffer var10 = var1.append(" ");
      long var11 = this.origttl;
      var1.append(var11);
      StringBuffer var14 = var1.append(" ");
      if(Options.check("multiline")) {
         StringBuffer var15 = var1.append("(\n\t");
      }

      String var16 = FormattedTime.format(this.expire);
      var1.append(var16);
      StringBuffer var18 = var1.append(" ");
      String var19 = FormattedTime.format(this.timeSigned);
      var1.append(var19);
      StringBuffer var21 = var1.append(" ");
      int var22 = this.footprint;
      var1.append(var22);
      StringBuffer var24 = var1.append(" ");
      Name var25 = this.signer;
      var1.append(var25);
      if(Options.check("multiline")) {
         StringBuffer var27 = var1.append("\n");
         String var28 = base64.formatString(this.signature, 64, "\t", (boolean)1);
         var1.append(var28);
      } else {
         StringBuffer var30 = var1.append(" ");
         String var31 = base64.toString(this.signature);
         var1.append(var31);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.covered;
      var1.writeU16(var4);
      int var5 = this.alg;
      var1.writeU8(var5);
      int var6 = this.labels;
      var1.writeU8(var6);
      long var7 = this.origttl;
      var1.writeU32(var7);
      long var9 = this.expire.getTime() / 1000L;
      var1.writeU32(var9);
      long var11 = this.timeSigned.getTime() / 1000L;
      var1.writeU32(var11);
      int var13 = this.footprint;
      var1.writeU16(var13);
      this.signer.toWire(var1, (Compression)null, var3);
      byte[] var14 = this.signature;
      var1.writeByteArray(var14);
   }

   void setSignature(byte[] var1) {
      this.signature = var1;
   }
}
