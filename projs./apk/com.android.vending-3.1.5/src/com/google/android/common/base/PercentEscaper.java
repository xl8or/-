package com.google.android.common.base;

import com.google.android.common.base.Preconditions;
import com.google.android.common.base.UnicodeEscaper;

public class PercentEscaper extends UnicodeEscaper {

   public static final String SAFECHARS_URLENCODER = "-_.*";
   public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*\'()@:$&,;=";
   public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*\'()@:$,;/?:";
   private static final char[] UPPER_HEX_DIGITS;
   private static final char[] URI_ESCAPED_SPACE;
   private final boolean plusForSpace;
   private final boolean[] safeOctets;


   static {
      char[] var0 = new char[]{'+'};
      URI_ESCAPED_SPACE = var0;
      UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
   }

   public PercentEscaper(String var1, boolean var2) {
      Object var3 = Preconditions.checkNotNull(var1);
      if(var1.matches(".*[0-9A-Za-z].*")) {
         throw new IllegalArgumentException("Alphanumeric characters are always \'safe\' and should not be explicitly specified");
      } else if(var2 && var1.contains(" ")) {
         throw new IllegalArgumentException("plusForSpace cannot be specified when space is a \'safe\' character");
      } else if(var1.contains("%")) {
         throw new IllegalArgumentException("The \'%\' character cannot be specified as \'safe\'");
      } else {
         this.plusForSpace = var2;
         boolean[] var4 = createSafeOctets(var1);
         this.safeOctets = var4;
      }
   }

   private static boolean[] createSafeOctets(String var0) {
      int var1 = 122;
      char[] var2 = var0.toCharArray();
      char[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         var1 = Math.max(var3[var5], var1);
      }

      boolean[] var6 = new boolean[var1 + 1];

      for(int var7 = 48; var7 <= 57; ++var7) {
         var6[var7] = true;
      }

      for(int var8 = 65; var8 <= 90; ++var8) {
         var6[var8] = true;
      }

      for(int var9 = 97; var9 <= 122; ++var9) {
         var6[var9] = true;
      }

      char[] var10 = var2;
      int var11 = var2.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         char var13 = var10[var12];
         var6[var13] = true;
      }

      return var6;
   }

   public String escape(String var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      int var3 = var1.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var1.charAt(var4);
         int var6 = this.safeOctets.length;
         if(var5 >= var6 || !this.safeOctets[var5]) {
            var1 = this.escapeSlow(var1, var4);
            break;
         }
      }

      return var1;
   }

   protected char[] escape(int var1) {
      int var2 = this.safeOctets.length;
      char[] var3;
      if(var1 < var2 && this.safeOctets[var1]) {
         var3 = null;
      } else if(var1 == 32 && this.plusForSpace) {
         var3 = URI_ESCAPED_SPACE;
      } else if(var1 <= 127) {
         var3 = new char[]{'%', '\u0000', '\u0000'};
         char[] var4 = UPPER_HEX_DIGITS;
         int var5 = var1 & 15;
         char var6 = var4[var5];
         var3[2] = var6;
         char[] var7 = UPPER_HEX_DIGITS;
         int var8 = var1 >>> 4;
         char var9 = var7[var8];
         var3[1] = var9;
      } else if(var1 <= 2047) {
         var3 = new char[]{'%', '\u0000', '\u0000', '%', '\u0000', '\u0000'};
         char[] var10 = UPPER_HEX_DIGITS;
         int var11 = var1 & 15;
         char var12 = var10[var11];
         var3[5] = var12;
         int var13 = var1 >>> 4;
         char[] var14 = UPPER_HEX_DIGITS;
         int var15 = var13 & 3 | 8;
         char var16 = var14[var15];
         var3[4] = var16;
         int var17 = var13 >>> 2;
         char[] var18 = UPPER_HEX_DIGITS;
         int var19 = var17 & 15;
         char var20 = var18[var19];
         var3[2] = var20;
         int var21 = var17 >>> 4;
         char[] var22 = UPPER_HEX_DIGITS;
         int var23 = var21 | 12;
         char var24 = var22[var23];
         var3[1] = var24;
      } else if(var1 <= '\uffff') {
         var3 = new char[9];
         var3[0] = 37;
         var3[1] = 69;
         var3[3] = 37;
         var3[6] = 37;
         char[] var25 = UPPER_HEX_DIGITS;
         int var26 = var1 & 15;
         char var27 = var25[var26];
         var3[8] = var27;
         int var28 = var1 >>> 4;
         char[] var29 = UPPER_HEX_DIGITS;
         int var30 = var28 & 3 | 8;
         char var31 = var29[var30];
         var3[7] = var31;
         int var32 = var28 >>> 2;
         char[] var33 = UPPER_HEX_DIGITS;
         int var34 = var32 & 15;
         char var35 = var33[var34];
         var3[5] = var35;
         int var36 = var32 >>> 4;
         char[] var37 = UPPER_HEX_DIGITS;
         int var38 = var36 & 3 | 8;
         char var39 = var37[var38];
         var3[4] = var39;
         int var40 = var36 >>> 2;
         char var41 = UPPER_HEX_DIGITS[var40];
         var3[2] = var41;
      } else {
         if(var1 > 1114111) {
            String var69 = "Invalid unicode character value " + var1;
            throw new IllegalArgumentException(var69);
         }

         var3 = new char[12];
         var3[0] = 37;
         var3[1] = 70;
         var3[3] = 37;
         var3[6] = 37;
         var3[9] = 37;
         char[] var42 = UPPER_HEX_DIGITS;
         int var43 = var1 & 15;
         char var44 = var42[var43];
         var3[11] = var44;
         int var45 = var1 >>> 4;
         char[] var46 = UPPER_HEX_DIGITS;
         int var47 = var45 & 3 | 8;
         char var48 = var46[var47];
         var3[10] = var48;
         int var49 = var45 >>> 2;
         char[] var50 = UPPER_HEX_DIGITS;
         int var51 = var49 & 15;
         char var52 = var50[var51];
         var3[8] = var52;
         int var53 = var49 >>> 4;
         char[] var54 = UPPER_HEX_DIGITS;
         int var55 = var53 & 3 | 8;
         char var56 = var54[var55];
         var3[7] = var56;
         int var57 = var53 >>> 2;
         char[] var58 = UPPER_HEX_DIGITS;
         int var59 = var57 & 15;
         char var60 = var58[var59];
         var3[5] = var60;
         int var61 = var57 >>> 4;
         char[] var62 = UPPER_HEX_DIGITS;
         int var63 = var61 & 3 | 8;
         char var64 = var62[var63];
         var3[4] = var64;
         int var65 = var61 >>> 2;
         char[] var66 = UPPER_HEX_DIGITS;
         int var67 = var65 & 7;
         char var68 = var66[var67];
         var3[2] = var68;
      }

      return var3;
   }

   protected int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      while(true) {
         if(var2 < var3) {
            char var4 = var1.charAt(var2);
            int var5 = this.safeOctets.length;
            if(var4 < var5 && this.safeOctets[var4]) {
               ++var2;
               continue;
            }
         }

         return var2;
      }
   }
}
