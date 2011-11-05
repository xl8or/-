package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.KeyParameter;

public class DESParameters extends KeyParameter {

   public static final int DES_KEY_LENGTH = 8;
   private static byte[] DES_weak_keys = new byte[]{(byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)31, (byte)31, (byte)31, (byte)31, (byte)14, (byte)14, (byte)14, (byte)14, (byte)224, (byte)224, (byte)224, (byte)224, (byte)241, (byte)241, (byte)241, (byte)241, (byte)254, (byte)254, (byte)254, (byte)254, (byte)254, (byte)254, (byte)254, (byte)254, (byte)1, (byte)254, (byte)1, (byte)254, (byte)1, (byte)254, (byte)1, (byte)254, (byte)31, (byte)224, (byte)31, (byte)224, (byte)14, (byte)241, (byte)14, (byte)241, (byte)1, (byte)224, (byte)1, (byte)224, (byte)1, (byte)241, (byte)1, (byte)241, (byte)31, (byte)254, (byte)31, (byte)254, (byte)14, (byte)254, (byte)14, (byte)254, (byte)1, (byte)31, (byte)1, (byte)31, (byte)1, (byte)14, (byte)1, (byte)14, (byte)224, (byte)254, (byte)224, (byte)254, (byte)241, (byte)254, (byte)241, (byte)254, (byte)254, (byte)1, (byte)254, (byte)1, (byte)254, (byte)1, (byte)254, (byte)1, (byte)224, (byte)31, (byte)224, (byte)31, (byte)241, (byte)14, (byte)241, (byte)14, (byte)224, (byte)1, (byte)224, (byte)1, (byte)241, (byte)1, (byte)241, (byte)1, (byte)254, (byte)31, (byte)254, (byte)31, (byte)254, (byte)14, (byte)254, (byte)14, (byte)31, (byte)1, (byte)31, (byte)1, (byte)14, (byte)1, (byte)14, (byte)1, (byte)254, (byte)224, (byte)254, (byte)224, (byte)254, (byte)241, (byte)254, (byte)241};
   private static final int N_DES_WEAK_KEYS = 16;


   public DESParameters(byte[] var1) {
      super(var1);
      if(isWeakKey(var1, 0)) {
         throw new IllegalArgumentException("attempt to create weak DES key");
      }
   }

   public static boolean isWeakKey(byte[] var0, int var1) {
      if(var0.length - var1 < 8) {
         throw new IllegalArgumentException("key material too short.");
      } else {
         int var2 = 0;

         boolean var9;
         label27:
         while(true) {
            if(var2 < 16) {
               for(int var3 = 0; var3 < 8; ++var3) {
                  int var4 = var3 + var1;
                  byte var5 = var0[var4];
                  byte[] var6 = DES_weak_keys;
                  int var7 = var2 * 8 + var3;
                  byte var8 = var6[var7];
                  if(var5 != var8) {
                     ++var2;
                     continue label27;
                  }
               }

               var9 = true;
               break;
            }

            var9 = false;
            break;
         }

         return var9;
      }
   }

   public static void setOddParity(byte[] var0) {
      int var1 = 0;

      while(true) {
         int var2 = var0.length;
         if(var1 >= var2) {
            return;
         }

         byte var3 = var0[var1];
         int var4 = var3 & 254;
         int var5 = var3 >> 1;
         int var6 = var3 >> 2;
         int var7 = var5 ^ var6;
         int var8 = var3 >> 3;
         int var9 = var7 ^ var8;
         int var10 = var3 >> 4;
         int var11 = var9 ^ var10;
         int var12 = var3 >> 5;
         int var13 = var11 ^ var12;
         int var14 = var3 >> 6;
         int var15 = var13 ^ var14;
         int var16 = var3 >> 7;
         int var17 = (var15 ^ var16 ^ 1) & 1;
         byte var18 = (byte)(var4 | var17);
         var0[var1] = var18;
         ++var1;
      }
   }
}
