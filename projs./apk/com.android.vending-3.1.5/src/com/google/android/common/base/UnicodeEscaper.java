package com.google.android.common.base;

import com.google.android.common.base.Escaper;
import com.google.android.common.base.Platform;
import com.google.android.common.base.Preconditions;
import java.io.IOException;

public abstract class UnicodeEscaper extends Escaper {

   private static final int DEST_PAD = 32;


   public UnicodeEscaper() {}

   protected static final int codePointAt(CharSequence var0, int var1, int var2) {
      if(var1 < var2) {
         int var3 = var1 + 1;
         int var4 = var0.charAt(var1);
         if(var4 >= '\ud800' && var4 <= '\udfff') {
            if(var4 > '\udbff') {
               StringBuilder var7 = (new StringBuilder()).append("Unexpected low surrogate character \'").append((char)var4).append("\' with value ").append(var4).append(" at index ");
               int var8 = var3 + -1;
               String var9 = var7.append(var8).toString();
               throw new IllegalArgumentException(var9);
            }

            if(var3 == var2) {
               var4 = -var4;
            } else {
               char var5 = var0.charAt(var3);
               if(!Character.isLowSurrogate(var5)) {
                  String var6 = "Expected low surrogate but got char \'" + var5 + "\' with value " + var5 + " at index " + var3;
                  throw new IllegalArgumentException(var6);
               }

               var4 = Character.toCodePoint((char)var4, var5);
            }
         }

         return var4;
      } else {
         throw new IndexOutOfBoundsException("Index exceeds specified range");
      }
   }

   private static final char[] growBuffer(char[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      if(var1 > 0) {
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      return var3;
   }

   public Appendable escape(Appendable var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return new UnicodeEscaper.1(var1);
   }

   public String escape(String var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      int var3 = var1.length();
      int var4 = this.nextEscapeIndex(var1, 0, var3);
      if(var4 != var3) {
         var1 = this.escapeSlow(var1, var4);
      }

      return var1;
   }

   protected abstract char[] escape(int var1);

   protected final String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = Platform.charBufferFromThreadLocal();
      int var5 = 0;

      int var6;
      int var10;
      for(var6 = 0; var2 < var3; var2 = this.nextEscapeIndex(var1, var10, var3)) {
         int var7 = codePointAt(var1, var2, var3);
         if(var7 < 0) {
            throw new IllegalArgumentException("Trailing high surrogate at end of input");
         }

         char[] var8 = this.escape(var7);
         byte var9;
         if(Character.isSupplementaryCodePoint(var7)) {
            var9 = 2;
         } else {
            var9 = 1;
         }

         var10 = var2 + var9;
         if(var8 != null) {
            int var11 = var2 - var6;
            int var12 = var5 + var11;
            int var13 = var8.length;
            int var14 = var12 + var13;
            if(var4.length < var14) {
               int var15 = var3 - var2 + var14 + 32;
               var4 = growBuffer(var4, var5, var15);
            }

            if(var11 > 0) {
               var1.getChars(var6, var2, var4, var5);
               var5 += var11;
            }

            if(var8.length > 0) {
               int var16 = var8.length;
               System.arraycopy(var8, 0, var4, var5, var16);
               int var17 = var8.length;
               var5 += var17;
            }

            var6 = var10;
         }
      }

      int var18 = var3 - var6;
      if(var18 > 0) {
         int var19 = var5 + var18;
         if(var4.length < var19) {
            var4 = growBuffer(var4, var5, var19);
         }

         var1.getChars(var6, var3, var4, var5);
         var5 = var19;
      }

      return new String(var4, 0, var5);
   }

   protected int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      int var4;
      byte var6;
      for(var4 = var2; var4 < var3; var4 += var6) {
         int var5 = codePointAt(var1, var4, var3);
         if(var5 < 0 || this.escape(var5) != null) {
            break;
         }

         if(Character.isSupplementaryCodePoint(var5)) {
            var6 = 2;
         } else {
            var6 = 1;
         }
      }

      return var4;
   }

   class 1 implements Appendable {

      char pendingHighSurrogate;
      // $FF: synthetic field
      final Appendable val$out;


      1(Appendable var2) {
         this.val$out = var2;
         this.pendingHighSurrogate = 0;
      }

      private void completeSurrogatePair(char var1) throws IOException {
         if(!Character.isLowSurrogate(var1)) {
            String var2 = "Expected low surrogate character but got \'" + var1 + "\' with value " + var1;
            throw new IllegalArgumentException(var2);
         } else {
            UnicodeEscaper var3 = UnicodeEscaper.this;
            int var4 = Character.toCodePoint(this.pendingHighSurrogate, var1);
            char[] var5 = var3.escape(var4);
            if(var5 != null) {
               this.outputChars(var5);
            } else {
               Appendable var6 = this.val$out;
               char var7 = this.pendingHighSurrogate;
               var6.append(var7);
               this.val$out.append(var1);
            }

            this.pendingHighSurrogate = 0;
         }
      }

      private void outputChars(char[] var1) throws IOException {
         int var2 = 0;

         while(true) {
            int var3 = var1.length;
            if(var2 >= var3) {
               return;
            }

            Appendable var4 = this.val$out;
            char var5 = var1[var2];
            var4.append(var5);
            ++var2;
         }
      }

      public Appendable append(char var1) throws IOException {
         if(this.pendingHighSurrogate != 0) {
            this.completeSurrogatePair(var1);
         } else if(Character.isHighSurrogate(var1)) {
            this.pendingHighSurrogate = var1;
         } else {
            if(Character.isLowSurrogate(var1)) {
               String var2 = "Unexpected low surrogate character \'" + var1 + "\' with value " + var1;
               throw new IllegalArgumentException(var2);
            }

            char[] var3 = UnicodeEscaper.this.escape(var1);
            if(var3 != null) {
               this.outputChars(var3);
            } else {
               this.val$out.append(var1);
            }
         }

         return this;
      }

      public Appendable append(CharSequence var1) throws IOException {
         int var2 = var1.length();
         return this.append(var1, 0, var2);
      }

      public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
         Object var4 = Preconditions.checkNotNull(var1);
         int var5 = var1.length();
         Preconditions.checkPositionIndexes(var2, var3, var5);
         if(this.pendingHighSurrogate != 0 && var2 < var3) {
            int var6 = var2 + 1;
            char var7 = var1.charAt(var2);
            this.completeSurrogatePair(var7);
            var2 = var6;
         }

         if(var2 < var3) {
            int var8 = var3 + -1;
            char var9 = var1.charAt(var8);
            if(Character.isHighSurrogate(var9)) {
               this.pendingHighSurrogate = var9;
               var3 += -1;
            }

            Appendable var10 = this.val$out;
            UnicodeEscaper var11 = UnicodeEscaper.this;
            String var12 = var1.subSequence(var2, var3).toString();
            String var13 = var11.escape(var12);
            var10.append(var13);
         }

         return this;
      }
   }
}
