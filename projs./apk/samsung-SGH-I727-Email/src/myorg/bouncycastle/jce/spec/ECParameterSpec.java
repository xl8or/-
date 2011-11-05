package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECParameterSpec implements AlgorithmParameterSpec {

   private ECPoint G;
   private ECCurve curve;
   private BigInteger h;
   private BigInteger n;
   private byte[] seed;


   public ECParameterSpec(ECCurve var1, ECPoint var2, BigInteger var3) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      BigInteger var4 = BigInteger.valueOf(1L);
      this.h = var4;
      this.seed = null;
   }

   public ECParameterSpec(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      this.h = var4;
      this.seed = null;
   }

   public ECParameterSpec(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
      this.curve = var1;
      this.G = var2;
      this.n = var3;
      this.h = var4;
      this.seed = var5;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof ECParameterSpec)) {
         var2 = false;
      } else {
         ECParameterSpec var3 = (ECParameterSpec)var1;
         ECCurve var4 = this.getCurve();
         ECCurve var5 = var3.getCurve();
         if(var4.equals(var5)) {
            ECPoint var6 = this.getG();
            ECPoint var7 = var3.getG();
            if(var6.equals(var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public ECPoint getG() {
      return this.G;
   }

   public BigInteger getH() {
      return this.h;
   }

   public BigInteger getN() {
      return this.n;
   }

   public byte[] getSeed() {
      return this.seed;
   }

   public int hashCode() {
      int var1 = this.getCurve().hashCode();
      int var2 = this.getG().hashCode();
      return var1 ^ var2;
   }
}
