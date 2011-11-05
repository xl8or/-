package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class ZeroBytePadding implements BlockCipherPadding {

   public ZeroBytePadding() {}

   public int addPadding(byte[] var1, int var2) {
      int var3 = var1.length - var2;

      while(true) {
         int var4 = var1.length;
         if(var2 >= var4) {
            return var3;
         }

         var1[var2] = 0;
         ++var2;
      }
   }

   public String getPaddingName() {
      return "ZeroByte";
   }

   public void init(SecureRandom var1) throws IllegalArgumentException {}

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2;
      for(var2 = var1.length; var2 > 0; var2 += -1) {
         int var3 = var2 - 1;
         if(var1[var3] != 0) {
            break;
         }
      }

      return var1.length - var2;
   }
}
