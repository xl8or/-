package org.codehaus.jackson.util;

import java.util.Arrays;

public final class CharTypes {

   static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
   static final int[] sHexValues;
   static final int[] sInputCodes;
   static final int[] sInputCodesComment;
   static final int[] sInputCodesUtf8;
   static final int[] sOutputEscapes;


   static {
      int[] var0 = new int[256];

      for(int var1 = 0; var1 < 32; ++var1) {
         var0[var1] = -1;
      }

      var0[34] = 1;
      var0[92] = 1;
      sInputCodes = var0;
      int[] var2 = new int[sInputCodes.length];
      int[] var3 = sInputCodes;
      int var4 = sInputCodes.length;
      System.arraycopy(var3, 0, var2, 0, var4);

      for(int var5 = 128; var5 < 256; ++var5) {
         byte var6;
         if((var5 & 224) == 192) {
            var6 = 2;
         } else if((var5 & 240) == 224) {
            var6 = 3;
         } else if((var5 & 248) == 240) {
            var6 = 4;
         } else {
            var6 = -1;
         }

         var2[var5] = var6;
      }

      sInputCodesUtf8 = var2;
      sInputCodesComment = new int[256];
      int[] var7 = sInputCodesUtf8;
      int[] var8 = sInputCodesComment;
      System.arraycopy(var7, 128, var8, 128, 128);
      Arrays.fill(sInputCodesComment, 0, 32, -1);
      sInputCodesComment[9] = 0;
      sInputCodesComment[10] = 10;
      sInputCodesComment[13] = 13;
      sInputCodesComment[42] = 42;
      int[] var9 = new int[256];

      for(int var10 = 0; var10 < 32; ++var10) {
         int var11 = -(var10 + 1);
         var9[var10] = var11;
      }

      var9[34] = 34;
      var9[92] = 92;
      var9[8] = 98;
      var9[9] = 116;
      var9[12] = 102;
      var9[10] = 110;
      var9[13] = 114;
      sOutputEscapes = var9;
      sHexValues = new int[128];
      Arrays.fill(sHexValues, -1);

      int[] var13;
      int var14;
      for(int var12 = 0; var12 < 10; var13[var14] = var12++) {
         var13 = sHexValues;
         var14 = var12 + 48;
      }

      for(int var15 = 0; var15 < 6; ++var15) {
         int[] var16 = sHexValues;
         int var17 = var15 + 97;
         int var18 = var15 + 10;
         var16[var17] = var18;
         int[] var19 = sHexValues;
         int var20 = var15 + 65;
         int var21 = var15 + 10;
         var19[var20] = var21;
      }

   }

   private CharTypes() {}

   public static void appendQuoted(StringBuilder var0, String var1) {
      int[] var2 = sOutputEscapes;
      int var3 = var2.length;
      int var4 = 0;

      for(int var5 = var1.length(); var4 < var5; ++var4) {
         char var6 = var1.charAt(var4);
         if(var6 < var3 && var2[var6] != 0) {
            StringBuilder var8 = var0.append('\\');
            int var9 = var2[var6];
            if(var9 < 0) {
               StringBuilder var10 = var0.append('u');
               StringBuilder var11 = var0.append('0');
               StringBuilder var12 = var0.append('0');
               int var13 = -(var9 + 1);
               char[] var14 = HEX_CHARS;
               int var15 = var13 >> 4;
               char var16 = var14[var15];
               var0.append(var16);
               char[] var18 = HEX_CHARS;
               int var19 = var13 & 15;
               char var20 = var18[var19];
               var0.append(var20);
            } else {
               char var22 = (char)var9;
               var0.append(var22);
            }
         } else {
            var0.append(var6);
         }
      }

   }

   public static int charToHex(int var0) {
      int var1;
      if(var0 > 127) {
         var1 = -1;
      } else {
         var1 = sHexValues[var0];
      }

      return var1;
   }

   public static final int[] getInputCodeComment() {
      return sInputCodesComment;
   }

   public static final int[] getInputCodeLatin1() {
      return sInputCodes;
   }

   public static final int[] getInputCodeUtf8() {
      return sInputCodesUtf8;
   }

   public static final int[] getOutputEscapes() {
      return sOutputEscapes;
   }
}
