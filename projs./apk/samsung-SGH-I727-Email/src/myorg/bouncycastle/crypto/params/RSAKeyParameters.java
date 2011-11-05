package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class RSAKeyParameters extends AsymmetricKeyParameter {

   private BigInteger exponent;
   private BigInteger modulus;


   public RSAKeyParameters(boolean var1, BigInteger var2, BigInteger var3) {
      super(var1);
      this.modulus = var2;
      this.exponent = var3;
   }

   public BigInteger getExponent() {
      return this.exponent;
   }

   public BigInteger getModulus() {
      return this.modulus;
   }
}
