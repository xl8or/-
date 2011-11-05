package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class GOST3410PublicKeySpec implements KeySpec {

   private BigInteger a;
   private BigInteger p;
   private BigInteger q;
   private BigInteger y;


   public GOST3410PublicKeySpec(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4) {
      this.y = var1;
      this.p = var2;
      this.q = var3;
      this.a = var4;
   }

   public BigInteger getA() {
      return this.a;
   }

   public BigInteger getP() {
      return this.p;
   }

   public BigInteger getQ() {
      return this.q;
   }

   public BigInteger getY() {
      return this.y;
   }
}
