package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;

abstract class TXTBase extends Record {

   private static final long serialVersionUID = -4319510507246305931L;
   protected List strings;


   protected TXTBase() {}

   protected TXTBase(Name var1, int var2, int var3, long var4) {
      super(var1, var2, var3, var4);
   }

   protected TXTBase(Name var1, int var2, int var3, long var4, String var6) {
      List var7 = Collections.singletonList(var6);
      this(var1, var2, var3, var4, var7);
   }

   protected TXTBase(Name var1, int var2, int var3, long var4, List var6) {
      super(var1, var2, var3, var4);
      if(var6 == null) {
         throw new IllegalArgumentException("strings must not be null");
      } else {
         int var7 = var6.size();
         ArrayList var8 = new ArrayList(var7);
         this.strings = var8;
         Iterator var9 = var6.iterator();

         try {
            while(var9.hasNext()) {
               String var10 = (String)var9.next();
               List var11 = this.strings;
               byte[] var12 = byteArrayFromString(var10);
               var11.add(var12);
            }

         } catch (TextParseException var15) {
            String var14 = var15.getMessage();
            throw new IllegalArgumentException(var14);
         }
      }
   }

   public List getStrings() {
      int var1 = this.strings.size();
      ArrayList var2 = new ArrayList(var1);
      int var3 = 0;

      while(true) {
         int var4 = this.strings.size();
         if(var3 >= var4) {
            return var2;
         }

         String var5 = byteArrayToString((byte[])((byte[])this.strings.get(var3)), (boolean)0);
         var2.add(var5);
         ++var3;
      }
   }

   public List getStringsAsByteArrays() {
      return this.strings;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      ArrayList var3 = new ArrayList(2);
      this.strings = var3;

      while(true) {
         Tokenizer.Token var4 = var1.get();
         if(!var4.isString()) {
            var1.unget();
            return;
         }

         try {
            List var5 = this.strings;
            byte[] var6 = byteArrayFromString(var4.value);
            var5.add(var6);
         } catch (TextParseException var9) {
            String var8 = var9.getMessage();
            throw var1.exception(var8);
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      ArrayList var2 = new ArrayList(2);
      this.strings = var2;

      while(var1.remaining() > 0) {
         byte[] var3 = var1.readCountedString();
         this.strings.add(var3);
      }

   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = this.strings.iterator();

      while(var2.hasNext()) {
         String var3 = byteArrayToString((byte[])((byte[])var2.next()), (boolean)1);
         var1.append(var3);
         if(var2.hasNext()) {
            StringBuffer var5 = var1.append(" ");
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      Iterator var4 = this.strings.iterator();

      while(var4.hasNext()) {
         byte[] var5 = (byte[])((byte[])var4.next());
         var1.writeCountedString(var5);
      }

   }
}
