package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.GOST3410ValidationParameters;

public class GOST3410Parameters implements CipherParameters {

   private BigInteger a;
   private BigInteger p;
   private BigInteger q;
   private GOST3410ValidationParameters validation;


   public GOST3410Parameters(BigInteger var1, BigInteger var2, BigInteger var3) {
      this.p = var1;
      this.q = var2;
      this.a = var3;
   }

   public GOST3410Parameters(BigInteger var1, BigInteger var2, BigInteger var3, GOST3410ValidationParameters var4) {
      this.a = var3;
      this.p = var1;
      this.q = var2;
      this.validation = var4;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof GOST3410Parameters)) {
         var2 = false;
      } else {
         GOST3410Parameters var3 = (GOST3410Parameters)var1;
         BigInteger var4 = var3.getP();
         BigInteger var5 = this.p;
         if(var4.equals(var5)) {
            BigInteger var6 = var3.getQ();
            BigInteger var7 = this.q;
            if(var6.equals(var7)) {
               BigInteger var8 = var3.getA();
               BigInteger var9 = this.a;
               if(var8.equals(var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
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

   public GOST3410ValidationParameters getValidationParameters() {
      return this.validation;
   }

   public int hashCode() {
      int var1 = this.p.hashCode();
      int var2 = this.q.hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.a.hashCode();
      return var3 ^ var4;
   }
}
