package myorg.bouncycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters {

   private SecureRandom random;
   private int strength;


   public KeyGenerationParameters(SecureRandom var1, int var2) {
      this.random = var1;
      this.strength = var2;
   }

   public SecureRandom getRandom() {
      return this.random;
   }

   public int getStrength() {
      return this.strength;
   }
}
