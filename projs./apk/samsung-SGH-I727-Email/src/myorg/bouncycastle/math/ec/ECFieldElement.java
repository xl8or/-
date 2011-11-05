package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.IntArray;

public abstract class ECFieldElement implements ECConstants {

   public ECFieldElement() {}

   public abstract ECFieldElement add(ECFieldElement var1);

   public abstract ECFieldElement divide(ECFieldElement var1);

   public abstract String getFieldName();

   public abstract int getFieldSize();

   public abstract ECFieldElement invert();

   public abstract ECFieldElement multiply(ECFieldElement var1);

   public abstract ECFieldElement negate();

   public abstract ECFieldElement sqrt();

   public abstract ECFieldElement square();

   public abstract ECFieldElement subtract(ECFieldElement var1);

   public abstract BigInteger toBigInteger();

   public String toString() {
      return this.toBigInteger().toString(2);
   }

   public static class Fp extends ECFieldElement {

      BigInteger q;
      BigInteger x;


      public Fp(BigInteger var1, BigInteger var2) {
         this.x = var2;
         if(var2.compareTo(var1) >= 0) {
            throw new IllegalArgumentException("x value too large in field element");
         } else {
            this.q = var1;
         }
      }

      private static BigInteger[] lucasSequence(BigInteger var0, BigInteger var1, BigInteger var2, BigInteger var3) {
         int var4 = var3.bitLength();
         int var5 = var3.getLowestSetBit();
         BigInteger var6 = ECConstants.ONE;
         BigInteger var7 = ECConstants.TWO;
         BigInteger var8 = var1;
         BigInteger var9 = ECConstants.ONE;
         BigInteger var10 = ECConstants.ONE;
         int var11 = var4 - 1;

         while(true) {
            int var12 = var5 + 1;
            if(var11 < var12) {
               BigInteger var21 = var9.multiply(var10).mod(var0);
               BigInteger var22 = var21.multiply(var2).mod(var0);
               BigInteger var23 = var6.multiply(var7).subtract(var21).mod(var0);
               BigInteger var24 = var8.multiply(var7);
               BigInteger var25 = var1.multiply(var21);
               BigInteger var26 = var24.subtract(var25).mod(var0);
               BigInteger var27 = var21.multiply(var22).mod(var0);

               for(int var28 = 1; var28 <= var5; ++var28) {
                  var23 = var23.multiply(var26).mod(var0);
                  BigInteger var29 = var26.multiply(var26);
                  BigInteger var30 = var27.shiftLeft(1);
                  var26 = var29.subtract(var30).mod(var0);
                  var27 = var27.multiply(var27).mod(var0);
               }

               BigInteger[] var31 = new BigInteger[]{var23, var26};
               return var31;
            }

            var9 = var9.multiply(var10).mod(var0);
            if(var3.testBit(var11)) {
               var10 = var9.multiply(var2).mod(var0);
               var6 = var6.multiply(var8).mod(var0);
               BigInteger var13 = var8.multiply(var7);
               BigInteger var14 = var1.multiply(var9);
               var7 = var13.subtract(var14).mod(var0);
               BigInteger var15 = var8.multiply(var8);
               BigInteger var16 = var10.shiftLeft(1);
               var8 = var15.subtract(var16).mod(var0);
            } else {
               var10 = var9;
               var6 = var6.multiply(var7).subtract(var9).mod(var0);
               BigInteger var17 = var8.multiply(var7);
               BigInteger var18 = var1.multiply(var9);
               var8 = var17.subtract(var18).mod(var0);
               BigInteger var19 = var7.multiply(var7);
               BigInteger var20 = var9.shiftLeft(1);
               var7 = var19.subtract(var20).mod(var0);
            }

            var11 += -1;
         }
      }

      public ECFieldElement add(ECFieldElement var1) {
         BigInteger var2 = this.q;
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = var3.add(var4);
         BigInteger var6 = this.q;
         BigInteger var7 = var5.mod(var6);
         return new ECFieldElement.Fp(var2, var7);
      }

      public ECFieldElement divide(ECFieldElement var1) {
         BigInteger var2 = this.q;
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = this.q;
         BigInteger var6 = var4.modInverse(var5);
         BigInteger var7 = var3.multiply(var6);
         BigInteger var8 = this.q;
         BigInteger var9 = var7.mod(var8);
         return new ECFieldElement.Fp(var2, var9);
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof ECFieldElement.Fp)) {
            var2 = false;
         } else {
            ECFieldElement.Fp var3 = (ECFieldElement.Fp)var1;
            BigInteger var4 = this.q;
            BigInteger var5 = var3.q;
            if(var4.equals(var5)) {
               BigInteger var6 = this.x;
               BigInteger var7 = var3.x;
               if(var6.equals(var7)) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         }

         return var2;
      }

      public String getFieldName() {
         return "Fp";
      }

      public int getFieldSize() {
         return this.q.bitLength();
      }

      public BigInteger getQ() {
         return this.q;
      }

      public int hashCode() {
         int var1 = this.q.hashCode();
         int var2 = this.x.hashCode();
         return var1 ^ var2;
      }

      public ECFieldElement invert() {
         BigInteger var1 = this.q;
         BigInteger var2 = this.x;
         BigInteger var3 = this.q;
         BigInteger var4 = var2.modInverse(var3);
         return new ECFieldElement.Fp(var1, var4);
      }

      public ECFieldElement multiply(ECFieldElement var1) {
         BigInteger var2 = this.q;
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = var3.multiply(var4);
         BigInteger var6 = this.q;
         BigInteger var7 = var5.mod(var6);
         return new ECFieldElement.Fp(var2, var7);
      }

      public ECFieldElement negate() {
         BigInteger var1 = this.q;
         BigInteger var2 = this.x.negate();
         BigInteger var3 = this.q;
         BigInteger var4 = var2.mod(var3);
         return new ECFieldElement.Fp(var1, var4);
      }

      public ECFieldElement sqrt() {
         if(!this.q.testBit(0)) {
            throw new RuntimeException("not done yet");
         } else {
            ECFieldElement.Fp var11;
            if(this.q.testBit(1)) {
               BigInteger var1 = this.q;
               BigInteger var2 = this.x;
               BigInteger var3 = this.q.shiftRight(2);
               BigInteger var4 = ECConstants.ONE;
               BigInteger var5 = var3.add(var4);
               BigInteger var6 = this.q;
               BigInteger var7 = var2.modPow(var5, var6);
               ECFieldElement.Fp var8 = new ECFieldElement.Fp(var1, var7);
               ECFieldElement var9 = var8.square();
               if(var9.equals(this)) {
                  var11 = var8;
               } else {
                  var11 = null;
               }
            } else {
               BigInteger var12 = this.q;
               BigInteger var13 = ECConstants.ONE;
               BigInteger var14 = var12.subtract(var13);
               BigInteger var15 = var14.shiftRight(1);
               BigInteger var16 = this.x;
               BigInteger var17 = this.q;
               BigInteger var18 = var16.modPow(var15, var17);
               BigInteger var19 = ECConstants.ONE;
               if(!var18.equals(var19)) {
                  var11 = null;
               } else {
                  BigInteger var20 = var14.shiftRight(2).shiftLeft(1);
                  BigInteger var21 = ECConstants.ONE;
                  BigInteger var22 = var20.add(var21);
                  BigInteger var23 = this.x;
                  BigInteger var24 = var23.shiftLeft(2);
                  BigInteger var25 = this.q;
                  BigInteger var26 = var24.mod(var25);
                  Random var27 = new Random();

                  while(true) {
                     BigInteger var29;
                     BigInteger var31;
                     BigInteger var32;
                     do {
                        BigInteger var30;
                        do {
                           int var28 = this.q.bitLength();
                           var29 = new BigInteger(var28, var27);
                           var30 = this.q;
                        } while(var29.compareTo(var30) >= 0);

                        var31 = var29.multiply(var29).subtract(var26);
                        var32 = this.q;
                     } while(!var31.modPow(var15, var32).equals(var14));

                     BigInteger[] var33 = lucasSequence(this.q, var29, var23, var22);
                     BigInteger var34 = var33[0];
                     BigInteger var35 = var33[1];
                     BigInteger var36 = var35.multiply(var35);
                     BigInteger var37 = this.q;
                     if(var36.mod(var37).equals(var26)) {
                        if(var35.testBit(0)) {
                           BigInteger var38 = this.q;
                           var35.add(var38);
                        }

                        BigInteger var40 = var35.shiftRight(1);
                        BigInteger var41 = this.q;
                        var11 = new ECFieldElement.Fp(var41, var40);
                        break;
                     }

                     BigInteger var42 = ECConstants.ONE;
                     if(!var34.equals(var42) && !var34.equals(var14)) {
                        var11 = null;
                        break;
                     }
                  }
               }
            }

            return var11;
         }
      }

      public ECFieldElement square() {
         BigInteger var1 = this.q;
         BigInteger var2 = this.x;
         BigInteger var3 = this.x;
         BigInteger var4 = var2.multiply(var3);
         BigInteger var5 = this.q;
         BigInteger var6 = var4.mod(var5);
         return new ECFieldElement.Fp(var1, var6);
      }

      public ECFieldElement subtract(ECFieldElement var1) {
         BigInteger var2 = this.q;
         BigInteger var3 = this.x;
         BigInteger var4 = var1.toBigInteger();
         BigInteger var5 = var3.subtract(var4);
         BigInteger var6 = this.q;
         BigInteger var7 = var5.mod(var6);
         return new ECFieldElement.Fp(var2, var7);
      }

      public BigInteger toBigInteger() {
         return this.x;
      }
   }

   public static class F2m extends ECFieldElement {

      public static final int GNB = 1;
      public static final int PPB = 3;
      public static final int TPB = 2;
      private int k1;
      private int k2;
      private int k3;
      private int m;
      private int representation;
      private int t;
      private IntArray x;


      public F2m(int var1, int var2, int var3, int var4, BigInteger var5) {
         int var6 = var1 + 31 >> 5;
         this.t = var6;
         int var7 = this.t;
         IntArray var8 = new IntArray(var5, var7);
         this.x = var8;
         if(var3 == 0 && var4 == 0) {
            this.representation = 2;
         } else {
            if(var3 >= var4) {
               throw new IllegalArgumentException("k2 must be smaller than k3");
            }

            if(var3 <= 0) {
               throw new IllegalArgumentException("k2 must be larger than 0");
            }

            this.representation = 3;
         }

         if(var5.signum() < 0) {
            throw new IllegalArgumentException("x value cannot be negative");
         } else {
            this.m = var1;
            this.k1 = var2;
            this.k2 = var3;
            this.k3 = var4;
         }
      }

      private F2m(int var1, int var2, int var3, int var4, IntArray var5) {
         int var6 = var1 + 31 >> 5;
         this.t = var6;
         this.x = var5;
         this.m = var1;
         this.k1 = var2;
         this.k2 = var3;
         this.k3 = var4;
         if(var3 == 0 && var4 == 0) {
            this.representation = 2;
         } else {
            this.representation = 3;
         }
      }

      public F2m(int var1, int var2, BigInteger var3) {
         byte var7 = 0;
         this(var1, var2, 0, var7, var3);
      }

      public static void checkFieldElements(ECFieldElement var0, ECFieldElement var1) {
         if(var0 instanceof ECFieldElement.F2m && var1 instanceof ECFieldElement.F2m) {
            ECFieldElement.F2m var2 = (ECFieldElement.F2m)var0;
            ECFieldElement.F2m var3 = (ECFieldElement.F2m)var1;
            int var4 = var2.m;
            int var5 = var3.m;
            if(var4 == var5) {
               int var6 = var2.k1;
               int var7 = var3.k1;
               if(var6 == var7) {
                  int var8 = var2.k2;
                  int var9 = var3.k2;
                  if(var8 == var9) {
                     int var10 = var2.k3;
                     int var11 = var3.k3;
                     if(var10 == var11) {
                        int var12 = var2.representation;
                        int var13 = var3.representation;
                        if(var12 == var13) {
                           return;
                        }

                        throw new IllegalArgumentException("One of the field elements are not elements has incorrect representation");
                     }
                  }
               }
            }

            throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
         } else {
            throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
         }
      }

      public ECFieldElement add(ECFieldElement var1) {
         IntArray var2 = (IntArray)this.x.clone();
         IntArray var3 = ((ECFieldElement.F2m)var1).x;
         var2.addShifted(var3, 0);
         int var4 = this.m;
         int var5 = this.k1;
         int var6 = this.k2;
         int var7 = this.k3;
         return new ECFieldElement.F2m(var4, var5, var6, var7, var2);
      }

      public ECFieldElement divide(ECFieldElement var1) {
         ECFieldElement var2 = var1.invert();
         return this.multiply(var2);
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof ECFieldElement.F2m)) {
            var2 = false;
         } else {
            ECFieldElement.F2m var3 = (ECFieldElement.F2m)var1;
            int var4 = this.m;
            int var5 = var3.m;
            if(var4 == var5) {
               int var6 = this.k1;
               int var7 = var3.k1;
               if(var6 == var7) {
                  int var8 = this.k2;
                  int var9 = var3.k2;
                  if(var8 == var9) {
                     int var10 = this.k3;
                     int var11 = var3.k3;
                     if(var10 == var11) {
                        int var12 = this.representation;
                        int var13 = var3.representation;
                        if(var12 == var13) {
                           IntArray var14 = this.x;
                           IntArray var15 = var3.x;
                           if(var14.equals(var15)) {
                              var2 = true;
                              return var2;
                           }
                        }
                     }
                  }
               }
            }

            var2 = false;
         }

         return var2;
      }

      public String getFieldName() {
         return "F2m";
      }

      public int getFieldSize() {
         return this.m;
      }

      public int getK1() {
         return this.k1;
      }

      public int getK2() {
         return this.k2;
      }

      public int getK3() {
         return this.k3;
      }

      public int getM() {
         return this.m;
      }

      public int getRepresentation() {
         return this.representation;
      }

      public int hashCode() {
         int var1 = this.x.hashCode();
         int var2 = this.m;
         int var3 = var1 ^ var2;
         int var4 = this.k1;
         int var5 = var3 ^ var4;
         int var6 = this.k2;
         int var7 = var5 ^ var6;
         int var8 = this.k3;
         return var7 ^ var8;
      }

      public ECFieldElement invert() {
         IntArray var1 = (IntArray)this.x.clone();
         IntArray var2 = new IntArray;
         int var3 = this.t;
         var2.<init>(var3);
         int var6 = this.m;
         var2.setBit(var6);
         byte var10 = 0;
         var2.setBit(var10);
         int var11 = this.k1;
         var2.setBit(var11);
         if(this.representation == 3) {
            int var14 = this.k2;
            var2.setBit(var14);
            int var17 = this.k3;
            var2.setBit(var17);
         }

         int var20 = this.t;
         IntArray var21 = new IntArray(var20);
         var21.setBit(0);
         int var22 = this.t;
         IntArray var23 = new IntArray(var22);

         while(!var1.isZero()) {
            int var24 = var1.bitLength();
            int var25 = var2.bitLength();
            int var26 = var24 - var25;
            if(var26 < 0) {
               IntArray var27 = var1;
               var1 = var2;
               var2 = var27;
               IntArray var28 = var21;
               var21 = var23;
               var23 = var28;
               var26 = -var26;
            }

            int var29 = var26 >> 5;
            int var30 = var26 & 31;
            IntArray var33 = var2.shiftLeft(var30);
            var1.addShifted(var33, var29);
            IntArray var37 = var23.shiftLeft(var30);
            var21.addShifted(var37, var29);
         }

         int var38 = this.m;
         int var39 = this.k1;
         int var40 = this.k2;
         int var41 = this.k3;
         return new ECFieldElement.F2m(var38, var39, var40, var41, var23);
      }

      public ECFieldElement multiply(ECFieldElement var1) {
         ECFieldElement.F2m var2 = (ECFieldElement.F2m)var1;
         IntArray var3 = this.x;
         IntArray var4 = var2.x;
         int var5 = this.m;
         IntArray var6 = var3.multiply(var4, var5);
         int var7 = this.m;
         int[] var8 = new int[3];
         int var9 = this.k1;
         var8[0] = var9;
         int var10 = this.k2;
         var8[1] = var10;
         int var11 = this.k3;
         var8[2] = var11;
         var6.reduce(var7, var8);
         int var12 = this.m;
         int var13 = this.k1;
         int var14 = this.k2;
         int var15 = this.k3;
         return new ECFieldElement.F2m(var12, var13, var14, var15, var6);
      }

      public ECFieldElement negate() {
         return this;
      }

      public ECFieldElement sqrt() {
         throw new RuntimeException("Not implemented");
      }

      public ECFieldElement square() {
         IntArray var1 = this.x;
         int var2 = this.m;
         IntArray var3 = var1.square(var2);
         int var4 = this.m;
         int[] var5 = new int[3];
         int var6 = this.k1;
         var5[0] = var6;
         int var7 = this.k2;
         var5[1] = var7;
         int var8 = this.k3;
         var5[2] = var8;
         var3.reduce(var4, var5);
         int var9 = this.m;
         int var10 = this.k1;
         int var11 = this.k2;
         int var12 = this.k3;
         return new ECFieldElement.F2m(var9, var10, var11, var12, var3);
      }

      public ECFieldElement subtract(ECFieldElement var1) {
         return this.add(var1);
      }

      public BigInteger toBigInteger() {
         return this.x.toBigInteger();
      }
   }
}
