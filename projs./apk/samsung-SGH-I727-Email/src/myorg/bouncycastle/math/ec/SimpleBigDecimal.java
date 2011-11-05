package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECConstants;

class SimpleBigDecimal {

   private static final long serialVersionUID = 1L;
   private final BigInteger bigInt;
   private final int scale;


   public SimpleBigDecimal(BigInteger var1, int var2) {
      if(var2 < 0) {
         throw new IllegalArgumentException("scale may not be negative");
      } else {
         this.bigInt = var1;
         this.scale = var2;
      }
   }

   private SimpleBigDecimal(SimpleBigDecimal var1) {
      BigInteger var2 = var1.bigInt;
      this.bigInt = var2;
      int var3 = var1.scale;
      this.scale = var3;
   }

   private void checkScale(SimpleBigDecimal var1) {
      int var2 = this.scale;
      int var3 = var1.scale;
      if(var2 != var3) {
         throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
      }
   }

   public static SimpleBigDecimal getInstance(BigInteger var0, int var1) {
      BigInteger var2 = var0.shiftLeft(var1);
      return new SimpleBigDecimal(var2, var1);
   }

   public SimpleBigDecimal add(BigInteger var1) {
      BigInteger var2 = this.bigInt;
      int var3 = this.scale;
      BigInteger var4 = var1.shiftLeft(var3);
      BigInteger var5 = var2.add(var4);
      int var6 = this.scale;
      return new SimpleBigDecimal(var5, var6);
   }

   public SimpleBigDecimal add(SimpleBigDecimal var1) {
      this.checkScale(var1);
      BigInteger var2 = this.bigInt;
      BigInteger var3 = var1.bigInt;
      BigInteger var4 = var2.add(var3);
      int var5 = this.scale;
      return new SimpleBigDecimal(var4, var5);
   }

   public SimpleBigDecimal adjustScale(int var1) {
      if(var1 < 0) {
         throw new IllegalArgumentException("scale may not be negative");
      } else {
         int var2 = this.scale;
         SimpleBigDecimal var3;
         if(var1 == var2) {
            var3 = new SimpleBigDecimal(this);
         } else {
            BigInteger var4 = this.bigInt;
            int var5 = this.scale;
            int var6 = var1 - var5;
            BigInteger var7 = var4.shiftLeft(var6);
            var3 = new SimpleBigDecimal(var7, var1);
         }

         return var3;
      }
   }

   public int compareTo(BigInteger var1) {
      BigInteger var2 = this.bigInt;
      int var3 = this.scale;
      BigInteger var4 = var1.shiftLeft(var3);
      return var2.compareTo(var4);
   }

   public int compareTo(SimpleBigDecimal var1) {
      this.checkScale(var1);
      BigInteger var2 = this.bigInt;
      BigInteger var3 = var1.bigInt;
      return var2.compareTo(var3);
   }

   public SimpleBigDecimal divide(BigInteger var1) {
      BigInteger var2 = this.bigInt.divide(var1);
      int var3 = this.scale;
      return new SimpleBigDecimal(var2, var3);
   }

   public SimpleBigDecimal divide(SimpleBigDecimal var1) {
      this.checkScale(var1);
      BigInteger var2 = this.bigInt;
      int var3 = this.scale;
      BigInteger var4 = var2.shiftLeft(var3);
      BigInteger var5 = var1.bigInt;
      BigInteger var6 = var4.divide(var5);
      int var7 = this.scale;
      return new SimpleBigDecimal(var6, var7);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else if(!(var1 instanceof SimpleBigDecimal)) {
         var2 = false;
      } else {
         SimpleBigDecimal var3 = (SimpleBigDecimal)var1;
         BigInteger var4 = this.bigInt;
         BigInteger var5 = var3.bigInt;
         if(var4.equals(var5)) {
            int var6 = this.scale;
            int var7 = var3.scale;
            if(var6 == var7) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public BigInteger floor() {
      BigInteger var1 = this.bigInt;
      int var2 = this.scale;
      return var1.shiftRight(var2);
   }

   public int getScale() {
      return this.scale;
   }

   public int hashCode() {
      int var1 = this.bigInt.hashCode();
      int var2 = this.scale;
      return var1 ^ var2;
   }

   public int intValue() {
      return this.floor().intValue();
   }

   public long longValue() {
      return this.floor().longValue();
   }

   public SimpleBigDecimal multiply(BigInteger var1) {
      BigInteger var2 = this.bigInt.multiply(var1);
      int var3 = this.scale;
      return new SimpleBigDecimal(var2, var3);
   }

   public SimpleBigDecimal multiply(SimpleBigDecimal var1) {
      this.checkScale(var1);
      BigInteger var2 = this.bigInt;
      BigInteger var3 = var1.bigInt;
      BigInteger var4 = var2.multiply(var3);
      int var5 = this.scale;
      int var6 = this.scale;
      int var7 = var5 + var6;
      return new SimpleBigDecimal(var4, var7);
   }

   public SimpleBigDecimal negate() {
      BigInteger var1 = this.bigInt.negate();
      int var2 = this.scale;
      return new SimpleBigDecimal(var1, var2);
   }

   public BigInteger round() {
      BigInteger var1 = ECConstants.ONE;
      SimpleBigDecimal var2 = new SimpleBigDecimal(var1, 1);
      int var3 = this.scale;
      SimpleBigDecimal var4 = var2.adjustScale(var3);
      return this.add(var4).floor();
   }

   public SimpleBigDecimal shiftLeft(int var1) {
      BigInteger var2 = this.bigInt.shiftLeft(var1);
      int var3 = this.scale;
      return new SimpleBigDecimal(var2, var3);
   }

   public SimpleBigDecimal subtract(BigInteger var1) {
      BigInteger var2 = this.bigInt;
      int var3 = this.scale;
      BigInteger var4 = var1.shiftLeft(var3);
      BigInteger var5 = var2.subtract(var4);
      int var6 = this.scale;
      return new SimpleBigDecimal(var5, var6);
   }

   public SimpleBigDecimal subtract(SimpleBigDecimal var1) {
      SimpleBigDecimal var2 = var1.negate();
      return this.add(var2);
   }

   public String toString() {
      String var1;
      if(this.scale == 0) {
         var1 = this.bigInt.toString();
      } else {
         BigInteger var2 = this.floor();
         BigInteger var3 = this.bigInt;
         int var4 = this.scale;
         BigInteger var5 = var2.shiftLeft(var4);
         BigInteger var6 = var3.subtract(var5);
         if(this.bigInt.signum() == -1) {
            BigInteger var7 = ECConstants.ONE;
            int var8 = this.scale;
            var6 = var7.shiftLeft(var8).subtract(var6);
         }

         if(var2.signum() == -1) {
            BigInteger var9 = ECConstants.ZERO;
            if(!var6.equals(var9)) {
               BigInteger var10 = ECConstants.ONE;
               var2 = var2.add(var10);
            }
         }

         String var11 = var2.toString();
         char[] var12 = new char[this.scale];
         String var13 = var6.toString(2);
         int var14 = var13.length();
         int var15 = this.scale - var14;

         for(int var16 = 0; var16 < var15; ++var16) {
            var12[var16] = 48;
         }

         for(int var17 = 0; var17 < var14; ++var17) {
            int var18 = var15 + var17;
            char var19 = var13.charAt(var17);
            var12[var18] = var19;
         }

         String var20 = new String(var12);
         StringBuffer var21 = new StringBuffer(var11);
         StringBuffer var22 = var21.append(".");
         var21.append(var20);
         var1 = var21.toString();
      }

      return var1;
   }
}
