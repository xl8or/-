package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.DESParameters;

public class DESedeParameters extends DESParameters {

   public static final int DES_EDE_KEY_LENGTH = 24;


   public DESedeParameters(byte[] var1) {
      super(var1);
      int var2 = var1.length;
      if(isWeakKey(var1, 0, var2)) {
         throw new IllegalArgumentException("attempt to create weak DESede key");
      }
   }

   public static boolean isWeakKey(byte[] var0, int var1) {
      int var2 = var0.length - var1;
      return isWeakKey(var0, var1, var2);
   }

   public static boolean isWeakKey(byte[] var0, int var1, int var2) {
      int var3 = var1;

      boolean var4;
      while(true) {
         if(var3 >= var2) {
            var4 = false;
            break;
         }

         if(DESParameters.isWeakKey(var0, var3)) {
            var4 = true;
            break;
         }

         var3 += 8;
      }

      return var4;
   }
}
