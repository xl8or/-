package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class PKCS7Padding implements BlockCipherPadding {

   public PKCS7Padding() {}

   public int addPadding(byte[] var1, int var2) {
      byte var3 = (byte)(var1.length - var2);

      while(true) {
         int var4 = var1.length;
         if(var2 >= var4) {
            return var3;
         }

         var1[var2] = var3;
         ++var2;
      }
   }

   public String getPaddingName() {
      return "PKCS7";
   }

   public void init(SecureRandom var1) throws IllegalArgumentException {}

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2 = var1.length - 1;
      int var3 = var1[var2] & 255;
      int var4 = var1.length;
      if(var3 <= var4 && var3 != 0) {
         for(int var5 = 1; var5 <= var3; ++var5) {
            int var6 = var1.length - var5;
            if(var1[var6] != var3) {
               throw new InvalidCipherTextException("pad block corrupted");
            }
         }

         return var3;
      } else {
         throw new InvalidCipherTextException("pad block corrupted");
      }
   }
}
