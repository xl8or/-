package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;

public class GOST3410PublicKeyParameterSetSpec {

   private BigInteger a;
   private BigInteger p;
   private BigInteger q;


   public GOST3410PublicKeyParameterSetSpec(BigInteger var1, BigInteger var2, BigInteger var3) {
      this.p = var1;
      this.q = var2;
      this.a = var3;
   }

   public boolean equals(Object var1) {
      boolean var9;
      if(var1 instanceof GOST3410PublicKeyParameterSetSpec) {
         GOST3410PublicKeyParameterSetSpec var2 = (GOST3410PublicKeyParameterSetSpec)var1;
         BigInteger var3 = this.a;
         BigInteger var4 = var2.a;
         if(var3.equals(var4)) {
            BigInteger var5 = this.p;
            BigInteger var6 = var2.p;
            if(var5.equals(var6)) {
               BigInteger var7 = this.q;
               BigInteger var8 = var2.q;
               if(var7.equals(var8)) {
                  var9 = true;
                  return var9;
               }
            }
         }

         var9 = false;
      } else {
         var9 = false;
      }

      return var9;
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

   public int hashCode() {
      int var1 = this.a.hashCode();
      int var2 = this.p.hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.q.hashCode();
      return var3 ^ var4;
   }
}
