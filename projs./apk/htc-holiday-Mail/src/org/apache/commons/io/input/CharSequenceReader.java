package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

public class CharSequenceReader extends Reader implements Serializable {

   private final CharSequence charSequence;
   private int idx;
   private int mark;


   public CharSequenceReader(CharSequence var1) {
      Object var2;
      if(var1 != null) {
         var2 = var1;
      } else {
         var2 = "";
      }

      this.charSequence = (CharSequence)var2;
   }

   public void close() {
      this.idx = 0;
      this.mark = 0;
   }

   public void mark(int var1) {
      int var2 = this.idx;
      this.mark = var2;
   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      int var1 = this.idx;
      int var2 = this.charSequence.length();
      char var3;
      if(var1 >= var2) {
         var3 = '\uffff';
      } else {
         CharSequence var4 = this.charSequence;
         int var5 = this.idx;
         int var6 = var5 + 1;
         this.idx = var6;
         var3 = var4.charAt(var5);
      }

      return var3;
   }

   public int read(char[] var1, int var2, int var3) {
      int var4 = this.idx;
      int var5 = this.charSequence.length();
      int var6;
      if(var4 >= var5) {
         var6 = -1;
         return var6;
      } else if(var1 == null) {
         throw new NullPointerException("Character array is missing");
      } else {
         if(var3 >= 0) {
            int var7 = var2 + var3;
            int var8 = var1.length;
            if(var7 <= var8) {
               int var12 = 0;

               for(int var13 = 0; var13 < var3; ++var13) {
                  int var14 = this.read();
                  if(var14 == -1) {
                     var6 = var12;
                     return var6;
                  }

                  int var15 = var2 + var13;
                  char var16 = (char)var14;
                  var1[var15] = var16;
                  ++var12;
               }

               var6 = var12;
               return var6;
            }
         }

         StringBuilder var9 = (new StringBuilder()).append("Array Size=");
         int var10 = var1.length;
         String var11 = var9.append(var10).append(", offset=").append(var2).append(", length=").append(var3).toString();
         throw new IndexOutOfBoundsException(var11);
      }
   }

   public void reset() {
      int var1 = this.mark;
      this.idx = var1;
   }

   public long skip(long var1) {
      if(var1 < 0L) {
         String var3 = "Number of characters to skip is less than zero: " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         int var4 = this.idx;
         int var5 = this.charSequence.length();
         long var6;
         if(var4 >= var5) {
            var6 = 65535L;
         } else {
            long var8 = (long)this.charSequence.length();
            long var10 = (long)this.idx + var1;
            int var12 = (int)Math.min(var8, var10);
            int var13 = this.idx;
            int var14 = var12 - var13;
            this.idx = var12;
            var6 = (long)var14;
         }

         return var6;
      }
   }

   public String toString() {
      return this.charSequence.toString();
   }
}
