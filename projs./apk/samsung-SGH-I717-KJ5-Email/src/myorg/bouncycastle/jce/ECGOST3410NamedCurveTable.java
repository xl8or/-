package myorg.bouncycastle.jce;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECGOST3410NamedCurveTable {

   public ECGOST3410NamedCurveTable() {}

   public static Enumeration getNames() {
      return ECGOST3410NamedCurves.getNames();
   }

   public static ECNamedCurveParameterSpec getParameterSpec(String var0) {
      ECDomainParameters var1 = ECGOST3410NamedCurves.getByName(var0);
      ECNamedCurveParameterSpec var3;
      if(var1 == null) {
         ECDomainParameters var2;
         try {
            var2 = ECGOST3410NamedCurves.getByOID(new DERObjectIdentifier(var0));
         } catch (IllegalArgumentException var11) {
            var3 = null;
            return var3;
         }

         var1 = var2;
      }

      if(var1 == null) {
         var3 = null;
      } else {
         ECCurve var5 = var1.getCurve();
         ECPoint var6 = var1.getG();
         BigInteger var7 = var1.getN();
         BigInteger var8 = var1.getH();
         byte[] var9 = var1.getSeed();
         var3 = new ECNamedCurveParameterSpec(var0, var5, var6, var7, var8, var9);
      }

      return var3;
   }
}
