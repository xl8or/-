package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import myorg.bouncycastle.math.ec.ECCurve;

public class ECNamedCurveSpec extends java.security.spec.ECParameterSpec {

   private String name;


   public ECNamedCurveSpec(String var1, EllipticCurve var2, ECPoint var3, BigInteger var4) {
      super(var2, var3, var4, 1);
      this.name = var1;
   }

   public ECNamedCurveSpec(String var1, EllipticCurve var2, ECPoint var3, BigInteger var4, BigInteger var5) {
      int var6 = var5.intValue();
      super(var2, var3, var4, var6);
      this.name = var1;
   }

   public ECNamedCurveSpec(String var1, ECCurve var2, myorg.bouncycastle.math.ec.ECPoint var3, BigInteger var4) {
      EllipticCurve var5 = convertCurve(var2, (byte[])null);
      ECPoint var6 = convertPoint(var3);
      super(var5, var6, var4, 1);
      this.name = var1;
   }

   public ECNamedCurveSpec(String var1, ECCurve var2, myorg.bouncycastle.math.ec.ECPoint var3, BigInteger var4, BigInteger var5) {
      EllipticCurve var6 = convertCurve(var2, (byte[])null);
      ECPoint var7 = convertPoint(var3);
      int var8 = var5.intValue();
      super(var6, var7, var4, var8);
      this.name = var1;
   }

   public ECNamedCurveSpec(String var1, ECCurve var2, myorg.bouncycastle.math.ec.ECPoint var3, BigInteger var4, BigInteger var5, byte[] var6) {
      EllipticCurve var7 = convertCurve(var2, var6);
      ECPoint var8 = convertPoint(var3);
      int var9 = var5.intValue();
      super(var7, var8, var4, var9);
      this.name = var1;
   }

   private static EllipticCurve convertCurve(ECCurve var0, byte[] var1) {
      EllipticCurve var6;
      if(var0 instanceof ECCurve.Fp) {
         BigInteger var2 = ((ECCurve.Fp)var0).getQ();
         ECFieldFp var3 = new ECFieldFp(var2);
         BigInteger var4 = var0.getA().toBigInteger();
         BigInteger var5 = var0.getB().toBigInteger();
         var6 = new EllipticCurve(var3, var4, var5, var1);
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
            var6 = new EllipticCurve(var11, var12, var13, var1);
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
            var6 = new EllipticCurve(var19, var20, var21, var1);
         }
      }

      return var6;
   }

   private static ECPoint convertPoint(myorg.bouncycastle.math.ec.ECPoint var0) {
      BigInteger var1 = var0.getX().toBigInteger();
      BigInteger var2 = var0.getY().toBigInteger();
      return new ECPoint(var1, var2);
   }

   public String getName() {
      return this.name;
   }
}
