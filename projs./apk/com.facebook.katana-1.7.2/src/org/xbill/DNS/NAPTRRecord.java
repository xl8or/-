package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;

public class NAPTRRecord extends Record {

   private static final long serialVersionUID = 5191232392044947002L;
   private byte[] flags;
   private int order;
   private int preference;
   private byte[] regexp;
   private Name replacement;
   private byte[] service;


   NAPTRRecord() {}

   public NAPTRRecord(Name var1, int var2, long var3, int var5, int var6, String var7, String var8, String var9, Name var10) {
      super(var1, 35, var2, var3);
      int var16 = checkU16("order", var5);
      this.order = var16;
      int var17 = checkU16("preference", var6);
      this.preference = var17;

      try {
         byte[] var18 = byteArrayFromString(var7);
         this.flags = var18;
         byte[] var19 = byteArrayFromString(var8);
         this.service = var19;
         byte[] var20 = byteArrayFromString(var9);
         this.regexp = var20;
      } catch (TextParseException var25) {
         String var24 = var25.getMessage();
         throw new IllegalArgumentException(var24);
      }

      String var21 = "replacement";
      Name var23 = checkName(var21, var10);
      this.replacement = var23;
   }

   public Name getAdditionalName() {
      return this.replacement;
   }

   public String getFlags() {
      return byteArrayToString(this.flags, (boolean)0);
   }

   Record getObject() {
      return new NAPTRRecord();
   }

   public int getOrder() {
      return this.order;
   }

   public int getPreference() {
      return this.preference;
   }

   public String getRegexp() {
      return byteArrayToString(this.regexp, (boolean)0);
   }

   public Name getReplacement() {
      return this.replacement;
   }

   public String getService() {
      return byteArrayToString(this.service, (boolean)0);
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.order = var3;
      int var4 = var1.getUInt16();
      this.preference = var4;

      try {
         byte[] var5 = byteArrayFromString(var1.getString());
         this.flags = var5;
         byte[] var6 = byteArrayFromString(var1.getString());
         this.service = var6;
         byte[] var7 = byteArrayFromString(var1.getString());
         this.regexp = var7;
      } catch (TextParseException var10) {
         String var9 = var10.getMessage();
         throw var1.exception(var9);
      }

      Name var8 = var1.getName(var2);
      this.replacement = var8;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.order = var2;
      int var3 = var1.readU16();
      this.preference = var3;
      byte[] var4 = var1.readCountedString();
      this.flags = var4;
      byte[] var5 = var1.readCountedString();
      this.service = var5;
      byte[] var6 = var1.readCountedString();
      this.regexp = var6;
      Name var7 = new Name(var1);
      this.replacement = var7;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.order;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.preference;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      String var8 = byteArrayToString(this.flags, (boolean)1);
      var1.append(var8);
      StringBuffer var10 = var1.append(" ");
      String var11 = byteArrayToString(this.service, (boolean)1);
      var1.append(var11);
      StringBuffer var13 = var1.append(" ");
      String var14 = byteArrayToString(this.regexp, (boolean)1);
      var1.append(var14);
      StringBuffer var16 = var1.append(" ");
      Name var17 = this.replacement;
      var1.append(var17);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.order;
      var1.writeU16(var4);
      int var5 = this.preference;
      var1.writeU16(var5);
      byte[] var6 = this.flags;
      var1.writeCountedString(var6);
      byte[] var7 = this.service;
      var1.writeCountedString(var7);
      byte[] var8 = this.regexp;
      var1.writeCountedString(var8);
      this.replacement.toWire(var1, (Compression)null, var3);
   }
}
