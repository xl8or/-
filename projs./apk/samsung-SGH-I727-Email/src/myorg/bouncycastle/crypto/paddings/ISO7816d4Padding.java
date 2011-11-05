package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class ISO7816d4Padding implements BlockCipherPadding {

   public ISO7816d4Padding() {}

   public int addPadding(byte[] var1, int var2) {
      int var3 = var1.length - var2;
      var1[var2] = (byte)'\uff80';
      int var4 = var2 + 1;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            return var3;
         }

         var1[var4] = 0;
         ++var4;
      }
   }

   public String getPaddingName() {
      return "ISO7816-4";
   }

   public void init(SecureRandom var1) throws IllegalArgumentException {}

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2;
      for(var2 = var1.length - 1; var2 > 0 && var1[var2] == 0; var2 += -1) {
         ;
      }

      if(var1[var2] != '\uff80') {
         throw new InvalidCipherTextException("pad block corrupted");
      } else {
         return var1.length - var2;
      }
   }
}
