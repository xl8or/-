package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class X25Record extends Record {

   private static final long serialVersionUID = 4267576252335579764L;
   private byte[] address;


   X25Record() {}

   public X25Record(Name var1, int var2, long var3, String var5) {
      super(var1, 19, var2, var3);
      byte[] var11 = checkAndConvertAddress(var5);
      this.address = var11;
      if(this.address == null) {
         String var12 = "invalid PSDN address " + var5;
         throw new IllegalArgumentException(var12);
      }
   }

   private static final byte[] checkAndConvertAddress(String var0) {
      int var1 = var0.length();
      byte[] var2 = new byte[var1];
      int var3 = 0;

      byte[] var6;
      while(true) {
         if(var3 >= var1) {
            var6 = var2;
            break;
         }

         char var4 = var0.charAt(var3);
         if(!Character.isDigit(var4)) {
            var6 = null;
            break;
         }

         byte var5 = (byte)var4;
         var2[var3] = var5;
         ++var3;
      }

      return var6;
   }

   public String getAddress() {
      return byteArrayToString(this.address, (boolean)0);
   }

   Record getObject() {
      return new X25Record();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      String var3 = var1.getString();
      byte[] var4 = checkAndConvertAddress(var3);
      this.address = var4;
      if(this.address == null) {
         String var5 = "invalid PSDN address " + var3;
         throw var1.exception(var5);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readCountedString();
      this.address = var2;
   }

   String rrToString() {
      return byteArrayToString(this.address, (boolean)1);
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.address;
      var1.writeCountedString(var4);
   }
}
