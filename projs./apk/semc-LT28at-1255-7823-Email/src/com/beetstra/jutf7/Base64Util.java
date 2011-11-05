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
         int var7 = 0;

         while(true) {
            int var8 = this.alphabet.length;
            if(var7 >= var8) {
               return;
            }

            char var9 = this.alphabet[var7];
            if(var9 >= 128) {
               String var10 = "invalid character in alphabet: " + var9;
               throw new IllegalArgumentException(var10);
            }

            this.inverseAlphabet[var9] = var7++;
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
