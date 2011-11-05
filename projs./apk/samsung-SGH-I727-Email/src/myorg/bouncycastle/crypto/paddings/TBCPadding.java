package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class TBCPadding implements BlockCipherPadding {

   public TBCPadding() {}

   public int addPadding(byte[] var1, int var2) {
      int var3 = var1.length - var2;
      short var5;
      byte var6;
      if(var2 > 0) {
         int var4 = var2 - 1;
         if((var1[var4] & 1) == 0) {
            var5 = 255;
         } else {
            var5 = 0;
         }

         var6 = (byte)var5;
      } else {
         int var8 = var1.length - 1;
         if((var1[var8] & 1) == 0) {
            var5 = 255;
         } else {
            var5 = 0;
         }

         var6 = (byte)var5;
      }

      while(true) {
         int var7 = var1.length;
         if(var2 >= var7) {
            return var3;
         }

         var1[var2] = var6;
         ++var2;
      }
   }

   public String getPaddingName() {
      return "TBC";
   }

   public void init(SecureRandom var1) throws IllegalArgumentException {}

   public int padCount(byte[] var1) throws InvalidCipherTextException {
      int var2 = var1.length - 1;
      byte var3 = var1[var2];

      int var4;
      for(var4 = var1.length - 1; var4 > 0; var4 += -1) {
         int var5 = var4 - 1;
         if(var1[var5] != var3) {
            break;
         }
      }

      return var1.length - var4;
   }
}
