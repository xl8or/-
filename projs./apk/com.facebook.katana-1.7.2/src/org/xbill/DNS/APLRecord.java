package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.Address;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.WireParseException;
import org.xbill.DNS.utils.base16;

public class APLRecord extends Record {

   private static final long serialVersionUID = -1348173791712935864L;
   private List elements;


   APLRecord() {}

   public APLRecord(Name var1, int var2, long var3, List var5) {
      super(var1, 42, var2, var3);
      int var11 = var5.size();
      ArrayList var12 = new ArrayList(var11);
      this.elements = var12;
      Iterator var13 = var5.iterator();

      while(var13.hasNext()) {
         Object var14 = var13.next();
         if(!(var14 instanceof APLRecord.Element)) {
            throw new IllegalArgumentException("illegal element");
         }

         APLRecord.Element var15 = (APLRecord.Element)var14;
         if(var15.family != 1 && var15.family != 2) {
            throw new IllegalArgumentException("unknown family");
         }

         this.elements.add(var15);
      }

   }

   private static int addressLength(byte[] var0) {
      int var1 = var0.length - 1;

      int var2;
      while(true) {
         if(var1 < 0) {
            var2 = 0;
            break;
         }

         if(var0[var1] != 0) {
            var2 = var1 + 1;
            break;
         }

         var1 += -1;
      }

      return var2;
   }

   private static byte[] parseAddress(byte[] var0, int var1) throws WireParseException {
      if(var0.length > var1) {
         throw new WireParseException("invalid address length");
      } else {
         byte[] var2;
         if(var0.length == var1) {
            var2 = var0;
         } else {
            var2 = new byte[var1];
            int var3 = var0.length;
            System.arraycopy(var0, 0, var2, 0, var3);
         }

         return var2;
      }
   }

   private static boolean validatePrefixLength(int var0, int var1) {
      boolean var2;
      if(var1 >= 0 && var1 < 256) {
         if((var0 != 1 || var1 <= 32) && (var0 != 2 || var1 <= 128)) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public List getElements() {
      return this.elements;
   }

   Record getObject() {
      return new APLRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      ArrayList var3 = new ArrayList(1);
      this.elements = var3;

      while(true) {
         Tokenizer.Token var4 = var1.get();
         if(!var4.isString()) {
            var1.unget();
            return;
         }

         String var5 = var4.value;
         byte var6;
         byte var7;
         if(var5.startsWith("!")) {
            var6 = 1;
            var7 = 1;
         } else {
            var6 = 0;
            var7 = 0;
         }

         int var8 = var5.indexOf(58, var6);
         if(var8 < 0) {
            throw var1.exception("invalid address prefix element");
         }

         int var9 = var5.indexOf(47, var8);
         if(var9 < 0) {
            throw var1.exception("invalid address prefix element");
         }

         String var10 = var5.substring(var6, var8);
         int var11 = var8 + 1;
         String var12 = var5.substring(var11, var9);
         int var13 = var9 + 1;
         String var14 = var5.substring(var13);

         int var15;
         try {
            var15 = Integer.parseInt(var10);
         } catch (NumberFormatException var27) {
            throw var1.exception("invalid family");
         }

         int var16 = var15;
         if(var15 != 1 && var15 != 2) {
            throw var1.exception("unknown family");
         }

         try {
            var15 = Integer.parseInt(var14);
         } catch (NumberFormatException var26) {
            throw var1.exception("invalid prefix length");
         }

         if(!validatePrefixLength(var16, var15)) {
            throw var1.exception("invalid prefix length");
         }

         byte[] var20 = Address.toByteArray(var12, var16);
         if(var20 == null) {
            String var21 = "invalid IP address " + var12;
            throw var1.exception(var21);
         }

         InetAddress var22 = InetAddress.getByAddress(var20);
         List var23 = this.elements;
         APLRecord.Element var24 = new APLRecord.Element((boolean)var7, var22, var15);
         var23.add(var24);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      ArrayList var2 = new ArrayList(1);

      APLRecord.Element var11;
      for(this.elements = var2; var1.remaining() != 0; this.elements.add(var11)) {
         int var3 = var1.readU16();
         int var4 = var1.readU8();
         int var5 = var1.readU8();
         byte var6;
         if((var5 & 128) != 0) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         int var7 = var5 & -129;
         byte[] var8 = var1.readByteArray(var7);
         if(!validatePrefixLength(var3, var4)) {
            throw new WireParseException("invalid prefix length");
         }

         if(var3 != 1 && var3 != 2) {
            var11 = new APLRecord.Element(var3, (boolean)var6, var8, var4, (APLRecord.1)null);
         } else {
            int var9 = Address.addressLength(var3);
            InetAddress var10 = InetAddress.getByAddress(parseAddress(var8, var9));
            var11 = new APLRecord.Element((boolean)var6, var10, var4);
         }
      }

   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = this.elements.iterator();

      while(var2.hasNext()) {
         APLRecord.Element var3 = (APLRecord.Element)var2.next();
         var1.append(var3);
         if(var2.hasNext()) {
            StringBuffer var5 = var1.append(" ");
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      Iterator var4 = this.elements.iterator();

      while(var4.hasNext()) {
         APLRecord.Element var10 = (APLRecord.Element)var4.next();
         byte[] var5;
         int var6;
         if(var10.family != 1 && var10.family != 2) {
            var5 = (byte[])((byte[])var10.address);
            var6 = var5.length;
         } else {
            var5 = ((InetAddress)var10.address).getAddress();
            var6 = addressLength(var5);
         }

         int var7;
         if(var10.negative) {
            var7 = var6 | 128;
         } else {
            var7 = var6;
         }

         int var8 = var10.family;
         var1.writeU16(var8);
         int var9 = var10.prefixLength;
         var1.writeU8(var9);
         var1.writeU8(var7);
         var1.writeByteArray(var5, 0, var6);
      }

   }

   // $FF: synthetic class
   static class 1 {
   }

   public static class Element {

      public final Object address;
      public final int family;
      public final boolean negative;
      public final int prefixLength;


      private Element(int var1, boolean var2, Object var3, int var4) {
         this.family = var1;
         this.negative = var2;
         this.address = var3;
         this.prefixLength = var4;
         if(!APLRecord.validatePrefixLength(var1, var4)) {
            throw new IllegalArgumentException("invalid prefix length");
         }
      }

      // $FF: synthetic method
      Element(int var1, boolean var2, Object var3, int var4, APLRecord.1 var5) {
         this(var1, var2, var3, var4);
      }

      public Element(boolean var1, InetAddress var2, int var3) {
         int var4 = Address.familyOf(var2);
         this(var4, var1, var2, var3);
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 != null && var1 instanceof APLRecord.Element) {
            APLRecord.Element var11 = (APLRecord.Element)var1;
            int var3 = this.family;
            int var4 = var11.family;
            if(var3 == var4) {
               boolean var5 = this.negative;
               boolean var6 = var11.negative;
               if(var5 == var6) {
                  int var7 = this.prefixLength;
                  int var8 = var11.prefixLength;
                  if(var7 == var8) {
                     Object var9 = this.address;
                     Object var10 = var11.address;
                     if(var9.equals(var10)) {
                        var2 = true;
                        return var2;
                     }
                  }
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int hashCode() {
         int var1 = this.address.hashCode();
         int var2 = this.prefixLength;
         int var3 = var1 + var2;
         byte var4;
         if(this.negative) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         return var3 + var4;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         if(this.negative) {
            StringBuffer var2 = var1.append("!");
         }

         int var3 = this.family;
         var1.append(var3);
         StringBuffer var5 = var1.append(":");
         if(this.family != 1 && this.family != 2) {
            String var11 = base16.toString((byte[])((byte[])this.address));
            var1.append(var11);
         } else {
            String var6 = ((InetAddress)this.address).getHostAddress();
            var1.append(var6);
         }

         StringBuffer var8 = var1.append("/");
         int var9 = this.prefixLength;
         var1.append(var9);
         return var1.toString();
      }
   }
}
