package myorg.bouncycastle.crypto.modes.gcm;

import myorg.bouncycastle.crypto.util.Pack;

abstract class GCMUtil {

   GCMUtil() {}

   static int[] asInts(byte[] var0) {
      int[] var1 = new int[4];
      int var2 = Pack.bigEndianToInt(var0, 0);
      var1[0] = var2;
      int var3 = Pack.bigEndianToInt(var0, 4);
      var1[1] = var3;
      int var4 = Pack.bigEndianToInt(var0, 8);
      var1[2] = var4;
      int var5 = Pack.bigEndianToInt(var0, 12);
      var1[3] = var5;
      return var1;
   }

   static void multiplyP(int[] var0) {
      boolean var1;
      if((var0[3] & 1) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      shiftRight(var0);
      if(var1) {
         int var2 = var0[0] ^ -520093696;
         var0[0] = var2;
      }
   }

   static void multiplyP8(int[] var0) {
      for(int var1 = 8; var1 != 0; var1 += -1) {
         multiplyP(var0);
      }

   }

   static void shiftRight(byte[] var0) {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = var0[var1] & 255;
         byte var4 = (byte)(var3 >>> 1 | var2);
         var0[var1] = var4;
         ++var1;
         if(var1 == 16) {
            return;
         }

         var2 = (var3 & 1) << 7;
      }
   }

   static void shiftRight(int[] var0) {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = var0[var1];
         int var4 = var3 >>> 1 | var2;
         var0[var1] = var4;
         ++var1;
         if(var1 == 4) {
            return;
         }

         var2 = var3 << 31;
      }
   }

   static void xor(byte[] var0, byte[] var1) {
      for(int var2 = 15; var2 >= 0; var2 += -1) {
         byte var3 = var0[var2];
         byte var4 = var1[var2];
         byte var5 = (byte)(var3 ^ var4);
         var0[var2] = var5;
      }

   }

   static void xor(int[] var0, int[] var1) {
      for(int var2 = 3; var2 >= 0; var2 += -1) {
         int var3 = var0[var2];
         int var4 = var1[var2];
         int var5 = var3 ^ var4;
         var0[var2] = var5;
      }

   }
}
