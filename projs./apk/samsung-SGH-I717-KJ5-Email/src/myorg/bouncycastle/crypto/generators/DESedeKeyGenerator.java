package myorg.bouncycastle.crypto.generators;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.generators.DESKeyGenerator;
import myorg.bouncycastle.crypto.params.DESedeParameters;

public class DESedeKeyGenerator extends DESKeyGenerator {

   public DESedeKeyGenerator() {}

   public byte[] generateKey() {
      byte[] var1 = new byte[this.strength];

      int var2;
      do {
         this.random.nextBytes(var1);
         DESedeParameters.setOddParity(var1);
         var2 = var1.length;
      } while(DESedeParameters.isWeakKey(var1, 0, var2));

      return var1;
   }

   public void init(KeyGenerationParameters var1) {
      SecureRandom var2 = var1.getRandom();
      this.random = var2;
      int var3 = (var1.getStrength() + 7) / 8;
      this.strength = var3;
      if(this.strength != 0 && this.strength != 21) {
         if(this.strength == 14) {
            this.strength = 16;
         } else if(this.strength != 24) {
            if(this.strength != 16) {
               throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
            }
         }
      } else {
         this.strength = 24;
      }
   }
}
