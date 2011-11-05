package org.xbill.DNS;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;
import org.xbill.DNS.WireParseException;

final class TypeBitmap implements Serializable {

   private static final long serialVersionUID = -125354057735389003L;
   private TreeSet types;


   private TypeBitmap() {
      TreeSet var1 = new TreeSet();
      this.types = var1;
   }

   public TypeBitmap(DNSInput var1) throws WireParseException {
      this();

      while(var1.remaining() > 0) {
         if(var1.remaining() < 2) {
            throw new WireParseException("invalid bitmap descriptor");
         }

         int var2 = var1.readU8();
         if(var2 < -1) {
            throw new WireParseException("invalid ordering");
         }

         int var3 = var1.readU8();
         int var4 = var1.remaining();
         if(var3 > var4) {
            throw new WireParseException("invalid bitmap");
         }

         for(int var5 = 0; var5 < var3; ++var5) {
            int var6 = var1.readU8();
            if(var6 != 0) {
               for(int var7 = 0; var7 < 8; ++var7) {
                  int var8 = 7 - var7;
                  if((1 << var8 & var6) != 0) {
                     int var9 = var2 * 256;
                     int var10 = var5 * 8;
                     int var11 = var9 + var10 + var7;
                     TreeSet var12 = this.types;
                     Integer var13 = Mnemonic.toInteger(var11);
                     var12.add(var13);
                  }
               }
            }
         }
      }

   }

   public TypeBitmap(Tokenizer var1) throws IOException {
      this();

      while(true) {
         Tokenizer.Token var2 = var1.get();
         if(!var2.isString()) {
            var1.unget();
            return;
         }

         int var3 = Type.value(var2.value);
         if(var3 < 0) {
            StringBuilder var4 = (new StringBuilder()).append("Invalid type: ");
            String var5 = var2.value;
            String var6 = var4.append(var5).toString();
            throw var1.exception(var6);
         }

         TreeSet var7 = this.types;
         Integer var8 = Mnemonic.toInteger(var3);
         var7.add(var8);
      }
   }

   public TypeBitmap(int[] var1) {
      this();
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         Type.check(var1[var2]);
         TreeSet var4 = this.types;
         int var5 = var1[var2];
         Integer var6 = new Integer(var5);
         var4.add(var6);
         ++var2;
      }
   }

   private static void mapToWire(DNSOutput var0, TreeSet var1, int var2) {
      int var3 = (((Integer)var1.last()).intValue() & 255) / 8 + 1;
      int[] var4 = new int[var3];
      var0.writeU8(var2);
      var0.writeU8(var3);

      int var7;
      int var11;
      for(Iterator var5 = var1.iterator(); var5.hasNext(); var4[var7] = var11) {
         int var6 = ((Integer)var5.next()).intValue();
         var7 = (var6 & 255) / 8;
         int var8 = var4[var7];
         int var9 = var6 % 8;
         int var10 = 7 - var9;
         var11 = 1 << var10 | var8;
      }

      for(int var12 = 0; var12 < var3; ++var12) {
         int var13 = var4[var12];
         var0.writeU8(var13);
      }

   }

   public boolean contains(int var1) {
      TreeSet var2 = this.types;
      Integer var3 = Mnemonic.toInteger(var1);
      return var2.contains(var3);
   }

   public boolean empty() {
      return this.types.isEmpty();
   }

   public int[] toArray() {
      int[] var1 = new int[this.types.size()];
      int var2 = 0;

      int var4;
      for(Iterator var3 = this.types.iterator(); var3.hasNext(); var2 = var4) {
         var4 = var2 + 1;
         int var5 = ((Integer)var3.next()).intValue();
         var1[var2] = var5;
      }

      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      StringBuffer var5;
      for(Iterator var2 = this.types.iterator(); var2.hasNext(); var5 = var1.append(' ')) {
         String var3 = Type.string(((Integer)var2.next()).intValue());
         var1.append(var3);
      }

      int var6 = var1.length() - 1;
      return var1.substring(0, var6);
   }

   public void toWire(DNSOutput var1) {
      if(this.types.size() != 0) {
         byte var2 = -1;
         TreeSet var3 = new TreeSet();
         Iterator var4 = this.types.iterator();

         while(var4.hasNext()) {
            int var5 = ((Integer)var4.next()).intValue();
            int var6 = var5 >> 8;
            if(var6 != var2 && var3.size() > 0) {
               mapToWire(var1, var3, var2);
               var3.clear();
            }

            Integer var8 = new Integer(var5);
            var3.add(var8);
         }

         mapToWire(var1, var3, var2);
      }
   }
}
