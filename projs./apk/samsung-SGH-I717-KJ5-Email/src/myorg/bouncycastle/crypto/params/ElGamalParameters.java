package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;

public class ElGamalParameters implements CipherParameters {

   private BigInteger g;
   private int l;
   private BigInteger p;


   public ElGamalParameters(BigInteger var1, BigInteger var2) {
      this(var1, var2, 0);
   }

   public ElGamalParameters(BigInteger var1, BigInteger var2, int var3) {
      this.g = var2;
      this.p = var1;
      this.l = var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof ElGamalParameters)) {
         var2 = false;
      } else {
         ElGamalParameters var3 = (ElGamalParameters)var1;
         BigInteger var4 = var3.getP();
         BigInteger var5 = this.p;
         if(var4.equals(var5)) {
            BigInteger var6 = var3.getG();
            BigInteger var7 = this.g;
            if(var6.equals(var7)) {
               int var8 = var3.getL();
               int var9 = this.l;
               if(var8 == var9) {
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

   public int getL() {
      return this.l;
   }

   public BigInteger getP() {
      return this.p;
   }

   public int hashCode() {
      int var1 = this.getP().hashCode();
      int var2 = this.getG().hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.l;
      return var3 + var4;
   }
}
