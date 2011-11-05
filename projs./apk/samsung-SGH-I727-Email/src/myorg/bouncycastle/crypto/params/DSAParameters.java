package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.DSAValidationParameters;

public class DSAParameters implements CipherParameters {

   private BigInteger g;
   private BigInteger p;
   private BigInteger q;
   private DSAValidationParameters validation;


   public DSAParameters(BigInteger var1, BigInteger var2, BigInteger var3) {
      this.g = var3;
      this.p = var1;
      this.q = var2;
   }

   public DSAParameters(BigInteger var1, BigInteger var2, BigInteger var3, DSAValidationParameters var4) {
      this.g = var3;
      this.p = var1;
      this.q = var2;
      this.validation = var4;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DSAParameters)) {
         var2 = false;
      } else {
         DSAParameters var3 = (DSAParameters)var1;
         BigInteger var4 = var3.getP();
         BigInteger var5 = this.p;
         if(var4.equals(var5)) {
            BigInteger var6 = var3.getQ();
            BigInteger var7 = this.q;
            if(var6.equals(var7)) {
               BigInteger var8 = var3.getG();
               BigInteger var9 = this.g;
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

   public BigInteger getG() {
      return this.g;
   }

   public BigInteger getP() {
      return this.p;
   }

   public BigInteger getQ() {
      return this.q;
   }

   public DSAValidationParameters getValidationParameters() {
      return this.validation;
   }

   public int hashCode() {
      int var1 = this.getP().hashCode();
      int var2 = this.getQ().hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.getG().hashCode();
      return var3 ^ var4;
   }
}
