package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {

   private BigInteger g;
   int lowerSigmaBound;
   private BigInteger n;


   public NaccacheSternKeyParameters(boolean var1, BigInteger var2, BigInteger var3, int var4) {
      super(var1);
      this.g = var2;
      this.n = var3;
      this.lowerSigmaBound = var4;
   }

   public BigInteger getG() {
      return this.g;
   }

   public int getLowerSigmaBound() {
      return this.lowerSigmaBound;
   }

   public BigInteger getModulus() {
      return this.n;
   }
}
