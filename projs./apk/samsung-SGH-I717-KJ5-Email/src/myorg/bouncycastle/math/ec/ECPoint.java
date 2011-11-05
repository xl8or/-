package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.x9.X9IntegerConverter;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;
import myorg.bouncycastle.math.ec.ECMultiplier;
import myorg.bouncycastle.math.ec.FpNafMultiplier;
import myorg.bouncycastle.math.ec.PreCompInfo;

public abstract class ECPoint {

   private static X9IntegerConverter converter = new X9IntegerConverter();
   ECCurve curve;
   protected ECMultiplier multiplier = null;
   protected PreCompInfo preCompInfo = null;
   protected boolean withCompression;
   ECFieldElement x;
   ECFieldElement y;


   protected ECPoint(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
      this.curve = var1;
      this.x = var2;
      this.y = var3;
   }

   public abstract ECPoint add(ECPoint var1);

   void assertECMultiplier() {
      synchronized(this){}

      try {
         if(this.multiplier == null) {
            FpNafMultiplier var1 = new FpNafMultiplier();
            this.multiplier = var1;
         }
      } finally {
         ;
      }

   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof ECPoint)) {
         var2 = false;
      } else {
         ECPoint var3 = (ECPoint)var1;
         if(this.isInfinity()) {
            var2 = var3.isInfinity();
         } else {
            ECFieldElement var4 = this.x;
            ECFieldElement var5 = var3.x;
            if(var4.equals(var5)) {
               ECFieldElement var6 = this.y;
               ECFieldElement var7 = var3.y;
               if(var6.equals(var7)) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         }
      }

      return var2;
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public abstract byte[] getEncoded();

   public ECFieldElement getX() {
      return this.x;
   }

   public ECFieldElement getY() {
      return this.y;
   }

   public int hashCode() {
      int var1;
      if(this.isInfinity()) {
         var1 = 0;
      } else {
         int var2 = this.x.hashCode();
         int var3 = this.y.hashCode();
         var1 = var2 ^ var3;
      }

      return var1;
   }

   public boolean isCompressed() {
      return this.withCompression;
   }

   public boolean isInfinity() {
      boolean var1;
      if(this.x == null && this.y == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public ECPoint multiply(BigInteger var1) {
      ECPoint var2;
      if(this.isInfinity()) {
         var2 = this;
      } else if(var1.signum() == 0) {
         var2 = this.curve.getInfinity();
      } else {
         this.assertECMultiplier();
         ECMultiplier var3 = this.multiplier;
         PreCompInfo var4 = this.preCompInfo;
         var2 = var3.multiply(this, var1, var4);
      }

      return var2;
   }

   public abstract ECPoint negate();

   void setPreCompInfo(PreCompInfo var1) {
      this.preCompInfo = var1;
   }

   public abstract ECPoint subtract(ECPoint var1);

   public abstract ECPoint twice();

   public static class F2m extends ECPoint {

      public F2m(ECCurve var1) {
         super(var1, (ECFieldElement)null, (ECFieldElement)null);
      }

      public F2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
         this(var1, var2, var3, (boolean)0);
      }

      public F2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
         super(var1, var2, var3);
         if((var2 == null || var3 != null) && (var2 != null || var3 == null)) {
            if(var2 != null) {
               ECFieldElement var5 = this.x;
               ECFieldElement var6 = this.y;
               ECFieldElement.F2m.checkFieldElements(var5, var6);
               if(var1 != null) {
                  ECFieldElement var7 = this.x;
                  ECFieldElement var8 = this.curve.getA();
                  ECFieldElement.F2m.checkFieldElements(var7, var8);
               }
            }

            this.withCompression = var4;
         } else {
            throw new IllegalArgumentException("Exactly one of the field elements is null");
         }
      }

      private static void checkPoints(ECPoint var0, ECPoint var1) {
         ECCurve var2 = var0.curve;
         ECCurve var3 = var1.curve;
         if(!var2.equals(var3)) {
            throw new IllegalArgumentException("Only points on the same curve can be added or subtracted");
         }
      }

      public ECPoint add(ECPoint var1) {
         checkPoints(this, var1);
         ECPoint.F2m var2 = (ECPoint.F2m)var1;
         return this.addSimple(var2);
      }

      public ECPoint.F2m addSimple(ECPoint.F2m var1) {
         ECPoint.F2m var3;
         if(this.isInfinity()) {
            var3 = var1;
         } else if(var1.isInfinity()) {
            var3 = this;
         } else {
            ECFieldElement.F2m var4 = (ECFieldElement.F2m)var1.getX();
            ECFieldElement.F2m var5 = (ECFieldElement.F2m)var1.getY();
            if(this.x.equals(var4)) {
               if(this.y.equals(var5)) {
                  var3 = (ECPoint.F2m)this.twice();
               } else {
                  var3 = (ECPoint.F2m)this.curve.getInfinity();
               }
            } else {
               ECFieldElement var6 = this.y.add(var5);
               ECFieldElement var7 = this.x.add(var4);
               ECFieldElement.F2m var8 = (ECFieldElement.F2m)var6.divide(var7);
               ECFieldElement var9 = var8.square().add(var8);
               ECFieldElement var10 = this.x;
               ECFieldElement var11 = var9.add(var10).add(var4);
               ECFieldElement var12 = this.curve.getA();
               ECFieldElement.F2m var13 = (ECFieldElement.F2m)var11.add(var12);
               ECFieldElement var14 = this.x.add(var13);
               ECFieldElement var15 = var8.multiply(var14).add(var13);
               ECFieldElement var16 = this.y;
               ECFieldElement.F2m var17 = (ECFieldElement.F2m)var15.add(var16);
               ECCurve var18 = this.curve;
               boolean var19 = this.withCompression;
               var3 = new ECPoint.F2m(var18, var13, var17, var19);
            }
         }

         return var3;
      }

      public byte[] getEncoded() {
         byte[] var1;
         if(this.isInfinity()) {
            var1 = new byte[1];
         } else {
            X9IntegerConverter var2 = ECPoint.converter;
            ECFieldElement var3 = this.x;
            int var4 = var2.getByteLength(var3);
            X9IntegerConverter var5 = ECPoint.converter;
            BigInteger var6 = this.getX().toBigInteger();
            byte[] var7 = var5.integerToBytes(var6, var4);
            byte[] var8;
            if(this.withCompression) {
               var8 = new byte[var4 + 1];
               var8[0] = 2;
               BigInteger var9 = this.getX().toBigInteger();
               BigInteger var10 = ECConstants.ZERO;
               if(!var9.equals(var10)) {
                  ECFieldElement var11 = this.getY();
                  ECFieldElement var12 = this.getX().invert();
                  if(var11.multiply(var12).toBigInteger().testBit(0)) {
                     var8[0] = 3;
                  }
               }

               System.arraycopy(var7, 0, var8, 1, var4);
            } else {
               X9IntegerConverter var13 = ECPoint.converter;
               BigInteger var14 = this.getY().toBigInteger();
               byte[] var15 = var13.integerToBytes(var14, var4);
               var8 = new byte[var4 + var4 + 1];
               var8[0] = 4;
               System.arraycopy(var7, 0, var8, 1, var4);
               int var16 = var4 + 1;
               System.arraycopy(var15, 0, var8, var16, var4);
            }

            var1 = var8;
         }

         return var1;
      }

      public ECPoint negate() {
         ECCurve var1 = this.curve;
         ECFieldElement var2 = this.getX();
         ECFieldElement var3 = this.getY();
         ECFieldElement var4 = this.getX();
         ECFieldElement var5 = var3.add(var4);
         boolean var6 = this.withCompression;
         return new ECPoint.F2m(var1, var2, var5, var6);
      }

      public ECPoint subtract(ECPoint var1) {
         checkPoints(this, var1);
         ECPoint.F2m var2 = (ECPoint.F2m)var1;
         return this.subtractSimple(var2);
      }

      public ECPoint.F2m subtractSimple(ECPoint.F2m var1) {
         ECPoint.F2m var2;
         if(var1.isInfinity()) {
            var2 = this;
         } else {
            ECPoint.F2m var3 = (ECPoint.F2m)var1.negate();
            var2 = this.addSimple(var3);
         }

         return var2;
      }

      public ECPoint twice() {
         Object var1;
         if(this.isInfinity()) {
            var1 = this;
         } else if(this.x.toBigInteger().signum() == 0) {
            var1 = this.curve.getInfinity();
         } else {
            ECFieldElement var2 = this.x;
            ECFieldElement var3 = this.y;
            ECFieldElement var4 = this.x;
            ECFieldElement var5 = var3.divide(var4);
            ECFieldElement.F2m var6 = (ECFieldElement.F2m)var2.add(var5);
            ECFieldElement var7 = var6.square().add(var6);
            ECFieldElement var8 = this.curve.getA();
            ECFieldElement.F2m var9 = (ECFieldElement.F2m)var7.add(var8);
            ECCurve var10 = this.curve;
            BigInteger var11 = ECConstants.ONE;
            ECFieldElement var12 = var10.fromBigInteger(var11);
            ECFieldElement var13 = this.x.square();
            ECFieldElement var14 = var6.add(var12);
            ECFieldElement var15 = var9.multiply(var14);
            ECFieldElement.F2m var16 = (ECFieldElement.F2m)var13.add(var15);
            ECCurve var17 = this.curve;
            boolean var18 = this.withCompression;
            var1 = new ECPoint.F2m(var17, var9, var16, var18);
         }

         return (ECPoint)var1;
      }
   }

   public static class Fp extends ECPoint {

      public Fp(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
         this(var1, var2, var3, (boolean)0);
      }

      public Fp(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
         super(var1, var2, var3);
         if((var2 == null || var3 != null) && (var2 != null || var3 == null)) {
            this.withCompression = var4;
         } else {
            throw new IllegalArgumentException("Exactly one of the field elements is null");
         }
      }

      public ECPoint add(ECPoint var1) {
         Object var2;
         if(this.isInfinity()) {
            var2 = var1;
         } else if(var1.isInfinity()) {
            var2 = this;
         } else {
            ECFieldElement var3 = this.x;
            ECFieldElement var4 = var1.x;
            if(var3.equals(var4)) {
               ECFieldElement var5 = this.y;
               ECFieldElement var6 = var1.y;
               if(var5.equals(var6)) {
                  var2 = this.twice();
               } else {
                  var2 = this.curve.getInfinity();
               }
            } else {
               ECFieldElement var7 = var1.y;
               ECFieldElement var8 = this.y;
               ECFieldElement var9 = var7.subtract(var8);
               ECFieldElement var10 = var1.x;
               ECFieldElement var11 = this.x;
               ECFieldElement var12 = var10.subtract(var11);
               ECFieldElement var13 = var9.divide(var12);
               ECFieldElement var14 = var13.square();
               ECFieldElement var15 = this.x;
               ECFieldElement var16 = var14.subtract(var15);
               ECFieldElement var17 = var1.x;
               ECFieldElement var18 = var16.subtract(var17);
               ECFieldElement var19 = this.x.subtract(var18);
               ECFieldElement var20 = var13.multiply(var19);
               ECFieldElement var21 = this.y;
               ECFieldElement var22 = var20.subtract(var21);
               ECCurve var23 = this.curve;
               var2 = new ECPoint.Fp(var23, var18, var22);
            }
         }

         return (ECPoint)var2;
      }

      public byte[] getEncoded() {
         byte[] var1;
         if(this.isInfinity()) {
            var1 = new byte[1];
         } else {
            X9IntegerConverter var2 = ECPoint.converter;
            ECFieldElement var3 = this.x;
            int var4 = var2.getByteLength(var3);
            if(this.withCompression) {
               byte var5;
               if(this.getY().toBigInteger().testBit(0)) {
                  var5 = 3;
               } else {
                  var5 = 2;
               }

               X9IntegerConverter var6 = ECPoint.converter;
               BigInteger var7 = this.getX().toBigInteger();
               byte[] var8 = var6.integerToBytes(var7, var4);
               byte[] var9 = new byte[var8.length + 1];
               var9[0] = var5;
               int var10 = var8.length;
               System.arraycopy(var8, 0, var9, 1, var10);
               var1 = var9;
            } else {
               X9IntegerConverter var11 = ECPoint.converter;
               BigInteger var12 = this.getX().toBigInteger();
               byte[] var13 = var11.integerToBytes(var12, var4);
               X9IntegerConverter var14 = ECPoint.converter;
               BigInteger var15 = this.getY().toBigInteger();
               byte[] var16 = var14.integerToBytes(var15, var4);
               int var17 = var13.length;
               int var18 = var16.length;
               byte[] var19 = new byte[var17 + var18 + 1];
               var19[0] = 4;
               int var20 = var13.length;
               System.arraycopy(var13, 0, var19, 1, var20);
               int var21 = var13.length + 1;
               int var22 = var16.length;
               System.arraycopy(var16, 0, var19, var21, var22);
               var1 = var19;
            }
         }

         return var1;
      }

      public ECPoint negate() {
         ECCurve var1 = this.curve;
         ECFieldElement var2 = this.x;
         ECFieldElement var3 = this.y.negate();
         boolean var4 = this.withCompression;
         return new ECPoint.Fp(var1, var2, var3, var4);
      }

      public ECPoint subtract(ECPoint var1) {
         Object var2;
         if(var1.isInfinity()) {
            var2 = this;
         } else {
            ECPoint var3 = var1.negate();
            var2 = this.add(var3);
         }

         return (ECPoint)var2;
      }

      public ECPoint twice() {
         Object var1;
         if(this.isInfinity()) {
            var1 = this;
         } else if(this.y.toBigInteger().signum() == 0) {
            var1 = this.curve.getInfinity();
         } else {
            ECCurve var2 = this.curve;
            BigInteger var3 = BigInteger.valueOf(2L);
            ECFieldElement var4 = var2.fromBigInteger(var3);
            ECCurve var5 = this.curve;
            BigInteger var6 = BigInteger.valueOf(3L);
            ECFieldElement var7 = var5.fromBigInteger(var6);
            ECFieldElement var8 = this.x.square().multiply(var7);
            ECFieldElement var9 = this.curve.a;
            ECFieldElement var10 = var8.add(var9);
            ECFieldElement var11 = this.y.multiply(var4);
            ECFieldElement var12 = var10.divide(var11);
            ECFieldElement var13 = var12.square();
            ECFieldElement var14 = this.x.multiply(var4);
            ECFieldElement var15 = var13.subtract(var14);
            ECFieldElement var16 = this.x.subtract(var15);
            ECFieldElement var17 = var12.multiply(var16);
            ECFieldElement var18 = this.y;
            ECFieldElement var19 = var17.subtract(var18);
            ECCurve var20 = this.curve;
            boolean var21 = this.withCompression;
            var1 = new ECPoint.Fp(var20, var15, var19, var21);
         }

         return (ECPoint)var1;
      }
   }
}
