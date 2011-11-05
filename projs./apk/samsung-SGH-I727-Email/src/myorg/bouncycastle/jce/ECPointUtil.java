package myorg.bouncycastle.jce;

import java.math.BigInteger;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import myorg.bouncycastle.math.ec.ECCurve;

public class ECPointUtil {

   public ECPointUtil() {}

   public static ECPoint decodePoint(EllipticCurve var0, byte[] var1) {
      Object var5;
      if(var0.getField() instanceof ECFieldFp) {
         BigInteger var2 = ((ECFieldFp)var0.getField()).getP();
         BigInteger var3 = var0.getA();
         BigInteger var4 = var0.getB();
         var5 = new ECCurve.Fp(var2, var3, var4);
      } else {
         int[] var9 = ((ECFieldF2m)var0.getField()).getMidTermsOfReductionPolynomial();
         if(var9.length == 3) {
            int var10 = ((ECFieldF2m)var0.getField()).getM();
            int var11 = var9[2];
            int var12 = var9[1];
            int var13 = var9[0];
            BigInteger var14 = var0.getA();
            BigInteger var15 = var0.getB();
            var5 = new ECCurve.F2m(var10, var11, var12, var13, var14, var15);
         } else {
            int var16 = ((ECFieldF2m)var0.getField()).getM();
            int var17 = var9[0];
            BigInteger var18 = var0.getA();
            BigInteger var19 = var0.getB();
            var5 = new ECCurve.F2m(var16, var17, var18, var19);
         }
      }

      myorg.bouncycastle.math.ec.ECPoint var6 = ((ECCurve)var5).decodePoint(var1);
      BigInteger var7 = var6.getX().toBigInteger();
      BigInteger var8 = var6.getY().toBigInteger();
      return new ECPoint(var7, var8);
   }
}
