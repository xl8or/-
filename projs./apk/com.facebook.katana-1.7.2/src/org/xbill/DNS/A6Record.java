package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.Address;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class A6Record extends Record {

   private static final long serialVersionUID = -8815026887337346789L;
   private Name prefix;
   private int prefixBits;
   private InetAddress suffix;


   A6Record() {}

   public A6Record(Name var1, int var2, long var3, int var5, InetAddress var6, Name var7) {
      super(var1, 38, var2, var3);
      int var13 = checkU8("prefixBits", var5);
      this.prefixBits = var13;
      if(var6 != false && Address.familyOf(var6) != 2) {
         throw new IllegalArgumentException("invalid IPv6 address");
      } else {
         this.suffix = var6;
         if(var7 != null) {
            Name var14 = checkName("prefix", var7);
            this.prefix = var14;
         }
      }
   }

   Record getObject() {
      return new A6Record();
   }

   public Name getPrefix() {
      return this.prefix;
   }

   public int getPrefixBits() {
      return this.prefixBits;
   }

   public InetAddress getSuffix() {
      return this.suffix;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt8();
      this.prefixBits = var3;
      if(this.prefixBits > 128) {
         throw var1.exception("prefix bits must be [0..128]");
      } else {
         if(this.prefixBits < 128) {
            String var4 = var1.getString();
            byte var5 = 2;

            try {
               InetAddress var6 = Address.getByAddress(var4, var5);
               this.suffix = var6;
            } catch (UnknownHostException var10) {
               String var9 = "invalid IPv6 address: " + var4;
               throw var1.exception(var9);
            }
         }

         if(this.prefixBits > 0) {
            Name var7 = var1.getName(var2);
            this.prefix = var7;
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU8();
      this.prefixBits = var2;
      int var3 = this.prefixBits;
      int var4 = (128 - var3 + 7) / 8;
      if(this.prefixBits < 128) {
         byte[] var5 = new byte[16];
         int var6 = 16 - var4;
         var1.readByteArray(var5, var6, var4);
         InetAddress var7 = InetAddress.getByAddress(var5);
         this.suffix = var7;
      }

      if(this.prefixBits > 0) {
         Name var8 = new Name(var1);
         this.prefix = var8;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.prefixBits;
      var1.append(var2);
      if(this.suffix != null) {
         StringBuffer var4 = var1.append(" ");
         String var5 = this.suffix.getHostAddress();
         var1.append(var5);
      }

      if(this.prefix != null) {
         StringBuffer var7 = var1.append(" ");
         Name var8 = this.prefix;
         var1.append(var8);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.prefixBits;
      var1.writeU8(var4);
      if(this.suffix != null) {
         int var5 = this.prefixBits;
         int var6 = (128 - var5 + 7) / 8;
         byte[] var7 = this.suffix.getAddress();
         int var8 = 16 - var6;
         var1.writeByteArray(var7, var8, var6);
      }

      if(this.prefix != null) {
         this.prefix.toWire(var1, (Compression)null, var3);
      }
   }
}
