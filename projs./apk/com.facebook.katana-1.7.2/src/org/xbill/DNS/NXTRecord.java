package org.xbill.DNS;

import java.io.IOException;
import java.util.BitSet;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;

public class NXTRecord extends Record {

   private static final long serialVersionUID = -8851454400765507520L;
   private BitSet bitmap;
   private Name next;


   NXTRecord() {}

   public NXTRecord(Name var1, int var2, long var3, Name var5, BitSet var6) {
      super(var1, 30, var2, var3);
      Name var12 = checkName("next", var5);
      this.next = var12;
      this.bitmap = var6;
   }

   public BitSet getBitmap() {
      return this.bitmap;
   }

   public Name getNext() {
      return this.next;
   }

   Record getObject() {
      return new NXTRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.next = var3;
      BitSet var4 = new BitSet();
      this.bitmap = var4;

      while(true) {
         Tokenizer.Token var5 = var1.get();
         if(!var5.isString()) {
            var1.unget();
            return;
         }

         int var6 = Type.value(var5.value, (boolean)1);
         if(var6 <= 0 || var6 > 128) {
            StringBuilder var7 = (new StringBuilder()).append("Invalid type: ");
            String var8 = var5.value;
            String var9 = var7.append(var8).toString();
            throw var1.exception(var9);
         }

         this.bitmap.set(var6);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.next = var2;
      BitSet var3 = new BitSet();
      this.bitmap = var3;
      int var4 = var1.remaining();

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var1.readU8();

         for(int var7 = 0; var7 < 8; ++var7) {
            int var8 = 7 - var7;
            if((1 << var8 & var6) != 0) {
               BitSet var9 = this.bitmap;
               int var10 = var5 * 8 + var7;
               var9.set(var10);
            }
         }
      }

   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.next;
      var1.append(var2);
      int var4 = this.bitmap.length();

      for(short var5 = 0; var5 < var4; ++var5) {
         if(this.bitmap.get(var5)) {
            StringBuffer var6 = var1.append(" ");
            String var7 = Type.string(var5);
            var1.append(var7);
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.next.toWire(var1, (Compression)null, var3);
      int var4 = this.bitmap.length();
      int var5 = 0;

      for(int var6 = 0; var6 < var4; ++var6) {
         int var9;
         if(this.bitmap.get(var6)) {
            int var7 = var6 % 8;
            int var8 = 7 - var7;
            var9 = 1 << var8;
         } else {
            var9 = 0;
         }

         var5 |= var9;
         if(var6 % 8 != 7) {
            int var10 = var4 - 1;
            if(var6 != var10) {
               continue;
            }
         }

         var1.writeU8(var5);
         boolean var11 = false;
      }

   }
}
