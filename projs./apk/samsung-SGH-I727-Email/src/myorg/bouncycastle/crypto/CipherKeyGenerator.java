package myorg.bouncycastle.crypto;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;

public class CipherKeyGenerator {

   protected SecureRandom random;
   protected int strength;


   public CipherKeyGenerator() {}

   public byte[] generateKey() {
      byte[] var1 = new byte[this.strength];
      this.random.nextBytes(var1);
      return var1;
   }

   public void init(KeyGenerationParameters var1) {
      SecureRandom var2 = var1.getRandom();
      this.random = var2;
      int var3 = (var1.getStrength() + 7) / 8;
      this.strength = var3;
   }
}
