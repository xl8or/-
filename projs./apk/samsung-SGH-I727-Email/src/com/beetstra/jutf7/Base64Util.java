package com.beetstra.jutf7;

import java.util.Arrays;

class Base64Util {

   private static final int ALPHABET_LENGTH = 64;
   private final char[] alphabet;
   private final int[] inverseAlphabet;


   Base64Util(String var1) {
      char[] var2 = var1.toCharArray();
      this.alphabet = var2;
      if(var1.length() != 64) {
         StringBuilder var3 = (new StringBuilder()).append("alphabet has incorrect length (should be 64, not ");
         int var4 = var1.length();
         String var5 = var3.append(var4).append(")").toString();
         throw new IllegalArgumentException(var5);
      } else {
         int[] var6 = new int[128];
         this.inverseAlphabet = var6;
         Arrays.fill(this.inverseAlphabet, -1);
         StringBuffer var7 = new StringBuffer("invalid character in alphabet: ");
         int var8 = 0;

         while(true) {
            int var9 = this.alphabet.length;
            if(var8 >= var9) {
               return;
            }

            char var10 = this.alphabet[var8];
            if(var10 >= 128) {
               String var11 = var7.append(var10).toString();
               throw new IllegalArgumentException(var11);
            }

            this.inverseAlphabet[var10] = var8++;
         }
      }
   }

   boolean contains(char var1) {
      boolean var2;
      if(var1 >= 128) {
         var2 = false;
      } else if(this.inverseAlphabet[var1] >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   byte getChar(int var1) {
      return (byte)this.alphabet[var1];
   }

   int getSextet(byte var1) {
      int var2;
      if(var1 >= 128) {
         var2 = -1;
      } else {
         var2 = this.inverseAlphabet[var1];
      }

      return var2;
   }
}
