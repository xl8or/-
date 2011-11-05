package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;

public class ISDNRecord extends Record {

   private static final long serialVersionUID = -8730801385178968798L;
   private byte[] address;
   private byte[] subAddress;


   ISDNRecord() {}

   public ISDNRecord(Name var1, int var2, long var3, String var5, String var6) {
      super(var1, 20, var2, var3);

      try {
         byte[] var12 = byteArrayFromString(var5);
         this.address = var12;
         if(var6 != null) {
            byte[] var13 = byteArrayFromString(var6);
            this.subAddress = var13;
         }
      } catch (TextParseException var15) {
         String var14 = var15.getMessage();
         throw new IllegalArgumentException(var14);
      }
   }

   public String getAddress() {
      return byteArrayToString(this.address, (boolean)0);
   }

   Record getObject() {
      return new ISDNRecord();
   }

   public String getSubAddress() {
      String var1;
      if(this.subAddress == null) {
         var1 = null;
      } else {
         var1 = byteArrayToString(this.subAddress, (boolean)0);
      }

      return var1;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      try {
         byte[] var3 = byteArrayFromString(var1.getString());
         this.address = var3;
         Tokenizer.Token var4 = var1.get();
         if(var4.isString()) {
            byte[] var5 = byteArrayFromString(var4.value);
            this.subAddress = var5;
         } else {
            var1.unget();
         }
      } catch (TextParseException var7) {
         String var6 = var7.getMessage();
         throw var1.exception(var6);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readCountedString();
      this.address = var2;
      if(var1.remaining() > 0) {
         byte[] var3 = var1.readCountedString();
         this.subAddress = var3;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = byteArrayToString(this.address, (boolean)1);
      var1.append(var2);
      if(this.subAddress != null) {
         StringBuffer var4 = var1.append(" ");
         String var5 = byteArrayToString(this.subAddress, (boolean)1);
         var1.append(var5);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.address;
      var1.writeCountedString(var4);
      if(this.subAddress != null) {
         byte[] var5 = this.subAddress;
         var1.writeCountedString(var5);
      }
   }
}
