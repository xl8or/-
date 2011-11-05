package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;

public class HINFORecord extends Record {

   private static final long serialVersionUID = -4732870630947452112L;
   private byte[] cpu;
   private byte[] os;


   HINFORecord() {}

   public HINFORecord(Name var1, int var2, long var3, String var5, String var6) {
      super(var1, 13, var2, var3);

      try {
         byte[] var12 = byteArrayFromString(var5);
         this.cpu = var12;
         byte[] var13 = byteArrayFromString(var6);
         this.os = var13;
      } catch (TextParseException var15) {
         String var14 = var15.getMessage();
         throw new IllegalArgumentException(var14);
      }
   }

   public String getCPU() {
      return byteArrayToString(this.cpu, (boolean)0);
   }

   public String getOS() {
      return byteArrayToString(this.os, (boolean)0);
   }

   Record getObject() {
      return new HINFORecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      try {
         byte[] var3 = byteArrayFromString(var1.getString());
         this.cpu = var3;
         byte[] var4 = byteArrayFromString(var1.getString());
         this.os = var4;
      } catch (TextParseException var6) {
         String var5 = var6.getMessage();
         throw var1.exception(var5);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readCountedString();
      this.cpu = var2;
      byte[] var3 = var1.readCountedString();
      this.os = var3;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = byteArrayToString(this.cpu, (boolean)1);
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      String var5 = byteArrayToString(this.os, (boolean)1);
      var1.append(var5);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.cpu;
      var1.writeCountedString(var4);
      byte[] var5 = this.os;
      var1.writeCountedString(var5);
   }
}
