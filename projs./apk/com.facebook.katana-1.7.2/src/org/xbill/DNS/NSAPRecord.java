package org.xbill.DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base16;

public class NSAPRecord extends Record {

   private static final long serialVersionUID = -1037209403185658593L;
   private byte[] address;


   NSAPRecord() {}

   public NSAPRecord(Name var1, int var2, long var3, String var5) {
      super(var1, 22, var2, var3);
      byte[] var11 = checkAndConvertAddress(var5);
      this.address = var11;
      if(this.address == null) {
         String var12 = "invalid NSAP address " + var5;
         throw new IllegalArgumentException(var12);
      }
   }

   private static final byte[] checkAndConvertAddress(String var0) {
      byte var1 = 2;
      byte[] var2;
      if(!var0.substring(0, var1).equalsIgnoreCase("0x")) {
         var2 = null;
      } else {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         byte var4 = 2;
         boolean var5 = false;
         int var9 = 0;

         while(true) {
            int var6 = var0.length();
            if(var4 >= var6) {
               if(var5) {
                  var2 = null;
               } else {
                  var2 = var3.toByteArray();
               }
               break;
            }

            char var7 = var0.charAt(var4);
            if(var7 != 46) {
               int var10 = Character.digit(var7, 16);
               if(var10 == -1) {
                  var2 = null;
                  break;
               }

               if(var5) {
                  var9 += var10;
                  var3.write(var9);
                  var5 = false;
               } else {
                  var9 = var10 << 4;
                  var5 = true;
               }
            }

            int var8 = var4 + 1;
         }
      }

      return var2;
   }

   public String getAddress() {
      return byteArrayToString(this.address, (boolean)0);
   }

   Record getObject() {
      return new NSAPRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      String var3 = var1.getString();
      byte[] var4 = checkAndConvertAddress(var3);
      this.address = var4;
      if(this.address == null) {
         String var5 = "invalid NSAP address " + var3;
         throw var1.exception(var5);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readByteArray();
      this.address = var2;
   }

   String rrToString() {
      StringBuilder var1 = (new StringBuilder()).append("0x");
      String var2 = base16.toString(this.address);
      return var1.append(var2).toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.address;
      var1.writeByteArray(var4);
   }
}
