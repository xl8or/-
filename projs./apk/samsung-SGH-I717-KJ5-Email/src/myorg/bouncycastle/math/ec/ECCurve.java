package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECFieldElement;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.Tnaf;

public abstract class ECCurve {

   ECFieldElement a;
   ECFieldElement b;


   public ECCurve() {}

   public abstract ECPoint createPoint(BigInteger var1, BigInteger var2, boolean var3);

   public abstract ECPoint decodePoint(byte[] var1);

   public abstract ECFieldElement fromBigInteger(BigInteger var1);

   public ECFieldElement getA() {
      return this.a;
   }

   public ECFieldElement getB() {
      return this.b;
   }

   public abstract int getFieldSize();

   public abstract ECPoint getInfinity();

   public static class F2m extends ECCurve {

      private BigInteger h;
      private ECPoint.F2m infinity;
      private int k1;
      private int k2;
      private int k3;
      private int m;
      private byte mu;
      private BigInteger n;
      private BigInteger[] si;


      public F2m(int var1, int var2, int var3, int var4, BigInteger var5, BigInteger var6) {
         Object var14 = null;
         this(var1, var2, var3, var4, var5, var6, (BigInteger)null, (BigInteger)var14);
      }

      public F2m(int var1, int var2, int var3, int var4, BigInteger var5, BigInteger var6, BigInteger var7, BigInteger var8) {
         this.mu = 0;
         this.si = null;
         this.m = var1;
         this.k1 = var2;
         this.k2 = var3;
         this.k3 = var4;
         this.n = var7;
         this.h = var8;
         if(var2 == 0) {
            throw new IllegalArgumentException("k1 must be > 0");
         } else {
            if(var3 == 0) {
               if(var4 != 0) {
                  throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
               }
            } else {
               if(var3 <= var2) {
                  throw new IllegalArgumentException("k2 must be > k1");
               }

               if(var4 <= var3) {
                  throw new IllegalArgumentException("k3 must be > k2");
               }
            }

            ECFieldElement var9 = this.fromBigInteger(var5);
            this.a = var9;
            ECFieldElement var10 = this.fromBigInteger(var6);
            this.b = var10;
            ECPoint.F2m var11 = new ECPoint.F2m(this, (ECFieldElement)null, (ECFieldElement)null);
            this.infinity = var11;
         }
      }

      public F2m(int var1, int var2, BigInteger var3, BigInteger var4) {
         byte var8 = 0;
         Object var11 = null;
         this(var1, var2, 0, var8, var3, var4, (BigInteger)null, (BigInteger)var11);
      }

      public F2m(int var1, int var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6) {
         byte var10 = 0;
         this(var1, var2, 0, var10, var3, var4, var5, var6);
      }

      private ECPoint decompressPoint(byte[] var1, int var2) {
         int var3 = this.m;
         int var4 = this.k1;
         int var5 = this.k2;
         int var6 = this.k3;
         BigInteger var7 = new BigInteger(1, var1);
         ECFieldElement.F2m var8 = new ECFieldElement.F2m(var3, var4, var5, var6, var7);
         BigInteger var9 = var8.toBigInteger();
         BigInteger var10 = ECConstants.ZERO;
         Object var11;
         if(var9.equals(var10)) {
            var11 = (ECFieldElement.F2m)this.b;
            int var12 = 0;

            while(true) {
               int var13 = this.m - 1;
               if(var12 >= var13) {
                  break;
               }

               var11 = ((ECFieldElement)var11).square();
               ++var12;
            }
         } else {
            ECFieldElement var14 = this.a;
            ECFieldElement var15 = var8.add(var14);
            ECFieldElement var16 = this.b;
            ECFieldElement var17 = var8.square().invert();
            ECFieldElement var18 = var16.multiply(var17);
            ECFieldElement var19 = var15.add(var18);
            ECFieldElement var20 = this.solveQuadradicEquation(var19);
            if(var20 == null) {
               throw new RuntimeException("Invalid point compression");
            }

            byte var21 = 0;
            if(var20.toBigInteger().testBit(0)) {
               var21 = 1;
            }

            if(var21 != var2) {
               int var22 = this.m;
               int var23 = this.k1;
               int var24 = this.k2;
               int var25 = this.k3;
               BigInteger var26 = ECConstants.ONE;
               ECFieldElement.F2m var27 = new ECFieldElement.F2m(var22, var23, var24, var25, var26);
               var20 = var20.add(var27);
            }

            var11 = var8.multiply(var20);
         }

         return new ECPoint.F2m(this, var8, (ECFieldElement)var11);
      }

      private ECFieldElement solveQuadradicEquation(ECFieldElement var1) {
         int var2 = this.m;
         int var3 = this.k1;
         int var4 = this.k2;
         int var5 = this.k3;
         BigInteger var6 = ECConstants.ZERO;
         ECFieldElement.F2m var7 = new ECFieldElement.F2m(var2, var3, var4, var5, var6);
         BigInteger var8 = var1.toBigInteger();
         BigInteger var9 = ECConstants.ZERO;
         ECFieldElement.F2m var10;
         if(var8.equals(var9)) {
            var10 = var7;
            return var10;
         } else {
            Random var12 = new Random();

            while(true) {
               int var13 = this.m;
               int var14 = this.k1;
               int var15 = this.k2;
               int var16 = this.k3;
               int var17 = this.m;
               BigInteger var18 = new BigInteger(var17, var12);
               ECFieldElement.F2m var19 = new ECFieldElement.F2m(var13, var14, var15, var16, var18);
               ECFieldElement.F2m var20 = var7;
               ECFieldElement var21 = var1;
               int var22 = 1;

               while(true) {
                  int var23 = this.m - 1;
                  if(var22 > var23) {
                     BigInteger var28 = var21.toBigInteger();
                     BigInteger var29 = ECConstants.ZERO;
                     if(!var28.equals(var29)) {
                        var10 = null;
                        return var10;
                     }

                     BigInteger var30 = var20.square().add(var20).toBigInteger();
                     BigInteger var31 = ECConstants.ZERO;
                     if(!var30.equals(var31)) {
                        var10 = var20;
                        return var10;
                     }
                     break;
                  }

                  ECFieldElement var24 = var21.square();
                  ECFieldElement var25 = var20.square();
                  ECFieldElement var26 = var24.multiply(var19);
                  var25.add(var26);
                  var21 = var24.add(var1);
                  ++var22;
               }
            }
         }
      }

      public ECPoint createPoint(BigInteger var1, BigInteger var2, boolean var3) {
         ECFieldElement var4 = this.fromBigInteger(var1);
         ECFieldElement var5 = this.fromBigInteger(var2);
         return new ECPoint.F2m(this, var4, var5, var3);
      }

      public ECPoint decodePoint(byte[] var1) {
         Object var5;
         switch(var1[0]) {
         case 0:
            var5 = this.getInfinity();
            break;
         case 1:
         case 5:
         default:
            StringBuilder var2 = (new StringBuilder()).append("Invalid point encoding 0x");
            String var3 = Integer.toString(var1[0], 16);
            String var4 = var2.append(var3).toString();
            throw new RuntimeException(var4);
         case 2:
         case 3:
            byte[] var6 = new byte[var1.length - 1];
            int var7 = var6.length;
            System.arraycopy(var1, 1, var6, 0, var7);
            if(var1[0] == 2) {
               var5 = this.decompressPoint(var6, 0);
            } else {
               var5 = this.decompressPoint(var6, 1);
            }
            break;
         case 4:
         case 6:
         case 7:
            byte[] var8 = new byte[(var1.length - 1) / 2];
            byte[] var9 = new byte[(var1.length - 1) / 2];
            int var10 = var8.length;
            System.arraycopy(var1, 1, var8, 0, var10);
            int var11 = var8.length + 1;
            int var12 = var9.length;
            System.arraycopy(var1, var11, var9, 0, var12);
            int var13 = this.m;
            int var14 = this.k1;
            int var15 = this.k2;
            int var16 = this.k3;
            BigInteger var17 = new BigInteger(1, var8);
            ECFieldElement.F2m var18 = new ECFieldElement.F2m(var13, var14, var15, var16, var17);
            int var19 = this.m;
            int var20 = this.k1;
            int var21 = this.k2;
            int var22 = this.k3;
            BigInteger var23 = new BigInteger(1, var9);
            ECFieldElement.F2m var24 = new ECFieldElement.F2m(var19, var20, var21, var22, var23);
            var5 = new ECPoint.F2m(this, var18, var24, (boolean)0);
         }

         return (ECPoint)var5;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof ECCurve.F2m)) {
            var2 = false;
         } else {
            ECCurve.F2m var3 = (ECCurve.F2m)var1;
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
                        ECFieldElement var12 = this.a;
                        ECFieldElement var13 = var3.a;
                        if(var12.equals(var13)) {
                           ECFieldElement var14 = this.b;
                           ECFieldElement var15 = var3.b;
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

      public ECFieldElement fromBigInteger(BigInteger var1) {
         int var2 = this.m;
         int var3 = this.k1;
         int var4 = this.k2;
         int var5 = this.k3;
         return new ECFieldElement.F2m(var2, var3, var4, var5, var1);
      }

      public int getFieldSize() {
         return this.m;
      }

      public BigInteger getH() {
         return this.h;
      }

      public ECPoint getInfinity() {
         return this.infinity;
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

      byte getMu() {
         synchronized(this){}

         byte var2;
         try {
            if(this.mu == 0) {
               byte var1 = Tnaf.getMu(this);
               this.mu = var1;
            }

            var2 = this.mu;
         } finally {
            ;
         }

         return var2;
      }

      public BigInteger getN() {
         return this.n;
      }

      BigInteger[] getSi() {
         synchronized(this){}

         BigInteger[] var2;
         try {
            if(this.si == null) {
               BigInteger[] var1 = Tnaf.getSi(this);
               this.si = var1;
            }

            var2 = this.si;
         } finally {
            ;
         }

         return var2;
      }

      public int hashCode() {
         int var1 = this.a.hashCode();
         int var2 = this.b.hashCode();
         int var3 = var1 ^ var2;
         int var4 = this.m;
         int var5 = var3 ^ var4;
         int var6 = this.k1;
         int var7 = var5 ^ var6;
         int var8 = this.k2;
         int var9 = var7 ^ var8;
         int var10 = this.k3;
         return var9 ^ var10;
      }

      public boolean isKoblitz() {
         boolean var7;
         if(this.n != null && this.h != null) {
            label17: {
               BigInteger var1 = this.a.toBigInteger();
               BigInteger var2 = ECConstants.ZERO;
               if(!var1.equals(var2)) {
                  BigInteger var3 = this.a.toBigInteger();
                  BigInteger var4 = ECConstants.ONE;
                  if(!var3.equals(var4)) {
                     break label17;
                  }
               }

               BigInteger var5 = this.b.toBigInteger();
               BigInteger var6 = ECConstants.ONE;
               if(var5.equals(var6)) {
                  var7 = true;
                  return var7;
               }
            }
         }

         var7 = false;
         return var7;
      }

      public boolean isTrinomial() {
         boolean var1;
         if(this.k2 == 0 && this.k3 == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public static class Fp extends ECCurve {

      ECPoint.Fp infinity;
      BigInteger q;


      public Fp(BigInteger var1, BigInteger var2, BigInteger var3) {
         this.q = var1;
         ECFieldElement var4 = this.fromBigInteger(var2);
         this.a = var4;
         ECFieldElement var5 = this.fromBigInteger(var3);
         this.b = var5;
         ECPoint.Fp var6 = new ECPoint.Fp(this, (ECFieldElement)null, (ECFieldElement)null);
         this.infinity = var6;
      }

      public ECPoint createPoint(BigInteger var1, BigInteger var2, boolean var3) {
         ECFieldElement var4 = this.fromBigInteger(var1);
         ECFieldElement var5 = this.fromBigInteger(var2);
         return new ECPoint.Fp(this, var4, var5, var3);
      }

      public ECPoint decodePoint(byte[] var1) {
         Object var5;
         switch(var1[0]) {
         case 0:
            var5 = this.getInfinity();
            break;
         case 1:
         case 5:
         default:
            StringBuilder var2 = (new StringBuilder()).append("Invalid point encoding 0x");
            String var3 = Integer.toString(var1[0], 16);
            String var4 = var2.append(var3).toString();
            throw new RuntimeException(var4);
         case 2:
         case 3:
            int var6 = var1[0] & 1;
            byte[] var7 = new byte[var1.length - 1];
            int var8 = var7.length;
            System.arraycopy(var1, 1, var7, 0, var8);
            BigInteger var9 = this.q;
            BigInteger var10 = new BigInteger(1, var7);
            ECFieldElement.Fp var11 = new ECFieldElement.Fp(var9, var10);
            ECFieldElement var12 = var11.square();
            ECFieldElement var13 = this.a;
            ECFieldElement var14 = var12.add(var13);
            ECFieldElement var15 = var11.multiply(var14);
            ECFieldElement var16 = this.b;
            ECFieldElement var17 = var15.add(var16).sqrt();
            if(var17 == null) {
               throw new RuntimeException("Invalid point compression");
            }

            byte var18;
            if(var17.toBigInteger().testBit(0)) {
               var18 = 1;
            } else {
               var18 = 0;
            }

            if(var18 == var6) {
               var5 = new ECPoint.Fp(this, var11, var17, (boolean)1);
            } else {
               BigInteger var19 = this.q;
               BigInteger var20 = this.q;
               BigInteger var21 = var17.toBigInteger();
               BigInteger var22 = var20.subtract(var21);
               ECFieldElement.Fp var23 = new ECFieldElement.Fp(var19, var22);
               var5 = new ECPoint.Fp(this, var11, var23, (boolean)1);
            }
            break;
         case 4:
         case 6:
         case 7:
            byte[] var24 = new byte[(var1.length - 1) / 2];
            byte[] var25 = new byte[(var1.length - 1) / 2];
            int var26 = var24.length;
            System.arraycopy(var1, 1, var24, 0, var26);
            int var27 = var24.length + 1;
            int var28 = var25.length;
            System.arraycopy(var1, var27, var25, 0, var28);
            BigInteger var29 = this.q;
            BigInteger var30 = new BigInteger(1, var24);
            ECFieldElement.Fp var31 = new ECFieldElement.Fp(var29, var30);
            BigInteger var32 = this.q;
            BigInteger var33 = new BigInteger(1, var25);
            ECFieldElement.Fp var34 = new ECFieldElement.Fp(var32, var33);
            var5 = new ECPoint.Fp(this, var31, var34);
         }

         return (ECPoint)var5;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof ECCurve.Fp)) {
            var2 = false;
         } else {
            ECCurve.Fp var3 = (ECCurve.Fp)var1;
            BigInteger var4 = this.q;
            BigInteger var5 = var3.q;
            if(var4.equals(var5)) {
               ECFieldElement var6 = this.a;
               ECFieldElement var7 = var3.a;
               if(var6.equals(var7)) {
                  ECFieldElement var8 = this.b;
                  ECFieldElement var9 = var3.b;
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

      public ECFieldElement fromBigInteger(BigInteger var1) {
         BigInteger var2 = this.q;
         return new ECFieldElement.Fp(var2, var1);
      }

      public int getFieldSize() {
         return this.q.bitLength();
      }

      public ECPoint getInfinity() {
         return this.infinity;
      }

      public BigInteger getQ() {
         return this.q;
      }

      public int hashCode() {
         int var1 = this.a.hashCode();
         int var2 = this.b.hashCode();
         int var3 = var1 ^ var2;
         int var4 = this.q.hashCode();
         return var3 ^ var4;
      }
   }
}
