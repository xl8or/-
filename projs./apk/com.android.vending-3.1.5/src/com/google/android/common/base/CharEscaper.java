package com.google.android.common.base;

import com.google.android.common.base.Escaper;
import com.google.android.common.base.Platform;
import com.google.android.common.base.Preconditions;
import java.io.IOException;

public abstract class CharEscaper extends Escaper {

   private static final int DEST_PAD = 32;


   public CharEscaper() {}

   private static char[] growBuffer(char[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      if(var1 > 0) {
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      return var3;
   }

   public Appendable escape(Appendable var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return new CharEscaper.1(var1);
   }

   public String escape(String var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      int var3 = var1.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var1.charAt(var4);
         if(this.escape(var5) != null) {
            var1 = this.escapeSlow(var1, var4);
            break;
         }
      }

      return var1;
   }

   protected abstract char[] escape(char var1);

   protected String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = Platform.charBufferFromThreadLocal();
      int var5 = var4.length;
      int var6 = 0;

      int var7;
      for(var7 = 0; var2 < var3; ++var2) {
         char var8 = var1.charAt(var2);
         char[] var9 = this.escape(var8);
         if(var9 != null) {
            int var10 = var9.length;
            int var11 = var2 - var7;
            int var12 = var6 + var11 + var10;
            if(var5 < var12) {
               var5 = var3 - var2 + var12 + 32;
               var4 = growBuffer(var4, var6, var5);
            }

            if(var11 > 0) {
               var1.getChars(var7, var2, var4, var6);
               var6 += var11;
            }

            if(var10 > 0) {
               System.arraycopy(var9, 0, var4, var6, var10);
               var6 += var10;
            }

            var7 = var2 + 1;
         }
      }

      int var13 = var3 - var7;
      if(var13 > 0) {
         int var14 = var6 + var13;
         if(var5 < var14) {
            var4 = growBuffer(var4, var6, var14);
         }

         var1.getChars(var7, var3, var4, var6);
         var6 = var14;
      }

      return new String(var4, 0, var6);
   }

   class 1 implements Appendable {

      // $FF: synthetic field
      final Appendable val$out;


      1(Appendable var2) {
         this.val$out = var2;
      }

      public Appendable append(char var1) throws IOException {
         char[] var2 = CharEscaper.this.escape(var1);
         if(var2 == null) {
            this.val$out.append(var1);
         } else {
            char[] var4 = var2;
            int var5 = var2.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               char var7 = var4[var6];
               this.val$out.append(var7);
            }
         }

         return this;
      }

      public Appendable append(CharSequence var1) throws IOException {
         Appendable var2 = this.val$out;
         CharEscaper var3 = CharEscaper.this;
         String var4 = var1.toString();
         String var5 = var3.escape(var4);
         var2.append(var5);
         return this;
      }

      public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
         Appendable var4 = this.val$out;
         CharEscaper var5 = CharEscaper.this;
         String var6 = var1.subSequence(var2, var3).toString();
         String var7 = var5.escape(var6);
         var4.append(var7);
         return this;
      }
   }
}
