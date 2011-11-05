package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import myorg.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import myorg.bouncycastle.jce.spec.ECNamedCurveSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class EC5Util {

   public EC5Util() {}

   public static EllipticCurve convertCurve(ECCurve var0, byte[] var1) {
      EllipticCurve var6;
      if(var0 instanceof ECCurve.Fp) {
         BigInteger var2 = ((ECCurve.Fp)var0).getQ();
         ECFieldFp var3 = new ECFieldFp(var2);
         BigInteger var4 = var0.getA().toBigInteger();
         BigInteger var5 = var0.getB().toBigInteger();
         var6 = new EllipticCurve(var3, var4, var5, (byte[])null);
      } else {
         ECCurve.F2m var7 = (ECCurve.F2m)var0;
         if(var7.isTrinomial()) {
            int[] var8 = new int[1];
            int var9 = var7.getK1();
            var8[0] = var9;
            int var10 = var7.getM();
            ECFieldF2m var11 = new ECFieldF2m(var10, var8);
            BigInteger var12 = var0.getA().toBigInteger();
            BigInteger var13 = var0.getB().toBigInteger();
            var6 = new EllipticCurve(var11, var12, var13, (byte[])null);
         } else {
            int[] var14 = new int[3];
            int var15 = var7.getK3();
            var14[0] = var15;
            int var16 = var7.getK2();
            var14[1] = var16;
            int var17 = var7.getK1();
            var14[2] = var17;
            int var18 = var7.getM();
            ECFieldF2m var19 = new ECFieldF2m(var18, var14);
            BigInteger var20 = var0.getA().toBigInteger();
            BigInteger var21 = var0.getB().toBigInteger();
            var6 = new EllipticCurve(var19, var20, var21, (byte[])null);
         }
      }

      return var6;
   }

   public static ECCurve convertCurve(EllipticCurve var0) {
      ECField var1 = var0.getField();
      BigInteger var2 = var0.getA();
      BigInteger var3 = var0.getB();
      Object var5;
      if(var1 instanceof ECFieldFp) {
         BigInteger var4 = ((ECFieldFp)var1).getP();
         var5 = new ECCurve.Fp(var4, var2, var3);
      } else {
         ECFieldF2m var6 = (ECFieldF2m)var1;
         int var7 = var6.getM();
         int[] var8 = ECUtil.convertMidTerms(var6.getMidTermsOfReductionPolynomial());
         int var9 = var8[0];
         int var10 = var8[1];
         int var11 = var8[2];
         var5 = new ECCurve.F2m(var7, var9, var10, var11, var2, var3);
      }

      return (ECCurve)var5;
   }

   public static ECPoint convertPoint(ECParameterSpec var0, java.security.spec.ECPoint var1, boolean var2) {
      return convertPoint(convertCurve(var0.getCurve()), var1, var2);
   }

   public static ECPoint convertPoint(ECCurve var0, java.security.spec.ECPoint var1, boolean var2) {
      BigInteger var3 = var1.getAffineX();
      BigInteger var4 = var1.getAffineY();
      return var0.createPoint(var3, var4, var2);
   }

   public static ECParameterSpec convertSpec(EllipticCurve var0, myorg.bouncycastle.jce.spec.ECParameterSpec var1) {
      Object var9;
      if(var1 instanceof ECNamedCurveParameterSpec) {
         String var2 = ((ECNamedCurveParameterSpec)var1).getName();
         BigInteger var3 = var1.getG().getX().toBigInteger();
         BigInteger var4 = var1.getG().getY().toBigInteger();
         java.security.spec.ECPoint var5 = new java.security.spec.ECPoint(var3, var4);
         BigInteger var6 = var1.getN();
         BigInteger var7 = var1.getH();
         var9 = new ECNamedCurveSpec(var2, var0, var5, var6, var7);
      } else {
         BigInteger var10 = var1.getG().getX().toBigInteger();
         BigInteger var11 = var1.getG().getY().toBigInteger();
         java.security.spec.ECPoint var12 = new java.security.spec.ECPoint(var10, var11);
         BigInteger var13 = var1.getN();
         int var14 = var1.getH().intValue();
         var9 = new ECParameterSpec(var0, var12, var13, var14);
      }

      return (ECParameterSpec)var9;
   }

   public static myorg.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec var0, boolean var1) {
      ECCurve var2 = convertCurve(var0.getCurve());
      java.security.spec.ECPoint var3 = var0.getGenerator();
      ECPoint var4 = convertPoint(var2, var3, var1);
      BigInteger var5 = var0.getOrder();
      BigInteger var6 = BigInteger.valueOf((long)var0.getCofactor());
      byte[] var7 = var0.getCurve().getSeed();
      return new myorg.bouncycastle.jce.spec.ECParameterSpec(var2, var4, var5, var6, var7);
   }
}
