package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.DHValidationParameters;

public class DHParameters implements CipherParameters {

   private static final int DEFAULT_MINIMUM_LENGTH = 160;
   private BigInteger g;
   private BigInteger j;
   private int l;
   private int m;
   private BigInteger p;
   private BigInteger q;
   private DHValidationParameters validation;


   public DHParameters(BigInteger var1, BigInteger var2) {
      this(var1, var2, (BigInteger)null, 0);
   }

   public DHParameters(BigInteger var1, BigInteger var2, BigInteger var3) {
      this(var1, var2, var3, 0);
   }

   public DHParameters(BigInteger var1, BigInteger var2, BigInteger var3, int var4) {
      int var5 = getDefaultMParam(var4);
      Object var11 = null;
      this(var1, var2, var3, var5, var4, (BigInteger)null, (DHValidationParameters)var11);
   }

   public DHParameters(BigInteger var1, BigInteger var2, BigInteger var3, int var4, int var5) {
      Object var12 = null;
      this(var1, var2, var3, var4, var5, (BigInteger)null, (DHValidationParameters)var12);
   }

   public DHParameters(BigInteger var1, BigInteger var2, BigInteger var3, int var4, int var5, BigInteger var6, DHValidationParameters var7) {
      if(var5 != 0) {
         int var8 = var1.bitLength();
         if(var5 >= var8) {
            throw new IllegalArgumentException("when l value specified, it must be less than bitlength(p)");
         }

         if(var5 < var4) {
            throw new IllegalArgumentException("when l value specified, it may not be less than m value");
         }
      }

      this.g = var2;
      this.p = var1;
      this.q = var3;
      this.m = var4;
      this.l = var5;
      this.j = var6;
      this.validation = var7;
   }

   public DHParameters(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, DHValidationParameters var5) {
      this(var1, var2, var3, 160, 0, var4, var5);
   }

   private static int getDefaultMParam(int var0) {
      int var1 = 160;
      if(var0 != 0 && var0 < 160) {
         var1 = var0;
      }

      return var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DHParameters)) {
         var2 = false;
      } else {
         DHParameters var3 = (DHParameters)var1;
         if(this.getQ() != null) {
            BigInteger var4 = this.getQ();
            BigInteger var5 = var3.getQ();
            if(!var4.equals(var5)) {
               var2 = false;
               return var2;
            }
         } else if(var3.getQ() != null) {
            var2 = false;
            return var2;
         }

         BigInteger var6 = var3.getP();
         BigInteger var7 = this.p;
         if(var6.equals(var7)) {
            BigInteger var8 = var3.getG();
            BigInteger var9 = this.g;
            if(var8.equals(var9)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public BigInteger getG() {
      return this.g;
   }

   public BigInteger getJ() {
      return this.j;
   }

   public int getL() {
      return this.l;
   }

   public int getM() {
      return this.m;
   }

   public BigInteger getP() {
      return this.p;
   }

   public BigInteger getQ() {
      return this.q;
   }

   public DHValidationParameters getValidationParameters() {
      return this.validation;
   }

   public int hashCode() {
      int var1 = this.getP().hashCode();
      int var2 = this.getG().hashCode();
      int var3 = var1 ^ var2;
      int var4;
      if(this.getQ() != null) {
         var4 = this.getQ().hashCode();
      } else {
         var4 = 0;
      }

      return var3 ^ var4;
   }
}
