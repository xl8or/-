package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {

   private BigInteger dP;
   private BigInteger dQ;
   private BigInteger e;
   private BigInteger p;
   private BigInteger q;
   private BigInteger qInv;


   public RSAPrivateCrtKeyParameters(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6, BigInteger var7, BigInteger var8) {
      super((boolean)1, var1, var3);
      this.e = var2;
      this.p = var4;
      this.q = var5;
      this.dP = var6;
      this.dQ = var7;
      this.qInv = var8;
   }

   public BigInteger getDP() {
      return this.dP;
   }

   public BigInteger getDQ() {
      return this.dQ;
   }

   public BigInteger getP() {
      return this.p;
   }

   public BigInteger getPublicExponent() {
      return this.e;
   }

   public BigInteger getQ() {
      return this.q;
   }

   public BigInteger getQInv() {
      return this.qInv;
   }
}
