package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class X923Padding implements BlockCipherPadding {

   SecureRandom random = null;


   public X923Padding() {}

   public int addPadding(byte[] var1, int var2) {
      byte var3 = (byte)(var1.length - var2);

      while(true) {
         int var4 = var1.length - 1;
         if(var2 >= var4) {
            var1[var2] = var3;
            return var3;
         }

         if(this.random == null) {
            var1[var2] = 0;
         } else {
            byte var5 = (byte)this.random.nextInt();
            var1[var2] = var5;
         }

         ++var2;
      }
   }

   public String getPaddingName() {
      return "X9.23";
   }

   public void init(SecureRandom var1) throws IllegalArgumentException {
      this.random = var1;
   }

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2 = var1.length - 1;
      int var3 = var1[var2] & 255;
      int var4 = var1.length;
      if(var3 > var4) {
         throw new InvalidCipherTextException("pad block corrupted");
      } else {
         return var3;
      }
   }
}
