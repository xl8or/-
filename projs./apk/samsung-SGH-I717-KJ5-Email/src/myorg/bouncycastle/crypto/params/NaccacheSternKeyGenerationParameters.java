package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;

public class NaccacheSternKeyGenerationParameters extends KeyGenerationParameters {

   private int certainty;
   private int cntSmallPrimes;
   private boolean debug;


   public NaccacheSternKeyGenerationParameters(SecureRandom var1, int var2, int var3, int var4) {
      this(var1, var2, var3, var4, (boolean)0);
   }

   public NaccacheSternKeyGenerationParameters(SecureRandom var1, int var2, int var3, int var4, boolean var5) {
      super(var1, var2);
      this.debug = (boolean)0;
      this.certainty = var3;
      if(var4 % 2 == 1) {
         throw new IllegalArgumentException("cntSmallPrimes must be a multiple of 2");
      } else if(var4 < 30) {
         throw new IllegalArgumentException("cntSmallPrimes must be >= 30 for security reasons");
      } else {
         this.cntSmallPrimes = var4;
         this.debug = var5;
      }
   }

   public int getCertainty() {
      return this.certainty;
   }

   public int getCntSmallPrimes() {
      return this.cntSmallPrimes;
   }

   public boolean isDebug() {
      return this.debug;
   }
}
